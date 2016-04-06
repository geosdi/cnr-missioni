package it.cnr.missioni.model;

import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.TipologiaSpesa.VoceSpesaEnum;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.validator.IValidatorPastoFattura;
import it.cnr.missioni.model.validator.IValidatorPastoFattura.ValidatorPastoFattura;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Salvia Vito
 */
public class ValidatorFatturaItaliaTest {


    @Test
    public void validator_1Test() throws Exception {
        DateTime dataInizio = new DateTime(2016, 1, 1, 10, 0);
        DateTime dataFine = new DateTime(2016, 1, 1, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 1, 15, 0);
        Missione missione = new Missione();
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        TipologiaSpesa t = new TipologiaSpesa();
        t.setNoCheckData(true);
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        IValidatorPastoFattura v = ValidatorPastoFattura.getValidatorPastoFattura().withDataFattura(dataFattura).withMissione(missione).withTipologiaSpesa(t);
        v.build();
        Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxNumOccurrence() == 1);
    }

    @Test
    public void validator_2Test() throws Exception {
        DateTime dataInizio = new DateTime(2016, 1, 1, 10, 0);
        DateTime dataFine = new DateTime(2016, 1, 2, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 1, 15, 0);
        Missione missione = new Missione();
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        TipologiaSpesa t = new TipologiaSpesa();
        t.setNoCheckData(true);
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        IValidatorPastoFattura v = ValidatorPastoFattura.getValidatorPastoFattura().withDataFattura(dataFattura).withMissione(missione).withTipologiaSpesa(t);
        v.build();
        Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxNumOccurrence() == 2);
    }

    @Test
    public void validator_3Test() throws Exception {
        DateTime dataInizio = new DateTime(2016, 1, 1, 10, 0);
        DateTime dataFine = new DateTime(2016, 1, 2, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 2, 15, 0);
        Missione missione = new Missione();
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        TipologiaSpesa t = new TipologiaSpesa();
        t.setNoCheckData(true);
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        IValidatorPastoFattura v = ValidatorPastoFattura.getValidatorPastoFattura().withDataFattura(dataFattura).withMissione(missione).withTipologiaSpesa(t);
        v.build();
        Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxNumOccurrence() == 2);
    }

    @Test
    public void validator_4Test() throws Exception {
        DateTime dataInizio = new DateTime(2016, 1, 1, 10, 0);
        DateTime dataFine = new DateTime(2016, 1, 3, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 2, 15, 0);
        Missione missione = new Missione();
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        TipologiaSpesa t = new TipologiaSpesa();
        t.setNoCheckData(true);
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        IValidatorPastoFattura v = ValidatorPastoFattura.getValidatorPastoFattura().withDataFattura(dataFattura).withMissione(missione).withTipologiaSpesa(t);
        v.build();
        Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxNumOccurrence() == 2);
    }

    @Test
    public void validator_5Test() throws Exception {
        DateTime dataInizio = new DateTime(2016, 1, 3, 10, 0);
        DateTime dataFine = new DateTime(2016, 1, 6, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 2, 15, 0);
        Missione missione = new Missione();
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        TipologiaSpesa t = new TipologiaSpesa();
        t.setNoCheckData(false);
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        IValidatorPastoFattura v = ValidatorPastoFattura.getValidatorPastoFattura().withDataFattura(dataFattura).withMissione(missione).withTipologiaSpesa(t);
        boolean check = v.build();
        Assert.assertTrue("FIND MAX OCCORRENZE", check == false);
    }

}
