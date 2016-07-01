package it.cnr.missioni.notification.bridge.implementor.prod;

import it.cnr.missioni.notification.message.preparator.IMissioniMessagePreparator;
import it.cnr.missioni.notification.support.itext.PDFBuilder;
import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;
import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class NotifyUsersInMissioneMailProd extends MissioniMailProd {

	private PDFBuilder pdfBuilder;
	private File file;

	/**
	 * @param message
	 * @param velocityEngine
	 * @param gpMailDetail
	 * @return {@link MimeMessagePreparator}
	 * @throws Exception
	 */
	@Override
	public List<IMissioniMessagePreparator> prepareMessage(
			IMissioniMailNotificationTask.IMissioneNotificationMessage message, VelocityEngine velocityEngine,
			GPMailDetail gpMailDetail) throws Exception {
		this.pdfBuilder = (PDFBuilder) message.getMessageParameters().get("missionePDFBuilder");
		Path tempFilePath = Files.createTempFile("UsersMissione", ".pdf");
		file = tempFilePath.toFile();
		pdfBuilder.withFile(file);
		pdfBuilder.build();
		List<IMissioniMessagePreparator> lista = new ArrayList<IMissioniMessagePreparator>();
		IMissioniMessagePreparator missioniMessagePreparator = super.createMissioniMessagePreparator();
		missioniMessagePreparator.setMimeMessagePreparator(mimeMessage -> {
            MimeMessageHelper message1 = createMimeMessageHelper(mimeMessage, gpMailDetail, Boolean.TRUE);
            message1.setTo(new String[] { "vito.salvia@gmail.com" });
            Map model = new HashMap();
            String messageText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "template/usersInMissioneNotification.html.vm", "UTF-8", model);
            message1.setText(messageText, Boolean.TRUE);
            createAttachment(message1, missioniMessagePreparator);
        });
		lista.add(missioniMessagePreparator);
		return lista;
	}

	private void createAttachment(MimeMessageHelper message, IMissioniMessagePreparator missioniMessagePreparator)
			throws Exception {
		message.addAttachment(this.file.getName(), this.file);
		missioniMessagePreparator.addAttachment(this.file);
	}
	
	/**
	 * @return
	 */
	@Override
	protected String getSubjectMessage() {
		return "Elenco Users in Missione";
	}
	

	/**
	 * @return {@link ImplementorKey}
	 */
	@Override
	public ImplementorKey getKey() {
		return NotificationMessageType.NOTIFY_USERS_IN_MISSIONE_MAIL_PROD;
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
