package it.cnr.missioni.notification.mail;

import it.cnr.missioni.notification.spring.configuration.CNRMissioniEmail;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-Email-Test.xml"})
public class CNRMissioniEmailTest {

    @GeoPlatformLog
    private static Logger logger;
    //
    public static final String GP_MAIL_KEY = "GP_MAIL_FILE_PROP";
    //
    @Resource(name = "cnrMissioniItaliaEmail")
    private CNRMissioniEmail cnrMissioniItaliaEmail;
    @Resource(name = "cnrMissioniEsteroEmail")
    private CNRMissioniEmail cnrMissioniEsteroEmail;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty(GP_MAIL_KEY, "gp-mail-test.prop");
    }

    @AfterClass
    public static void afterClass() {
        System.clearProperty(GP_MAIL_KEY);
    }

    @Before
    public void setUp() throws Exception {
        Assert.assertNotNull(this.cnrMissioniItaliaEmail);
        Assert.assertNotNull(this.cnrMissioniEsteroEmail);
    }

    @Test
    public void cnrMissioniItaliaEmailTest() {
        logger.info("########################CNR_MISSIONI_ITALIA_EMAIL : {}\n",
                this.cnrMissioniItaliaEmail);
    }

    @Test
    public void cnrMissioniEsteroEmailTest() {
        logger.info("########################CNR_MISSIONI_ESTERO_EMAIL : {}\n",
                this.cnrMissioniEsteroEmail);
    }
}
