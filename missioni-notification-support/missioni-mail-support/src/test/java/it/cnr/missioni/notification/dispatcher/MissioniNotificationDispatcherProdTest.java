package it.cnr.missioni.notification.dispatcher;

import it.cnr.missioni.notification.message.factory.NotificationMessageFactory;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.UUID;

import static it.cnr.missioni.notification.mail.CNRMissioniEmailTest.GP_MAIL_KEY;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath:applicationContext-Dispatcher-Prod-Test.xml"})
@ActiveProfiles(value = {"prod", "GPMailVelocitySupport"})
public class MissioniNotificationDispatcherProdTest {

    @GeoPlatformLog
    private static Logger logger;
    //
    private static final String GP_ASYNC_KEY = "GP_ASYNC_FILE_PROP";
    //
    @Resource(name = "missioniMailDispatcher")
    private MissioniMailDispatcher missioniMailDispatcher;
    @Resource(name = "notificationMessageProdFactory")
    private NotificationMessageFactory notificationMessageProdFactory;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty(GP_MAIL_KEY, "gp-mail-test.prop");
        System.setProperty(GP_ASYNC_KEY, "gp-async-test.prop");
    }

    @AfterClass
    public static void afterClass() {
        System.clearProperty(GP_MAIL_KEY);
        System.clearProperty(GP_ASYNC_KEY);
    }

    @Before
    public void setUp() throws Exception {
        Assert.assertNotNull(this.missioniMailDispatcher);
        Assert.assertNotNull(this.notificationMessageProdFactory);
    }

    @Test
    public void dispatchAddMissioneMailProdTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageProdFactory
                .buildAddMissioneMessage("Giuseppe", "La Scaleia", "glascaleia@gmail.com",
                        "vito.salvia@gmail.com"));
        Thread.sleep(4000);
    }

    @Test
    public void dispatchUpdateMissioneMailProdTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageProdFactory
                .buildUpdateMissioneMessage("Giuseppe", "La Scaleia", "glascaleia@gmail.com",
                        "vito.salvia@gmail.com", UUID.randomUUID().toString()));
        Thread.sleep(4000);
    }
}
