package it.cnr.missioni.notification.bridge.implementor.dev;

import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;
import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class NotifyUsersInMissioneMailDev extends MissioniMailDev {

    /**
     * @param message
     * @param velocityEngine
     * @param gpMailDetail
     * @return {@link String}
     * @throws Exception
     */
    @Override
    public List<String> prepareMessage(IMissioniMailNotificationTask.IMissioneNotificationMessage message,
            VelocityEngine velocityEngine, GPMailDetail gpMailDetail) throws Exception {

        Map model = new HashMap();
        List<String> lista = new ArrayList<String>();
        lista.add( VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                "template/usersInMissioneNotification.html.vm", "UTF-8", model));
        return lista;
    }

    /**
     * @return {@link ImplementorKey}
     */
    @Override
    public ImplementorKey getKey() {
        return NotificationMessageType.NOTIFY_USERS_IN_MISSIONE_MAIL_DEV;
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
