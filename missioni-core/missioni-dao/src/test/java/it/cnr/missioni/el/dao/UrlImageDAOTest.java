package it.cnr.missioni.el.dao;

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

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-DAO-Test.xml"})
public class UrlImageDAOTest {

    @GeoPlatformLog
    static Logger logger;
    @Resource(name = "missioniIndexConfigurator")
    private GPIndexConfigurator urlImageDocIndexConfigurator;

    @Resource(name = "urlImageIndexCreator")
    private GPIndexCreator urlImageDocIndexCreator;

    @Resource(name = "urlImageDAO")
    private IUrlImageDAO urlImageDAO;

    @Before
    public void setUp() {
        Assert.assertNotNull(urlImageDocIndexConfigurator);
        Assert.assertNotNull(urlImageDocIndexCreator);
        Assert.assertNotNull(urlImageDAO);
    }

    @Test
    public void A_countUrlImageTest() throws Exception {
    	Thread.sleep(1000);
        Assert.assertTrue("###########COUNT URL_IMAGE", this.urlImageDAO.count() == 1);
    }


/*    @Test
    public void tearDown() throws Exception {
        this.rimborsoKmDocIndexCreator.deleteIndex();
    }*/

}
