package it.cnr.missioni.notification.message;

import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import net.jcip.annotations.Immutable;

import java.util.Map;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Immutable
public class AddMissioneMessage extends CNRMissioniMessage {

    private final String cnrMissioniEmail;

    public AddMissioneMessage(String userName, String userSurname, String userEmail, String theCnrMissioniEmail) {
        super(userName, userSurname, userEmail);
        this.cnrMissioniEmail = theCnrMissioniEmail;
    }

    /**
     * @return {@link MissioniMailImplementor.NotificationMessageType}
     */
    @Override
    public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
        return MissioniMailImplementor.NotificationMessageType.AGGIUNGI_MISSIONE_MAIL_PROD;
    }

    /**
     * @param map
     */
    @Override
    protected void injectParameters(Map<String, Object> map) {
        map.put("cnrMissioniEmail", this.cnrMissioniEmail);
    }
}
