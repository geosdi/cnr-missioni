package it.cnr.missioni.notification.bridge.implementor.prod;

import it.cnr.missioni.notification.bridge.implementor.Implementor;
import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import it.cnr.missioni.notification.message.preparator.IMissioniMessagePreparator;
import it.cnr.missioni.notification.support.itext.PDFBuilder;
import it.cnr.missioni.notification.task.IMissioniMailNotificationTask;
import org.apache.velocity.app.VelocityEngine;
import org.geosdi.geoplatform.support.mail.configuration.detail.GPMailDetail;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;

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
public class UpdateMissioneMailProd extends MissioniMailProd {

	private PDFBuilder pdfBuilder;
	private String userName;
	private String userSurname;
	private String userEmail;
	private String responsabileEmail;
	private File file;
	private File fileVeicolo;
	private String missioneID;
	private String stato;

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

		List<IMissioniMessagePreparator> lista = new ArrayList<IMissioniMessagePreparator>();
		this.userName = (String) message.getMessageParameters().get("userName");
		this.userSurname = (String) message.getMessageParameters().get("userSurname");
		this.userEmail = (String) message.getMessageParameters().get("userEmail");
		this.missioneID = (String) message.getMessageParameters().get("missioneID");
		this.stato = (String) message.getMessageParameters().get("stato");
		this.responsabileEmail = (String) message.getMessageParameters().get("responsabileEmail");

		
		this.pdfBuilder = (PDFBuilder) message.getMessageParameters().get("missionePDFBuilder");

		Path tempFilePath = Files.createTempFile("Missione - ".concat(userSurname).concat("-"), ".pdf");
		file = tempFilePath.toFile();
		pdfBuilder.withFile(file);
		pdfBuilder.build();

		if (pdfBuilder.isMezzoProprio()) {
			Path tempFilePathVeicolo = Files.createTempFile("Modulo Mezzo Proprio - ".concat(userName), ".pdf");
			fileVeicolo = tempFilePathVeicolo.toFile();
			pdfBuilder.withFileVeicolo(fileVeicolo);
			pdfBuilder.buildVeicolo();
		}

		IMissioniMessagePreparator missioniMessagePreparatorResponsabileGruppo = super.createMissioniMessagePreparator();
		missioniMessagePreparatorResponsabileGruppo.setMimeMessagePreparator(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = createMimeMessageHelper(mimeMessage, gpMailDetail, Boolean.TRUE);
				message.setTo(new String[] { responsabileEmail });

				Map model = new HashMap();
				model.put("userName", userName);
				model.put("userSurname", userSurname);
				model.put("missioneID", missioneID);
				model.put("stato", stato);

				String messageText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"template/updateMissioneMailNotificationResponsabileGruppo.html.vm", "UTF-8", model);

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
				model.put("missioneID", missioneID);
				model.put("stato", stato);

				String messageText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"template/updateMissioneMailNotification.html.vm", "UTF-8", model);
				message.setText(messageText, Boolean.TRUE);
				createAttachment(message, missioniMessagePreparatorResponsabileGruppo);
				createAttachmentVeicoloProprio(message, missioniMessagePreparatorResponsabileGruppo);

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
		return "Update Ordine di Missione:".concat(missioneID).concat("-").concat(userSurname);
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
