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

/**
 * @author Salvia Vito
 */
public class UserTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void anagraficaErrataTest() {
		User user = new User();
		Anagrafica anagrafica = new Anagrafica();

		anagrafica.setCodiceFiscale("sl");
		DateTime now = new DateTime();
		DateTime d = now.plusDays(1);
		anagrafica.setDataNascita(d);
		user.setAnagrafica(anagrafica);

		Set<ConstraintViolation<Anagrafica>> constraintViolations = validator.validate(anagrafica);
		assertEquals(5, constraintViolations.size());
	}

	@Test
	public void anagraficaOkTest() {
		User user = new User();
		Anagrafica anagrafica = new Anagrafica();

		anagrafica.setCodiceFiscale("slvvti82l30g942l");
		anagrafica.setCognome("Salvia");
		anagrafica.setNome("Vito");
		anagrafica.setLuogoNascita("Potenza");
		DateTime now = new DateTime();
		DateTime d = now.minusDays(1);
		anagrafica.setDataNascita(d);
		user.setAnagrafica(anagrafica);

		Set<ConstraintViolation<Anagrafica>> constraintViolations = validator.validate(anagrafica);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	public void credenzialiErrataTest() {
		Credenziali credenziali = new Credenziali();
		Set<ConstraintViolation<Credenziali>> constraintViolations = validator.validate(credenziali);
		assertEquals(2, constraintViolations.size());
	}

	@Test
	public void credenzialiOkTest() {
		Credenziali credenziali = new Credenziali();
		credenziali.setPassword("prova");
		credenziali.setUsername("prova");
		Set<ConstraintViolation<Credenziali>> constraintViolations = validator.validate(credenziali);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	public void residenzaErrataTest() {
		Residenza residenza = new Residenza();
		Set<ConstraintViolation<Residenza>> constraintViolations = validator.validate(residenza);
		assertEquals(2, constraintViolations.size());
	}

	@Test
	public void residenzaOkTest() {
		Residenza residenza = new Residenza();
		residenza.setComune("Potenza");
		residenza.setIndirizzo("Via verdi");
		Set<ConstraintViolation<Residenza>> constraintViolations = validator.validate(residenza);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	public void patenteErrataTest() {
		Patente patente = new Patente();
		DateTime now = new DateTime();
		DateTime d = now.plusDays(1);
		patente.setDataRilascio(d);
		patente.setValidaFinoAl(now.minusDays(1));
		Set<ConstraintViolation<Patente>> constraintViolations = validator.validate(patente);
		assertEquals(4, constraintViolations.size());
	}

	@Test
	public void patenteOkTest() {
		Patente patente = new Patente();
		DateTime now = new DateTime();
		DateTime d = now.minusDays(1);
		patente.setDataRilascio(d);
		patente.setValidaFinoAl(now.plusDays(1));
		patente.setNumeroPatente("1234");
		patente.setRilasciataDa("MCTC");
		Set<ConstraintViolation<Patente>> constraintViolations = validator.validate(patente);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	public void datiCNRErrataTest() {
		DatiCNR datiCNR = new DatiCNR();
		datiCNR.setMail("vito.salvia");
		Set<ConstraintViolation<DatiCNR>> constraintViolations = validator.validate(datiCNR);
		assertEquals(4, constraintViolations.size());
	}

	@Test
	public void datiCNROkTest() {
		DatiCNR datiCNR = new DatiCNR();
		datiCNR.setMail("vito.salvia@gmail.com");
		datiCNR.setIban("112345");
		datiCNR.setMatricola("9876");
		datiCNR.setLivello(1);
		Set<ConstraintViolation<DatiCNR>> constraintViolations = validator.validate(datiCNR);
		assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void veicoloErrataTest() {
		
		Veicolo veicolo = new Veicolo();
		
		Set<ConstraintViolation<Veicolo>> constraintViolations = validator.validate(veicolo);
		assertEquals(4, constraintViolations.size());
	}

	@Test
	public void veicoloOkTest() {
		Veicolo veicolo = new Veicolo();
		veicolo.setTipo("Ford");
		veicolo.setTarga("AA111BBB");
		veicolo.setCartaCircolazione("carta");
		veicolo.setPolizzaAssicurativa("polizza");
		Set<ConstraintViolation<Veicolo>> constraintViolations = validator.validate(veicolo);
		assertEquals(0, constraintViolations.size());
	}

}
