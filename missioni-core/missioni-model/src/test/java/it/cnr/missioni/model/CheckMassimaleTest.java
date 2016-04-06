package it.cnr.missioni.model;


import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.validator.ICheckMassimale;
import it.cnr.missioni.model.validator.ICheckMassimale.CheckMassimale;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Salvia Vito
 */
public class CheckMassimaleTest {

    @Test
    public void checkMassimaleChainOfResponsibility() throws Exception {
        Rimborso r = createRimborso();
        Missione missione = new Missione();
        missione.setIdUser("01");
        missione.setMissioneEstera(false);
        missione.setRimborso(r);
        Massimale massimale = new Massimale();
        massimale.setValue(40.0);
        User user = new User();
        user.getDatiCNR().setLivello(LivelloUserEnum.IV);
        ICheckMassimale checkMassimale = CheckMassimale.getCheckMassimale().withMissione(missione).withMassimale(massimale).initialize();
        Map<String, Fattura> mappa = new HashMap<String, Fattura>();

        r.getMappaFattura().values().stream().filter(f -> !mappa.containsKey(f.getId())).forEach(f -> {
            checkMassimale.withFattura(f).withMappa(mappa);
            try {
                checkMassimale.initSteps();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mappa.put(f.getId(), f);
        });
    }

    private Rimborso createRimborso(){
        return new Rimborso(){
            {
                super.setAnticipazionePagamento(new Double(300));
                super.setDataRimborso(new DateTime());
                super.setMandatoPagamento("01");
                Fattura fattura = new Fattura();
                fattura.setNumeroFattura(new Long(138));
                fattura.setData(new DateTime(2015, 11, 12, 13, 0, DateTimeZone.UTC));
                fattura.setImporto(30.0);
                fattura.setValuta("Euro");
                fattura.setIdTipologiaSpesa("01");
                fattura.setShortDescriptionTipologiaSpesa("Vitto");
                fattura.setId("01");
                super.getMappaFattura().put("01", fattura);
                fattura = new Fattura();
                fattura.setNumeroFattura(new Long(138));
                fattura.setData(new DateTime(2015, 11, 12, 18, 0, DateTimeZone.UTC));
                fattura.setImporto(25.0);
                fattura.setValuta("Euro");
                fattura.setIdTipologiaSpesa("01");
                fattura.setShortDescriptionTipologiaSpesa("Vitto");
                fattura.setId("03");
                super.getMappaFattura().put("03", fattura);
                fattura = new Fattura();
                fattura.setNumeroFattura(new Long(135));
                fattura.setData(new DateTime(2015, 11, 13, 13, 0, DateTimeZone.UTC));
                fattura.setImporto(89.8);
                fattura.setValuta("Euro");
                fattura.setIdTipologiaSpesa("02");
                fattura.setShortDescriptionTipologiaSpesa("Albergo");
                fattura.setId("02");
                super.getMappaFattura().put("02", fattura);
            }
        };
    }

}
