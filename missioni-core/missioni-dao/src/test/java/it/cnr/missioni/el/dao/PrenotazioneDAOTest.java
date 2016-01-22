package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO.Page;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.model.user.Anagrafica;
import it.cnr.missioni.model.user.Credenziali;
import it.cnr.missioni.model.user.DatiCNR;
import it.cnr.missioni.model.user.Patente;
import it.cnr.missioni.model.user.Residenza;
import it.cnr.missioni.model.user.RuoloUtenteEnum;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class PrenotazioneDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator missioniDocIndexConfigurator;

	@Resource(name = "prenotazioneIndexCreator")
	private GPIndexCreator prenotazioneDocIndexCreator;

	@Resource(name = "prenotazioneDAO")
	private IPrenotazioneDAO prenotazioneDAO;

	private List<Prenotazione> listaPrenotazioni = new ArrayList<Prenotazione>();

	@Before
	public void setUp() {
		Assert.assertNotNull(missioniDocIndexConfigurator);
		Assert.assertNotNull(prenotazioneDocIndexCreator);
		Assert.assertNotNull(prenotazioneDAO);
	}

	@Test
	public void A_createPrenotazioneTest() throws Exception {
		creaPrenotazionie();
		prenotazioneDAO.persist(listaPrenotazioni);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_OF_USERS: {}\n", prenotazioneDAO.count().intValue());
	}


	
	//
	//// @Test
	//// public void tearDown() throws Exception {
	//// this.utenteDocIndexCreator.deleteIndex();
	//// }

	private void creaPrenotazionie() {
		
		Prenotazione p = new Prenotazione();
		p.setDataFrom(new DateTime(2016,1,21,8,0));
		p.setDataFrom(new DateTime(2016,1,22,18,0));
		p.setIdUser("01");
		p.setIdVeicoloCNR("01");
		listaPrenotazioni.add(p);
		
		 p = new Prenotazione();
		p.setDataFrom(new DateTime(2016,1,23,8,0));
		p.setDataFrom(new DateTime(2016,1,24,18,0));
		p.setIdUser("02");
		p.setIdVeicoloCNR("01");
		listaPrenotazioni.add(p);


	}

}
