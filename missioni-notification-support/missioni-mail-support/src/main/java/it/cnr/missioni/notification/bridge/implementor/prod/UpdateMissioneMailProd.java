package it.cnr.missioni.notification.bridge.implementor.prod;

import it.cnr.missioni.notification.bridge.implementor.Implementor;
import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;
import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class UpdateMissioneMailProd extends MissioniMailProd {

    /**
     * @param message
     * @param velocityEngine
     * @param gpMailDetail
     * @return {@link MimeMessagePreparator}
     * @throws Exception
     */
    @Override
    public MimeMessagePreparator prepareMessage(IMissioniMailNotificationTask.IMissioneNotificationMessage message,
            VelocityEngine velocityEngine, GPMailDetail gpMailDetail) throws Exception {
        return null;
    }

    /**
     * @return {@link Implementor.ImplementorKey}
     */
    @Override
    public Implementor.ImplementorKey getKey() {
        return MissioniMailImplementor.NotificationMessageType.MODIFICA_MISSIONE_MAIL_PROD;
    }

    /**
     * <p>
     * Specify if {@link Implementor.ImplementorKey} is valid or not
     * </p>
     *
     * @return {@link Boolean}
     */
    @Override
    public Boolean isImplementorValid() {
        return Boolean.TRUE;
    }
}
