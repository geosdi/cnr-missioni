package it.cnr.missioni.notification.dispatcher;

import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.notification.message.factory.NotificationMessageFactory;
import it.cnr.missioni.notification.support.itext.anticipopagamento.AnticipoPagamentoPDFBuilder;
import it.cnr.missioni.notification.support.itext.missione.MissionePDFBuilder;
import it.cnr.missioni.notification.support.itext.rimborso.RimborsoPDFBuilder;
import it.cnr.missioni.notification.support.itext.user.UserMissionePDFBuilder;

import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
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
                .buildAddMissioneMessage("Giuseppe", "La Scaleia", "vito.salvia@gmail.com","",
                        "vito.salvia@gmail.com", MissionePDFBuilder.newPDFBuilder(),"1-2016"));
    }

    @Test
    public void dispatchUpdateMissioneMailDevTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageDevFactory
                .buildUpdateMissioneMessage("Giuseppe", "La Scaleia", "Inserita",
                        "vito.salvia@gmail.com","vito.salvia@gmail.com", UUID.randomUUID().toString(), MissionePDFBuilder.newPDFBuilder()));
    }

    @Test
    public void dispatchAddRimborsoMissioneMailDevTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageDevFactory
                .buildAddRimborsoMessage("Giuseppe", "La Scaleia", "vito.salvia@gmail.com",
                        "vito.salvia@gmail.com", UUID.randomUUID().toString(), RimborsoPDFBuilder.newPDFBuilder()));
    }
    
    @Test
    public void dispatchUpdateRimborsoMissioneMailDevTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageDevFactory
                .buildUpdateRimborsoMessage("Giuseppe", "La Scaleia", "vito.salvia@gmail.com", UUID.randomUUID().toString(),"Si","01",new Double(130)));
    }
    
    @Test
    public void dispatchAddAnticipoPagamentoMailDevTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageDevFactory
                .buildAddAnticipoPagamentoMessage("Giuseppe", "La Scaleia", "vito.salvia@gmail.com",
                        "vito.salvia@gmail.com","01", AnticipoPagamentoPDFBuilder.newPDFBuilder()));
    }
    
    @Test
    public void dispatchUpdateAnticipoPagamentoMailDevTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageDevFactory
                .buildUpdateAnticipoPagamentoMessage("Giuseppe", "La Scaleia", "vito.salvia@gmail.com",
                        "vito.salvia@gmail.com","01", AnticipoPagamentoPDFBuilder.newPDFBuilder()));
    }
    
    @Test
    public void dispatchRecuperaPasswordMailDevTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageDevFactory
                .buildRecuperaPasswordMessage("Vito", "Salvia", "vito.salvia@gmail.com",
                        "new_password"));
    }
    
    @Test
    public void dispatchUsersInMissioneMailDevTest() throws Exception {
        this.missioniMailDispatcher.dispatchMessage(this.notificationMessageDevFactory
                .buildUsersInMissioneMessage(UserMissionePDFBuilder.newPDFBuilder(),new String[0],buildMissioneList()));
    }
    
    List<Missione> buildMissioneList(){
    	List<Missione> lista = new ArrayList<Missione>();
    	Missione missione = new Missione();
    	missione.setShortUser("Salvia Vito");
    	missione.setLocalita("Roma");
    	lista.add(missione);
    	missione = new Missione();
    	missione.setShortUser("Franco Luigi");
    	missione.setLocalita("Milano");
    	lista.add(missione);
    	return lista;
    }
    
    
}
