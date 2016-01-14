package it.cnr.missioni.notification.dispatcher;

import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface MissioniNotificationDispatcher {

    /**
     * @param message
     * @throws Exception
     */
    void dispatchMessage(IMissioniMailNotificationTask.IMissioneNotificationMessage message) throws Exception;

    /**
     * @param <TYPE>
     * @return {@link DispatcherType}
     */
    <TYPE extends DispatcherType> TYPE getDispatcherType();

    /**
     *
     */
    interface DispatcherType {

        String getDispatcherType();
    }

    enum MissioniDispatcherType implements DispatcherType {
        MAIL_DISPATCHER("MAIL");

        private final String value;

        MissioniDispatcherType(String theValue) {
            this.value = theValue;
        }

        @Override
        public String getDispatcherType() {
            return this.value;
        }
    }
}
