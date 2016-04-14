package it.cnr.missioni.notification.bridge.implementor.prod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import it.cnr.missioni.notification.message.preparator.IMissioniMessagePreparator;
import it.cnr.missioni.notification.support.itext.PDFBuilder;
import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class RecuperaPasswordMailProd extends MissioniMailProd {

	private String userName;
	private String userSurname;
	private String userEmail;
	private String password;

    /**
     * @param message
     * @param velocityEngine
     * @param gpMailDetail
     * @return {@link MimeMessagePreparator}
     * @throws Exception
     */
    @Override
    public List<IMissioniMessagePreparator> prepareMessage(IMissioniMailNotificationTask.IMissioneNotificationMessage message,
            VelocityEngine velocityEngine, GPMailDetail gpMailDetail) throws Exception {
    	 List<IMissioniMessagePreparator> lista = new ArrayList<IMissioniMessagePreparator>();
    	 
         this.userName = (String) message.getMessageParameters().get("userName");
         this.userSurname = (String) message.getMessageParameters().get("userSurname");
         this.userEmail = (String) message.getMessageParameters().get("userEmail");
         this.password = (String) message.getMessageParameters().get("password");
    	 
        IMissioniMessagePreparator missioniMessagePreparator = super.createMissioniMessagePreparator();
        missioniMessagePreparator.setMimeMessagePreparator(new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = createMimeMessageHelper(mimeMessage, gpMailDetail, Boolean.TRUE);
                message.setTo(new String[]{userEmail});
                Map model = new HashMap();
                model.put("userName", userName);
                model.put("userSurname", userSurname);
                model.put("password", password);
                String messageText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        "template/recuperaPasswordMailNotification.html.vm", "UTF-8", model);
                message.setText(messageText, Boolean.TRUE);
            }
        });
        lista.add(missioniMessagePreparator);
        return lista;
    }
    
	/**
	 * @return
	 */
	@Override
	protected String getSubjectMessage() {
		return "Recupero Password";
	}

    /**
     * @return {@link ImplementorKey}
     */
    @Override
    public ImplementorKey getKey() {
        return NotificationMessageType.RECUPERA_PASSWORD_MAIL_PROD;
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
