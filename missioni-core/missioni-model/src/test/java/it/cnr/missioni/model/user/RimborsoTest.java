package it.cnr.missioni.model.user;


import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;

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
	
	@Test
	public void massimaleEsteroTest(){
		Massimale m = new Massimale();
		m.setAreaGeografica(AreaGeograficaEnum.A);
		m.setLivello(LivelloUserEnum.I);
		m.setValue(new Double(120));
		
		Rimborso r = new Rimborso();
		r.calcolaTotaleTAM(m, new DateTime(2016,2,1,8,0), new DateTime(2016,2,2,8,0));
		Assert.assertTrue("TAM", r.getTotaleTAM() == 120);		
	}
	
	@Test
	public void massimaleEsteroTest_2(){
		Massimale m = new Massimale();
		m.setAreaGeografica(AreaGeograficaEnum.A);
		m.setLivello(LivelloUserEnum.I);
		m.setValue(new Double(120));
		
		Rimborso r = new Rimborso();
		 r.calcolaTotaleTAM(m, new DateTime(2016,2,1,8,0), new DateTime(2016,2,2,20,1));
		Assert.assertTrue("TAM", r.getTotaleTAM() == 180);	
		
	}

}
