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

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.ExactSearch;
import it.cnr.missioni.el.model.search.PrefixSearch;
import it.cnr.missioni.model.utente.Anagrafica;
import it.cnr.missioni.model.utente.Credenziali;
import it.cnr.missioni.model.utente.DatiCNR;
import it.cnr.missioni.model.utente.Patente;
import it.cnr.missioni.model.utente.Residenza;
import it.cnr.missioni.model.utente.RuoloUtenteEnum;
import it.cnr.missioni.model.utente.User;
import it.cnr.missioni.model.utente.Veicolo;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class UserDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator missioniDocIndexConfigurator;

	@Resource(name = "userIndexCreator")
	private GPIndexCreator userDocIndexCreator;

	@Resource(name = "userDAO")
	private IUserDAO userDAO;

	private List<User> listaUsers = new ArrayList<User>();

	BooleanModelSearch booleanModelSearch;

	private final static String FIELD_NOME = "user.anagrafica.nome";
	private final static String FIELD_COGNOME = "user.anagrafica.cognome";
	private final static String FIELD_CODICE_FISCALE = "user.anagrafica.codiceFiscale";
	private final static String FIELD_USERNAME = "user.credenziali.username";
	private final static String FIELD_MATRICOLA = "user.datiCNR.matricola";

	@Before
	public void setUp() {
		Assert.assertNotNull(userDocIndexCreator);
		Assert.assertNotNull(missioniDocIndexConfigurator);
		Assert.assertNotNull(userDAO);
	}

	@Test
	public void A_createUserCNRTest() throws Exception {
		creaUsers();
		userDAO.persist(listaUsers);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_OF_USERS: {}\n", this.userDAO.count().intValue());
	}

	@Test
	public void B_findUserByUsernameValidaTest() throws Exception {

		PrefixSearch p = new PrefixSearch();
		p.setValue("vito.salvia");
		p.setField(FIELD_USERNAME);
		booleanModelSearch = new BooleanModelSearch();
		booleanModelSearch.getListaSearch().add(p);
		List<User> lista = userDAO.findUtenteByQuery(new Page(0, 10), booleanModelSearch);
		logger.debug("############################UTENTE_WITH_USERNAME : {}\n",
				lista.get(0).getAnagrafica().getCognome() + " " + lista.get(0).getAnagrafica().getNome());
		Assert.assertTrue("FIND USER BY USERNAME VALIDA", lista.get(0) != null);
	}

	@Test
	public void C_findUserByUsernameErrataTest() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		ExactSearch p = new ExactSearch();
		p.setValue("vito.salvi");
		p.setField(FIELD_USERNAME);
		booleanModelSearch.getListaSearch().add(p);
		List<User> lista = userDAO.findUtenteByQuery(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND USER BY USERNAME ERRATA", lista.isEmpty());
	}

	@Test
	public void D_updatePasswordUser() throws Exception {

		booleanModelSearch = new BooleanModelSearch();
		PrefixSearch p = new PrefixSearch();
		p.setValue("vito.salvia");
		p.setField(FIELD_USERNAME);
		booleanModelSearch.getListaSearch().add(p);
		List<User> lista = userDAO.findUtenteByQuery(new Page(0, 10), booleanModelSearch);
		User user = lista.get(0);
		String oldPassword = user.getCredenziali().getPassword();
		user.getCredenziali().setPassword(user.getCredenziali().md5hash("salvia.vito"));
		userDAO.persist(user);
		Thread.sleep(1000);
		booleanModelSearch = new BooleanModelSearch();
		p = new PrefixSearch();
		p.setValue("vito.salvia");
		p.setField(FIELD_USERNAME);
		booleanModelSearch.getListaSearch().add(p);
		lista = userDAO.findUtenteByQuery(new Page(0, 10), booleanModelSearch);
		user = lista.get(0);
		String newPassword = user.getCredenziali().getPassword();
		Assert.assertTrue("Update Password User", !oldPassword.equals(newPassword));

	}

	@Test
	public void E_findUserByCognome() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		PrefixSearch p = new PrefixSearch();
		p.setValue("Salv");
		p.setField(FIELD_COGNOME);
		booleanModelSearch.getListaSearch().add(p);
		List<User> lista = userDAO.findUtenteByQuery(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND USER BY COGNOME", lista.size() == 1);
	}

	@Test
	public void F_findUserByNome() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		PrefixSearch p = new PrefixSearch();
		p.setValue("Salv");
		p.setField(FIELD_COGNOME);
		booleanModelSearch.getListaSearch().add(p);
		List<User> lista = userDAO.findUtenteByQuery(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND USER BY NOME", lista.size() == 1);
	}

	@Test
	public void G_findUserByCodiceFiscale() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		PrefixSearch p = new PrefixSearch();
		p.setValue("slvv");
		p.setField(FIELD_CODICE_FISCALE);
		booleanModelSearch.getListaSearch().add(p);
		List<User> lista = userDAO.findUtenteByQuery(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND USER BY CODICE FISCALE", lista.size() == 1);
	}

	@Test
	public void G_findUserALL() throws Exception {

		booleanModelSearch = new BooleanModelSearch();
		PrefixSearch p = new PrefixSearch();
		p.setValue("slvvttttttttttt");
		p.setField(FIELD_CODICE_FISCALE);
		
		PrefixSearch p2 = new PrefixSearch();
		p2.setValue("Vito");
		p2.setField(FIELD_NOME);
		
		PrefixSearch p3 = new PrefixSearch();
		p3.setValue("salvia");
		p3.setField(FIELD_COGNOME);

		booleanModelSearch.getListaSearch().add(p);
		booleanModelSearch.getListaSearch().add(p2);
		booleanModelSearch.getListaSearch().add(p3);

		List<User> lista = userDAO.findUtenteByQuery(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND USER BY ALL", lista.size() == 1);
	}

	@Test
	public void G_findUserErrataALL() throws Exception {

		booleanModelSearch = new BooleanModelSearch();
		PrefixSearch p = new PrefixSearch();
		p.setValue("slvvttttttttttt");
		p.setField(FIELD_CODICE_FISCALE);
		
		PrefixSearch p2 = new PrefixSearch();
		p2.setValue("Vito");
		p2.setField(FIELD_NOME);
		
		PrefixSearch p3 = new PrefixSearch();
		p3.setValue("salvia");
		p3.setField(FIELD_COGNOME);
		
		PrefixSearch p4 = new PrefixSearch();
		p4.setValue("4111111");
		p4.setField(FIELD_MATRICOLA);

		booleanModelSearch.getListaSearch().add(p);
		booleanModelSearch.getListaSearch().add(p2);
		booleanModelSearch.getListaSearch().add(p3);
		booleanModelSearch.getListaSearch().add(p4);

		List<User> lista = userDAO.findUtenteByQuery(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND USER BY ALL", lista.size() == 0);
	}

	@Test
	public void H_findUserByMatricola() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		PrefixSearch p = new PrefixSearch();
		p.setValue("1111111");
		p.setField(FIELD_MATRICOLA);
		booleanModelSearch.getListaSearch().add(p);
		List<User> lista = userDAO.findUtenteByQuery(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND USER BY MATRICOLA", lista.size() == 1);
	}

	@Test
	public void H_findUserByMatricolaErrata() throws Exception {
		booleanModelSearch = new BooleanModelSearch();
		PrefixSearch p = new PrefixSearch();
		p.setValue("2111111");
		p.setField(FIELD_MATRICOLA);
		booleanModelSearch.getListaSearch().add(p);		
		List<User> lista = userDAO.findUtenteByQuery(new Page(0, 10), booleanModelSearch);
		Assert.assertTrue("FIND USER BY MATRICOLA ERRATA", lista.size() == 0);
	}

//	 @Test
//	 public void tearDown() throws Exception {
//	 this.utenteDocIndexCreator.deleteIndex();
//	 }

	private void creaUsers() {
		User user = null;
		Anagrafica anagrafica = null;
		Credenziali credenziali = null;
		user = new User();
		user.setId("01");
		anagrafica = new Anagrafica();
		anagrafica.setCognome("Salvia");
		anagrafica.setNome("Vito");
		anagrafica.setDataNascita(new DateTime(1982, 7, 30, 0, 0));
		anagrafica.setCodiceFiscale("slvvttttttttttt");
		anagrafica.setLuogoNascita("Potenza");
		credenziali = new Credenziali();
		credenziali.setUsername("vito.salvia");
		credenziali.setRuoloUtente(RuoloUtenteEnum.UTENTE_SEMPLICE);
		credenziali.setPassword(credenziali.md5hash("vitosalvia"));
		user.setCredenziali(credenziali);
		user.setAnagrafica(anagrafica);
		Veicolo veicolo = new Veicolo();
		veicolo.setTipo("Ford Fiesta");
		veicolo.setTarga("AA111BB");
		veicolo.setCartaCircolazione("12234");
		veicolo.setPolizzaAssicurativa("A1B2");
		Map<String, Veicolo> mappaVeicoli = new HashMap<String, Veicolo>();
		mappaVeicoli.put(veicolo.getTarga(), veicolo);
		user.setMappaVeicolo(mappaVeicoli);
		DatiCNR datiCNR = new DatiCNR();
		datiCNR.setDatoreLavoro("Izzi");
		datiCNR.setIban("IT0000000000000000");
		datiCNR.setLivello(5);
		datiCNR.setMail("vito.salvia@gmail.com");
		datiCNR.setMatricola("1111111");
		datiCNR.setQualifica("");
		user.setDatiCNR(datiCNR);
		Patente p = new Patente();
		p.setDataRilascio(new DateTime(2001, 12, 15, 0, 0));
		p.setNumeroPatente("12334");
		p.setRilasciataDa("MCTC");
		p.setValidaFinoAl(new DateTime(2021, 12, 15, 0, 0));
		user.setPatente(p);
		Residenza r = new Residenza();
		r.setIndirizzo("Via Verdi");
		r.setComune("Tito");
		r.setDomicilioFiscale("Via Convento");
		user.setResidenza(r);
		user.setDataRegistrazione(new DateTime(2015, 1, 4, 0, 0));
		listaUsers.add(user);

		user = new User();
		user.setId("02");
		anagrafica = new Anagrafica();
		anagrafica.setCognome("Rossi");
		anagrafica.setNome("Paolo");
		credenziali = new Credenziali();
		credenziali.setUsername("paolo.rossi");
		credenziali.setPassword(credenziali.md5hash("paolorossi"));
		user.setCredenziali(credenziali);
		user.setAnagrafica(anagrafica);
		listaUsers.add(user);

		user = new User();
		user.setId("03");
		anagrafica = new Anagrafica();
		anagrafica.setCognome("Mario");
		anagrafica.setNome("Bianchi");
		credenziali = new Credenziali();
		credenziali.setUsername("mario.bianchi");
		credenziali.setPassword(credenziali.md5hash("mariobianchi"));
		user.setCredenziali(credenziali);
		user.setAnagrafica(anagrafica);
		listaUsers.add(user);

	}

}
