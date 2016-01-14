package it.cnr.missioni.notification.message;

import com.google.common.collect.ImmutableMap;
import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
abstract class CNRMissioniMessage implements IMissioniMailNotificationTask.IMissioneNotificationMessage {

    private ImmutableMap<String, Object> messParameters;
    private final ThreadLocal<Map<String, Object>> threadLocalParameters;

    public CNRMissioniMessage(String userName, String userSurname, String userEmail) {
        this.threadLocalParameters = ThreadLocal.<Map<String, Object>>withInitial(new Supplier<Map<String, Object>>() {

            @Override
            public Map<String, Object> get() {
                return new HashMap<String, Object>() {

                    {
                        super.put("userName", userName);
                        super.put("userSurname", userSurname);
                        super.put("userEmail", userEmail);
                        injectParameters(this);
                    }

                };
            }
        });
    }

    @Override
    public ImmutableMap<String, Object> getMessageParameters() {
        return (this.messParameters = ((this.messParameters == null)
                ? ImmutableMap.<String, Object>builder().putAll(this.threadLocalParameters.get()).build()
                : this.messParameters));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "  notificationMessageType = " + getNotificationMessageType() +
                " ,messParameters = " + getMessageParameters() +
                '}';
    }

    /**
     * @param map
     */
    protected abstract void injectParameters(Map<String, Object> map);
}
