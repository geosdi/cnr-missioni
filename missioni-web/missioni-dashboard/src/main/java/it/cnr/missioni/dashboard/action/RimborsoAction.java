package it.cnr.missioni.dashboard.action;

import org.joda.time.DateTime;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class RimborsoAction implements IAction {

	private Missione missione;

	public RimborsoAction(Missione missione) {
		this.missione = missione;
	}

	public boolean doAction() {

		try {

			if (missione.getRimborso().getDataRimborso() == null)
				missione.getRimborso().setDataRimborso(new DateTime());
			missione.getRimborso().setDateLastModified(new DateTime());

			ClientConnector.updateMissione(missione);
			Thread.sleep(1000);
			DashboardEventBus.post(new DashboardEvent.TableRimborsiUpdatedEvent());

			ClientConnector.sendRimborsoMail(missione.getId());
			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);

			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
