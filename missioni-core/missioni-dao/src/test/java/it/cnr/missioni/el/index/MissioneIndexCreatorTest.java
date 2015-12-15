package it.cnr.missioni.el.index;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-Index-Test.xml"})
public class MissioneIndexCreatorTest {

    @GeoPlatformLog
    static Logger logger;
    //
    @Resource(name = "missioneIndexCreator")
    private GPIndexCreator missioneIndexCreator;

    @Before
    public void setUp() {
        Assert.assertNotNull(missioneIndexCreator);
    }

    @Test
    public void createIndexTest() throws Exception {
        this.missioneIndexCreator.createIndex();
    }

    @After
    public void tearDown() throws Exception {
        this.missioneIndexCreator.deleteIndex();
    }
}