package it.cnr.missioni.dashboard.action;

import org.joda.time.DateTime;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.PrenotazioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;

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
			
			
			Prenotazione prenotazione = new Prenotazione();
			prenotazione.setDataFrom(new DateTime(prenotazioneEvent.getStart().getTime()));
			prenotazione.setDataTo(new DateTime(prenotazioneEvent.getEnd().getTime()));
			prenotazione.setIdVeicoloCNR(prenotazioneEvent.getVeicolo());
			prenotazione.setAllDay(prenotazioneEvent.isAllDay());
			prenotazione.setLocalita(prenotazioneEvent.getLocalita());
			prenotazione.setDescriptionVeicoloCNR(prenotazioneEvent.getVeicoloDescription());
//			VeicoloCNR veicolo = ClientConnector.getVeicoloCNR(VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder().)
			
			//Nel caso sia una nuova prenotazione
			if(!modifica){
				User user = DashboardUI.getCurrentUser();
				prenotazione.setIdUser(user.getId());
				prenotazione.setDescrizione(user.getAnagrafica().getCognome()
						+ " " + user.getAnagrafica().getNome());
				ClientConnector.addPrenotazione(prenotazione);

			}
			//Modifica
			else{
				prenotazione.setId(prenotazioneEvent.getId());
				prenotazione.setIdUser(prenotazioneEvent.getIdUser());
				prenotazione.setDescrizione(prenotazioneEvent.getDescrizione());
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
