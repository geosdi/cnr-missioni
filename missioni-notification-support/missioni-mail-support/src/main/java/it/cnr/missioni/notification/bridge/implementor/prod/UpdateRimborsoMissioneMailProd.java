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
public class UpdateRimborsoMissioneMailProd extends MissioniMailProd {

	
	private String userName;
	private String userSurname;
	private String userEmail;
	private String rimborsoID;
	private String pagata;
	private String mandatoPagamento;
	private Double totaleDovuto;

	
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
         this.rimborsoID = (String) message.getMessageParameters().get("rimborsoID");
         this.pagata = (String) message.getMessageParameters().get("pagata");
         this.mandatoPagamento = (String) message.getMessageParameters().get("mandatoPagamento");
         this.totaleDovuto = (Double) message.getMessageParameters().get("totaleDovuto");
    	 
        IMissioniMessagePreparator missioniMessagePreparator = super.createMissioniMessagePreparator();
        missioniMessagePreparator.setMimeMessagePreparator(new MimeMessagePreparator() {


            
            PDFBuilder pdfBuilder = (PDFBuilder) message.getMessageParameters().get("rimborsoPDFBuilder");
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = createMimeMessageHelper(mimeMessage, gpMailDetail, Boolean.TRUE);
                message.setTo(new String[]{userEmail});
                Map model = new HashMap();
                model.put("userName", userName);
                model.put("userSurname", userSurname);
                model.put("rimborsoID", rimborsoID);
                model.put("pagata", pagata);
                model.put("mandatoPagamento", mandatoPagamento);
                model.put("totaleDovuto", totaleDovuto);


                String messageText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        "template/updateRimborsoMissioneMailNotification.html.vm", "UTF-8", model);
                message.setText(messageText, Boolean.TRUE);

                Path tempFilePath = Files.createTempFile("Rimborso-Missione - ".concat(userSurname).concat("-"), ".pdf");
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
	 * @return
	 */
	@Override
	protected String getSubjectMessage() {
		return "Update Rimborso-".concat(rimborsoID).concat("-").concat(userSurname);
	}

    /**
     * @return {@link ImplementorKey}
     */
    @Override
    public ImplementorKey getKey() {
        return NotificationMessageType.MODIFICA_RIMBORSO_MISSIONE_MAIL_PROD;
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
