package it.cnr.missioni.dashboard.action.admin;

import org.joda.time.DateTime;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.action.IAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;

/**
 * @author Salvia Vito
 */
public class UpdateRimborsoAction implements IAction {

	private Missione missione;

	public UpdateRimborsoAction(Missione missione) {
		this.missione = missione;
	}

	public boolean doAction() {

		try {

			missione.getRimborso().setDateLastModified(new DateTime());
			
			if(missione.getRimborso().isPagata())
				missione.setStato(StatoEnum.PAGATA);
			
			ClientConnector.updateRimborso(missione);
			Thread.sleep(1000);	
			DashboardEventBus.post(new DashboardEvent.TableRimborsiUpdatedEvent());
			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);

			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
