package it.cnr.missioni.model.validator;

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.validator.IValidatorFattura.IStepValidator;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * Chain of Responsiblity per verificare il massimale di una fattura
 *
 * @author Salvia Vito
 */

public interface ICheckMassimale {

    /**
     * @param missione
     * @return
     */
    ICheckMassimale withMissione(Missione missione);

    /**
     * @param fattura
     * @return
     */
    ICheckMassimale withFattura(Fattura fattura);

    /**
     * @param massimale
     * @return
     */
    ICheckMassimale withMassimale(Massimale massimale);

    /**
     * @param massimaleEstero
     * @return
     */
    ICheckMassimale withMassimaleEstero(Massimale massimaleEstero);

    /**
     * @param map
     * @return
     */
    ICheckMassimale withMappa(Map<String, Fattura> map);

    /**
     * @throws Exception
     */
    void initSteps() throws Exception;

    /**
     * @throws Exception
     */
    ICheckMassimale initialize() throws Exception;

    public class CheckMassimale implements ICheckMassimale {

        private double totaleFattureGiornaliera = 0.0;
        private Fattura fattura;
        private Massimale massimale;
        private Massimale massimaleEstero;
        private Rimborso rimborso;
        private Map<String, Fattura> mappa;
        private Fattura otherFattura;
        private Missione missione;
        private IStepValidator step_1;
        private IStepValidator step_2;
        private IStepValidator step_4;

        private CheckMassimale() {
        }

        public static CheckMassimale getCheckMassimale() {
            return new CheckMassimale();
        }

        public ICheckMassimale initialize() throws Exception {
            buildChain();
            return self();
        }

        private void buildChain() throws Exception {
            step_1 = new Step1();
            step_2 = new Step2();
            IStepValidator step_3 = new Step3();
            IStepValidator step_5 = new Step5();
            step_4 = new Step4();
            step_2.setNextValidator(step_3);
            step_4.setNextValidator(step_5);
        }

        public void initSteps() throws Exception {
            DateTime dateTo = new DateTime(fattura.getData().getYear(), fattura.getData().getMonthOfYear(),
                    fattura.getData().getDayOfMonth(), 0, 0);
            DateTime datFrom = new DateTime(fattura.getData().getYear(), fattura.getData().getMonthOfYear(),
                    fattura.getData().getDayOfMonth(), 23, 59);
            List<Fattura> listaF = rimborso.getNumberOfFatturaInDayWithNotID(dateTo, datFrom, fattura.getIdTipologiaSpesa(),
                    fattura.getId());
            if (!listaF.isEmpty()) {
                otherFattura = listaF.get(0);
            }
            List<Fattura> lista = rimborso.getNumberOfFatturaInDayWithNotID(dateTo, datFrom, fattura.getIdTipologiaSpesa(),
                    null);
            // calcolo del totale delle fatture per giorno
            lista.forEach(ff -> {
                mappa.put(ff.getId(), ff);
                totaleFattureGiornaliera = totaleFattureGiornaliera + ff.getImporto();
            });
            fattura.setImportoSpettante(fattura.getImporto());
            step_1.check();
        }


        public ICheckMassimale withMassimale(Massimale massimale) {
            this.massimale = massimale;
            return self();
        }

        public ICheckMassimale withMassimaleEstero(Massimale massimaleEstero) {
            this.massimaleEstero = massimaleEstero;
            return self();
        }

        public ICheckMassimale withMissione(Missione missione) {
            this.missione = missione;
            this.rimborso = missione.getRimborso();
            return self();
        }

        public ICheckMassimale withFattura(Fattura fattura) {
            this.fattura = fattura;
            return self();
        }

        public ICheckMassimale withMappa(Map<String, Fattura> map) {
            this.mappa = map;
            return self();
        }

        private ICheckMassimale self() {
            return this;
        }

        class Step1 extends IStepValidator.AbstractStepValidator {

            /**
             *
             */
            @Override
            public void check() throws Exception {

                if (missione.isMissioneEstera())
                    step_2.check();
                else
                    step_4.check();
            }
        }

        //Una sola fattura
        class Step2 extends IStepValidator.AbstractStepValidator {

            /**
             *
             */
            @Override
            public void check() throws Exception {

                if (otherFattura == null && totaleFattureGiornaliera > massimaleEstero.getValue())
                    fattura.setImportoSpettante(fattura.getImporto() - (massimaleEstero.getValue()));
                else
                    this.nextValidator.check();
            }
        }

        //Due fatture
        class Step3 extends IStepValidator.AbstractStepValidator {

            /**
             *
             */
            @Override
            public void check() throws Exception {

                if (totaleFattureGiornaliera > massimaleEstero.getValue()) {

                    if (fattura.getImporto() >= otherFattura.getImporto()) {
                        fattura.setImportoSpettante(massimaleEstero.getValue());
                        otherFattura.setImportoSpettante(0.0);
                    } else {
                        otherFattura.setImportoSpettante(massimaleEstero.getValue());
                        fattura.setImportoSpettante(0.0);
                    }
                }
            }
        }

        //Una sola fattura
        class Step4 extends IStepValidator.AbstractStepValidator {

            /**
             *
             */
            @Override
            public void check() throws Exception {

                if (otherFattura == null && totaleFattureGiornaliera > massimale.getValueMezzaGiornata()) {
                    double t = massimale.getValueMezzaGiornata() != null ? massimale.getValueMezzaGiornata() : 0.0;
                    if (totaleFattureGiornaliera > t)
                        fattura.setImportoSpettante(t);
                } else
                    this.nextValidator.check();
            }
        }

        //Due fatture
        class Step5 extends IStepValidator.AbstractStepValidator {

            /**
             *
             */
            @Override
            public void check() throws Exception {

                if (totaleFattureGiornaliera > massimale.getValue()) {

                    if (fattura.getImporto() >= otherFattura.getImporto()) {
                        fattura.setImportoSpettante(massimale.getValue());
                        otherFattura.setImportoSpettante(0.0);
                    } else {
                        otherFattura.setImportoSpettante(massimale.getValue());
                        fattura.setImportoSpettante(0.0);
                    }
                }
            }
        }
    }

}
