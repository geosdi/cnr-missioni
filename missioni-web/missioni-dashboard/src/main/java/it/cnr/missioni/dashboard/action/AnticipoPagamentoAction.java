package it.cnr.missioni.dashboard.action;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class AnticipoPagamentoAction implements IAction {

	private Missione missione;

	public AnticipoPagamentoAction(Missione missione) {
		this.missione = missione;
	}

	public boolean doAction() {

		try {

			ClientConnector.updateMissioneForAnticipo(missione);
			Thread.sleep(1000);	
			DashboardEventBus.post(new DashboardEvent.TableMissioniUpdateUpdatedEvent());
			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);

			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
