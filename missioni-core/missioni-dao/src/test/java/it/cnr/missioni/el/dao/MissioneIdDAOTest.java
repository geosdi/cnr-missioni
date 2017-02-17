package it.cnr.missioni.el.dao;

import java.util.UUID;

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

import it.cnr.missioni.model.configuration.MissioneId;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-DAO-Test.xml"})
public class MissioneIdDAOTest {

    @GeoPlatformLog
    static Logger logger;
    @Resource(name = "missioniIndexConfigurator")
    private GPIndexConfigurator missioniIndexConfigurator;

    @Resource(name = "missioneIdIndexCreator")
    private GPIndexCreator misioneIdDocIndexCreator;

    @Resource(name = "missioneIdDAO")
    private IMissioneIdDAO missioneIdDAO;

    @Before
    public void setUp() {
        Assert.assertNotNull(missioniIndexConfigurator);
        Assert.assertNotNull(misioneIdDocIndexCreator);
        Assert.assertNotNull(missioneIdDAO);
    }
    
    @Test
    public void A_insertTest() throws Exception {
    	MissioneId missioneId = new MissioneId();
    	missioneId.setId(UUID.randomUUID().toString());
    	missioneId.setValue("1");
    	this.missioneIdDAO.persist(missioneId);
    	Thread.sleep(1000);
        Assert.assertTrue("###########COUNT MISSIONE ID", this.missioneIdDAO.count() == 1);
    }

    @Test
    public void tearDown() throws Exception {
        this.misioneIdDocIndexCreator.deleteIndex();
    }

}
