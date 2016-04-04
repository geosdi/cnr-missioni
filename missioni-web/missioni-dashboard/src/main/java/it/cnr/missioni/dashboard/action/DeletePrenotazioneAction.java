package it.cnr.missioni.dashboard.action;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.binder.IPrenotazioneBinder;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.prenotazione.Prenotazione;

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
			Prenotazione prenotazione = IPrenotazioneBinder.PrenotazioneBinder.getPrenotazioneBinder().withFrom(prenotazioneEvent).withModifica(false).withUser(DashboardUI.getCurrentUser()).bind();
			Thread.sleep(1000);
			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);	
			String p = prenotazione.buildStringMessage();
			DashboardEventBus.post(new DashboardEvent.CalendarUpdateEvent(null));
//	        Broadcaster.broadcast("Prenotazione eliminata: ".concat(p));
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
