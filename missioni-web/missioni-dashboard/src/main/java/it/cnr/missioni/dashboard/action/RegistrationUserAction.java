package it.cnr.missioni.dashboard.action;

import org.joda.time.DateTime;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.RuoloUserEnum;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public class RegistrationUserAction implements IAction {

private User user;

	public RegistrationUserAction(User user) {
		this.user = user;
	}

	public boolean doAction() {

		try {
			UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withUsername(user.getCredenziali().getUsername());
			
			UserStore userStore = ClientConnector.getUser(userSearchBuilder);
			
			
			if(userStore != null){
				Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("user_already_inserted"),
						Type.ERROR_MESSAGE);
				return false;
			}else{
				user.setRegistrazioneCompletata(false);
				user.setDataRegistrazione(new DateTime());
				user.setDateLastModified(new DateTime());
				user.getCredenziali().setPassword(user.getCredenziali().md5hash(user.getCredenziali().getPassword()));
				user.getCredenziali().setRuoloUtente(RuoloUserEnum.UTENTE_SEMPLICE);
				ClientConnector.addUser(user);
				Utility.getNotification(Utility.getMessage("success_message"),null,
						Type.HUMANIZED_MESSAGE);


				return true;
			}
			

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
