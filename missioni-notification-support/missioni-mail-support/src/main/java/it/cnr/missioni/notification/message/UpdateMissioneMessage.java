package it.cnr.missioni.notification.message;

import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import it.cnr.missioni.notification.support.itext.PDFBuilder;
import net.jcip.annotations.Immutable;

import java.util.Map;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Immutable
public class UpdateMissioneMessage extends AddMissioneMessage {

    private final String missioneID;

    public UpdateMissioneMessage(String userName, String userSurname, String userEmail,
            String theCnrMissioniEmail, String theMissioneID, PDFBuilder theMissionePDFBuilder) {
        super(userName, userSurname, userEmail, theCnrMissioniEmail, theMissionePDFBuilder);
        this.missioneID = theMissioneID;
    }

    /**
     * @return {@link MissioniMailImplementor.NotificationMessageType}
     */
    @Override
    public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
        return MissioniMailImplementor.NotificationMessageType.MODIFICA_MISSIONE_MAIL_PROD;
    }

    /**
     * @param map
     */
    @Override
    protected void injectParameters(Map<String, Object> map) {
        super.injectParameters(map);
        map.put("missioneID", this.missioneID);
    }
}
