package it.cnr.missioni.model;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.TipologiaSpesa.TipoSpesaEnum;
import it.cnr.missioni.model.configuration.TipologiaSpesa.VoceSpesaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;

/**
 * @author Salvia Vito
 */
public class TipologiaSpesaTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void fatturaErrataTest() {
		TipologiaSpesa tipologiaSpesa = new TipologiaSpesa();
		Set<ConstraintViolation<TipologiaSpesa>> constraintViolations = validator.validate(tipologiaSpesa);
		assertEquals(4, constraintViolations.size());
	}

	@Test
	public void fatturaOkTest() {
		TipologiaSpesa tipologiaSpesa = new TipologiaSpesa();
		tipologiaSpesa.setValue("VITTO");
		tipologiaSpesa.setVoceSpesa(VoceSpesaEnum.PASTO);
		tipologiaSpesa.setTipo(TipoSpesaEnum.ESTERA);
		tipologiaSpesa.setTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO);
		Set<ConstraintViolation<TipologiaSpesa>> constraintViolations = validator.validate(tipologiaSpesa);
		assertEquals(0, constraintViolations.size());
	}


}
