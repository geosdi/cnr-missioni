package it.cnr.missioni.model;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import it.cnr.missioni.model.prenotazione.StatoVeicoloEnum;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
public class VeicoloCNRTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void veicoloErrataTest() {
		
		VeicoloCNR veicoloCNR = new VeicoloCNR();
		
		Set<ConstraintViolation<VeicoloCNR>> constraintViolations = validator.validate(veicoloCNR);
		assertEquals(6, constraintViolations.size());
	}

	@Test
	public void veicoloOkTest() {
		VeicoloCNR veicoloCNR = new VeicoloCNR();
		veicoloCNR.setId("01");
		veicoloCNR.setTipo("Ford");
		veicoloCNR.setTarga("AA111BBB");
		veicoloCNR.setCartaCircolazione("carta");
		veicoloCNR.setPolizzaAssicurativa("polizza");
		veicoloCNR.setStato(StatoVeicoloEnum.DISPONIBILE);
		Set<ConstraintViolation<Veicolo>> constraintViolations = validator.validate(veicoloCNR);
		assertEquals(0, constraintViolations.size());
	}
}
