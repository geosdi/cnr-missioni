package it.cnr.missioni.el.dao;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.utente.Anagrafica;
import it.cnr.missioni.model.utente.Utente;


/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class MissioneDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator missioniDocIndexConfigurator;
	@Resource(name = "missioneIndexCreator")
	private GPIndexCreator missioneDocIndexCreator;
	
	@Resource(name = "missioneDAO")
	private IMissioneDAO missioneDAO;
	
	private Missione missione;
	
	  @Before
	    public void setUp() {
	        Assert.assertNotNull(missioneDocIndexCreator);
	        Assert.assertNotNull(missioniDocIndexConfigurator);
	        Assert.assertNotNull(missioneDAO);
	    }
	  
	  @Test
	  public void A_createUtenteCNRTest() throws Exception{

		  
		  for(int i = 0; i<10000;i++){
			  creaMissione();
			  missioneDAO.persist(this.missione);
		  }
		  

	  }
	  
	  
	  private void creaMissione(){
		  this.missione = new Missione();
		  missione.setAltro("altreo");
	  }

}
