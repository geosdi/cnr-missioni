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

import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class TipologiaSpesaDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator tipologiaSpesaDocIndexConfigurator;

	@Resource(name = "tipologiaSpesaIndexCreator")
	private GPIndexCreator tipoligiaSpesaDocIndexCreator;

	@Resource(name = "tipologiaSpesaDAO")
	private ITipologiaSpesaDAO tipologiaSpesaDAO;

	private List<TipologiaSpesa> listaTipoligiaSpesa = new ArrayList<TipologiaSpesa>();

	@Before
	public void setUp() {
		Assert.assertNotNull(tipologiaSpesaDocIndexConfigurator);
		Assert.assertNotNull(tipoligiaSpesaDocIndexCreator);
		Assert.assertNotNull(tipologiaSpesaDAO);
	}

	
	@Test
	public void A_createTipologiaSpesaTest() throws Exception {
		creaTipologiaSpesa();
		tipologiaSpesaDAO.persist(listaTipoligiaSpesa);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_TIPOLOGIA_SPESA: {}\n", tipologiaSpesaDAO.count().intValue());
	}

	@Test
	public void B_findTipologiaSpesaTest() throws Exception {
		TipologiaSpesaSearchBuilder tipologiaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder();
		List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipologiaSearchBuilder).getResults();
		logger.debug("############################NUMBER_ALL_TIPOLOGIA_SPESA: {}\n", lista.size());
		Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 2);

	}

	@Test
	public void C_addTipologiaSpesaAdd() throws Exception {
		
		TipologiaSpesa tipoligiaSpesa = new TipologiaSpesa();
		tipoligiaSpesa.setId("03");
		tipoligiaSpesa.setValue("Taxi");
		
		tipologiaSpesaDAO.persist(tipoligiaSpesa);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_TIPOLOGIA: {}\n", tipologiaSpesaDAO.count().intValue());
		TipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder();
		List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder).getResults();
		Assert.assertTrue("FIND  TIPOLOGIA SPESA", lista.size() == 3);
	}

	@Test
	public void D_updateVeicolOCNRTest() throws Exception {
		TipologiaSpesa tipoligiaSpesa = new TipologiaSpesa();
		tipoligiaSpesa.setId("03");
		tipoligiaSpesa.setValue("Taxi");
		tipologiaSpesaDAO.update(tipoligiaSpesa);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_TIPOLOGIA_SPESA: {}\n", tipologiaSpesaDAO.count().intValue());
		TipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder();
		List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder).getResults();
		Assert.assertTrue("FIND  TIPOLOGIA SPESA", lista.size() == 3);
	}
	
	@Test
	public void E_deleteTipologiaSpesaTest() throws Exception {
		tipologiaSpesaDAO.delete("03");
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_TIPOLOGIA_SPESA: {}\n", tipologiaSpesaDAO.count().intValue());
		TipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder();
		List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder).getResults();
		Assert.assertTrue("FIND  TIPOLOGIA SPESA", lista.size() == 2);
	}
	
	private void creaTipologiaSpesa(){
		TipologiaSpesa tipoligiaSpesa = new TipologiaSpesa();
		tipoligiaSpesa.setId("01");
		tipoligiaSpesa.setValue("Vitto");
		listaTipoligiaSpesa.add(tipoligiaSpesa);
		
		tipoligiaSpesa = new TipologiaSpesa();
		tipoligiaSpesa.setId("02");
		tipoligiaSpesa.setValue("Albergo");
		listaTipoligiaSpesa.add(tipoligiaSpesa);
		
	}



}