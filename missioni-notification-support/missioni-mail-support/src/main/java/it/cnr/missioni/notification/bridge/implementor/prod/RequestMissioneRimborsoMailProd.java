package it.cnr.missioni.notification.bridge.implementor.prod;

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
public class RequestMissioneRimborsoMailProd extends MissioniMailProd {
	
    private String userName;
    private String userSurname;
    private String userEmail;
    private String cnrMissioniEmail;
    private String missioneID;
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
    public List<IMissioniMessagePreparator> prepareMessage(IMissioniMailNotificationTask.IMissioneNotificationMessage message,
            VelocityEngine velocityEngine, GPMailDetail gpMailDetail) throws Exception {
    	
        List<IMissioniMessagePreparator> lista = new ArrayList<IMissioniMessagePreparator>();
         this.userName = (String) message.getMessageParameters().get("userName");
         this.userSurname = (String) message.getMessageParameters().get("userSurname");
         this.userEmail = (String) message.getMessageParameters().get("userEmail");
         this.cnrMissioniEmail = (String) message.getMessageParameters().get("cnrMissioniEmail");
         this.missioneID = (String) message.getMessageParameters().get("missioneID");
         this.pdfBuilder = (PDFBuilder) message.getMessageParameters().get("rimborsoPDFBuilder");
         
         Path tempFilePath = Files.createTempFile("Rimborso-Missione - ".concat(userSurname).concat("-"), ".pdf");
         this.file = tempFilePath.toFile();
         pdfBuilder.withFile(file);
         pdfBuilder.build();
        IMissioniMessagePreparator missioniMessagePreparatorUser = super.createMissioniMessagePreparator();
        missioniMessagePreparatorUser.setMimeMessagePreparator(new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = createMimeMessageHelper(mimeMessage, gpMailDetail, Boolean.TRUE);
                message.setTo(new String[]{userEmail});
                Map model = new HashMap();
                model.put("userName", userName);
                model.put("userSurname", userSurname);
                model.put("missioneID", missioneID);
                String messageText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        "template/addRimborsoMailNotificationUser.html.vm", "UTF-8", model);
                message.setText(messageText, Boolean.TRUE);
                createAttachment(message, missioniMessagePreparatorUser);
            }
        });
        lista.add(missioniMessagePreparatorUser);
        
        IMissioniMessagePreparator missioniMessagePreparatorAdmin = super.createMissioniMessagePreparator();
        missioniMessagePreparatorAdmin.setMimeMessagePreparator(new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = createMimeMessageHelper(mimeMessage, gpMailDetail, Boolean.TRUE);
                message.setTo(new String[]{cnrMissioniEmail});
                Map model = new HashMap();
                model.put("userName", userName);
                model.put("userSurname", userSurname);
                model.put("missioneID", missioneID);
                String messageText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        "template/addRimborsoMailNotificationAdmin.html.vm", "UTF-8", model);
                message.setText(messageText, Boolean.TRUE);
                createAttachment(message, missioniMessagePreparatorAdmin);

            }
        });
        lista.add(missioniMessagePreparatorAdmin);
        
        return lista;
    }
    
	private void createAttachment(MimeMessageHelper message, IMissioniMessagePreparator missioniMessagePreparator)
			throws Exception {
        message.addAttachment(file.getName(), file);
        missioniMessagePreparator.addAttachment(file);
	}

    /**
     * @return {@link ImplementorKey}
     */
    @Override
    public ImplementorKey getKey() {
        return NotificationMessageType.RICHIESTA_RIMBORSO_MISSIONE_MAIL_PROD;
    }

	/**
	 * @return
	 */
	@Override
	protected String getSubjectMessage() {
		return "Rimborso-".concat(userSurname).concat("-Ordine di Missione:").concat(missioneID);
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
