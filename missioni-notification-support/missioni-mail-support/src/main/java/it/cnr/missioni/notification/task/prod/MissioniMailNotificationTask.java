package it.cnr.missioni.notification.task.prod;

import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import it.cnr.missioni.notification.message.preparator.IMissioniMessagePreparator;
import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;
import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.configurator.bootstrap.Production;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Production
@Component(value = "missioniMailNotificationTask")
public class MissioniMailNotificationTask implements IMissioniMailNotificationTask<List<IMissioniMessagePreparator>, IMissioniMailNotificationTask.IMissioneNotificationMessage, Boolean> {

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
        List<IMissioniMessagePreparator> missioniMessagePreparator = null;

        try {
            missioniMessagePreparator = prepareMessage(theMissioneNotificationMessage);
            missioniMessagePreparator.stream().forEach(m -> this.gpMailSpringSender.send(m.getMimeMessagePreparator()));
        } catch (Exception ex) {
            logger.error("####################MAIL_ASYNC_TASK_EXCEPTION : {}\n", ex.getMessage());
            ex.printStackTrace();
            return new AsyncResult<>(Boolean.FALSE);
        } finally {
            if ((missioniMessagePreparator != null) && (!missioniMessagePreparator.isEmpty())) {
                missioniMessagePreparator.stream().forEach(m -> m.deleteAttachments());
            }
        }

        logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@ {} end Notification "
                + "Task {}\n", Thread.currentThread().getName(), getAsyncTaskType());
        return new AsyncResult<>(Boolean.TRUE);
    }


    @Override
    public List<IMissioniMessagePreparator> prepareMessage(IMissioneNotificationMessage theMissioneNotificationMessage) throws Exception {
        return ((MissioniMailImplementor<IMissioniMessagePreparator>) missioniMailImplementor
                .getImplementorByKey(theMissioneNotificationMessage.getNotificationMessageType()))
                .prepareMessage(theMissioneNotificationMessage, gpSpringVelocityEngine, gpMailSpringDetail);
    }

    @Override
    public GPAsyncTaskType getAsyncTaskType() {
        return NotificationMailTaskType.MISSIONI_MAIL_NOTIFICATION_TASK_PROD;
    }
}
