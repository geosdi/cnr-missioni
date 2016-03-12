package it.cnr.missioni.model;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import it.cnr.missioni.model.validator.ValidatorFatturaPastoEstera;

/**
 * @author Salvia Vito
 */
public class ValidatorFatturaEsteraTest {


	
	@Test
	public void validator_1Test() throws Exception{

		DateTime dataInizio = new DateTime(2016,1,1,10,0);
		DateTime dataFine = new DateTime(2016,1,1,20,0);
		DateTime dataFattura = new DateTime(2016,1,1,10,30);
		DateTime dataFrontieraAndata = new DateTime(2016,1,1,11,0);
		DateTime dataFrontieraRitorno = new DateTime(2016,1,1,19,0);

		
		ValidatorFatturaPastoEstera v = new ValidatorFatturaPastoEstera(dataFattura,dataInizio,dataFine,dataFrontieraAndata,dataFrontieraRitorno);
		v.initialize();
		Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxOccorrenze() == 0);
	}
	
	@Test
	public void validator_2Test() throws Exception{

		DateTime dataInizio = new DateTime(2016,1,1,10,0);
		DateTime dataFine = new DateTime(2016,1,3,20,0);
		DateTime dataFattura = new DateTime(2016,1,2,10,30);
		DateTime dataFrontieraAndata = new DateTime(2016,1,1,11,0);
		DateTime dataFrontieraRitorno = new DateTime(2016,1,3,19,0);

		
		ValidatorFatturaPastoEstera v = new ValidatorFatturaPastoEstera(dataFattura,dataInizio,dataFine,dataFrontieraAndata,dataFrontieraRitorno);
		v.initialize();
		Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxOccorrenze() == 2);
	}
	
	@Test
	public void validator_3Test() throws Exception{

		DateTime dataInizio = new DateTime(2016,1,1,10,0);
		DateTime dataFine = new DateTime(2016,1,3,20,0);
		DateTime dataFattura = new DateTime(2016,1,1,10,30);
		DateTime dataFrontieraAndata = new DateTime(2016,1,1,11,0);
		DateTime dataFrontieraRitorno = new DateTime(2016,1,3,19,0);
		ValidatorFatturaPastoEstera v = new ValidatorFatturaPastoEstera(dataFattura,dataInizio,dataFine,dataFrontieraAndata,dataFrontieraRitorno);
		v.initialize();
		Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxOccorrenze() == 0);
	}
	
	@Test
	public void validator_4Test() throws Exception{

		DateTime dataInizio = new DateTime(2016,1,1,13,0);
		DateTime dataFine = new DateTime(2016,1,3,20,0);
		DateTime dataFattura = new DateTime(2016,1,1,17,30);
		DateTime dataFrontieraAndata = new DateTime(2016,1,1,19,0);
		DateTime dataFrontieraRitorno = new DateTime(2016,1,3,19,0);
		ValidatorFatturaPastoEstera v = new ValidatorFatturaPastoEstera(dataFattura,dataInizio,dataFine,dataFrontieraAndata,dataFrontieraRitorno);
		v.initialize();
		Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxOccorrenze() == 1);
	}
	
	@Test
	public void validator_5Test() throws Exception {

		DateTime dataInizio = new DateTime(2016,1,1,10,0);
		DateTime dataFine = new DateTime(2016,1,3,20,0);
		DateTime dataFattura = new DateTime(2016,1,3,19,30);
		DateTime dataFrontieraAndata = new DateTime(2016,1,1,11,0);
		DateTime dataFrontieraRitorno = new DateTime(2016,1,3,19,0);
		ValidatorFatturaPastoEstera v = new ValidatorFatturaPastoEstera(dataFattura,dataInizio,dataFine,dataFrontieraAndata,dataFrontieraRitorno);
		v.initialize();
		Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxOccorrenze() == 0);
	}
	
//	public void validator_4Test() {
//
//		DateTime dataInizio = new DateTime(2016,1,1,13,0);
//		DateTime dataFine = new DateTime(2016,1,3,20,0);
//		DateTime dataFattura = new DateTime(2016,1,1,17,30);
//		DateTime dataFrontieraAndata = new DateTime(2016,1,1,19,0);
//		DateTime dataFrontieraRitorno = new DateTime(2016,1,3,19,0);
//		ValidatorFatturaPastoEstera v = new ValidatorFatturaPastoEstera(dataFattura,dataInizio,dataFine,dataFrontieraAndata,dataFrontieraRitorno);
//		v.initialize();
//		Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxOccorrenze() == 1);
//	}

}
