package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.Anagrafica;
import it.cnr.missioni.model.user.Credenziali;
import it.cnr.missioni.model.user.DatiCNR;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;
import it.cnr.missioni.model.user.Patente;
import it.cnr.missioni.model.user.Residenza;
import it.cnr.missioni.model.user.RuoloUserEnum;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;

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

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withUsername("vito.salvia");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		logger.debug("############################UTENTE_WITH_USERNAME : {}\n",
				lista.get(0).getAnagrafica().getCognome() + " " + lista.get(0).getAnagrafica().getNome());
		Assert.assertTrue("FIND USER BY USERNAME VALIDA", lista.get(0) != null);
	}

	@Test
	public void C_findUserByUsernameErrataTest() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withUsername("vito.salvi");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY USERNAME ERRATA", lista.isEmpty());
	}

	@Test
	public void D_updatePasswordUser() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withUsername("vito.salvia");

		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		User user = lista.get(0);
		String oldPassword = user.getCredenziali().getPassword();
		user.getCredenziali().setPassword(user.getCredenziali().md5hash("salvia.vito"));
		userDAO.update(user);
		Thread.sleep(1000);
		lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		user = lista.get(0);
		String newPassword = user.getCredenziali().getPassword();
		Assert.assertTrue("Update Password User", !oldPassword.equals(newPassword));

	}

	@Test
	public void E_findUserByCognome() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withCognome("Salv");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY COGNOME", lista.size() == 1);
	}

	@Test
	public void F_findUserByNome() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withNome("Vi");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY NOME", lista.size() == 1);
	}

	@Test
	public void G_findUserByCodiceFiscale() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withCodiceFiscale("slvv");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY CODICE FISCALE", lista.size() == 1);
	}

	@Test
	public void G_findUserALL() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
				.withCodiceFiscale("slvvtttttttttttt").withNome("Vito").withCognome("salvia").withMatricola("1111111");

		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY ALL", lista.size() == 1);
	}

	@Test
	public void G_findUserErrataALL() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
				.withCodiceFiscale("slvvttttttttttt").withNome("Vito").withCognome("salvia").withMatricola("4111111");

		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY ALL", lista.size() == 0);
	}

	@Test
	public void H_findUserByMatricola() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withSearchType("exact").withMatricola("1111111");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY MATRICOLA", lista.size() == 1);
	}

	@Test
	public void I_findUserByMatricolaErrata() throws Exception {
		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withMatricola("2111111");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY MATRICOLA ERRATA", lista.size() == 0);
	}

	@Test
	public void L_findUserByTarga() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withTarga("AA111BB");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY TARGA", lista.size() == 1);
	}

	@Test
	public void M_testUserByIDMustNot() throws Exception {
		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withNotId("01");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		// logger.debug("############################USER_ID FOUND{}\n",
		// userStore);
		Assert.assertTrue("FIND USER BY ID", lista.size() == 3);

	}

	@Test
	public void N_testUserByIDCodiceFiscale() throws Exception {
		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withNotId("01")
				.withCodiceFiscale("slvvttttttttttttt");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

	}

	@Test
	public void O_testUserByIDIban() throws Exception {
		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withNotId("01")
				.withIban("0000000000000000");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

	}

	@Test
	public void P_testUserByIDMatricola() throws Exception {
		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withNotId("01")
				.withMatricola("1111111");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

	}

	@Test
	public void P_testUserByIDMail() throws Exception {
		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withNotId("01")
				.withMail("vito.salvia@gmail.com");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

	}

	@Test
	public void Q_testUserByIDTarga() throws Exception {
		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withNotId("01")
				.withTarga("AA111BB");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

	}

	@Test
	public void R_testUserByIDPolizza() throws Exception {
		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withNotId("01")
				.withPolizzaAssicurativa("A1B2");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

	}

	@Test
	public void S_testUserByIDCartaCircolazione() throws Exception {
		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withNotId("01")
				.withCartaCircolazione("12234");
		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY ID", lista.size() == 0);

	}
	
	
	@Test
	public void T_findByMultiMatch() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
				.withMultiMatch("Salvia 1111111");
		List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL USER", lista.size() == 1);
	}

	@Test
	public void T_findByMultiMatch2() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
				.withMultiMatch("Paolo Salvia");
		List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL USER", lista.size() == 1);
	}

	@Test
	public void U_findByMail() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withMail("prova@gmail.com");

		List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL USER", lista.size() == 0);
	}
	
	@Test
	public void V_findByRespnsabileGruppo() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withResponsabileGruppo(true).withAll(true);

		List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL USER", lista.size() == 1);
	}
	
	@Test
	public void V_findByNotRespnsabileGruppo() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withResponsabileGruppo(false).withAll(true);

		List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND ALL USER", lista.size() == 3);
	}
	
	@Test
	public void V_findByIdNotPresent() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withId("10");

		List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY ID", lista.isEmpty());
	}
	
	@Test
	public void V_findById() throws Exception {

		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withId("01");

		List<User> lista = this.userDAO.findUserByQuery(userSearchBuilder).getResults();
		Assert.assertTrue("FIND USER BY ID", lista.size() == 1);
	}
	
