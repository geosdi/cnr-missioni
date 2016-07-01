package it.cnr.missioni.notification.message.factory.prod;

import it.cnr.missioni.notification.message.AddAnticipoPagamentoMessage;
import it.cnr.missioni.notification.message.AddMissioneMessage;
import it.cnr.missioni.notification.message.AddRimborsoMessage;
import it.cnr.missioni.notification.message.NotifyUsersMissioneMessage;
import it.cnr.missioni.notification.message.RecuperaPasswordMessage;
import it.cnr.missioni.notification.message.UpdateAnticipoPagamentoMessage;
import it.cnr.missioni.notification.message.UpdateMissioneMessage;
import it.cnr.missioni.notification.message.UpdateRimborsoMessage;
import it.cnr.missioni.notification.message.factory.NotificationMessageFactory;
import it.cnr.missioni.notification.support.itext.PDFBuilder;
import org.geosdi.geoplatform.configurator.bootstrap.Production;
import org.springframework.stereotype.Component;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Production
@Component(value = "notificationMessageProdFactory")
public class NotificationMessageProdFactory implements NotificationMessageFactory {

	/**
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param cnrMissioniEmail
	 * @param pdfBuilder
	 * @param missioneId
	 * @return {@link AddMissioneMessage}
	 */
	@Override
	public AddMissioneMessage buildAddMissioneMessage(String userName, String userSurname, String userEmail,
			String responsabileMail, String cnrMissioniEmail, PDFBuilder pdfBuilder, String missioneId) {
		return new AddMissioneMessage(userName, userSurname, userEmail, responsabileMail, cnrMissioniEmail, pdfBuilder,
				missioneId);
	}

	/**
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param missioneID
	 * @return {@link UpdateMissioneMessage}
	 */
	@Override
	public UpdateMissioneMessage buildUpdateMissioneMessage(String userName, String userSurname, String stato,
			String userEmail, String esponsabileGruppoEmail, String missioneID, PDFBuilder pdfBuilder) {
		return new UpdateMissioneMessage(userName, userSurname, stato, userEmail, esponsabileGruppoEmail, missioneID,
				pdfBuilder);
	}

	/**
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param cnrMissioniEmail
	 * @param missioneID
	 * @param pdfBuilder
	 * @return {@link AddRimborsoMessage}
	 */
	@Override
	public AddRimborsoMessage buildAddRimborsoMessage(String userName, String userSurname, String userEmail,
			String cnrMissioniEmail, String missioneID, PDFBuilder pdfBuilder) {
		return new AddRimborsoMessage(userName, userSurname, userEmail, cnrMissioniEmail, missioneID, pdfBuilder);
	}

	/**
	 * 
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param numeroOrdine
	 * @return
	 * @return
	 */
	@Override
	public UpdateRimborsoMessage buildUpdateRimborsoMessage(String userName, String userSurname, String userEmail,
			String numeroOrdine, String pagata, String avvisoPagamento, Double importoDovuto) {
		return new UpdateRimborsoMessage(userName, userSurname, userEmail, numeroOrdine, pagata, avvisoPagamento,
				importoDovuto);
	}

	/**
	 * 
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param cnrMissioniEmail
	 * @param pdfBuilder
	 * @return
	 */
	@Override
	public AddAnticipoPagamentoMessage buildAddAnticipoPagamentoMessage(String userName, String userSurname,
			String userEmail, String cnrMissioniEmail, String missioneID, PDFBuilder pdfBuilder) {
		return new AddAnticipoPagamentoMessage(userName, userSurname, userEmail, cnrMissioniEmail, missioneID,
				pdfBuilder);
	}

	/**
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param cnrMissioniEmail
	 * @param missioneID
	 * @param pdfBuilder
	 * @return
	 */
	@Override
	public UpdateAnticipoPagamentoMessage buildUpdateAnticipoPagamentoMessage(String userName, String userSurname,
			String userEmail, String cnrMissioniEmail, String missioneID, PDFBuilder pdfBuilder) {
		return new UpdateAnticipoPagamentoMessage(userName, userSurname, userEmail, cnrMissioniEmail, missioneID,
				pdfBuilder);

	}

	/**
	 * 
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param password
	 * @return
	 */
	@Override
	public RecuperaPasswordMessage buildRecuperaPasswordMessage(String userName, String userSurname, String userEmail,
			String password) {
		return new RecuperaPasswordMessage(userName, userSurname, userEmail, password);

	}

	@Override
	public NotifyUsersMissioneMessage buildUsersInMissioneMessage(PDFBuilder pdfBuilder) {
		return new NotifyUsersMissioneMessage(pdfBuilder);
	}

}
