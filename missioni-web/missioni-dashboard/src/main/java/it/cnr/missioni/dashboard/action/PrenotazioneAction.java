package it.cnr.missioni.dashboard.action;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.vaadin.addon.calendar.event.CalendarEvent;
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
public class PrenotazioneAction implements IAction {

	private PrenotazioneEvent prenotazioneEvent;
	private boolean modifica;
	private List<CalendarEvent> list;

	public PrenotazioneAction(PrenotazioneEvent prenotazioneEvent, boolean modifica, List<CalendarEvent> list) {
		this.prenotazioneEvent = prenotazioneEvent;
		this.modifica = modifica;
		this.list = list;
	}

	public boolean doAction() {
		if (!checkPrenotazionePresent())
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("event_present"),
					Type.ERROR_MESSAGE);
		else {
			try {
				Prenotazione prenotazione = IPrenotazioneBinder.PrenotazioneBinder.getPrenotazioneBinder()
						.withFrom(prenotazioneEvent).withModifica(modifica).withUser(DashboardUI.getCurrentUser())
						.bind();
				String message = "";
				// Nel caso sia una nuova prenotazione
				if (!modifica) {
					// message = "Nuova prenotazione inserita: ";
					ClientConnector.addPrenotazione(prenotazione);
				}
				// Modifica
				else {
					// message = "Prenotazione modificata: ";
					ClientConnector.updatePrenotazione(prenotazione);
				}
				Thread.sleep(1000);
				prenotazioneEvent.setCaption(prenotazione.getDescrizione());
				Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
				String p = prenotazione.buildStringMessage();
				DashboardEventBus.post(new DashboardEvent.CalendarUpdateEvent(null));
				// Broadcaster.broadcast(message.concat(p));
				return true;
			} catch (Exception e) {
				Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
						Type.ERROR_MESSAGE);
				return false;
			}
		}
		return false;
	}

	final Predicate<CalendarEvent> condition = new Predicate<CalendarEvent>() {

		@Override
		public boolean test(CalendarEvent t) {
			Interval interval = new Interval(t.getStart().getTime(), new DateTime(t.getEnd()).minusMinutes(1).getMillis(),DateTimeZone.getDefault());
			Interval interval2 = new Interval(prenotazioneEvent.getStart().getTime(), new DateTime(prenotazioneEvent.getEnd()).plusMinutes(1).getMillis(),DateTimeZone.getDefault());

			return (interval.contains(prenotazioneEvent.getStart().getTime())
					|| interval.contains(prenotazioneEvent.getEnd().getTime()))
					
					|| (interval2.contains(t.getStart().getTime())
							|| interval2.contains(t.getEnd().getTime()));
		}
	};

	private boolean checkPrenotazionePresent() {
		return this.list.stream()
				.filter(f -> ((PrenotazioneEvent) f).getVeicolo().equals(prenotazioneEvent.getVeicolo()))
				.filter(condition).collect(Collectors.toList()).isEmpty();
	}

}
