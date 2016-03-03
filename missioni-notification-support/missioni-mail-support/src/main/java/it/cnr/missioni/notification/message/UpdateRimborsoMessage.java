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
    private String pagata;
    private String mandatoPagamento;
    private Double totaleDovuto;


    public UpdateRimborsoMessage(String userName, String userSurname, String userEmail,
             String theRimborsoID, String thePagata,String theMandatoPagamento,Double totaleDovuto, PDFBuilder theRimborsoPDFBuilder) {
        super(userName, userSurname, userEmail);
        this.rimborsoID = theRimborsoID;
        this.pagata = thePagata;
        this.mandatoPagamento = theMandatoPagamento;
        this.totaleDovuto = totaleDovuto;
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
    protected void injectParameters(Map<String, Object> map) {
        map.put("rimborsoID", this.rimborsoID);
        map.put("pagata", this.pagata);
        map.put("mandatoPagamento", this.mandatoPagamento);
        map.put("rimborsoPDFBuilder", this.rimborsoPDFBuilder);
        map.put("totaleDovuto", this.totaleDovuto);

    }
}
