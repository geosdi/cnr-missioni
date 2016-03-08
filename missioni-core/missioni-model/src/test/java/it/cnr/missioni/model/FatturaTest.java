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

import it.cnr.missioni.model.rimborso.Fattura;

/**
 * @author Salvia Vito
 */
public class FatturaTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void fatturaErrataTest() {
		Fattura fattura = new Fattura();
		Set<ConstraintViolation<Fattura>> constraintViolations = validator.validate(fattura);
		assertEquals(5, constraintViolations.size());
	}

	@Test
	public void fatturaOkTest() {
		Fattura fattura = new Fattura();
		fattura.setNumeroFattura(new Long(1));
		fattura.setData(new DateTime());
		fattura.setImporto(25.00);
		fattura.setValuta("Euro");
		fattura.setIdTipologiaSpesa("01");
		Set<ConstraintViolation<Fattura>> constraintViolations = validator.validate(fattura);
		assertEquals(0, constraintViolations.size());
	}


}
