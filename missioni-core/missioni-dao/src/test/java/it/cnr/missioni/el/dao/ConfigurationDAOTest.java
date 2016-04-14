package it.cnr.missioni.el.dao;

import javax.annotation.Resource;

import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
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

import it.cnr.missioni.el.configuration.IConfiguration;

/**
 * @author Salvia Vito
 */
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-DAO-Test.xml"})
public class ConfigurationDAOTest {

    @GeoPlatformLog
    static Logger logger;
    @Resource(name = "missioniIndexConfigurator")
    private GPIndexConfigurator massimaleDocIndexConfigurator;
    @Resource(name = "direttoreConfiguration")
    private IConfiguration direttoreConfiguration;
    @Resource(name = "rimborsoKmConfiguration")
    private IConfiguration rimborsoKmConfiguration;
	@Resource(name = "direttoreDAO")
	private IDirettoreDAO direttoreDAO;
    @Resource(name = "rimborsoKmDAO")
    private IRimborsoKmDAO rimborsoKmDAO;

    @Before
    public void setUp() {
        Assert.assertNotNull(massimaleDocIndexConfigurator);
        Assert.assertNotNull(direttoreConfiguration);
        Assert.assertNotNull(rimborsoKmConfiguration);
        Assert.assertNotNull(direttoreDAO);
        Assert.assertNotNull(rimborsoKmDAO);
    }
    
    @Test
    public void checkConfiguration() throws Exception{
    	Assert.assertTrue("COUNT DIRETTORE", direttoreDAO.count() == 1);
    	Assert.assertTrue("COUNT RIMBORSO KM", rimborsoKmDAO.count() == 1);
    }

}
