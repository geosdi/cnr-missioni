package it.cnr.missioni.model;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import it.cnr.missioni.model.prenotazione.Prenotazione;

/**
 * @author Salvia Vito
 */
public class PrenotazioneTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void prenotazioneErrataTest(){
		Prenotazione prenotazione = new Prenotazione();
		
		Set<ConstraintViolation<Prenotazione>> constraintViolations = validator.validate(prenotazione);
		assertEquals(3, constraintViolations.size());
		
	}

	@Test
	public void prenotazioneOkTest(){
		Prenotazione prenotazione = new Prenotazione();
		prenotazione.setDataFrom(new DateTime());
		prenotazione.setDataTo(new DateTime());
		prenotazione.setIdVeicoloCNR("1");
		
		Set<ConstraintViolation<Prenotazione>> constraintViolations = validator.validate(prenotazione);
		assertEquals(0, constraintViolations.size());
		
	}

}
