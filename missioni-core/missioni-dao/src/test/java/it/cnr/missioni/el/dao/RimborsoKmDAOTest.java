package it.cnr.missioni.el.dao;

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

import it.cnr.missioni.el.model.search.builder.RimborsoKmSearchBuilder;
import it.cnr.missioni.model.configuration.RimborsoKm;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class RimborsoKmDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator rimborsoKmDocIndexConfigurator;

	@Resource(name = "rimborsoKmIndexCreator")
	private GPIndexCreator rimborsoKmDocIndexCreator;

	@Resource(name = "rimborsoKmDAO")
	private IRimborsoKmDAO rimborsoKmDAO;


	@Before
	public void setUp() {
		Assert.assertNotNull(rimborsoKmDocIndexConfigurator);
		Assert.assertNotNull(rimborsoKmDocIndexCreator);
		Assert.assertNotNull(rimborsoKmDAO);
	}

	@Test
	public void A_deleteRimborsoKmTest() throws Exception {
		rimborsoKmDAO.delete("01");
	}

	@Test
	public void B_addRimborsoKmtest() throws Exception {
		
		RimborsoKm rimborsoKm = new RimborsoKm();
		rimborsoKm.setId("01");
		rimborsoKm.setValue(0.36);
		
		rimborsoKmDAO.persist(rimborsoKm);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_RIMBORSO_KM: {}\n", rimborsoKmDAO.count().intValue());
		RimborsoKmSearchBuilder rimborsoKmSearchBuilder = RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder();
		List<RimborsoKm> lista = rimborsoKmDAO.findRimborsoKmByQuery(rimborsoKmSearchBuilder).getResults();
		Assert.assertTrue("FIND RIMBORSO KM", lista.size() == 1);
	}

	@Test
	public void C_updateRimborsoKmTest() throws Exception {
		RimborsoKm rimborsoKm = new RimborsoKm();
		rimborsoKm.setId("01");
		rimborsoKm.setValue(0.40);
		Thread.sleep(1000);
		rimborsoKmDAO.update(rimborsoKm);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_RIMBORSO_KM: {}\n", rimborsoKmDAO.count().intValue());

		RimborsoKmSearchBuilder rimborsoKmSearchBuilder = RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder();
		List<RimborsoKm> lista = rimborsoKmDAO.findRimborsoKmByQuery(rimborsoKmSearchBuilder).getResults();
		Assert.assertTrue("FIND RIMBORSO KM", lista.size() == 1);
	}
	
	@Test
	public void D_addRimborsoKmtest() throws Exception {
		
		RimborsoKm rimborsoKm = new RimborsoKm();
		rimborsoKm.setId("02");
		rimborsoKm.setValue(0.36);
		rimborsoKmDAO.persist(rimborsoKm);
	}

	
	@Test
	public void E_findByIdTest() throws Exception {
		RimborsoKmSearchBuilder rimborsoKmSearchBuilder = RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder().withId("01");
		List<RimborsoKm> lista = rimborsoKmDAO.findRimborsoKmByQuery(rimborsoKmSearchBuilder).getResults();
		Assert.assertTrue("FIND RIMBORSO KM BY ID", lista.size() == 1);
		Assert.assertTrue("FIND  RIMBORSO KM BY ID", lista.get(0).getId().equals("01"));
	}

	@Test
	public void F_findByIdTest_2() throws Exception {
		RimborsoKmSearchBuilder rimborsoKmSearchBuilder = RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder().withId("02");
		List<RimborsoKm> lista = rimborsoKmDAO.findRimborsoKmByQuery(rimborsoKmSearchBuilder).getResults();
		Assert.assertTrue("FIND RIMBORSO KM BY ID", lista.isEmpty());
	}

	 @Test
	 public void tearDown() throws Exception {
	 this.rimborsoKmDocIndexCreator.deleteIndex();
	 }

}
