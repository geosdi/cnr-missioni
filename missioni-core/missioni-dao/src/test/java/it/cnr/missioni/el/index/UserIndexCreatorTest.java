package it.cnr.missioni.el.index;

import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-Index-Test.xml"})
public class UserIndexCreatorTest {

    @GeoPlatformLog
    static Logger logger;
    //
    @Resource(name = "userIndexCreator")
    private GPIndexCreator userIndexCreator;

    @Before
    public void setUp() {
        Assert.assertNotNull(userIndexCreator);
    }

    @Test
    public void createIndexTest() throws Exception {
        this.userIndexCreator.createIndex();
    }

    @After
    public void tearDown() throws Exception {
        this.userIndexCreator.deleteIndex();
    }
}