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
public class ValidatorFatturaEsteraTest {


    @Test
    public void validator_1Test() throws Exception {
        DateTime dataInizio = new DateTime(2016, 1, 1, 10, 0);
        DateTime dataFine = new DateTime(2016, 1, 1, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 1, 10, 30);
        DateTime dataFrontieraAndata = new DateTime(2016, 1, 1, 11, 0);
        DateTime dataFrontieraRitorno = new DateTime(2016, 1, 1, 19, 0);
        Missione missione = new Missione();
        missione.setMissioneEstera(true);
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraAndata(dataFrontieraAndata);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraRitorno(dataFrontieraRitorno);
        TipologiaSpesa t = new TipologiaSpesa();
        t.setNoCheckData(true);
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        IValidatorPastoFattura v = ValidatorPastoFattura.getValidatorPastoFattura().withDataFattura(dataFattura).withMissione(missione).withTipologiaSpesa(t);
        v.build();
        Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxNumOccurrence() == 0);
    }

    @Test
    public void validator_2Test() throws Exception {
        DateTime dataInizio = new DateTime(2016, 1, 1, 10, 0);
        DateTime dataFine = new DateTime(2016, 1, 3, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 2, 10, 30);
        DateTime dataFrontieraAndata = new DateTime(2016, 1, 1, 11, 0);
        DateTime dataFrontieraRitorno = new DateTime(2016, 1, 3, 19, 0);
        Missione missione = new Missione();
        missione.setMissioneEstera(true);
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraAndata(dataFrontieraAndata);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraRitorno(dataFrontieraRitorno);
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
        DateTime dataFine = new DateTime(2016, 1, 3, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 1, 10, 30);
        DateTime dataFrontieraAndata = new DateTime(2016, 1, 1, 11, 0);
        DateTime dataFrontieraRitorno = new DateTime(2016, 1, 3, 19, 0);
        Missione missione = new Missione();
        missione.setMissioneEstera(true);
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraAndata(dataFrontieraAndata);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraRitorno(dataFrontieraRitorno);
        TipologiaSpesa t = new TipologiaSpesa();
        t.setNoCheckData(true);
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        IValidatorPastoFattura v = ValidatorPastoFattura.getValidatorPastoFattura().withDataFattura(dataFattura).withMissione(missione).withTipologiaSpesa(t);
        v.build();
        Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxNumOccurrence() == 0);
    }

    @Test
    public void validator_4Test() throws Exception {
        DateTime dataInizio = new DateTime(2016, 1, 1, 13, 0);
        DateTime dataFine = new DateTime(2016, 1, 3, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 1, 17, 30);
        DateTime dataFrontieraAndata = new DateTime(2016, 1, 1, 19, 0);
        DateTime dataFrontieraRitorno = new DateTime(2016, 1, 3, 19, 0);
        Missione missione = new Missione();
        missione.setMissioneEstera(true);
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraAndata(dataFrontieraAndata);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraRitorno(dataFrontieraRitorno);
        TipologiaSpesa t = new TipologiaSpesa();
        t.setNoCheckData(true);
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        IValidatorPastoFattura v = ValidatorPastoFattura.getValidatorPastoFattura().withDataFattura(dataFattura).withMissione(missione).withTipologiaSpesa(t);
        v.build();
        Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxNumOccurrence() == 1);
    }

    @Test
    public void validator_5Test() throws Exception {
        DateTime dataInizio = new DateTime(2016, 1, 1, 10, 0);
        DateTime dataFine = new DateTime(2016, 1, 3, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 3, 19, 30);
        DateTime dataFrontieraAndata = new DateTime(2016, 1, 1, 11, 0);
        DateTime dataFrontieraRitorno = new DateTime(2016, 1, 3, 19, 0);
        Missione missione = new Missione();
        missione.setMissioneEstera(true);
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraAndata(dataFrontieraAndata);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraRitorno(dataFrontieraRitorno);
        TipologiaSpesa t = new TipologiaSpesa();
        t.setNoCheckData(true);
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        IValidatorPastoFattura v = ValidatorPastoFattura.getValidatorPastoFattura().withDataFattura(dataFattura).withMissione(missione).withTipologiaSpesa(t);
        v.build();
        Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxNumOccurrence() == 0);
    }

    @Test
    public void validator_6Test() throws Exception {
        DateTime dataInizio = new DateTime(2016, 1, 1, 13, 0);
        DateTime dataFine = new DateTime(2016, 1, 20, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 20, 19, 30);
        DateTime dataFrontieraAndata = new DateTime(2016, 1, 1, 19, 0);
        DateTime dataFrontieraRitorno = new DateTime(2016, 1, 4, 19, 0);
        Missione missione = new Missione();
        missione.setMissioneEstera(true);
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraAndata(dataFrontieraAndata);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraRitorno(dataFrontieraRitorno);
        TipologiaSpesa t = new TipologiaSpesa();
        t.setNoCheckData(true);
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        IValidatorPastoFattura v = ValidatorPastoFattura.getValidatorPastoFattura().withDataFattura(dataFattura).withMissione(missione).withTipologiaSpesa(t);
        v.build();
        Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxNumOccurrence() == 2);
    }

    @Test
    public void validator_7Test() throws Exception {
        DateTime dataInizio = new DateTime(2016, 1, 31, 13, 0);
        DateTime dataFine = new DateTime(2016, 2, 4, 20, 0);
        DateTime dataFattura = new DateTime(2016, 1, 31, 19, 30);
        DateTime dataFrontieraAndata = new DateTime(2016, 2, 1, 19, 0);
        DateTime dataFrontieraRitorno = new DateTime(2016, 2, 4, 19, 0);
        Missione missione = new Missione();
        missione.setMissioneEstera(true);
        missione.getDatiPeriodoMissione().setInizioMissione(dataInizio);
        missione.getDatiPeriodoMissione().setFineMissione(dataFine);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraAndata(dataFrontieraAndata);
        missione.getDatiMissioneEstera().setAttraversamentoFrontieraRitorno(dataFrontieraRitorno);
        TipologiaSpesa t = new TipologiaSpesa();
        t.setNoCheckData(true);
        t.setVoceSpesa(VoceSpesaEnum.PASTO);
        IValidatorPastoFattura v = ValidatorPastoFattura.getValidatorPastoFattura().withDataFattura(dataFattura).withMissione(missione).withTipologiaSpesa(t);
        v.build();
        Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxNumOccurrence() == 1);
    }

}
