package it.cnr.missioni.notification.bridge.store;


import it.cnr.missioni.notification.bridge.implementor.Implementor;
import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class MissioniMailImplementorStoreTest {

    private static final Logger logger = LoggerFactory.getLogger(MissioniMailImplementorStoreTest.class);
    //
    private static final ImplementorStore<MissioniMailImplementor> missioniMailImplementorStore = new MissioniMailImplementorStore();

    @Test
    public void getAllMissioniMailImplementorsTest() {
        Set<MissioniMailImplementor> missioniMailImplementors = missioniMailImplementorStore.getAllImplementors();

        logger.info("##################MISSIONI_MAIL_IMPLEMENTORS : {}\n", missioniMailImplementors);
        Assert.assertEquals(8, missioniMailImplementors.size());
    }

    @Test
    public void getAddMissioneMailProdByKeyTest() throws Exception {
        Implementor.ImplementorKey key = MissioniMailImplementor.NotificationMessageType.AGGIUNGI_MISSIONE_MAIL_PROD;
        logger.info("###################Found : {} for key {}\n\n",
                missioniMailImplementorStore.getImplementorByKey(key), key);
    }

    @Test
    public void getAddMissioneMailDevByKeyTest() throws Exception {
        Implementor.ImplementorKey key = MissioniMailImplementor.NotificationMessageType.AGGIUNGI_MISSIONE_MAIL_DEV;
        logger.info("###################Found : {} for key {}\n\n",
                missioniMailImplementorStore.getImplementorByKey(key), key);
    }

    @Test
    public void getUpdateMissioneMailProdByKeyTest() throws Exception {
        Implementor.ImplementorKey key = MissioniMailImplementor.NotificationMessageType.MODIFICA_MISSIONE_MAIL_PROD;
        logger.info("###################Found : {} for key {}\n\n",
                missioniMailImplementorStore.getImplementorByKey(key), key);
    }

    @Test
    public void getUpdateMissioneMailDevByKeyTest() throws Exception {
        Implementor.ImplementorKey key = MissioniMailImplementor.NotificationMessageType.MODIFICA_MISSIONE_MAIL_DEV;
        logger.info("###################Found : {} for key {}\n\n",
                missioniMailImplementorStore.getImplementorByKey(key), key);
    }

    @Test
    public void getRequestRimborsoMissioneMailProdByKeyTest() throws Exception {
        Implementor.ImplementorKey key = MissioniMailImplementor.NotificationMessageType.RICHIESTA_RIMBORSO_MISSIONE_MAIL_PROD;
        logger.info("###################Found : {} for key {}\n\n",
                missioniMailImplementorStore.getImplementorByKey(key), key);
    }

    @Test
    public void getRequestRimborsoMissioneMailDevByKeyTest() throws Exception {
        Implementor.ImplementorKey key = MissioniMailImplementor.NotificationMessageType.RICHIESTA_RIMBORSO_MISSIONE_MAIL_DEV;
        logger.info("###################Found : {} for key {}\n\n",
                missioniMailImplementorStore.getImplementorByKey(key), key);
    }

    @Test
    public void geRimborsoMissioneDoneMailProdByKeyTest() throws Exception {
        Implementor.ImplementorKey key = MissioniMailImplementor.NotificationMessageType.RIMBORSO_MISSIONE_EFFETTUATO_MAIL_PROD;
        logger.info("###################Found : {} for key {}\n\n",
                missioniMailImplementorStore.getImplementorByKey(key), key);
    }

    @Test
    public void getRimborsoMissioneDoneMailDevByKeyTest() throws Exception {
        Implementor.ImplementorKey key = MissioniMailImplementor.NotificationMessageType.RIMBORSO_MISSIONE_EFFETTUATO_MAIL_DEV;
        logger.info("###################Found : {} for key {}\n\n",
                missioniMailImplementorStore.getImplementorByKey(key), key);
    }
}
