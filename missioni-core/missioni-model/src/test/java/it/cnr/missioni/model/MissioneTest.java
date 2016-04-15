package it.cnr.missioni.model;

import it.cnr.missioni.model.missione.DatiMissioneEstera;
import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Salvia Vito
 */
public class MissioneTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void missioneErrataTest() {
        Missione missione = new Missione();
        missione.setDatiPeriodoMissione(null);
        missione.setDistanza(null);
        Set<ConstraintViolation<Missione>> constraintViolations = validator.validate(missione);
        assertEquals(5, constraintViolations.size());
    }

    @Test
    public void missioneOkTest() {
        Missione missione = createMissione();
        Set<ConstraintViolation<Missione>> constraintViolations = validator.validate(missione);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void periodoMissioneErrataTest() {
        Missione missione = createMissione();
        missione.setDatiPeriodoMissione(new DatiPeriodoMissione());
        Set<ConstraintViolation<DatiPeriodoMissione>> constraintViolations = validator.validate(missione.getDatiPeriodoMissione());
        assertEquals(3, constraintViolations.size());
    }

    private Missione createMissione(){
        return new Missione(){
            {
                super.setId("M_01");
                super.setOggetto("Conferenza");
                super.setLocalita("Roma");
                super.setIdUser("01");
                super.setMissioneEstera(false);
                super.setStato(StatoEnum.PRESA_IN_CARICO);
                super.setFondo("fondo");
                super.setGAE("GAE");
                super.setDataInserimento(new DateTime(2015, 11, 13, 0, 0, DateTimeZone.UTC));
                super.setMezzoProprio(true);
                super.setDistanza("100 km");
                DatiPeriodoMissione datiPeriodoMissione = new DatiPeriodoMissione();
                datiPeriodoMissione.setInizioMissione(new DateTime(2015, 11, 11, 0, 0, DateTimeZone.UTC));
                datiPeriodoMissione.setFineMissione(new DateTime(2015, 11, 15, 0, 0, DateTimeZone.UTC));
                super.setDatiPeriodoMissione(datiPeriodoMissione);
                DatiMissioneEstera datiMissioneEstera = new DatiMissioneEstera();
                datiMissioneEstera.setAttraversamentoFrontieraAndata(new DateTime(2015, 11, 11, 0, 0, DateTimeZone.UTC));
                datiMissioneEstera.setAttraversamentoFrontieraRitorno(new DateTime(2015, 11, 15, 0, 0, DateTimeZone.UTC));
                super.setDatiMissioneEstera(datiMissioneEstera);
                Fattura fattura = new Fattura();
                fattura.setNumeroFattura("134");
                fattura.setData(new DateTime(2015, 11, 12, 13, 0, DateTimeZone.UTC));
                fattura.setImporto(89.8);
                fattura.setValuta("Euro");
                fattura.setId("1111111111111");
                Fattura fattura_2 = new Fattura();
                fattura_2.setNumeroFattura("135");
                fattura_2.setData(new DateTime(2015, 11, 13, 13, 0, DateTimeZone.UTC));
                fattura_2.setImporto(89.8);
                fattura_2.setValuta("Euro");
                fattura_2.setId("2222222222222");
                Rimborso rimborso = new Rimborso();
                rimborso.setNumeroOrdine(new Long(1));
                rimborso.setAvvisoPagamento("Via Verdi");
                rimborso.setAnticipazionePagamento(0.0);
                rimborso.setDataRimborso(new DateTime(2015, 12, 12, 13, 14, DateTimeZone.UTC));
                rimborso.setTotale(179.6);
                rimborso.getMappaFattura().put("1111111111111", fattura);
                rimborso.getMappaFattura().put("2222222222222", fattura_2);
                super.setRimborso(rimborso);
            }
        };
    }

}
