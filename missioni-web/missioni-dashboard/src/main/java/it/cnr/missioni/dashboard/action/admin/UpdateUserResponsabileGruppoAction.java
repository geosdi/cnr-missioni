package it.cnr.missioni.dashboard.action.admin;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.action.IAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class UpdateUserResponsabileGruppoAction implements IAction {

	private User user;

	public UpdateUserResponsabileGruppoAction(User user) {
		this.user = user;
	}

	public boolean doAction() {

		try {

			ClientConnector.updateUser(user);

			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
			DashboardEventBus.post(new DashboardEvent.MenuUpdateEvent());
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), null, Type.ERROR_MESSAGE);
			return false;
		}

	}

}
