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
public class UpdateAnticipoPagamentoMailProd extends MissioniMailProd {

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

		
		IMissioniMessagePreparator missioniMessagePreparator = super.createMissioniMessagePreparator();
		missioniMessagePreparator.setMimeMessagePreparator(new MimeMessagePreparator() {

			String userName = (String) message.getMessageParameters().get("userName");
			String userSurname = (String) message.getMessageParameters().get("userSurname");
			String userEmail = (String) message.getMessageParameters().get("userEmail");
			String missioneID = (String) message.getMessageParameters().get("missioneID");

			PDFBuilder pdfBuilder = (PDFBuilder) message.getMessageParameters().get("anticipoPagamentoPDFBuilder");

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = createMimeMessageHelper(mimeMessage, gpMailDetail, Boolean.TRUE);
				message.setTo(new String[] { userEmail });
				Map model = new HashMap();
				model.put("userName", userName);
				model.put("userSurname", userSurname);
				model.put("missioneID", missioneID);

				String messageText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						"template/updateAnticipoPagamentoMailNotificationUser.html.vm", "UTF-8", model);
				message.setText(messageText, Boolean.TRUE);

				Path tempFilePath = Files.createTempFile("Anticipo Pagamento".concat("-").concat(missioneID).concat("-") .concat(userName), ".pdf");
				File file = tempFilePath.toFile();

				pdfBuilder.withFile(file);
				pdfBuilder.build();
				message.addAttachment(file.getName(), file);
				missioniMessagePreparator.addAttachment(file);
				

			}
		});
		lista.add(missioniMessagePreparator);
		return lista;
	}

	/**
	 * @return {@link Implementor.ImplementorKey}
	 */
	@Override
	public Implementor.ImplementorKey getKey() {
		return MissioniMailImplementor.NotificationMessageType.MODIFICA_ANTICIPO_PAGAMENTO_MAIL_PROD;
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
