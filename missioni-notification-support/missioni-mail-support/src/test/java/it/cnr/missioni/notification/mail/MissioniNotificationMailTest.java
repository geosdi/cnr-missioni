package it.cnr.missioni.notification.mail;

import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.geosdi.geoplatform.support.mail.loader.GPMailLoader;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GPMailLoader.class},
        loader = AnnotationConfigContextLoader.class)
public class MissioniNotificationMailTest {

    @GeoPlatformLog
    static Logger logger;
    //
    public static final String GP_MAIL_KEY = "GP_MAIL_FILE_PROP";

    @Resource(name = "gpMailSpringDetail")
    private GPMailDetail gpMailSpringDetail;
    @Resource(name = "gpMailSpringSender")
    JavaMailSender gpMailSpringSender;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty(GP_MAIL_KEY, "gp-mail-test.prop");
    }

    @AfterClass
    public static void afterClass() {
        System.clearProperty(GP_MAIL_KEY);
    }

    @Before
    public void setUp() {
        Assert.assertNotNull(logger);
        Assert.assertNotNull(gpMailSpringDetail);
        Assert.assertNotNull(gpMailSpringSender);
    }

    @Test
    public void ape2015MailDetailTest() {
        logger.info("\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@Mail Detail : {}",
                gpMailSpringDetail);
    }

    @Test
    public void ape2015SendMailTest() throws InterruptedException {
        this.gpMailSpringSender.send(new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(gpMailSpringDetail.getFrom(),
                        gpMailSpringDetail.getFromName());
                message.setReplyTo(gpMailSpringDetail.getReplayToName());
                message.setSubject("Test Missioni");
                message.setTo(new String[]{"giuseppe.lascaleia@geosdi.org", "vito.salvia@gmail.com"});
                message.setText("This is a test");
            }

        });
    }
}
