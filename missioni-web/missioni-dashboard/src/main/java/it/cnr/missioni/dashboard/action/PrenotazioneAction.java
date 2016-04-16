package it.cnr.missioni.dashboard.action;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.binder.IPrenotazioneBinder;
import it.cnr.missioni.dashboard.broadcast.Broadcaster;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.prenotazione.Prenotazione;

/**
 * @author Salvia Vito
 */
public class PrenotazioneAction implements IAction {

	private PrenotazioneEvent prenotazioneEvent;
	private boolean modifica;

	public PrenotazioneAction(PrenotazioneEvent prenotazioneEvent, boolean modifica) {
		this.prenotazioneEvent = prenotazioneEvent;
		this.modifica = modifica;
	}

	public boolean doAction() {

		try {
			
			
			Prenotazione prenotazione = IPrenotazioneBinder.PrenotazioneBinder.getPrenotazioneBinder().withFrom(prenotazioneEvent).withModifica(modifica).withUser(DashboardUI.getCurrentUser()).bind();
			String message = "";
			//Nel caso sia una nuova prenotazione
			if(!modifica){
//				message = "Nuova prenotazione inserita: ";
				ClientConnector.addPrenotazione(prenotazione);
			}
			//Modifica
			else{
//				message = "Prenotazione modificata: ";
				ClientConnector.updatePrenotazione(prenotazione);
			}
			Thread.sleep(1000);
			prenotazioneEvent.setCaption(prenotazione.getDescrizione());
			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
			String p = prenotazione.buildStringMessage();
			DashboardEventBus.post(new DashboardEvent.CalendarUpdateEvent(null));
//	        Broadcaster.broadcast(message.concat(p));

			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}


}
