package it.cnr.missioni.notification.dispatcher;

import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Component(value = "missioniMailDispatcher")
public class MissioniMailDispatcher implements MissioniNotificationDispatcher {

    @GeoPlatformLog
    private static Logger logger;
    //
    @Autowired
    private IMissioniMailNotificationTask missioniMailNotificationTask;

    /**
     * @param message
     * @throws Exception
     */
    @Override
    public void dispatchMessage(IMissioniMailNotificationTask.IMissioneNotificationMessage message)
            throws Exception {
        logger.debug("##########################{} :: Execution for Message-----------> {}\n",
                getDispatcherType(), message);
        this.missioniMailNotificationTask.async(message);
    }

    /**
     * @return {@link DispatcherType}
     */
    @Override
    public <TYPE extends DispatcherType> TYPE getDispatcherType() {
        return (TYPE) MissioniDispatcherType.MAIL_DISPATCHER;
    }
}
