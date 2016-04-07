package it.cnr.missioni.notification.bridge.implementor.prod;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class AddMissioneMailProd extends MissioniMailProd {

	private PDFBuilder pdfBuilder;
	private String userName;
	private String userSurname;
	private String userEmail;
	private String cnrMissioniEmail;
	private String responsabileEmail;
	private File file;
	private File fileVeicolo;
	private String missioneId;

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

		this.userName = (String) message.getMessageParameters().get("userName");
		this.userSurname = (String) message.getMessageParameters().get("userSurname");
		this.userEmail = (String) message.getMessageParameters().get("userEmail");
		this.cnrMissioniEmail = (String) message.getMessageParameters().get("cnrMissioniEmail");
		this.responsabileEmail = (String) message.getMessageParameters().get("responsabileEmail");
		this.pdfBuilder = (PDFBuilder) message.getMessageParameters().get("missionePDFBuilder");
		this.missioneId = (String) message.getMessageParameters().get("missioneId");

		Path tempFilePath = Files.createTempFile("Missione - ".concat(userSurname).concat("-"), ".pdf");
		file = tempFilePath.toFile();
		pdfBuilder.withFile(file);
		pdfBuilder.build();
		if (pdfBuilder.isMezzoProprio()) {
			Path tempFilePathVeicolo = Files.createTempFile("Modulo Mezzo Proprio - ".concat(userSurname).concat("-"), ".pdf");
			fileVeicolo = tempFilePathVeicolo.toFile();
			pdfBuilder.withFileVeicolo(fileVeicolo);
			pdfBuilder.buildVeicolo();
		}
		List<IMissioniMessagePreparator> lista = new ArrayList<IMissioniMessagePreparator>();
		IMissioniMessagePreparator missioniMessagePreparatorAdmin = super.createMissioniMessagePreparator();
		missioniMessagePreparatorAdmin.setMimeMessagePreparator(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = createMimeMessageHelper(mimeMessage, gpMailDetail, Boolean.TRUE);
				message.setTo(new String[] { cnrMissioniEmail });


				Map model = new HashMap();
				model.put("userName", userName);
				model.put("userSurname", userSurname);
				String messageText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"template/addMissioneMailNotificationAdmin.html.vm", "UTF-8", model);
				message.setText(messageText, Boolean.TRUE);
				createAttachment(message, missioniMessagePreparatorAdmin);
				createAttachmentVeicoloProprio(message, missioniMessagePreparatorAdmin);
			}
		});
		lista.add(missioniMessagePreparatorAdmin);
		IMissioniMessagePreparator missioniMessagePreparatorResponsabileGruppo = super.createMissioniMessagePreparator();
		missioniMessagePreparatorResponsabileGruppo.setMimeMessagePreparator(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = createMimeMessageHelper(mimeMessage, gpMailDetail, Boolean.TRUE);
				message.setTo(new String[] { responsabileEmail });
				Map model = new HashMap();
				model.put("userName", userName);
				model.put("userSurname", userSurname);
				String messageText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"template/addMissioneMailNotificationResponsabileGruppo.html.vm", "UTF-8", model);
				message.setText(messageText, Boolean.TRUE);
				createAttachment(message, missioniMessagePreparatorResponsabileGruppo);
				createAttachmentVeicoloProprio(message, missioniMessagePreparatorResponsabileGruppo);
			}
		});
		lista.add(missioniMessagePreparatorResponsabileGruppo);
		
		IMissioniMessagePreparator missioniMessagePreparatorUser = super.createMissioniMessagePreparator();
		missioniMessagePreparatorUser.setMimeMessagePreparator(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = createMimeMessageHelper(mimeMessage, gpMailDetail, Boolean.TRUE);
				message.setTo(new String[] { userEmail });
				Map model = new HashMap();
				model.put("userName", userName);
				model.put("userSurname", userSurname);
				String messageText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"template/addMissioneMailNotificationUser.html.vm", "UTF-8", model);
				message.setText(messageText, Boolean.TRUE);
				createAttachment(message, missioniMessagePreparatorUser);
				createAttachmentVeicoloProprio(message, missioniMessagePreparatorUser);
			}
		});
		lista.add(missioniMessagePreparatorUser);
		return lista;
	}

	private void createAttachment(MimeMessageHelper message, IMissioniMessagePreparator missioniMessagePreparator)
			throws Exception {
		message.addAttachment(this.file.getName(), this.file);
		missioniMessagePreparator.addAttachment(this.file);
	}

	private void createAttachmentVeicoloProprio(MimeMessageHelper message,
			IMissioniMessagePreparator missioniMessagePreparator) throws Exception {
		if (pdfBuilder.isMezzoProprio()) {
			message.addAttachment(this.fileVeicolo.getName(), this.fileVeicolo);
			missioniMessagePreparator.addAttachment(this.fileVeicolo);
		}
	}
	
	/**
	 * @return
	 */
	@Override
	protected String getSubjectMessage() {
		return "Ordine di Missione-".concat(this.missioneId).concat("-").concat(userSurname);
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
