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

import it.cnr.missioni.model.rimborso.Rimborso;

/**
 * @author Salvia Vito
 */
public class RimborsoTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void rimborsoErrataTest() {
		Rimborso rimborso = new Rimborso();
		rimborso.setAnticipazionePagamento(new Double(-3));
		Set<ConstraintViolation<Rimborso>> constraintViolations = validator.validate(rimborso);
		assertEquals(1, constraintViolations.size());
	}

	@Test
	public void missioneOkTest() {
		Rimborso rimborso = new Rimborso();
		rimborso.setAnticipazionePagamento(new Double(300));
		rimborso.setDataRimborso(new DateTime());
		Set<ConstraintViolation<Rimborso>> constraintViolations = validator.validate(rimborso);
		assertEquals(0, constraintViolations.size());
	}

}
