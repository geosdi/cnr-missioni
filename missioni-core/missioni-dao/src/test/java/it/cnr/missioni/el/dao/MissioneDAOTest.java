package it.cnr.missioni.el.dao;

import com.google.common.collect.Lists;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import org.elasticsearch.action.bulk.BulkResponse;
import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
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
import java.util.UUID;

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
        creaMissioni();
        missioneDAO.persist(this.listaMissioni);
        Thread.sleep(1000);
        logger.debug("############################NUMBER_OF_MISSIONI : {}\n", this.missioneDAO.count().intValue());

    }

    @Test
    public void B_findMissioneByUtente_1Test() throws Exception {
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato("01", null);
        Assert.assertTrue("FIND MISSIONE BY UTENTE", lista.size() == 2);

    }

    @Test
    public void C_findMissioneByUtente_2Test() throws Exception {
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato("03", null);
        Assert.assertTrue("FIND MISSIONE BY UTENTE", lista.isEmpty());

    }

    @Test
    public void D_findMissioneByStato_1Test() throws Exception {
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(null, StatoEnum.PRESA_IN_CARICO);
        Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 2);

    }

    @Test
    public void E_findMissioneByStato_2Test() throws Exception {
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato(null, StatoEnum.APPROVATA);
        Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 1);

    }

    @Test
    public void E_findMissioneByUtenteStato_1Test() throws Exception {
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato("01", StatoEnum.APPROVATA);
        Assert.assertTrue("FIND MISSIONE BY STATO", lista.isEmpty());

    }

    @Test
    public void E_findMissioneByUtenteStato_2Test() throws Exception {
        List<Missione> lista = this.missioneDAO.findMissioneByUtenteStato("01", StatoEnum.PRESA_IN_CARICO);
        Assert.assertTrue("FIND MISSIONE BY STATO", lista.size() == 2);
    }

    @Test
    public void F_insertMassiveMissioniTest() throws Exception {
        BulkResponse bulkResponse = this.missioneDAO.persist(createMassiveMissioni());
        logger.info("#####################MASSIVE_MISSIONI_INSERT_TIME : {}\n",
                bulkResponse.getTook().toString());
    }

    @Test
    public void G_findMissioniWithPageTest() throws Exception {
        GPElasticSearchDAO.IPageResult<Missione> pageResult = this.missioneDAO
                .find(new GPElasticSearchDAO.Page(0, 3000));
        logger.info("########################MISSIONI_FOUND : {}\n", pageResult.getTotal());
    }

    @Test
    public void tearDown() throws Exception {
        this.missioneDocIndexCreator.deleteIndex();
    }


    private void creaMissioni() {
        Missione missione = new Missione();
        missione.setId("M_01");
        missione.setOggetto("Conferenza");
        missione.setLocalita("Roma");
        missione.setIdUtente("01");
        missione.setStato(StatoEnum.PRESA_IN_CARICO);
        listaMissioni.add(missione);

        missione = new Missione();
        missione.setId("M_02");
        missione.setOggetto("Conferenza");
        missione.setLocalita("Milano");
        missione.setIdUtente("01");
        missione.setStato(StatoEnum.PRESA_IN_CARICO);
        listaMissioni.add(missione);

        missione = new Missione();
        missione.setId("M_03");
        missione.setOggetto("Riunione");
        missione.setLocalita("Milano");
        missione.setIdUtente("02");
        missione.setStato(StatoEnum.APPROVATA);
        listaMissioni.add(missione);
    }

    List<Missione> createMassiveMissioni() {
        List<Missione> missioni = Lists.newArrayList();
        for (int i = 0; i < 8000; i++) {
            Missione missione = new Missione();
            missione.setId(UUID.randomUUID().toString());
            missione.setOggetto("OGGETTO-TEST - " + i);
            missione.setLocalita("LOCALITAA' - " + i);
            missione.setIdUtente(UUID.randomUUID().toString());
            if ((i % 2) == 0) {
                missione.setStato(StatoEnum.APPROVATA);
            } else {
                missione.setStato(StatoEnum.PRESA_IN_CARICO);
            }
            missioni.add(missione);
        }
        return missioni;
    }
}
