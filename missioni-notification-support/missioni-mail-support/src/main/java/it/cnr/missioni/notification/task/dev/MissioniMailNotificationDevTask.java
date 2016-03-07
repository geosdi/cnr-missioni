package it.cnr.missioni.notification.task.dev;

import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.configurator.bootstrap.Develop;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Develop
@Component(value = "missioniMailNotificationDevTask")
public class MissioniMailNotificationDevTask implements IMissioniMailNotificationTask<List<String>, IMissioniMailNotificationTask.IMissioneNotificationMessage, Boolean> {

    @GeoPlatformLog
    private static Logger logger;
    //
    @Resource(name = "gpSpringVelocityEngine")
    private VelocityEngine gpSpringVelocityEngine;
    @Resource(name = "gpMailSpringDetail")
    private GPMailDetail gpMailSpringDetail;

    @Override
    public Future<Boolean> async(IMissioneNotificationMessage theMissioneNotificationMessage) throws Exception {
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@{} Notification "
                        + "Task {}, produces Message : {}\n\n", Thread.currentThread().getName(), getAsyncTaskType(),
                prepareMessage(theMissioneNotificationMessage));

        return new AsyncResult<>(Boolean.TRUE);
    }

    @Override
    public List<String> prepareMessage(IMissioneNotificationMessage theMissioneNotificationMessage) throws Exception {
        return ((MissioniMailImplementor<String>) missioniMailImplementor
                .getImplementorByKey(theMissioneNotificationMessage.getNotificationMessageType()))
                .prepareMessage(theMissioneNotificationMessage, gpSpringVelocityEngine, gpMailSpringDetail);
    }

    @Override
    public GPAsyncTaskType getAsyncTaskType() {
        return NotificationMailTaskType.MISSIONI_MAIL_NOTIFICATION_TASK_DEV;
    }
}
