package it.cnr.missioni.notification.bridge.implementor.prod;

import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;
import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class AddMissioneMailProd extends MissioniMailProd {

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
        return new MimeMessagePreparator() {

            String userName = (String) message.getMessageParameters().get("userName");
            String userSurname = (String) message.getMessageParameters().get("userSurname");
            String userEmail = (String) message.getMessageParameters().get("userEmail");
            String cnrMissioniEmail = (String) message.getMessageParameters().get("cnrMissioniEmail");

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = createMimeMessageHelper(mimeMessage, gpMailDetail);
                message.setTo(new String[] {userEmail, cnrMissioniEmail});
                Map model = new HashMap();
                model.put("userName", userName);
                model.put("userSurname", userSurname);
                String messageText = VelocityEngineUtils
                        .mergeTemplateIntoString(velocityEngine,
                                "template/addMissioneMailNotification.html.vm", "UTF-8", model);
                message.setText(messageText, Boolean.TRUE);
            }
        };
    }

    /**
     * @return {@link ImplementorKey}
     */
    @Override
    public ImplementorKey getKey() {
        return NotificationMessageType.AGGIUNGI_MISSIONE_MAIL_PROD;
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
