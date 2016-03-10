package it.cnr.missioni.dashboard.action;

import org.joda.time.DateTime;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class AddUpdatePrenotazioneAction implements IAction {

	private PrenotazioneEvent prenotazioneEvent;
	private boolean modifica;

	public AddUpdatePrenotazioneAction(PrenotazioneEvent prenotazioneEvent, boolean modifica) {
		this.prenotazioneEvent = prenotazioneEvent;
		this.modifica = modifica;
	}

	public boolean doAction() {

		try {
			
			
			Prenotazione prenotazione = new Prenotazione();
			prenotazione.setDataFrom(new DateTime(prenotazioneEvent.getStart().getTime()));
			prenotazione.setDataTo(new DateTime(prenotazioneEvent.getEnd().getTime()));
			prenotazione.setIdVeicoloCNR(prenotazioneEvent.getVeicolo());
			prenotazione.setAllDay(prenotazioneEvent.isAllDay());
			prenotazione.setLocalita(prenotazioneEvent.getLocalita());
			User user = DashboardUI.getCurrentUser();

			prenotazione.setIdUser(user.getId());

			prenotazione.setDescrizione(prenotazioneEvent.getDescrizione() + " - " + user.getAnagrafica().getCognome()
					+ " " + user.getAnagrafica().getNome());

			if (!modifica)
				ClientConnector.addPrenotazione(prenotazione);
			else{
				prenotazione.setId(prenotazioneEvent.getId());
				ClientConnector.updatePrenotazione(prenotazione);
			}
			Thread.sleep(1000);
			prenotazioneEvent.setCaption(prenotazione.getDescrizione());
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
