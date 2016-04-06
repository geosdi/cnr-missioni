package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.ITipologiaSpesaSearchBuilder;
import it.cnr.missioni.el.utility.TipologiaSpesaFunction;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
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
public class TipologiaSpesaDAOTest {

    @GeoPlatformLog
    static Logger logger;
    @Resource(name = "missioniIndexConfigurator")
    private GPIndexConfigurator missioniDocIndexConfigurator;

    @Resource(name = "tipologiaSpesaIndexCreator")
    private GPIndexCreator tipoligiaSpesaDocIndexCreator;

    @Resource(name = "tipologiaSpesaDAO")
    private ITipologiaSpesaDAO tipologiaSpesaDAO;

    private List<TipologiaSpesa> listaTipoligiaSpesa = new ArrayList<TipologiaSpesa>();

    @Before
    public void setUp() {
        Assert.assertNotNull(missioniDocIndexConfigurator);
        Assert.assertNotNull(tipoligiaSpesaDocIndexCreator);
        Assert.assertNotNull(tipologiaSpesaDAO);
    }

    @Test
    public void A_createTipologiaSpesaTest() throws Exception {
        listaTipoligiaSpesa = TipologiaSpesaFunction.creaMassiveTipologiaSpesa();
        tipologiaSpesaDAO.persist(listaTipoligiaSpesa);
        Thread.sleep(1000);
        logger.debug("############################NUMBER_ALL_TIPOLOGIA_SPESA: {}\n",
                tipologiaSpesaDAO.count().intValue());
    }

    @Test
    public void B_findTipologiaSpesaTest() throws Exception {
        ITipologiaSpesaSearchBuilder tipologiaSearchBuilder = ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                .getTipologiaSpesaSearchBuilder();
        List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipologiaSearchBuilder).getResults();
        logger.debug("############################NUMBER_ALL_TIPOLOGIA_SPESA: {}\n", lista.size());
        Assert.assertTrue("FINR  VEICOLO CNR", lista.size() == 4);

    }

    @Test
    public void C_addTipologiaSpesaAdd() throws Exception {

        TipologiaSpesa tipoligiaSpesa = new TipologiaSpesa();
        tipoligiaSpesa.setId("05");
        tipoligiaSpesa.setValue("Taxi");

        tipologiaSpesaDAO.persist(tipoligiaSpesa);
        Thread.sleep(1000);
        logger.debug("############################NUMBER_ALL_TIPOLOGIA: {}\n", tipologiaSpesaDAO.count().intValue());
        ITipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                .getTipologiaSpesaSearchBuilder();
        List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder)
                .getResults();
        Assert.assertTrue("FIND  TIPOLOGIA SPESA", lista.size() == 5);
    }

    @Test
    public void D_updateVeicolOCNRTest() throws Exception {
        TipologiaSpesa tipoligiaSpesa = new TipologiaSpesa();
        tipoligiaSpesa.setId("05");
        tipoligiaSpesa.setValue("Taxi");
        tipologiaSpesaDAO.update(tipoligiaSpesa);
        Thread.sleep(1000);
        logger.debug("############################NUMBER_ALL_TIPOLOGIA_SPESA: {}\n",
                tipologiaSpesaDAO.count().intValue());
        ITipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                .getTipologiaSpesaSearchBuilder();
        List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder)
                .getResults();
        Assert.assertTrue("FIND  TIPOLOGIA SPESA", lista.size() == 5);
    }

    @Test
    public void E_deleteTipologiaSpesaTest() throws Exception {
        tipologiaSpesaDAO.delete("05");
        Thread.sleep(1000);
        logger.debug("############################NUMBER_ALL_TIPOLOGIA_SPESA: {}\n",
                tipologiaSpesaDAO.count().intValue());
        ITipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                .getTipologiaSpesaSearchBuilder();
        List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder)
                .getResults();
        Assert.assertTrue("FIND  TIPOLOGIA SPESA", lista.size() == 4);
    }

    @Test
    public void F_findTipologiaSpesaTipoTest() throws Exception {
        ITipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                .getTipologiaSpesaSearchBuilder().withEstera(true);
        List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder)
                .getResults();
        Assert.assertTrue("FIND  TIPOLOGIA SPESA TIPO", lista.size() == 2);
    }

    @Test
    public void G_findTipologiaSpesaTipoTest() throws Exception {
        ITipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                .getTipologiaSpesaSearchBuilder().withItalia(true);
        List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder)
                .getResults();
        Assert.assertTrue("FIND  TIPOLOGIA SPESA TIPO", lista.size() == 2);
    }

    @Test
    public void G_findTipologiaSpesaIdTest() throws Exception {
        ITipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                .getTipologiaSpesaSearchBuilder().withId("03");
        List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder)
                .getResults();
        Assert.assertTrue("FIND  TIPOLOGIA SPESA ID", lista.size() == 1);
        Assert.assertTrue("FIND  TIPOLOGIA ID", lista.get(0).getId().equals("03"));
    }

    @Test
    public void H_findTipologiaSpesaTipoTrattamentoTest() throws Exception {
        ITipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                .getTipologiaSpesaSearchBuilder()
                .withTipoTrattamento(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name());
        List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder)
                .getResults();
        Assert.assertTrue("FIND  TIPOLOGIA SPESA TIPO TRATTAMENTO", lista.size() == 2);

    }

    @Test
    public void I_findTipologiaSpesaTipoTrattamentoIdTest() throws Exception {
        ITipologiaSpesaSearchBuilder tipoligiaSpesaSearchBuilder = ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                .getTipologiaSpesaSearchBuilder().withId("01")
                .withTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name());
        List<TipologiaSpesa> lista = tipologiaSpesaDAO.findTipologiaSpesaByQuery(tipoligiaSpesaSearchBuilder)
                .getResults();
        Assert.assertTrue("FIND  TIPOLOGIA SPESA TIPO TRATTAMENTO", lista.size() == 1);

    }

//	@Test
//	public void tearDown() throws Exception {
//		this.tipoligiaSpesaDocIndexCreator.deleteIndex();
//	}

}
