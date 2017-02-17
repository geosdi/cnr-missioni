package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.el.model.search.builder.IMissioneSearchBuilder;
import it.cnr.missioni.el.utility.MissioneFunction;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO.IPageResult;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
public class MissioneDAOTest {

    @GeoPlatformLog
    static Logger logger;
    @Resource(name = "missioniIndexConfigurator")
    private GPIndexConfigurator missioniDocIndexConfigurator;
    @Resource(name = "missioneIndexCreator")
    private GPIndexCreator missioneDocIndexCreator;

    @Resource(name = "missioneDAO")
    private IMissioneDAO missioneDAO;

    private List<Missione> listaMissioni = new ArrayList<Missione>();

    @Before
    public void setUp() {
        Assert.assertNotNull(missioneDocIndexCreator);
        Assert.assertNotNull(missioniDocIndexConfigurator);
        Assert.assertNotNull(missioneDAO);
    }

    @Test
    public void A_createMissioneCNRTest() throws Exception {
        listaMissioni = MissioneFunction.creaMassiveMissioni();
        missioneDAO.persist(this.listaMissioni);
        Thread.sleep(1000);
        logger.debug("############################NUMBER_OF_MISSIONI : {}\n", this.missioneDAO.count().intValue());

    }

    @Test
    public void B_findMissioneByUser_1Test() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("01");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONE BY USER", lista.size() == 2);
    }

    @Test
    public void C_findMissioneByUser_2Test() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("03");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONE BY USER", lista.isEmpty());

    }

    @Test
    public void D_findMissioneByStato_1Test() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withStato(StatoEnum.PRESA_IN_CARICO.name());
        missioneSearchBuilder.withStato(StatoEnum.PRESA_IN_CARICO.name());
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 2);

    }

    @Test
    public void E_findMissioneByStato_2Test() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withStato(StatoEnum.APPROVATA.name());

        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 1);

    }

    @Test
    public void F_findMissioneByUserStato_1Test() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withStato(StatoEnum.APPROVATA.name()).withIdUser("01");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONE BY STATO", lista.isEmpty());

    }

    @Test
    public void G_findMissioneByUserStato_2Test() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withStato(StatoEnum.PRESA_IN_CARICO.name()).withIdUser("01");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 2);
    }

    @Test
    public void I_findMissioniWithPageTest() throws Exception {
        GPElasticSearchDAO.IPageResult<Missione> pageResult = this.missioneDAO
                .find(new GPElasticSearchDAO.Page(0, 3000));
        logger.info("########################MISSIONI_FOUND : {}\n", pageResult.getTotal());
    }

    @Test
    public void L_findMissioniByData_1() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withRangeDataInserimento(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC),
                        new DateTime(2015, 8, 13, 0, 1, DateTimeZone.UTC));
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONE BY DATA", lista.size() == 1);
    }

    @Test
    public void M_findMissioniByData_2() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withRangeDataInserimento(new DateTime(2015, 8, 14, 0, 0, DateTimeZone.UTC),
                        new DateTime(2015, 8, 31, 0, 0, DateTimeZone.UTC));

        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONE BY DATA", lista.size() == 0);

    }

    @Test
    public void N_findMissioniByAll() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withRangeDataInserimento(new DateTime(2015, 8, 13, 0, 0, DateTimeZone.UTC),
                        new DateTime(2015, 8, 31, 0, 0, DateTimeZone.UTC));

        missioneSearchBuilder.withStato(StatoEnum.PRESA_IN_CARICO.name());
        missioneSearchBuilder.withIdUser("01");

        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONE BY ALL", lista.size() == 1);

    }

    @Test
    public void O_findMissioniByRimborsoNumeroOrdine() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withNumeroOrdineMissione("M_01");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONE BY NUMERO ORDINE RIMBORSO", lista.size() == 1);
    }

    @Test
    public void P_findMissioniByDataRimborso_1() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withRangeDataRimborso(new DateTime(2015, 12, 12, 0, 0, DateTimeZone.UTC), new DateTime(2015, 12, 13, 0, 1, DateTimeZone.UTC));
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONE BY DATA RIMBORSO", lista.size() == 1);
    }

    @Test
    public void Q_findAllMissioni() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder();
        IPageResult<Missione> pageResult = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder);
        Assert.assertTrue("FIND ALL MISSIONI", pageResult.getTotal() == 12);
    }

    @Test
    public void R_findAllMissioni() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withId("");
        IPageResult<Missione> pageResult = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder);
        Assert.assertTrue("FIND ALL MISSIONI", pageResult.getTotal() == 12);
    }

    @Test
    public void S_findByOggetto() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withOggetto("sviluppo");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND BY OGGETTO", lista.size() == 1);
    }

    @Test
    public void S_findByOggetto2() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withOggetto("prova");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND BY OGGETTO", lista.size() == 2);
    }

    @Test
    public void T_findByMultiMatch() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withMultiMatch("Milano Riunione prova");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 3);
    }

    @Test
    public void U_findByMultiMatch_2() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withMultiMatch("roma conferenza");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 2);
    }

    @Test
    public void V_findByMultiMatch_3() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withMultiMatch("roma sviluppo");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 1);
    }

    @Test
    public void V_findByMultiMatch_4() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withMultiMatch("sviluppo di applicazioni");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 1);
    }

    @Test
    public void V_findNumeroRimboroUser() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("01")
                .withFieldExist("missione.rimborso");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 1);
    }

    @Test
    public void V_findNumeroRimboroUser2() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("02")
                .withFieldExist("missione.rimborso");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND ALL MISSIONI", lista.size() == 0);
    }

    @Test
    public void V_findMissioneNoRimborso() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withFieldNotExist("missione.rimborso").withIdUser("01").withStato(StatoEnum.INSERITA.name());
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND  MISSIONI NO RIMBORSO", lista.size() == 0);
    }

    @Test
    public void V_findRimborsoExsist() throws Exception {

        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder()
                .withFieldExist("missione.rimborso");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND  MISSIONI NO RIMBORSO", lista.size() == 1);
    }

    @Test
    public void V_getStatistiche() throws Exception {
        StatisticheMissioni statisticheMissioni = this.missioneDAO.getStatisticheMissioni();
        Assert.assertTrue("FIND STATISTICHE",
                statisticheMissioni.getMappaStatistiche().get(StatoEnum.PRESA_IN_CARICO.getStato()) == 2);
    }

    @Test
    public void V_findRiByUser() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser("01");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND  MISSIONI NO RIMBORSO", lista.size() == 2);
    }

    @Test
    public void V_findByIdMissione() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder().withId("M_01");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONI BY ID", lista.size() == 1);
    }

    @Test
    public void V_findByIdMissione_02() throws Exception {
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder().withId("MM_01");
        List<Missione> lista = this.missioneDAO.findMissioneByQuery(missioneSearchBuilder).getResults();
        Assert.assertTrue("FIND MISSIONI BY ID", lista.size() == 0);
    }
    
    @Test
    public void Z_findUserInMissione() throws Exception {
        List<Missione> lista = this.missioneDAO.getUsersInMissione();
        Assert.assertTrue("FIND USER IN MISSIONE", lista.size() == 7);
    }

    //@Ignore
	@Test
	public void Z_tearDown() throws Exception {
		this.missioneDocIndexCreator.deleteIndex();
	}
}
