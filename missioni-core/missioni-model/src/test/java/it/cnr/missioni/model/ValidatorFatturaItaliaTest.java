package it.cnr.missioni.model;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import it.cnr.missioni.model.validator.ValidatorFatturaPastoItalia;

/**
 * @author Salvia Vito
 */
public class ValidatorFatturaItaliaTest {


	
	@Test
	public void validator_1Test() throws Exception {

		DateTime dataInizio = new DateTime(2016,1,1,10,0);
		DateTime dataFine = new DateTime(2016,1,1,20,0);
		DateTime dataFattura = new DateTime(2016,1,1,15,0);
		
		ValidatorFatturaPastoItalia v = new ValidatorFatturaPastoItalia(dataFattura,dataInizio,dataFine);
		v.initialize();
		Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxOccorrenze() == 1);
	}
	
	@Test
	public void validator_2Test() throws Exception {

		DateTime dataInizio = new DateTime(2016,1,1,10,0);
		DateTime dataFine = new DateTime(2016,1,2,20,0);
		DateTime dataFattura = new DateTime(2016,1,1,15,0);
		
		ValidatorFatturaPastoItalia v = new ValidatorFatturaPastoItalia(dataFattura,dataInizio,dataFine);
		v.initialize();
		Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxOccorrenze() == 1);
	}
	
	@Test
	public void validator_3Test() throws Exception{

		DateTime dataInizio = new DateTime(2016,1,1,10,0);
		DateTime dataFine = new DateTime(2016,1,2,20,0);
		DateTime dataFattura = new DateTime(2016,1,2,15,0);
		
		ValidatorFatturaPastoItalia v = new ValidatorFatturaPastoItalia(dataFattura,dataInizio,dataFine);
		v.initialize();
		Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxOccorrenze() == 2);

	}
	
	@Test
	public void validator_4Test() throws Exception {

		DateTime dataInizio = new DateTime(2016,1,1,10,0);
		DateTime dataFine = new DateTime(2016,1,3,20,0);
		DateTime dataFattura = new DateTime(2016,1,2,15,0);
		
		ValidatorFatturaPastoItalia v = new ValidatorFatturaPastoItalia(dataFattura,dataInizio,dataFine);
		v.initialize();
		Assert.assertTrue("FIND MAX OCCORRENZE", v.getMaxOccorrenze() == 2);

	}

}
