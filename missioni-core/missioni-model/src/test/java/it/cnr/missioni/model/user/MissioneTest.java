package it.cnr.missioni.model.user;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Period;
import org.junit.BeforeClass;
import org.junit.Test;

import it.cnr.missioni.model.missione.DatiAnticipoPagamenti;
import it.cnr.missioni.model.missione.DatiMissioneEstera;
import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;

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
		missione.setDistanza(-1);
		Set<ConstraintViolation<Missione>> constraintViolations = validator.validate(missione);
		assertEquals(3, constraintViolations.size());
	}

	@Test
	public void missioneOkTest() {
		Missione missione = new Missione();
		missione.setDatiPeriodoMissione(null);
		missione.setLocalita("Roma");
		missione.setOggetto("Conferenza");
		missione.setDistanza(100);
		Set<ConstraintViolation<Missione>> constraintViolations = validator.validate(missione);
		assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void periodoMissioneErrataTest() {
		Missione missione = new Missione();
		Set<ConstraintViolation<DatiPeriodoMissione>> constraintViolations = validator.validate(missione.getDatiPeriodoMissione());
		assertEquals(2, constraintViolations.size());
	}

	@Test
	public void periodoMissioneOkTest() {
		Missione missione = new Missione();
		missione.getDatiPeriodoMissione().setInizioMissione(new DateTime());
		missione.getDatiPeriodoMissione().setFineMissione(new DateTime());
		Set<ConstraintViolation<DatiPeriodoMissione>> constraintViolations = validator.validate(missione.getDatiPeriodoMissione());
		assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void buildMissioneTest(){
		Missione missione = new Missione();
		missione.setId("M_01");
		missione.setOggetto("Conferenza");
		missione.setLocalita("Roma");
		missione.setIdUser("01");
		missione.setMissioneEstera(false);
		missione.setStato(StatoEnum.PRESA_IN_CARICO);
		missione.setFondo("fondo");
		missione.setGAE("GAE");
		missione.setDataInserimento(new DateTime(2015, 11, 13, 0, 0, DateTimeZone.UTC));
		missione.setMezzoProprio(true);
		missione.setDistanza(100.00);
		DatiAnticipoPagamenti dati = new DatiAnticipoPagamenti();
		dati.setAnticipazioniMonetarie(true);
		dati.setMandatoCNR("AA11");
		dati.setRimborsoDaTerzi(false);
		dati.setSpeseMissioniAnticipate(102.00);
		missione.setDatiAnticipoPagamenti(dati);
		DatiPeriodoMissione datiPeriodoMissione = new DatiPeriodoMissione();
		datiPeriodoMissione.setInizioMissione(new DateTime(2015, 11, 11, 0, 0, DateTimeZone.UTC));
		datiPeriodoMissione.setFineMissione(new DateTime(2015, 11, 15, 0, 0, DateTimeZone.UTC));
		missione.setDatiPeriodoMissione(datiPeriodoMissione);
		
		DatiMissioneEstera datiMissioneEstera = new DatiMissioneEstera();
		datiMissioneEstera.setAttraversamentoFrontieraAndata(new DateTime(2015, 11, 11, 0, 0, DateTimeZone.UTC));
		datiMissioneEstera.setAttraversamentoFrontieraRitorno(new DateTime(2015, 11, 15, 0, 0, DateTimeZone.UTC));
		missione.setDatiMissioneEstera(datiMissioneEstera);

		Fattura fattura = new Fattura();
		fattura.setNumeroFattura(new Long(134));
		fattura.setData(new DateTime(2015, 11, 12, 13, 0, DateTimeZone.UTC));
		fattura.setImporto(89.8);
		fattura.setTipologiaSpesa("Pernottamento");
		fattura.setValuta("Euro");
		fattura.setId("1111111111111");
		
		Fattura fattura_2 = new Fattura();
		fattura_2.setNumeroFattura(new Long(135));
		fattura_2.setData(new DateTime(2015, 11, 13, 13, 0, DateTimeZone.UTC));
		fattura_2.setImporto(89.8);
		fattura_2.setTipologiaSpesa("Pernottamento");
		fattura_2.setValuta("Euro");
		fattura_2.setId("2222222222222");

		Rimborso rimborso = new Rimborso();
		rimborso.setNumeroOrdine("56");
		rimborso.setAvvisoPagamento("Via Verdi");
		rimborso.setAnticipazionePagamento(0.0);
		rimborso.setDataRimborso(new DateTime(2015, 12, 12, 13, 14, DateTimeZone.UTC));
		rimborso.setTotale(179.6);

		rimborso.getMappaFattura().put("1111111111111",fattura);
		rimborso.getMappaFattura().put("2222222222222",fattura_2);
		missione.setRimborso(rimborso);
	}

}
