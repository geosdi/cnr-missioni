package it.cnr.missioni.dashboard.request;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.Credenziali;
import it.cnr.missioni.model.user.RuoloUtenteEnum;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class RegistrationRequest implements IRequest {

	private String username;
	private String password;
	private String repeatPassword;

	public RegistrationRequest(String username, String password, String repeatPassword) {
		this.username = username;
		this.password = password;
		this.repeatPassword = repeatPassword;
	}

	public boolean callRestService() {
		if (username == null || username.equals("")) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("username_field_empty"),
					Type.ERROR_MESSAGE);
			return false;
		} else if (password == null || password.equals("")) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("password_field_empty"),
					Type.ERROR_MESSAGE);
			return false;
		} else if (!password.equals(repeatPassword)) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("password_not_equals"),
					Type.ERROR_MESSAGE);
			return false;
		} else {

			try {
				User user = new User();
				Credenziali credenziali = new Credenziali();
				credenziali.setPassword(credenziali.md5hash(password));
				credenziali.setUsername(username);
				credenziali.setRuoloUtente(RuoloUtenteEnum.UTENTE_SEMPLICE);
				user.setCredenziali(credenziali);
				ClientConnector.addUser(user);
				Utility.getNotification(Utility.getMessage("success_message"),
						Utility.getMessage("registration_success"), Type.HUMANIZED_MESSAGE);

				return true;
			} catch (Exception e) {
				Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
						Type.HUMANIZED_MESSAGE);
				return false;
			}
		}

	}

}
