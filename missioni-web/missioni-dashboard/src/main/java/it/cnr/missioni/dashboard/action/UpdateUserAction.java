package it.cnr.missioni.dashboard.action;

import org.joda.time.DateTime;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.RuoloUtenteEnum;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class UpdateUserAction implements IAction {

	private User user;

	public UpdateUserAction(User user) {
		this.user = user;
	}

	public boolean doAction() {

		try {
			boolean value = user.isRegistrazioneCompletata();
			user.setRegistrazioneCompletata(true);
			user.setDateLastModified(new DateTime());
			user.getCredenziali().setPassword(user.getCredenziali().getPassword());
			user.getCredenziali().setRuoloUtente(RuoloUtenteEnum.UTENTE_SEMPLICE);
			ClientConnector.updateUser(user);
			VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
			Utility.getNotification(Utility.getMessage("success_message"), Utility.getMessage("success_message"),
					Type.HUMANIZED_MESSAGE);

			DashboardEventBus.post(new  DashboardEvent.ProfileUpdatedEvent() );
			if(!value)
				DashboardEventBus.post(new  DashboardEvent.MenuUpdateEvent() );
			
			Utility.getNotification(Utility.getMessage("success_message"), null,
					Type.HUMANIZED_MESSAGE);
			
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), null,
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
