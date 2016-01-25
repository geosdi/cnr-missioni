package it.cnr.missioni.dashboard.action;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;

/**
 * @author Salvia Vito
 */
public class DeletePrenotazioneAction implements IAction {

	private PrenotazioneEvent prenotazioneEvent;

	public DeletePrenotazioneAction(PrenotazioneEvent prenotazioneEvent) {
		this.prenotazioneEvent = prenotazioneEvent;
	}

	public boolean doAction() {

		try {

			ClientConnector.deletePrenotazione(prenotazioneEvent.getId());

			Thread.sleep(1000);
			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
			DashboardEventBus.post(new DashboardEvent.CalendarUpdateEvent(prenotazioneEvent));

			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
