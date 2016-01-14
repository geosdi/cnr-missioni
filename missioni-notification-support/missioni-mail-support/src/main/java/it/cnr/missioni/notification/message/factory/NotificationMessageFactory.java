package it.cnr.missioni.notification.message.factory;

import it.cnr.missioni.notification.message.AddMissioneMessage;
import it.cnr.missioni.notification.message.UpdateMissioneMessage;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface NotificationMessageFactory {

    /**
     * @param userName
     * @param userSurname
     * @param userEmail
     * @param cnrMissioniEmail
     * @return {@link AddMissioneMessage}
     */
    AddMissioneMessage buildAddMissioneMessage(String userName, String userSurname, String userEmail,
            String cnrMissioniEmail);

    /**
     * @param userName
     * @param userSurname
     * @param userEmail
     * @param cnrMissioniEmail
     * @param missioneID
     * @return {@link  UpdateMissioneMessage}
     */
    UpdateMissioneMessage buildUpdateMissioneMessage(String userName, String userSurname, String userEmail,
            String cnrMissioniEmail, String missioneID);
}
