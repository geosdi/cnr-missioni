package it.cnr.missioni.notification.task;

import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import it.cnr.missioni.notification.bridge.store.ImplementorStore;
import it.cnr.missioni.notification.bridge.store.MissioniMailImplementorStore;
import org.geosdi.geoplatform.support.async.spring.task.GPAsyncTask;
import org.geosdi.geoplatform.support.async.spring.task.notification.GPAsyncNotificationTask;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface IMissioniMailNotificationTask<MESSAGE extends Object, S extends IMissioniMailNotificationTask.IMissioneNotificationMessage, R extends Object>
        extends GPAsyncNotificationTask<MESSAGE, S, R> {

    ImplementorStore<MissioniMailImplementor> missioniMailImplementor = new MissioniMailImplementorStore();

    /**
     *
     */
    interface IMissioneNotificationMessage extends GPAsyncNotificationTask.GPNotificationMessage {

        /**
         * @return {@link MissioniMailImplementor.NotificationMessageType}
         */
        MissioniMailImplementor.NotificationMessageType getNotificationMessageType();

    }

    /**
     *
     */
    enum NotificationMailTaskType implements GPAsyncTask.GPAsyncTaskType {
        MISSIONI_MAIL_NOTIFICATION_TASK_DEV("MISSIONI_MAIL_NOTIFICATION_TASK_DEV"),
        MISSIONI_MAIL_NOTIFICATION_TASK_PROD("MISSIONI_MAIL_NOTIFICATION_TASK_PROD");

        private final String value;

        NotificationMailTaskType(String theValue) {
            this.value = theValue;
        }

        /**
         * <p>Returns the Type of {@link org.geosdi.geoplatform.support.async.spring.task.GPAsyncTask}</p>
         *
         * @return {@link String}
         */
        @Override
        public String getType() {
            return this.value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public static NotificationMailTaskType fromString(String value) {
            for (NotificationMailTaskType taskType : NotificationMailTaskType.values()) {
                if (taskType.getType().equalsIgnoreCase(value))
                    return taskType;
            }
            return null;
        }
    }
}
