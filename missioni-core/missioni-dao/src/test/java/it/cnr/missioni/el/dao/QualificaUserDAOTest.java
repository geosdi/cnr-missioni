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

import it.cnr.missioni.el.model.search.builder.QualificaUserSearchBuilder;
import it.cnr.missioni.model.configuration.QualificaUser;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class QualificaUserDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator qualificaUserDocIndexConfigurator;

	@Resource(name = "qualificaUserIndexCreator")
	private GPIndexCreator qualificaUserDocIndexCreator;

	@Resource(name = "qualificaUserDAO")
	private IQualificaUserDAO qualificaUserDAO;

	private List<QualificaUser> listaQualificaUser = new ArrayList<QualificaUser>();

	@Before
	public void setUp() {
		Assert.assertNotNull(qualificaUserDocIndexConfigurator);
		Assert.assertNotNull(qualificaUserDocIndexCreator);
		Assert.assertNotNull(qualificaUserDAO);
	}

	@Test
	public void A_createQualificaUserTest() throws Exception {
		creaQualificaUser();
		qualificaUserDAO.persist(listaQualificaUser);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_QUALIFICA_USER_CNR: {}\n", qualificaUserDAO.count().intValue());
	}

	@Test
	public void B_findQualificaTest() throws Exception {
		QualificaUserSearchBuilder qualificaSearchBuilder = QualificaUserSearchBuilder.getQualificaUserSearchBuilder();
		List<QualificaUser> lista = qualificaUserDAO.findQualificaUserByQuery(qualificaSearchBuilder).getResults();
		logger.debug("############################NUMBER_ALL_QUALIFICA_USER: {}\n", lista.size());
		Assert.assertTrue("FIND  QUALIFICA USER", lista.size() == 2);

	}

	@Test
	public void C_addQualificaUsertest() throws Exception {
		
		QualificaUser qualificaUser = new QualificaUser();
		qualificaUser.setId("03");
		qualificaUser.setValue("Tecnologo");
		
		qualificaUserDAO.persist(qualificaUser);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_QUALIFICA_USER: {}\n", qualificaUserDAO.count().intValue());
		QualificaUserSearchBuilder qualificaSearchBuilder = QualificaUserSearchBuilder.getQualificaUserSearchBuilder();
		List<QualificaUser> lista = qualificaUserDAO.findQualificaUserByQuery(qualificaSearchBuilder).getResults();
		Assert.assertTrue("FIND  QUALIFICA USER", lista.size() == 3);
	}

	@Test
	public void D_updateQualificaUserTest() throws Exception {
		QualificaUser qualificaUser = new QualificaUser();
		qualificaUser.setId("03");
		qualificaUser.setValue("CTER");
		Thread.sleep(1000);
		qualificaUserDAO.update(qualificaUser);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_QUALIFICA_USER: {}\n", qualificaUserDAO.count().intValue());

		QualificaUserSearchBuilder qualificaSearchBuilder = QualificaUserSearchBuilder.getQualificaUserSearchBuilder();
		List<QualificaUser> lista = qualificaUserDAO.findQualificaUserByQuery(qualificaSearchBuilder).getResults();
		logger.debug("############################NUMBER_ALL_QUALIFICA_USER: {}\n", lista.size());
		Assert.assertTrue("FIND  QUALIFICA USER", lista.size() == 3);
	}

	@Test
	public void E_deleteQualificaUserTest() throws Exception {
		qualificaUserDAO.delete("03");
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_QUALIFICA_USER: {}\n", qualificaUserDAO.count().intValue());

		QualificaUserSearchBuilder qualificaSearchBuilder = QualificaUserSearchBuilder.getQualificaUserSearchBuilder();
		List<QualificaUser> lista = qualificaUserDAO.findQualificaUserByQuery(qualificaSearchBuilder).getResults();
		logger.debug("############################NUMBER_ALL_QUALIFICA_USER: {}\n", lista.size());
		Assert.assertTrue("FIND  QUALIFICA USER", lista.size() == 2);
	}

	//
	//// @Test
	//// public void tearDown() throws Exception {
	//// this.utenteDocIndexCreator.deleteIndex();
	//// }

	private void creaQualificaUser() {

		QualificaUser qualificaUser = new QualificaUser();
		qualificaUser.setId("01");
		qualificaUser.setValue("Assegnista");
		listaQualificaUser.add(qualificaUser);

		qualificaUser = new QualificaUser();
		qualificaUser.setId("02");
		qualificaUser.setValue("Ricercatore");
		listaQualificaUser.add(qualificaUser);

	}

}
