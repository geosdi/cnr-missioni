package it.cnr.missioni.model.validator;

import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;
import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public interface IValidatorFattura<B> {

    B withMissione(Missione missione);

    B withDataFattura(DateTime dataFattura);

    B withFattura(Fattura fattura);

    B withTipologiaSpesa(TipologiaSpesa tiplogiaSpesa);

    String getMessage();

    boolean build() throws Exception;

    public interface IStepValidator {

        void setNextValidator(IStepValidator validator);

        void check() throws Exception;

        abstract class AbstractStepValidator implements IStepValidator {

            protected IStepValidator nextValidator;

            /**
             * @return the nextValidator
             */
            public IStepValidator getNextValidator() {
                return nextValidator;
            }

            /**
             * @param nextValidator
             */
            public void setNextValidator(IStepValidator nextValidator) {
                this.nextValidator = nextValidator;
            }
        }
    }

    abstract class AbstractValidatorFattura<B> implements IValidatorFattura<B> {

        protected Missione missione;
        protected DateTime dataFattura;
        protected Fattura fattura;
        protected TipologiaSpesa tipologiaSpesa;
        protected boolean check = true;
        protected String message;

        protected AbstractValidatorFattura() {
        }

        public B withMissione(Missione missione) {
            this.missione = missione;
            return self();
        }

        public B withDataFattura(DateTime dataFattura) {
            this.dataFattura = dataFattura;
            return self();
        }

        public B withFattura(Fattura fattura) {
            this.fattura = fattura;
            return self();
        }

        public B withTipologiaSpesa(TipologiaSpesa tipologiaSpesa) {
            this.tipologiaSpesa = tipologiaSpesa;
            return self();
        }

        protected abstract B self();

        protected abstract void initialize() throws Exception;

        public boolean build() throws Exception {
            initialize();
            return check;
        }

        /**
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        // Per le fattura dove non è possibile dare una data antecedente
        // all'inizio missione
        class InitialStep extends IStepValidator.AbstractStepValidator {

            /**
             * @throws Exception
             */
            @Override
            public void check() throws Exception {
                if (!tipologiaSpesa.isNoCheckData()
                        && dataFattura.isBefore(missione.getDatiPeriodoMissione().getInizioMissione())) {
                    check = false;
                    message = "error_date_fattura";
                } else if (nextValidator != null)
                    this.nextValidator.check();
            }
        }
    }
}