//	@Test
//	public void E_findUserByMatricola() throws Exception {
//
//		UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withSearchType("exact").withMatricola("1111111");
//		List<User> lista = userDAO.findUserByQuery(userSearchBuilder).getResults();
//		Assert.assertTrue("FIND USER BY MATRICOLA", lista.size() == 1);
//	}
	
	//
	//// @Test
	//// public void tearDown() throws Exception {
	//// this.utenteDocIndexCreator.deleteIndex();
	//// }

	private void creaUsers() {
		User user = null;
		Anagrafica anagrafica = null;
		Credenziali credenziali = null;
		user = new User();
		user.setId("01");
		user.setResponsabileGruppo(true);
		anagrafica = new Anagrafica();
		anagrafica.setCognome("Salvia");
		anagrafica.setNome("Vito");
		anagrafica.setDataNascita(new DateTime(1982, 7, 30, 0, 0,ISOChronology.getInstanceUTC()));
		anagrafica.setCodiceFiscale("slvvtttttttttttt");
		anagrafica.setLuogoNascita("Potenza");
		credenziali = new Credenziali();
		credenziali.setUsername("vito.salvia");
		credenziali.setRuoloUtente(RuoloUserEnum.UTENTE_ADMIN);
		credenziali.setPassword(credenziali.md5hash("vitosalvia"));
		user.setCredenziali(credenziali);
		user.setAnagrafica(anagrafica);
		Veicolo veicolo = new Veicolo();
		veicolo.setTipo("Ford Fiesta");
		veicolo.setTarga("AA111BB");
		veicolo.setCartaCircolazione("12234");
		veicolo.setPolizzaAssicurativa("A1B2");
		veicolo.setVeicoloPrincipale(true);
		veicolo.setId("01");
		Map<String, Veicolo> mappaVeicoli = new HashMap<String, Veicolo>();
		mappaVeicoli.put(veicolo.getId(), veicolo);
		user.setMappaVeicolo(mappaVeicoli);
		DatiCNR datiCNR = new DatiCNR();
		datiCNR.setDatoreLavoro("02");
		datiCNR.setShortDescriptionDatoreLavoro("Rossi Paolo");
		datiCNR.setIban("IT0000000000000000");
		datiCNR.setLivello(LivelloUserEnum.V);
		datiCNR.setMail("vito.salvia@gmail.com");
		datiCNR.setMatricola("1111111");
		datiCNR.setDescrizioneQualifica("Assegnista");
		datiCNR.setIdQualifica("01");
		user.setDatiCNR(datiCNR);
		Patente p = new Patente();
		p.setDataRilascio(new DateTime(2001, 12, 15, 0, 0,ISOChronology.getInstanceUTC()));
		p.setNumeroPatente("12334");
		p.setRilasciataDa("MCTC");
		p.setValidaFinoAl(new DateTime(2021, 12, 15, 0, 0,ISOChronology.getInstanceUTC()));
		user.setPatente(p);
		Residenza r = new Residenza();
		r.setIndirizzo("Via Verdi");
		r.setComune("Tito");
		r.setDomicilioFiscale("Via Convento");
		user.setResidenza(r);
		user.setDataRegistrazione(new DateTime(2015, 1, 4, 0, 0,ISOChronology.getInstanceUTC()));
		user.setDateLastModified(new DateTime(2015, 1, 4, 0, 0,ISOChronology.getInstanceUTC()));
		user.setRegistrazioneCompletata(true);
		listaUsers.add(user);

		user = new User();
		user.setId("02");
		anagrafica = new Anagrafica();
		anagrafica.setCognome("Franco");
		anagrafica.setNome("Luigi");
		credenziali = new Credenziali();
		credenziali.setUsername("luigi.franco");
		credenziali.setPassword(credenziali.md5hash("luigifranco"));
		credenziali.setRuoloUtente(RuoloUserEnum.UTENTE_ADMIN);
		user.setCredenziali(credenziali);
		user.setAnagrafica(anagrafica);
		user.setRegistrazioneCompletata(false);
		user.setResponsabileGruppo(false);
		listaUsers.add(user);

		user = new User();
		user.setId("03");
		anagrafica = new Anagrafica();
		anagrafica.setCognome("Bianchi");
		anagrafica.setNome("Mario");
		credenziali = new Credenziali();
		credenziali.setUsername("mario.bianchi");
		credenziali.setPassword(credenziali.md5hash("mariobianchi"));
		credenziali.setRuoloUtente(RuoloUserEnum.UTENTE_SEMPLICE);
		user.setCredenziali(credenziali);
		user.setAnagrafica(anagrafica);
		user.setRegistrazioneCompletata(false);
		user.setResponsabileGruppo(false);
		listaUsers.add(user);
		
		user = new User();
		user.setId("04");
		anagrafica = new Anagrafica();
		credenziali = new Credenziali();
		credenziali.setUsername("admin");
		credenziali.setPassword(credenziali.md5hash("admin"));
		credenziali.setRuoloUtente(RuoloUserEnum.UTENTE_ADMIN);
		user.setCredenziali(credenziali);
		user.setRegistrazioneCompletata(false);
		user.setResponsabileGruppo(false);
		listaUsers.add(user);

	}

}
