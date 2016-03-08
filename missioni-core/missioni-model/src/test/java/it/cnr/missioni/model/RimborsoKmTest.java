package it.cnr.missioni.model;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import it.cnr.missioni.model.configuration.RimborsoKm;

/**
 * @author Salvia Vito
 */
public class RimborsoKmTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void fatturaErrataTest() {
		RimborsoKm rimborsoKm = new RimborsoKm();
		Set<ConstraintViolation<RimborsoKm>> constraintViolations = validator.validate(rimborsoKm);
		assertEquals(1, constraintViolations.size());
	}

	@Test
	public void fatturaOkTest() {
		RimborsoKm rimborsoKm = new RimborsoKm();
		rimborsoKm.setValue(0.36);
		Set<ConstraintViolation<RimborsoKm>> constraintViolations = validator.validate(rimborsoKm);
		assertEquals(0, constraintViolations.size());
	}


}
