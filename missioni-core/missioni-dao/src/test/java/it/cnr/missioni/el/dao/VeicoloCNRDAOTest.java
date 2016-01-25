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

import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.StatoVeicoloEnum;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-DAO-Test.xml" })
public class VeicoloCNRDAOTest {

	@GeoPlatformLog
	static Logger logger;
	@Resource(name = "missioniIndexConfigurator")
	private GPIndexConfigurator missioniDocIndexConfigurator;

	@Resource(name = "veicoloCNRIndexCreator")
	private GPIndexCreator veicoloCNRDocIndexCreator;

	@Resource(name = "veicoloCNRDAO")
	private IVeicoloCNRDAO veicoloCNRDAO;

	private List<VeicoloCNR> listaVeicoliCNR = new ArrayList<VeicoloCNR>();

	@Before
	public void setUp() {
		Assert.assertNotNull(missioniDocIndexConfigurator);
		Assert.assertNotNull(veicoloCNRDocIndexCreator);
		Assert.assertNotNull(veicoloCNRDAO);
	}

	@Test
	public void A_createVeicoloCNRTest() throws Exception {
		creaVeicoliCNR();
		veicoloCNRDAO.persist(listaVeicoliCNR);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_VEICOLI_CNR: {}\n", veicoloCNRDAO.count().intValue());
	}

	@Test
	public void B_findVeicoloCNRTest() throws Exception {
		VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder();
		List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
		logger.debug("############################NUMBER_ALL_VEICOLO_CNR_DISPONIBILE: {}\n", lista.size());
		Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 2);

	}

	@Test
	public void C_addVeicolotest() throws Exception {
		VeicoloCNR v = new VeicoloCNR();
		v.setCartaCircolazione("carta");
		v.setId("03");
		v.setStato(StatoVeicoloEnum.DISPONIBILE);
		v.setPolizzaAssicurativa("polizza");
		v.setTarga("AAAAAA");
		v.setTipo("Tipo");
		veicoloCNRDAO.persist(v);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_VEICOLO_CNR: {}\n", veicoloCNRDAO.count().intValue());
		VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder();
		List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
		Assert.assertTrue("FIND  VEICOLO CNR", lista.size() == 3);
	}

	@Test
	public void D_updateVeicolOCNRTest() throws Exception {
		VeicoloCNR v = new VeicoloCNR();
		v.setCartaCircolazione("carta 123");
		v.setId("03");
		v.setStato(StatoVeicoloEnum.DISPONIBILE);
		v.setPolizzaAssicurativa("polizza 123");
		v.setTarga("BBBBB");
		v.setTipo("Tipo");
		Thread.sleep(1000);
		veicoloCNRDAO.update(v);
		Thread.sleep(1000);
		logger.debug("############################NUMBER_ALL_VEICOLO_CNR: {}\n", veicoloCNRDAO.count().intValue());

		VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder();
		List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
		logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", lista.size());
		Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 3);
	}

	@Test
	public void E_deleteVeicoloCNRTest() throws Exception {
		veicoloCNRDAO.delete("03");
		Thread.sleep(1000);
		logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", veicoloCNRDAO.count().intValue());

		VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder();
		List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
		logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", lista.size());
		Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 2);
	}

	@Test
	public void F_testFindVeicoloCNR() throws Exception {

		VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder();
		List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
		logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", lista.size());
		Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 2);

	}

	@Test
	public void G_findVeicoloDisponibileCNRTest() throws Exception {
		VeicoloCNRSearchBuilder veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder()
				.withStato(StatoVeicoloEnum.DISPONIBILE.name());
		List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
		Thread.sleep(1000);
		logger.debug("############################NUMBER_DISPONIBILE_VEICOLO_CNR_DISPONIBILE: {}\n", lista.size());
		Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 1);

	}

	//
	//// @Test
	//// public void tearDown() throws Exception {
	//// this.utenteDocIndexCreator.deleteIndex();
	//// }

	private void creaVeicoliCNR() {

		VeicoloCNR veicoloCNR = new VeicoloCNR();
		veicoloCNR.setId("01");
		veicoloCNR.setCartaCircolazione("Carta 123");
		veicoloCNR.setPolizzaAssicurativa("polizza 1");
		veicoloCNR.setTipo("Citroen");
		veicoloCNR.setTarga("56654");
		veicoloCNR.setStato(StatoVeicoloEnum.DISPONIBILE);
		listaVeicoliCNR.add(veicoloCNR);

		veicoloCNR = new VeicoloCNR();
		veicoloCNR.setId("02");
		veicoloCNR.setCartaCircolazione("Carta 456");
		veicoloCNR.setPolizzaAssicurativa("polizza 2");
		veicoloCNR.setTipo("Peugeout");
		veicoloCNR.setTarga("6575");
		veicoloCNR.setStato(StatoVeicoloEnum.NON_DISPONIBILE);
		listaVeicoliCNR.add(veicoloCNR);

	}

}
