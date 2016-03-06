package it.cnr.missioni.notification.bridge.implementor.dev;

import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;
import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class UpdateMissioneMailDev extends MissioniMailDev {

    /**
     * @param message
     * @param velocityEngine
     * @param gpMailDetail
     * @return {@link String}
     * @throws Exception
     */
    @Override
    public String[] prepareMessage(IMissioniMailNotificationTask.IMissioneNotificationMessage message,
            VelocityEngine velocityEngine, GPMailDetail gpMailDetail) throws Exception {
        String userName = (String) message.getMessageParameters().get("userName");
        String userSurname = (String) message.getMessageParameters().get("userSurname");
        String missioneID = (String) message.getMessageParameters().get("missioneID");
        String stato = (String) message.getMessageParameters().get("stato");


        Map model = new HashMap();
        model.put("userName", userName);
        model.put("userSurname", userSurname);
        model.put("missioneID", missioneID);
        model.put("stato", stato);
        String[] lista = new String[1];
        lista[0] = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                "template/updateMissioneMailNotification.html.vm", "UTF-8", model);
        return lista;
    }

    /**
     * @return {@link ImplementorKey}
     */
    @Override
    public ImplementorKey getKey() {
        return NotificationMessageType.MODIFICA_MISSIONE_MAIL_DEV;
    }

    /**
     * <p>
     * Specify if {@link ImplementorKey} is valid or not
     * </p>
     *
     * @return {@link Boolean}
     */
    @Override
    public Boolean isImplementorValid() {
        return Boolean.TRUE;
    }
}
