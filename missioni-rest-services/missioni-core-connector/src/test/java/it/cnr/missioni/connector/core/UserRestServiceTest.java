/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2014 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. This program is distributed in the 
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. You should have received a copy of the GNU General 
 * Public License along with this program. If not, see http://www.gnu.org/licenses/ 
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is 
 * making a combined work based on this library. Thus, the terms and 
 * conditions of the GNU General Public License cover the whole combination. 
 * 
 * As a special exception, the copyright holders of this library give you permission 
 * to link this library with independent modules to produce an executable, regardless 
 * of the license terms of these independent modules, and to copy and distribute 
 * the resulting executable under terms of your choice, provided that you also meet, 
 * for each linked independent module, the terms and conditions of the license of 
 * that module. An independent module is a module which is not derived from or 
 * based on this library. If you modify this library, you may extend this exception 
 * to your version of the library, but you are not obligated to do so. If you do not 
 * wish to do so, delete this exception statement from your version. 
 *
 */
package it.cnr.missioni.connector.core;

import it.cnr.missioni.connector.core.spring.connector.MissioniCoreClientConnector;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.Anagrafica;
import it.cnr.missioni.model.user.Credenziali;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 
 * @author Salvia Vito
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class UserRestServiceTest {

	static final String CORE_CONNECTOR_KEY = "MISSIONI_CORE_FILE_PROP";
	//
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "missioniCoreClientConnector")
	protected MissioniCoreClientConnector missioniCoreClientConnector;

	@BeforeClass
	public static void beforeClass() {
		System.setProperty(CORE_CONNECTOR_KEY, "missioni-core-test.prop");
	}

	@Before
	public void setUp() throws Exception {
		logger.debug("\n\n\t@@@@@@@ {}.setUp @@@@@@\n\n", this.getClass().getSimpleName());

	}

	@AfterClass
	public static void afterClass() {
		System.clearProperty(CORE_CONNECTOR_KEY);
	}

