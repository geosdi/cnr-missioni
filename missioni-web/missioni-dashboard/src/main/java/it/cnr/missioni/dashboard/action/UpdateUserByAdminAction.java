package it.cnr.missioni.dashboard.action;

import org.joda.time.DateTime;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.RuoloUserEnum;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class UpdateUserByAdminAction implements IAction {

	private User user;

	public UpdateUserByAdminAction(User user ) {
		this.user = user;
	}

	public boolean doAction() {

		try {
			user.setDateLastModified(new DateTime());
			ClientConnector.updateUser(user);
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