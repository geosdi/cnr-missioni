package it.cnr.missioni.model.user;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;

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
		Set<ConstraintViolation<Missione>> constraintViolations = validator.validate(missione);
		assertEquals(2, constraintViolations.size());
	}

	@Test
	public void missioneOkTest() {
		Missione missione = new Missione();
		missione.setDatiPeriodoMissione(null);
		missione.setLocalita("Roma");
		missione.setOggetto("Conferenza");
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

}
