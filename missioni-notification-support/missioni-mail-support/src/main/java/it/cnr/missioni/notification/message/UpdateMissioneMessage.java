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
public class UpdateMissioneMessage extends CNRMissioniMessage {

    private final String missioneID;
    private final PDFBuilder missionePDFBuilder;
    private final String stato;
    private final String responsabileGruppoEmail;


    public UpdateMissioneMessage(String userName, String userSurname,String stato, String userEmail,String responsabileGruppoEmail,
            String theMissioneID, PDFBuilder theMissionePDFBuilder) {
        super(userName, userSurname, userEmail);
        this.missionePDFBuilder = theMissionePDFBuilder;
        this.missioneID = theMissioneID;
        this.responsabileGruppoEmail = responsabileGruppoEmail;
        this.stato = stato;
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
        map.put("missioneID", this.missioneID);
        map.put("responsabileEmail", this.responsabileGruppoEmail);
        map.put("missionePDFBuilder", this.missionePDFBuilder);
        map.put("stato", this.stato);


    }
}
