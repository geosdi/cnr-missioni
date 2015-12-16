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

import it.cnr.missioni.model.utente.Anagrafica;
import it.cnr.missioni.model.utente.Credenziali;
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

	private List<Utente> listaUtenti = new ArrayList<Utente>();

	@Before
	public void setUp() {
		Assert.assertNotNull(utenteDocIndexCreator);
		Assert.assertNotNull(missioniDocIndexConfigurator);
		Assert.assertNotNull(utenteDAO);
	}

	@Test
	public void A_createUtenteCNRTest() throws Exception {
		creaUtenti();
		utenteDAO.persist(listaUtenti);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_OF_UTENTI : {}\n", this.utenteDAO.count().intValue());
	}

	@Test
	public void B_findUtenteByUsernameValidaTest() throws Exception {
		Utente utente = utenteDAO.findUtenteByUsername("vito.salvia");
		logger.debug("############################UTENTE_WITH_USERNAME : {}\n",
				utente.getAnagrafica().getCognome() + " " + utente.getAnagrafica().getNome());
		Assert.assertTrue("FIND UTENTE BY USERNAME VALIDA", utente != null);
	}
	
	@Test
	public void C_findUtenteByUsernameErrataTest() throws Exception {
		Utente utente = utenteDAO.findUtenteByUsername("vito.salvi");
		Assert.assertTrue("FIND UTENTE BY USERNAME ERRATA", utente == null);
	}

	@Test
	public void D_updatePasswordUtente() throws Exception {
		Utente utente = utenteDAO.findUtenteByUsername("vito.salvia");
		String oldPassword = utente.getCredenziali().getPassword();
		utente.getCredenziali().setPassword(utente.getCredenziali().md5hash("salvia.vito"));
		utenteDAO.persist(utente);
		Thread.sleep(1000);
		utente = utenteDAO.findUtenteByUsername("vito.salvia");
		String newPassword = utente.getCredenziali().getPassword();
		Assert.assertTrue("Update Password Utente", !oldPassword.equals(newPassword));

	}

	// @Test
	// public void C_deleteTest() throws Exception {
	// utenteDAO.removeAll();
	// logger.debug("############################NUMBER_OF_DOCS_AFTER_DELETING :
	// {}\n",
	// this.utenteDAO.count().intValue());
	// }

	private void creaUtenti() {
		Utente utente = null;
		Anagrafica anagrafica = null;
		Credenziali credenziali = null;
		utente = new Utente();
		utente.setId("01");
		anagrafica = new Anagrafica();
		anagrafica.setCognome("Salvia");
		anagrafica.setNome("Vito");
		credenziali = new Credenziali();
		credenziali.setUsername("vito.salvia");
		credenziali.setPassword(credenziali.md5hash("vitosalvia"));
		utente.setCredenziali(credenziali);
		utente.setAnagrafica(anagrafica);
		listaUtenti.add(utente);

		utente = new Utente();
		utente.setId("02");
		anagrafica = new Anagrafica();
		anagrafica.setCognome("Rossi");
		anagrafica.setNome("Paolo");
		credenziali = new Credenziali();
		credenziali.setUsername("paolo.rossi");
		credenziali.setPassword(credenziali.md5hash("paolorossi"));
		utente.setCredenziali(credenziali);
		utente.setAnagrafica(anagrafica);
		listaUtenti.add(utente);
		
		utente = new Utente();
		utente.setId("03");
		anagrafica = new Anagrafica();
		anagrafica.setCognome("Mario");
		anagrafica.setNome("Bianchi");
		credenziali = new Credenziali();
		credenziali.setUsername("mario.bianchi");
		credenziali.setPassword(credenziali.md5hash("mariobianchi"));
		utente.setCredenziali(credenziali);
		utente.setAnagrafica(anagrafica);
		listaUtenti.add(utente);
		
	}

}
