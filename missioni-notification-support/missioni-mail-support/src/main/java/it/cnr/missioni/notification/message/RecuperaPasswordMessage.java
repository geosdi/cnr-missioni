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
public class RecuperaPasswordMessage extends CNRMissioniMessage {
	
    private final String password;
    private final String userName;
    private final String userSurname;
    private final String userEmail;

    public RecuperaPasswordMessage(String userName, String userSurname, String userEmail,
             String password) {
        super(userName, userSurname, userEmail);
        this.password = password;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userSurname = userSurname;
    }

    /**
     * @return {@link MissioniMailImplementor.NotificationMessageType}
     */
    @Override
    public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
        return MissioniMailImplementor.NotificationMessageType.RECUPERA_PASSWORD_MAIL_PROD;
    }

    /**
     * @param map
     */
    protected void injectParameters(Map<String, Object> map) {
        map.put("password", this.password);
        map.put("userEmail", this.userEmail);
        map.put("userName", this.userName);
        map.put("userSurname", this.userSurname);
    }
}
