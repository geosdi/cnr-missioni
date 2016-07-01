package it.cnr.missioni.notification.message;

import java.util.Map;

import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import it.cnr.missioni.notification.support.itext.PDFBuilder;
import net.jcip.annotations.Immutable;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Immutable
public class NotifyUsersMissioneMessage extends CNRMissioniMessage {

    private final PDFBuilder missionePDFBuilder;

    public NotifyUsersMissioneMessage(PDFBuilder theMissionePDFBuilder) {
        super("", "", "");
        this.missionePDFBuilder = theMissionePDFBuilder;
    }

    /**
     * @return {@link MissioniMailImplementor.NotificationMessageType}
     */
    @Override
    public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
        return MissioniMailImplementor.NotificationMessageType.NOTIFY_USERS_IN_MISSIONE_MAIL_PROD;
    }

    /**
     * @param map
     */
    @Override
    protected void injectParameters(Map<String, Object> map) {
        map.put("missionePDFBuilder", this.missionePDFBuilder);
    }
}
