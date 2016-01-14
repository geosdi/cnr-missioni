package it.cnr.missioni.notification.message.factory.prod;

import it.cnr.missioni.notification.message.AddMissioneMessage;
import it.cnr.missioni.notification.message.UpdateMissioneMessage;
import it.cnr.missioni.notification.message.factory.NotificationMessageFactory;
import org.geosdi.geoplatform.configurator.bootstrap.Production;
import org.springframework.stereotype.Component;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Production
@Component(value = "notificationMessageProdFactory")
public class NotificationMessageProdFactory implements NotificationMessageFactory {

    /**
     * @param userName
     * @param userSurname
     * @param userEmail
     * @param cnrMissioniEmail
     * @return {@link  AddMissioneMessage}
     */
    @Override
    public AddMissioneMessage buildAddMissioneMessage(String userName, String userSurname, String userEmail,
            String cnrMissioniEmail) {
        return new AddMissioneMessage(userName, userSurname, userEmail, cnrMissioniEmail);
    }

    /**
     * @param userName
     * @param userSurname
     * @param userEmail
     * @param cnrMissioniEmail
     * @param missioneID
     * @return {@link UpdateMissioneMessage}
     */
    @Override
    public UpdateMissioneMessage buildUpdateMissioneMessage(String userName, String userSurname, String userEmail,
            String cnrMissioniEmail, String missioneID) {
        return new UpdateMissioneMessage(userName, userSurname, userEmail, cnrMissioniEmail, missioneID);
    }
}
