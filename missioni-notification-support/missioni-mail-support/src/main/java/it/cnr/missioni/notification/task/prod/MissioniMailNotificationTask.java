package it.cnr.missioni.notification.task.prod;

import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;
import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.configurator.bootstrap.Production;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Production
@Component(value = "missioniMailNotificationTask")
public class MissioniMailNotificationTask implements IMissioniMailNotificationTask<MimeMessagePreparator, IMissioniMailNotificationTask.IMissioneNotificationMessage, Boolean> {

    @GeoPlatformLog
    private static Logger logger;
    //
    @Resource(name = "gpMailSpringSender")
    private JavaMailSender gpMailSpringSender;
    @Resource(name = "gpSpringVelocityEngine")
    private VelocityEngine gpSpringVelocityEngine;
    @Resource(name = "gpMailSpringDetail")
    private GPMailDetail gpMailSpringDetail;

    @Override
    @Async(value = "gpThreadPoolTaskExecutor")
    public Future<Boolean> async(IMissioneNotificationMessage theMissioneNotificationMessage) throws Exception {
        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@ {} start Notification "
                        + "Task {}\n", Thread.currentThread().getName(),
                getAsyncTaskType());

        try {
            this.gpMailSpringSender.send(prepareMessage(theMissioneNotificationMessage));
        } catch (Exception ex) {
            logger.error("####################MAIL_ASYNC_TASK_EXCEPTION : {}\n", ex);
            ex.printStackTrace();
            return new AsyncResult<>(Boolean.FALSE);
        }

        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@ {} end Notification "
                        + "Task {}\n", Thread.currentThread().getName(),
                getAsyncTaskType());

        return new AsyncResult<>(Boolean.TRUE);
    }

    @Override
    public MimeMessagePreparator prepareMessage(IMissioneNotificationMessage theMissioneNotificationMessage) throws Exception {
        return ((MissioniMailImplementor<MimeMessagePreparator>) missioniMailImplementor
                .getImplementorByKey(theMissioneNotificationMessage.getNotificationMessageType()))
                .prepareMessage(theMissioneNotificationMessage, gpSpringVelocityEngine, gpMailSpringDetail);
    }

    @Override
    public GPAsyncTaskType getAsyncTaskType() {
        return NotificationMailTaskType.MISSIONI_MAIL_NOTIFICATION_TASK_PROD;
    }
}
