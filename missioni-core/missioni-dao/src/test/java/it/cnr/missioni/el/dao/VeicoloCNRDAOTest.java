package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.List;

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

import it.cnr.missioni.model.prenotazione.VeicoloCNR;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class VeicoloCNRDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator missioniDocIndexConfigurator;

	@Resource(name = "veicoloCNRIndexCreator")
	private GPIndexCreator veicoloCNRDocIndexCreator;

	@Resource(name = "veicoloCNRDAO")
	private IVeicoloCNRDAO veicoloCNRDAO;

	private List<VeicoloCNR> listaVeicoliCNR = new ArrayList<VeicoloCNR>();

	@Before
	public void setUp() {
		Assert.assertNotNull(missioniDocIndexConfigurator);
		Assert.assertNotNull(veicoloCNRDocIndexCreator);
		Assert.assertNotNull(veicoloCNRDAO);
	}

	@Test
	public void A_createVeicoloCNRCNRTest() throws Exception {
		creaPrenotazionie();
		veicoloCNRDAO.persist(listaVeicoliCNR);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_OF_USERS: {}\n", veicoloCNRDAO.count().intValue());
	}


	
	//
	//// @Test
	//// public void tearDown() throws Exception {
	//// this.utenteDocIndexCreator.deleteIndex();
	//// }

	private void creaPrenotazionie() {
		
		VeicoloCNR veicoloCNR = new VeicoloCNR();
		veicoloCNR.setId("01");
		veicoloCNR.setCartaCircolazione("Carta 123");
		veicoloCNR.setPolizzaAssicurativa("polizza 1");
		veicoloCNR.setTipo("Citroen");
		veicoloCNR.setTarga("56654");
		listaVeicoliCNR.add(veicoloCNR);
		

		veicoloCNR = new VeicoloCNR();
		veicoloCNR.setId("02");
		veicoloCNR.setCartaCircolazione("Carta 456");
		veicoloCNR.setPolizzaAssicurativa("polizza 2");
		veicoloCNR.setTipo("Peugeout");
		veicoloCNR.setTarga("6575");
		listaVeicoliCNR.add(veicoloCNR);


	}

}