//	@Test
//	public void A_createTest() throws Exception {
//		List<User> lista = UserFunction.creaMassiveUsers();
//		lista.stream().forEach(m->{
//			try {
//				missioniCoreClientConnector.addUser(m);
//			} catch (Exception e) {
//			}
//		});
//		Thread.sleep(1000);
//	}
	
	@Test
	public void A_testFindUserByUsername() throws Exception {
		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withUsername("vito.salvia");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		Assert.assertNotNull(userStore);
		logger.debug("############################USER_ID FOUND{}\n", userStore);
	}

	@Test
	public void B_testFindUserByUsernameErrata() throws Exception {
		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withUsername("vito.salvi");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		logger.debug("############################USER_ID FOUND{}\n", userStore);
		Assert.assertTrue(userStore.getTotale() == 0);
	}

	@Test
	public void C_testInsertUser() throws Exception {
		User user = new User();
		user.setId("05");
		Anagrafica anagrafica = new Anagrafica();
		anagrafica.setCognome("Gialli");
		anagrafica.setNome("Giuseppe");
		Credenziali credenziali = new Credenziali();
		credenziali.setUsername("giuseppe.Gialli");
		credenziali.setPassword(credenziali.md5hash("giuseppegialli"));
		user.setCredenziali(credenziali);
		user.setAnagrafica(anagrafica);
		user.setRegistrazioneCompletata(false);
		user.setResponsabileGruppo(false);
		missioniCoreClientConnector.addUser(user);
		Thread.sleep(1000);
		logger.debug("############################INSERT USER\n");
	}

	@Test
	public void D_testUpdateUser() throws Exception {
		User user = new User();
		user.setId("05");
		user.getAnagrafica().setNome("Marco");
		missioniCoreClientConnector.updateUser(user);
		logger.debug("############################UPDATE USER\n");
	}

	@Test
	public void E_testDeleteUser() throws Exception {
		missioniCoreClientConnector.deleteUser("05");
		logger.debug("############################DELETE USER\n");

	}

	@Test
	public void F_findUserByCognome() throws Exception {
		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withCognome("Salv");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		Assert.assertTrue(userStore.getTotale() == 1);
	}

	@Test
	public void G_findUserByNome() throws Exception {
		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withNome("Vi");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		logger.debug("############################USERS_SIZE: {}\n", userStore.getUsers().size());
		Assert.assertTrue(userStore.getTotale() == 1);
	}

	@Test
	public void H_findUserByCodiceFiscale() throws Exception {
		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withNome("Vito")
				.withCognome("salvia").withMatricola("1111111").withCodiceFiscale("slvvtttttttttttt");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		logger.debug("############################USERS_SIZE: {}\n", userStore.getUsers().size());
		Assert.assertTrue(userStore.getTotale() == 1);
	}

	@Test
	public void I_findUserALL() throws Exception {

		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withCodiceFiscale("slvv");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		logger.debug("############################USERS_SIZE: {}\n", userStore.getUsers().size());
		Assert.assertTrue("FIND USER BY ALL", userStore.getUsers().size() == 1);
	}

	@Test
	public void L_findUserErrataALL() throws Exception {

		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withNome("Vito")
				.withCognome("salvia").withMatricola("4111111").withCodiceFiscale("slvvttttttttttttt");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		Assert.assertTrue("FIND USER BY ALL", userStore.getTotale() == 0);
	}

	@Test
	public void M_findUserByMatricola() throws Exception {
		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withSearchType("exact").withMatricola("1111111");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		Assert.assertTrue("FIND USER BY MATRICOLA", userStore.getUsers().size() == 1);
	}

	@Test
	public void N_findUserByMatricolaErrata() throws Exception {
		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withMatricola("1111111q");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		Assert.assertTrue("FIND USER BY MATRICOLA ERRATA", userStore.getTotale() == 0);
	}

	@Test
	public void O_testFindByTarga() throws Exception {

		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withTarga("AA111BB");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		logger.debug("############################USER_ID FOUND{}\n", userStore);
		Assert.assertTrue(userStore.getTotale() == 1);
	}

	@Test
	public void P_testFindByTargaErrata() throws Exception {

		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withTarga("AA111CC");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		logger.debug("############################USER_ID FOUND{}\n", userStore);
		Assert.assertTrue(userStore.getTotale() == 0);

	}

	@Test
	public void Q_testUserByIDMustNot() throws Exception {
		Thread.sleep(1000);
		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withNotId("01");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		logger.debug("############################USER_ID MUST NOT{}\n", userStore.getUsers().size());
		Assert.assertTrue("FIND USER BY ID", userStore.getUsers().size() == 3);
	}

	@Test
	public void R_testUserByCodiceFiscale() throws Exception {

		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withNotId("01")
				.withCodiceFiscale("slvvtttttttttttt");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		logger.debug("############################USER_ID FOUND{}\n", userStore);
		Assert.assertTrue("FIND USER BY ID", userStore.getTotale() == 0);
	}

	@Test
	public void S_findByRespnsabileGruppo() throws Exception {

		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withResponsabileGruppo(true)
				.withAll(true);

		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		Assert.assertTrue("FIND NO RESPONSABILI GRUPPO", userStore.getUsers().size() == 1);
	}

	@Test
	public void T_findByNotRespnsabileGruppo() throws Exception {

		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withResponsabileGruppo(false)
				.withAll(true);
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		Assert.assertTrue("FIND NO RESPONSABILI GRUPPO", userStore.getUsers().size() ==3);
	}
	
	@Test
	public void U_findByIdNotPresent() throws Exception {

		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withId("10");

		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		Assert.assertTrue("FIND USER BY ID", userStore.getTotale() == 0);
	}
	
	@Test
	public void U__findById() throws Exception {

		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withId("01");

		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		Assert.assertTrue("FIND USER BY ID", userStore.getUsers().size() == 1);
	}
	
	@Test
	public void V_findByMultiMatch() throws Exception {

		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
				.withMultiMatch("Salvia 1111111");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		Assert.assertTrue("FIND ALL USER", userStore.getUsers().size() == 1);
	}

	@Test
	public void Z_findByMultiMatch2() throws Exception {

		IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
				.withMultiMatch("Paolo Salvia");
		UserStore userStore = missioniCoreClientConnector.getUserByQuery(userSearchBuilder);
		Assert.assertTrue("FIND ALL USER", userStore.getUsers().size() == 1);
	}
}
