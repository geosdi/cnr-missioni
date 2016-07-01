package it.cnr.missioni.notification.message.factory;

import it.cnr.missioni.notification.message.AddAnticipoPagamentoMessage;
import it.cnr.missioni.notification.message.AddMissioneMessage;
import it.cnr.missioni.notification.message.AddRimborsoMessage;
import it.cnr.missioni.notification.message.NotifyUsersMissioneMessage;
import it.cnr.missioni.notification.message.RecuperaPasswordMessage;
import it.cnr.missioni.notification.message.UpdateAnticipoPagamentoMessage;
import it.cnr.missioni.notification.message.UpdateMissioneMessage;
import it.cnr.missioni.notification.message.UpdateRimborsoMessage;
import it.cnr.missioni.notification.support.itext.PDFBuilder;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface NotificationMessageFactory {

	/**
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param responsabileMail
	 * @param cnrMissioniEmail
	 * @param pdfBuilder
	 * @param missioneId
	 * @return {@link AddMissioneMessage}
	 */
	AddMissioneMessage buildAddMissioneMessage(String userName, String userSurname, String userEmail,
			String responsabileMail, String cnrMissioniEmail, PDFBuilder pdfBuilder, String missioneId);

	/**
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param cnrMissioniEmail
	 * @param missioneID
	 * @return {@link UpdateMissioneMessage}
	 */
	UpdateMissioneMessage buildUpdateMissioneMessage(String userName, String userSurname, String stato,
			String userEmail, String responsabileGruppoEmail, String missioneID, PDFBuilder pdfBuilder);

	/**
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param cnrMissioniEmail
	 * @param missioneID
	 * @param pdfBuilder
	 * @return {@link AddRimborsoMessage}
	 */
	AddRimborsoMessage buildAddRimborsoMessage(String userName, String userSurname, String userEmail,
			String cnrMissioniEmail, String missioneID, PDFBuilder pdfBuilder);

	/**
	 * 
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param rimborsoID
	 * @param pagata
	 * @param avvisoPagamento
	 * @param importoDovuto
	 * @return
	 */
	UpdateRimborsoMessage buildUpdateRimborsoMessage(String userName, String userSurname, String userEmail,
			String rimborsoID, String pagata, String avvisoPagamento, Double importoDovuto);

	/**
	 * 
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param cnrMissioniEmail
	 * @param missioneID
	 * @param pdfBuilder
	 * @return
	 */
	AddAnticipoPagamentoMessage buildAddAnticipoPagamentoMessage(String userName, String userSurname, String userEmail,
			String cnrMissioniEmail, String missioneID, PDFBuilder pdfBuilder);

	/**
	 * 
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param cnrMissioniEmail
	 * @param pdfBuilder
	 * @return
	 */
	UpdateAnticipoPagamentoMessage buildUpdateAnticipoPagamentoMessage(String userName, String userSurname,
			String userEmail, String cnrMissioniEmail, String missioneID, PDFBuilder pdfBuilder);

	/**
	 * 
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param password
	 * @return
	 */
	RecuperaPasswordMessage buildRecuperaPasswordMessage(String userName, String userSurname, String userEmail,
			String password);
	
	/**
	 * 
	 * @param pdfBuilder
	 * @return
	 */
	NotifyUsersMissioneMessage buildUsersInMissioneMessage(PDFBuilder pdfBuilder);
}
