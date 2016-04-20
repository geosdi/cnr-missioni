package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IRimborsoKmSearchBuilder;
import it.cnr.missioni.model.configuration.RimborsoKm;
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
import java.util.List;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-DAO-Test.xml"})
public class RimborsoKmDAOTest {

    @GeoPlatformLog
    static Logger logger;
    @Resource(name = "missioniIndexConfigurator")
    private GPIndexConfigurator rimborsoKmDocIndexConfigurator;

    @Resource(name = "rimborsoKmIndexCreator")
    private GPIndexCreator rimborsoKmDocIndexCreator;

    @Resource(name = "rimborsoKmDAO")
    private IRimborsoKmDAO rimborsoKmDAO;

    @Before
    public void setUp() {
        Assert.assertNotNull(rimborsoKmDocIndexConfigurator);
        Assert.assertNotNull(rimborsoKmDocIndexCreator);
        Assert.assertNotNull(rimborsoKmDAO);
    }

    @Test
    public void A_countRimborsoKmTest() throws Exception {
        Assert.assertTrue("###########COUNT RIMORSO_KM", this.rimborsoKmDAO.count() == 1);
    }

    @Test
    public void C_updateRimborsoKmTest() throws Exception {
    	RimborsoKm rimborsoKm = rimborsoKmDAO.findRimborsoKmByQuery(IRimborsoKmSearchBuilder.RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder()).getResults().get(0);
        rimborsoKm.setValue(0.40);
        Thread.sleep(1000);
        rimborsoKmDAO.update(rimborsoKm);
        Thread.sleep(1000);
        logger.debug("############################NUMBER_ALL_RIMBORSO_KM: {}\n", rimborsoKmDAO.count().intValue());

        IRimborsoKmSearchBuilder rimborsoKmSearchBuilder = IRimborsoKmSearchBuilder.RimborsoKmSearchBuilder
                .getRimborsoKmSearchBuilder();
        List<RimborsoKm> lista = rimborsoKmDAO.findRimborsoKmByQuery(rimborsoKmSearchBuilder).getResults();
        Assert.assertTrue("FIND RIMBORSO KM", lista.size() == 1);
    }

    @Test
    public void D_addRimborsoKmtest() throws Exception {
        RimborsoKm rimborsoKm = new RimborsoKm();
        rimborsoKm.setId("02");
        rimborsoKm.setValue(0.36);
        rimborsoKmDAO.persist(rimborsoKm);
    }

    @Test
    public void F_findByIdTest_2() throws Exception {
        IRimborsoKmSearchBuilder rimborsoKmSearchBuilder = IRimborsoKmSearchBuilder.RimborsoKmSearchBuilder
                .getRimborsoKmSearchBuilder().withId("02");
        List<RimborsoKm> lista = rimborsoKmDAO.findRimborsoKmByQuery(rimborsoKmSearchBuilder).getResults();
        Assert.assertTrue("FIND RIMBORSO KM BY ID", lista.isEmpty());
    }

/*    @Test
    public void tearDown() throws Exception {
        this.rimborsoKmDocIndexCreator.deleteIndex();
    }*/

}
