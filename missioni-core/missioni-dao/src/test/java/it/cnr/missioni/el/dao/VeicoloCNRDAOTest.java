package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IVeicoloCNRSearchBuilder;
import it.cnr.missioni.el.utility.VeicoloCNRFunction;
import it.cnr.missioni.model.prenotazione.StatoVeicoloEnum;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-DAO-Test.xml"})
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
        listaVeicoliCNR = VeicoloCNRFunction.creaMassiveVeicoloCNR();
        veicoloCNRDAO.persist(listaVeicoliCNR);
        Thread.sleep(1000);
        logger.debug("############################NUMBER_ALL_VEICOLI_CNR: {}\n", veicoloCNRDAO.count().intValue());
    }

    @Test
    public void B_findVeicoloCNRTest() throws Exception {
        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder();
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
        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder();
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

        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder();
        List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
        logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", lista.size());
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 3);
    }

    @Test
    public void E_deleteVeicoloCNRTest() throws Exception {
        veicoloCNRDAO.delete("03");
        Thread.sleep(1000);
        logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", veicoloCNRDAO.count().intValue());

        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder();
        List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
        logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", lista.size());
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 2);
    }

    @Test
    public void F_testFindVeicoloCNR() throws Exception {

        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder();
        List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
        logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", lista.size());
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 2);
    }

    @Test
    public void G_findVeicoloDisponibileCNRTest() throws Exception {
        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder().withStato(StatoVeicoloEnum.DISPONIBILE.name());
        List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
        Thread.sleep(1000);
        logger.debug("############################NUMBER_DISPONIBILE_VEICOLO_CNR_DISPONIBILE: {}\n", lista.size());
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 1);
    }

    @Test
    public void H_findVeicoloTargaWithNotID() throws Exception {
        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder().withTarga("6575").withNotId("02");
        List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
        Thread.sleep(1000);
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 0);
    }

    @Test
    public void I_findVeicoloCartaCircolazioneWithNotID() throws Exception {
        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder().withCartaCircolazione("carta 456").withNotId("02");
        List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
        Thread.sleep(1000);
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 0);
    }

    @Test
    public void L_findVeicoloPolizzaAssicurativaWithNotID() throws Exception {
        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder().withPolizzaAssicurativa("polizza 2").withNotId("02");
        List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
        Thread.sleep(1000);
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 0);
    }

    @Test
    public void M_findVeicoloPolizza() throws Exception {
        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder().withPolizzaAssicurativa("polizza 2");
        List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
        Thread.sleep(1000);
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 1);
    }

    @Test
    public void M_findVeicoloPolizza_2() throws Exception {
        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder().withPolizzaAssicurativa("Polizza 2");
        List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
        Thread.sleep(1000);
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 1);
    }

    @Test
    public void F_testFindVeicoloCNRBiId() throws Exception {

        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder().withId("01");
        List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
        logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", lista.size());
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 1);
    }

    @Test
    public void F_testFindVeicoloCNRBiId_2() throws Exception {

        IVeicoloCNRSearchBuilder veicoloCNRSearchBuilder = IVeicoloCNRSearchBuilder.VeicoloCNRSearchBuilder
                .getVeicoloCNRSearchBuilder().withId("05");
        List<VeicoloCNR> lista = veicoloCNRDAO.findVeicoloCNRByQuery(veicoloCNRSearchBuilder).getResults();
        logger.debug("############################NUMBER_OF_VEICOLO_CNR: {}\n", lista.size());
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 0);
    }

//	@Test
//	public void tearDown() throws Exception {
//		this.veicoloCNRDocIndexCreator.deleteIndex();
//	}

}
