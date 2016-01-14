package it.cnr.missioni.notification.message.factory.dev;

import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import it.cnr.missioni.notification.message.AddMissioneMessage;
import it.cnr.missioni.notification.message.UpdateMissioneMessage;
import it.cnr.missioni.notification.message.factory.NotificationMessageFactory;
import org.geosdi.geoplatform.configurator.bootstrap.Develop;
import org.springframework.stereotype.Component;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Develop
@Component(value = "notificationMessageDevFactory")
public class NotificationMessageDevFactory implements NotificationMessageFactory {

    /**
     * @param userName
     * @param userSurname
     * @param userEmail
     * @param cnrMissioniEmail
     * @return {@link String AddMissioneMessage}
     */
    @Override
    public AddMissioneMessage buildAddMissioneMessage(String userName, String userSurname, String userEmail,
            String cnrMissioniEmail) {
        return new AddMissioneMessage(userName, userSurname, userEmail, cnrMissioniEmail) {

            /**
             * @return {@link MissioniMailImplementor.NotificationMessageType}
             */
            @Override
            public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
                return MissioniMailImplementor.NotificationMessageType.AGGIUNGI_MISSIONE_MAIL_DEV;
            }
        };
    }

    /**
     * @param userName
     * @param userSurname
     * @param userEmail
     * @param cnrMissioniEmail
     * @return {@link  UpdateMissioneMessage}
     */
    @Override
    public UpdateMissioneMessage buildUpdateMissioneMessage(String userName, String userSurname, String userEmail,
            String cnrMissioniEmail, String missioneID) {
        return new UpdateMissioneMessage(userName, userSurname, userEmail, cnrMissioniEmail, missioneID) {

            /**
             * @return {@link MissioniMailImplementor.NotificationMessageType}
             */
            @Override
            public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
                return MissioniMailImplementor.NotificationMessageType.MODIFICA_MISSIONE_MAIL_DEV;
            }
        };
    }
}
