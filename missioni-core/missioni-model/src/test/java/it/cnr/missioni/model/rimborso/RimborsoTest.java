package it.cnr.missioni.model.rimborso;

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
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
public class RimborsoTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void rimborsoErrataTest() {
        Rimborso rimborso =createRimborso();
        rimborso.setAnticipazionePagamento(new Double(-3));
        Set<ConstraintViolation<Rimborso>> constraintViolations = validator.validate(rimborso);
        assertEquals(1, constraintViolations.size());
    }

    @Test
    public void rimborsoOkTest() {
        Rimborso rimborso = createRimborso();
        Set<ConstraintViolation<Rimborso>> constraintViolations = validator.validate(rimborso);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void massimaleEsteroTest() {
        Massimale m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setLivello(LivelloUserEnum.I);
        m.setValue(new Double(120));
        Rimborso r = new Rimborso();
        r.calcolaTotaleTAM(m, new DateTime(2016, 2, 1, 8, 0), new DateTime(2016, 2, 2, 8, 0));
        Assert.assertTrue("TAM", r.getTotaleTAM() == 120);
    }

    @Test
    public void massimaleEsteroTest_2() {
        Massimale m = new Massimale();
        m.setAreaGeografica(AreaGeograficaEnum.A);
        m.setLivello(LivelloUserEnum.I);
        m.setValue(new Double(120));
        Rimborso r = new Rimborso();
        r.calcolaTotaleTAM(m, new DateTime(2016, 2, 1, 8, 0), new DateTime(2016, 2, 2, 20, 1));
        Assert.assertTrue("TAM", r.getTotaleTAM() == 180);

    }

    @Test
    public void findFatturaGiorno() {
        Rimborso r = createRimborso();
        DateTime from = new DateTime(2015, 11, 13, 0, 0);
        DateTime to = new DateTime(2015, 11, 13, 23, 59);
        Assert.assertTrue("FIND FATTURA", r.getNumberOfFatturaInDayWithNotID(from, to, "02", null).size() == 1);
    }

    @Test
    public void findFatturaGiorno_2() {
        Rimborso r = createRimborso();
        DateTime from = new DateTime(2015, 11, 12, 0, 0);
        DateTime to = new DateTime(2015, 11, 12, 23, 59);
        Assert.assertTrue("FIND FATTURA", r.getNumberOfFatturaInDayWithNotID(from, to, "01", null).size() == 2);
    }

    private Rimborso createRimborso(){
        return new Rimborso(){
            {
                super.setAnticipazionePagamento(new Double(300));
                super.setDataRimborso(new DateTime());
                super.setMandatoPagamento("01");
                Fattura fattura = new Fattura();
                fattura.setNumeroFattura("138");
                fattura.setData(new DateTime(2015, 11, 12, 13, 0, DateTimeZone.UTC));
                fattura.setImporto(30.0);
                fattura.setValuta("Euro");
                fattura.setIdTipologiaSpesa("01");
                fattura.setShortDescriptionTipologiaSpesa("Vitto");
                fattura.setId("01");
                super.getMappaFattura().put("01", fattura);
                fattura = new Fattura();
                fattura.setNumeroFattura("138");
                fattura.setData(new DateTime(2015, 11, 12, 18, 0, DateTimeZone.UTC));
                fattura.setImporto(25.0);
                fattura.setValuta("Euro");
                fattura.setIdTipologiaSpesa("01");
                fattura.setShortDescriptionTipologiaSpesa("Vitto");
                fattura.setId("03");
                super.getMappaFattura().put("03", fattura);
                fattura = new Fattura();
                fattura.setNumeroFattura("135");
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
