package it.cnr.missioni.model.user;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;

/**
 * @author Salvia Vito
 */
public class NazioneTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void fatturaErrataTest() {
		Nazione nazione = new Nazione();
		Set<ConstraintViolation<Nazione>> constraintViolations = validator.validate(nazione);
		assertEquals(2, constraintViolations.size());
	}

	@Test
	public void fatturaOkTest() {
		Nazione nazione = new Nazione();
		nazione.setValue("GERMANIA");
		nazione.setAreaGeografica(AreaGeograficaEnum.G);
		Set<ConstraintViolation<Nazione>> constraintViolations = validator.validate(nazione);
		assertEquals(0, constraintViolations.size());
	}


}
