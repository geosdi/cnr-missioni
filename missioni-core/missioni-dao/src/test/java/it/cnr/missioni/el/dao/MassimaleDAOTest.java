package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
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

import it.cnr.missioni.el.model.search.builder.IMassimaleSearchBuilder;
import it.cnr.missioni.el.utility.MassimaleFunction;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.DatiCNR.LivelloUserEnum;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class MassimaleDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator massimaleDocIndexConfigurator;

	@Resource(name = "massimaleIndexCreator")
	private GPIndexCreator massimaleDocIndexCreator;

	@Resource(name = "massimaleDAO")
	private IMassimaleDAO massimaleDAO;

	private List<Massimale> listaMassimale = new ArrayList<Massimale>();

	@Before
	public void setUp() {
		Assert.assertNotNull(massimaleDocIndexConfigurator);
		Assert.assertNotNull(massimaleDocIndexCreator);
		Assert.assertNotNull(massimaleDAO);
	}

	@Test
	public void A_createMassimaleTest() throws Exception {
		listaMassimale = MassimaleFunction.creaMassiveMassimale();
		massimaleDAO.persist(listaMassimale);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_MASSIMALE_CNR: {}\n", massimaleDAO.count().intValue());
	}

	@Test
	public void B_findMassimaleTest() throws Exception {
		IMassimaleSearchBuilder massimaleSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder();
		PageResult<Massimale> page = massimaleDAO.findMassimaleByQuery(massimaleSearchBuilder);
		logger.debug("############################NUMBER_ALL_MASSIMALE: {}\n", page.getTotal());
		Assert.assertTrue("FIND  MASSIMALE", page.getTotal() == 11);

	}

	@Test
	public void C_addMassimaleTest() throws Exception {

		Massimale m = new Massimale();
		m.setAreaGeografica(AreaGeograficaEnum.B);
		m.setDescrizione("Ricercatore");
		m.setLivello(LivelloUserEnum.I);
		m.setValue(new Double(120));
		m.setId("12");

		massimaleDAO.persist(m);
		Thread.sleep(1000);
		IMassimaleSearchBuilder massimaleSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder();
		PageResult<Massimale> page = massimaleDAO.findMassimaleByQuery(massimaleSearchBuilder);
		logger.debug("############################NUMBER_ALL_MASSIMALE: {}\n", page.getTotal());
		Assert.assertTrue("FIND  MASSIMALE", page.getTotal() == 12);
	}

	@Test
	public void D_updateMassimaleTest() throws Exception {
		Massimale m = new Massimale();
		m.setAreaGeografica(AreaGeograficaEnum.C);
		m.setDescrizione("Ricercatore");
		m.setLivello(LivelloUserEnum.I);
		m.setValue(new Double(120));
		m.setId("12");

		massimaleDAO.update(m);
		Thread.sleep(1000);
		IMassimaleSearchBuilder massimaleSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder();
		PageResult<Massimale> page = massimaleDAO.findMassimaleByQuery(massimaleSearchBuilder);
		logger.debug("############################NUMBER_ALL_MASSIMALE: {}\n", page.getTotal());
		Assert.assertTrue("FIND  MASSIMALE", page.getTotal() == 12);
	}

	@Test
	public void E_deleteMassimaleTest() throws Exception {
		massimaleDAO.delete("12");
		Thread.sleep(1000);
		IMassimaleSearchBuilder massimaleSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder();
		PageResult<Massimale> page = massimaleDAO.findMassimaleByQuery(massimaleSearchBuilder);
		logger.debug("############################NUMBER_ALL_MASSIMALE: {}\n", page.getTotal());
		Assert.assertTrue("FIND  MASSIMALE", page.getTotal() == 11);
	}

	@Test
	public void F_findByIdTest() throws Exception {
		IMassimaleSearchBuilder massimaleSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder().withId("02");
		List<Massimale> lista = massimaleDAO.findMassimaleByQuery(massimaleSearchBuilder).getResults();
		logger.debug("############################FIND_MASSIMALE_BY_ID: {}\n", lista.size());
		Assert.assertTrue("FIND MASSIMALE BY ID", lista.size() == 1);
		Assert.assertTrue("FIND  MASSIMALE BY ID", lista.get(0).getId().equals("02"));
	}

	@Test
	public void F_findByIdTest_2() throws Exception {
		IMassimaleSearchBuilder massimaleSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder().withId("13");
		List<Massimale> lista = massimaleDAO.findMassimaleByQuery(massimaleSearchBuilder).getResults();
		Assert.assertTrue("FIND MASSIMALE BY ID", lista.isEmpty());
	}

	@Test
	public void G_findByLivelloAreaGeograficaTest() throws Exception {
		IMassimaleSearchBuilder massimaleSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder()
				.withLivello(LivelloUserEnum.I.name()).withAreaGeografica(AreaGeograficaEnum.A.name())
				.withTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name());
		List<Massimale> lista = massimaleDAO.findMassimaleByQuery(massimaleSearchBuilder).getResults();
		Assert.assertTrue("FIND MASSIMALE BY LIVELLO-AREA", lista.size() == 1);
	}

	@Test
	public void H_findByLivelloAreaGeograficaTest_2() throws Exception {
		IMassimaleSearchBuilder massimaleSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder()
				.withLivello(LivelloUserEnum.IV.name()).withAreaGeografica(AreaGeograficaEnum.A.name())
				.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name());
		List<Massimale> lista = massimaleDAO.findMassimaleByQuery(massimaleSearchBuilder).getResults();
		Assert.assertTrue("FIND MASSIMALE BY LIVELLO-AREA", lista.size() == 1);
	}

	@Test
	public void I_findByLivelloAreaGeograficaTest_3() throws Exception {
		IMassimaleSearchBuilder massimaleSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder()
				.withLivello(LivelloUserEnum.IV.name()).withAreaGeografica(AreaGeograficaEnum.C.name())
				.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name());
		List<Massimale> lista = massimaleDAO.findMassimaleByQuery(massimaleSearchBuilder).getResults();
		Assert.assertTrue("FIND MASSIMALE BY LIVELLO-AREA", lista.isEmpty());
	}

	@Test
	public void L_findByIdNotTest() throws Exception {
		IMassimaleSearchBuilder massimaleSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder()
				.withNotId("10").withLivello(LivelloUserEnum.IV.name()).withAreaGeografica(AreaGeograficaEnum.A.name())
				.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name());
		List<Massimale> lista = massimaleDAO.findMassimaleByQuery(massimaleSearchBuilder).getResults();
		Assert.assertTrue("FIND MASSIMALE BY NOT ID", lista.isEmpty());
	}

	@Test
	public void L_findByIdNotTest_2() throws Exception {
		IMassimaleSearchBuilder massimaleSearchBuilder = IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder()
				.withNotId("01").withLivello(LivelloUserEnum.I.name()).withAreaGeografica(AreaGeograficaEnum.A.name())
				.withTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name());
		List<Massimale> lista = massimaleDAO.findMassimaleByQuery(massimaleSearchBuilder).getResults();
		Assert.assertTrue("FIND MASSIMALE BY NOT ID", lista.isEmpty());
	}

//	@Test
//	public void tearDown() throws Exception {
//		this.massimaleDocIndexCreator.deleteIndex();
//	}

}
