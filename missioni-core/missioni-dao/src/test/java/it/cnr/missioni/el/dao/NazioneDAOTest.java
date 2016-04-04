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

import it.cnr.missioni.el.model.search.builder.INazioneSearchBuilder;
import it.cnr.missioni.el.utility.NazioneFunction;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class NazioneDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator nazioneDocIndexConfigurator;

	@Resource(name = "nazioneIndexCreator")
	private GPIndexCreator nazioneDocIndexCreator;

	@Resource(name = "nazioneDAO")
	private INazioneDAO nazioneDAO;

	private List<Nazione> listaNazione = new ArrayList<Nazione>();

	@Before
	public void setUp() {
		Assert.assertNotNull(nazioneDocIndexConfigurator);
		Assert.assertNotNull(nazioneDocIndexCreator);
		Assert.assertNotNull(nazioneDAO);
	}

	@Test
	public void A_createNazioneTest() throws Exception {
		listaNazione = NazioneFunction.creaMassiveNazioni();
		nazioneDAO.persist(listaNazione);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_NAZIONE: {}\n", nazioneDAO.count().intValue());
	}

	@Test
	public void B_findNazioneTest() throws Exception {
		INazioneSearchBuilder nazioneSearchBuilder = INazioneSearchBuilder.NazioneSearchBuilder
				.getNazioneSearchBuilder();
		List<Nazione> lista = nazioneDAO.findNazioneByQuery(nazioneSearchBuilder).getResults();
		logger.debug("############################NUMBER_ALL_NAZIONE: {}\n", lista.size());
		Assert.assertTrue("FIND  NAZIONE", lista.size() == 2);

	}

	@Test
	public void C_addNazionetest() throws Exception {

		Nazione nazione = new Nazione();
		nazione.setId("03");
		nazione.setValue("Svizzera");
		nazione.setAreaGeografica(AreaGeograficaEnum.G);

		nazioneDAO.persist(nazione);
		Thread.sleep(1000);
		INazioneSearchBuilder nazioneSearchBuilder = INazioneSearchBuilder.NazioneSearchBuilder
				.getNazioneSearchBuilder();
		List<Nazione> lista = nazioneDAO.findNazioneByQuery(nazioneSearchBuilder).getResults();
		logger.debug("############################NUMBER_ALL_NAZIONE: {}\n", lista.size());
		Assert.assertTrue("FIND  NAZIONE", lista.size() == 3);
	}

	@Test
	public void D_updateNazioneTest() throws Exception {
		Nazione nazione = new Nazione();
		nazione.setId("03");
		nazione.setValue("Svizzera");
		nazione.setAreaGeografica(AreaGeograficaEnum.E);
		Thread.sleep(1000);
		nazioneDAO.update(nazione);
		Thread.sleep(1000);
		INazioneSearchBuilder nazioneSearchBuilder = INazioneSearchBuilder.NazioneSearchBuilder
				.getNazioneSearchBuilder();
		List<Nazione> lista = nazioneDAO.findNazioneByQuery(nazioneSearchBuilder).getResults();
		logger.debug("############################NUMBER_ALL_NAZIONE: {}\n", lista.size());
		Assert.assertTrue("FIND  NAZIONE", lista.size() == 3);
	}

	@Test
	public void E_deleteNazioneTest() throws Exception {
		nazioneDAO.delete("03");
		Thread.sleep(1000);
		INazioneSearchBuilder nazioneSearchBuilder = INazioneSearchBuilder.NazioneSearchBuilder
				.getNazioneSearchBuilder();
		List<Nazione> lista = nazioneDAO.findNazioneByQuery(nazioneSearchBuilder).getResults();
		logger.debug("############################NUMBER_ALL_NAZIONE: {}\n", lista.size());
		Assert.assertTrue("FIND  NAZIONE", lista.size() == 2);
	}

	@Test
	public void F_findByIdTest() throws Exception {
		INazioneSearchBuilder nazioneSearchBuilder = INazioneSearchBuilder.NazioneSearchBuilder
				.getNazioneSearchBuilder().withId("02");
		List<Nazione> lista = nazioneDAO.findNazioneByQuery(nazioneSearchBuilder).getResults();
		Assert.assertTrue("FIND NAZIONE BY ID", lista.size() == 1);
		Assert.assertTrue("FIND  NAZIONE", lista.get(0).getId().equals("02"));
	}

	@Test
	public void F_findByIdTest_2() throws Exception {
		INazioneSearchBuilder nazioneSearchBuilder = INazioneSearchBuilder.NazioneSearchBuilder
				.getNazioneSearchBuilder().withId("03");
		List<Nazione> lista = nazioneDAO.findNazioneByQuery(nazioneSearchBuilder).getResults();
		Assert.assertTrue("FIND NAZIONE BY ID", lista.isEmpty());
	}

//	@Test
//	public void tearDown() throws Exception {
//		this.nazioneDocIndexCreator.deleteIndex();
//	}
}
