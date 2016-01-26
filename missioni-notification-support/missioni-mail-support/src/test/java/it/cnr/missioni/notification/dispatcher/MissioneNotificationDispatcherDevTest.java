package it.cnr.missioni.notification.dispatcher;

import it.cnr.missioni.notification.message.factory.NotificationMessageFactory;
import it.cnr.missioni.notification.support.itext.missione.MissionePDFBuilder;
import it.cnr.missioni.notification.support.itext.rimborso.RimborsoPDFBuilder;
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
        locations = {"classpath:applicationContext-Dispatcher-Dev-Test.xml"})
@ActiveProfiles(value = {"dev", "GPMailVelocitySupport"})
public class MissioneNotificationDispatcherDevTest {

    @GeoPlatformLog
    private static Logger logger;
    //
    @Resource(name = "missioniMailDispatcher")
    private MissioniMailDispatcher missioniMailDispatcher;
    @Resource(name = "notificationMessageDevFactory")
    private NotificationMessageFactory notificationMessageDevFactory;

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
        Assert.assertNotNull(this.missioniMailDispatcher);
        Assert.assertNotNull(this.notificationMessageDevFactory);
    }

    @Test
    public void dispatchAddMissioneMailDevTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageDevFactory
                .buildAddMissioneMessage("Giuseppe", "La Scaleia", "glascaleia@gmail.com",
                        "vito.salvia@gmail.com", MissionePDFBuilder.newPDFBuilder()));
    }

    @Test
    public void dispatchUpdateMissioneMailDevTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageDevFactory
                .buildUpdateMissioneMessage("Giuseppe", "La Scaleia", "glascaleia@gmail.com",
                        "vito.salvia@gmail.com", UUID.randomUUID().toString(), MissionePDFBuilder.newPDFBuilder()));
    }

    @Test
    public void dispatchAddRimborsoMissioneMailDevTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageDevFactory
                .buildAddRimborsoMessage("Giuseppe", "La Scaleia", "glascaleia@gmail.com",
                        "vito.salvia@gmail.com", UUID.randomUUID().toString(), RimborsoPDFBuilder.newPDFBuilder()));
    }
}
