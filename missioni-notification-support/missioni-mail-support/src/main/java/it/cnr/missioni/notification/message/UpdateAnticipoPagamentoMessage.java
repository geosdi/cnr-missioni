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
public class UpdateAnticipoPagamentoMessage extends CNRMissioniMessage {

    private final String cnrMissioniEmail;
    private final PDFBuilder anticipoPagamentoPDFBuilder;
    private final String missioneID;

    public UpdateAnticipoPagamentoMessage(String userName, String userSurname, String userEmail,
            String theCnrMissioniEmail,String theMissioneID, PDFBuilder theAnticipoPagamentoPDFBuilder) {
        super(userName, userSurname, userEmail);
        this.cnrMissioniEmail = theCnrMissioniEmail;
        this.anticipoPagamentoPDFBuilder = theAnticipoPagamentoPDFBuilder;
        this.missioneID = theMissioneID;
    }

    /**
     * @return {@link MissioniMailImplementor.NotificationMessageType}
     */
    @Override
    public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
        return MissioniMailImplementor.NotificationMessageType.MODIFICA_ANTICIPO_PAGAMENTO_MAIL_PROD;
    }

    /**
     * @param map
     */
    @Override
    protected void injectParameters(Map<String, Object> map) {
        map.put("cnrMissioniEmail", this.cnrMissioniEmail);
        map.put("missioneID", this.missioneID);
        map.put("anticipoPagamentoPDFBuilder", this.anticipoPagamentoPDFBuilder);
    }
}
