package it.cnr.missioni.model;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import it.cnr.missioni.model.configuration.QualificaUser;

/**
 * @author Salvia Vito
 */
public class QualificaUserTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void fatturaErrataTest() {
		QualificaUser qualificaUser = new QualificaUser();
		Set<ConstraintViolation<QualificaUser>> constraintViolations = validator.validate(qualificaUser);
		assertEquals(1, constraintViolations.size());
	}

	@Test
	public void fatturaOkTest() {
		QualificaUser qualificaUser = new QualificaUser();
		qualificaUser.setValue("Ricercatore");
		Set<ConstraintViolation<QualificaUser>> constraintViolations = validator.validate(qualificaUser);
		assertEquals(0, constraintViolations.size());
	}


}
