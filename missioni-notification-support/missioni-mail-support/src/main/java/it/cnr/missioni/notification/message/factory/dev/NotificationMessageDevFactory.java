package it.cnr.missioni.notification.message.factory.dev;

import it.cnr.missioni.notification.bridge.implementor.MissioniMailImplementor;
import it.cnr.missioni.notification.message.AddAnticipoPagamentoMessage;
import it.cnr.missioni.notification.message.AddMissioneMessage;
import it.cnr.missioni.notification.message.AddRimborsoMessage;
import it.cnr.missioni.notification.message.UpdateAnticipoPagamentoMessage;
import it.cnr.missioni.notification.message.UpdateMissioneMessage;
import it.cnr.missioni.notification.message.UpdateRimborsoMessage;
import it.cnr.missioni.notification.message.factory.NotificationMessageFactory;
import it.cnr.missioni.notification.support.itext.PDFBuilder;
import org.geosdi.geoplatform.configurator.bootstrap.Develop;
import org.springframework.stereotype.Component;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Develop
@Component(value = "notificationMessageDevFactory")
public class NotificationMessageDevFactory implements NotificationMessageFactory {

	/**
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param responsabileMail
	 * @param cnrMissioniEmail
	 * @param pdfBuilder
	 * @return {@link String AddMissioneMessage}
	 */
	@Override
	public AddMissioneMessage buildAddMissioneMessage(String userName, String userSurname, String userEmail,String responsabileMail,
			String cnrMissioniEmail, PDFBuilder pdfBuilder) {
		return new AddMissioneMessage(userName, userSurname, userEmail,responsabileMail, cnrMissioniEmail, pdfBuilder) {

			/**
			 * @return {@link MissioniMailImplementor.NotificationMessageType}
			 */
			@Override
			public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
				return MissioniMailImplementor.NotificationMessageType.AGGIUNGI_MISSIONE_MAIL_DEV;
			}
		};
	}

	/**
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param cnrMissioniEmail
	 * @return {@link UpdateMissioneMessage}
	 */
	@Override
	public UpdateMissioneMessage buildUpdateMissioneMessage(String userName, String userSurname, String stato,
			String userEmail,String responsabileGruppoEmail, String missioneID, PDFBuilder pdfBuilder) {
		return new UpdateMissioneMessage(userName, userSurname, stato, userEmail,responsabileGruppoEmail, missioneID, pdfBuilder) {

			/**
			 * @return {@link MissioniMailImplementor.NotificationMessageType}
			 */
			@Override
			public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
				return MissioniMailImplementor.NotificationMessageType.MODIFICA_MISSIONE_MAIL_DEV;
			}
		};
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
		return new AddRimborsoMessage(userName, userSurname, userEmail, cnrMissioniEmail, missioneID, pdfBuilder) {

			@Override
			public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
				return MissioniMailImplementor.NotificationMessageType.RICHIESTA_RIMBORSO_MISSIONE_MAIL_DEV;
			}
		};
	}

	/**
	 * 
	 * @param userName
	 * @param userSurname
	 * @param userEmail
	 * @param numeroOrdine
	 * @param pdfBuilder
	 * @return
	 */
	@Override
	public UpdateRimborsoMessage buildUpdateRimborsoMessage(String userName, String userSurname, String userEmail,
			String numeroOrdine, String pagata,String avvisoPagamento,Double importoDovuto, PDFBuilder pdfBuilder) {
		return new UpdateRimborsoMessage(userName, userSurname, userEmail, numeroOrdine, pagata,avvisoPagamento,importoDovuto, pdfBuilder) {

			@Override
			public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
				return MissioniMailImplementor.NotificationMessageType.MODIFICA_RIMBORSO_MISSIONE_MAIL_DEV;
			}
		};
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
	public AddAnticipoPagamentoMessage buildAddAnticipoPagamentoMessage(String userName, String userSurname, String userEmail,
			String cnrMissioniEmail,String missioneID, PDFBuilder pdfBuilder) {
		return new AddAnticipoPagamentoMessage(userName, userSurname, userEmail, cnrMissioniEmail,missioneID, pdfBuilder) {

			/**
			 * @return {@link MissioniMailImplementor.NotificationMessageType}
			 */
			@Override
			public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
				return MissioniMailImplementor.NotificationMessageType.AGGIUNGI_ANTICIPO_PAGAMENTO_MAIL_DEV;
			}
		};
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
		return new UpdateAnticipoPagamentoMessage(userName, userSurname, userEmail, cnrMissioniEmail,missioneID, pdfBuilder) {

			/**
			 * @return {@link MissioniMailImplementor.NotificationMessageType}
			 */
			@Override
			public MissioniMailImplementor.NotificationMessageType getNotificationMessageType() {
				return MissioniMailImplementor.NotificationMessageType.MODIFICA_ANTICIPO_PAGAMENTO_MAIL_DEV;
			}
		};
	}
	
	
	
}
