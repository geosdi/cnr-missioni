package it.cnr.missioni.connector.core;

import it.cnr.missioni.connector.core.spring.connector.MissioniCoreClientConnector;
import it.cnr.missioni.connector.core.spring.connector.provider.CoreConnectorProvider;
import it.cnr.missioni.dropwizard.connector.api.settings.ConnectorClientSettings;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class MissioneWithoutSpringTest {

    private static final Logger logger = LoggerFactory.getLogger(MissioneWithoutSpringTest.class);
    //
    private static final MissioniCoreClientConnector missioniCoreClientConnector;

    static {
        missioniCoreClientConnector = new MissioniCoreClientConnector(new ConnectorClientSettings() {

            @Override
            public String getRestServiceURL() {
                return "http://localhost:8080";
            }

            @Override
            public String getConnectorName() {
                return "Missioni Core Client Connector Without Spring.";
            }

            @Override
            public void afterPropertiesSet() throws Exception {

            }
        }, ClientBuilder.newClient(new ClientConfig(
                CoreConnectorProvider.class)));
    }

    @Test
    public void A_testFindMissione() throws Exception {
        Missione missione = missioniCoreClientConnector.getMissioneById("M_01");
        Assert.assertNotNull(missione);
    }

    @Test
    public void B_testFindMissioneByUser() throws Exception {
        MissioniStore missioniStore = missioniCoreClientConnector.getLastUserMissions("01");
        Assert.assertEquals("Find Missione by user", 2, missioniStore.getMissioni().size());
    }

    @Test
    public void C_testInsertMissione() throws Exception {
        Missione missione = new Missione();
        missione.setId("M_04");
        missione.setIdUser("0_1");
        missione.setLocalita("Roma");
        missioniCoreClientConnector.addMissione(missione);
        Thread.sleep(1000);
        Missione missione_update = missioniCoreClientConnector.getMissioneById("M_04");
        logger.debug("############################INSERT MISSIONE\n");
        missione_update.setLocalita("Napoli");
        missioniCoreClientConnector.updateMissione(missione_update);

        Thread.sleep(1000);
        missione = missioniCoreClientConnector.getMissioneById("M_04");

        Assert.assertTrue("Update Missione", missione.getLocalita().equals(missione_update.getLocalita()));
        missioniCoreClientConnector.deleteMissione("M_04");
        logger.debug("############################DELETE MISSIONE\n");
    }
}
