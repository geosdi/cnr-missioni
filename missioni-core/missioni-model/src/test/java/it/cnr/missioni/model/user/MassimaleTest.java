package it.cnr.missioni.model.user;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.configuration.QualificaUser;

/**
 * @author Salvia Vito
 */
public class MassimaleTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void fatturaErrataTest() {
		Massimale massimale = new Massimale();
		Set<ConstraintViolation<Massimale>> constraintViolations = validator.validate(massimale);
		assertEquals(3, constraintViolations.size());
	}

	@Test
	public void fatturaOkTest() {
		Massimale massimale = new Massimale();
		massimale.setValue(new Double(60));
		massimale.setAreaGeografica(AreaGeograficaEnum.A);
		Set<ConstraintViolation<Massimale>> constraintViolations = validator.validate(massimale);
		assertEquals(3, constraintViolations.size());
	}


}
