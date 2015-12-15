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
public class UtenteDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator missioniDocIndexConfigurator;
	
	@Resource(name = "utenteIndexCreator")
	private GPIndexCreator utenteDocIndexCreator;
	
	@Resource(name = "utenteDAO")
	private IUtenteDAO utenteDAO;
	
	
	private Utente utente;
	
	  @Before
	    public void setUp() {
	        Assert.assertNotNull(utenteDocIndexCreator);
	        Assert.assertNotNull(missioniDocIndexConfigurator);
	        Assert.assertNotNull(utenteDAO);
	    }
	  
	  @Test
	  public void A_createUtenteCNRTest() throws Exception{

		  for(int i = 0; i<10000;i++){
			  creaUtente();
			  utenteDAO.persist(this.utente);
		  }
		  

	  }
	  
	  @Test
	  public void B_countTest() throws Exception{
		  Thread.sleep(1000);
		  logger.debug("############################NUMBER_OF_DOCS : {}\n",
	                this.utenteDAO.count().intValue());
	  }
	  
	  @Test
	  public void C_deleteTest() throws Exception{
		  utenteDAO.removeAll();
		  logger.debug("############################NUMBER_OF_DOCS_AFTER_DELETING : {}\n",
	                this.utenteDAO.count().intValue());
	  }
	  
	  
	  private void creaUtente(){
		  this.utente = new Utente();
		  Anagrafica anagrafica = new Anagrafica();
		  anagrafica.setCognome("Salvia");
		  anagrafica.setNome("Vito");
		  utente.setAnagrafica(anagrafica);
	  }

}
