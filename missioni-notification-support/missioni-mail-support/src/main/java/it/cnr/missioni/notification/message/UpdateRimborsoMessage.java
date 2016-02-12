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
public class UpdateRimborsoMessage extends CNRMissioniMessage {

    private final PDFBuilder rimborsoPDFBuilder;
    private final String rimborsoID;
    private String stato;

    public UpdateRimborsoMessage(String userName, String userSurname, String userEmail,
             String theRimborsoID, String theStato, PDFBuilder theRimborsoPDFBuilder) {
        super(userName, userSurname, userEmail);
        this.rimborsoID = theRimborsoID;
        this.stato = theStato;
        this.rimborsoPDFBuilder = theRimborsoPDFBuilder;
    }

    /**
     * @return {@link MissioniMailImplementor.NotificationMessageType}
     */
    @Override
    public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
        return MissioniMailImplementor.NotificationMessageType.MODIFICA_RIMBORSO_MISSIONE_MAIL_PROD;
    }

    /**
     * @param map
     */
    @Override
    protected void injectParameters(Map<String, Object> map) {
        map.put("rimborsoID", this.rimborsoID);
        map.put("stato", this.stato);
        map.put("rimborsoPDFBuilder", this.rimborsoPDFBuilder);
    }
}
