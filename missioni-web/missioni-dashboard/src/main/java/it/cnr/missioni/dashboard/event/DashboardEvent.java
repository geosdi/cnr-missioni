package it.cnr.missioni.dashboard.event;

import java.util.List;

import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.menu.DashboardViewType;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;
import it.cnr.missioni.rest.api.response.user.UserStore;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;

/*
 * Event bus events used in Dashboard are listed here as inner classes.
 */
public abstract class DashboardEvent {

	public static final class UserLoginRequestedEvent {
		private final String userName, password;

		public UserLoginRequestedEvent(final String userName, final String password) {
			this.userName = userName;
			this.password = password;
		}

		public String getUserName() {
			return userName;
		}

		public String getPassword() {
			return password;
		}
	}

	public static class BrowserResizeEvent {

	}

	public static class UserLoggedOutEvent {

	}

	public static class NotificationsCountUpdatedEvent {
	}

	// public static final class ReportsCountUpdatedEvent {
	// private final int count;
	//
	// public ReportsCountUpdatedEvent(final int count) {
	// this.count = count;
	// }
	//
	// public int getCount() {
	// return count;
	// }
	//
	// }
	//
	public static final class PostViewChangeEvent {
		private final DashboardViewType view;

		public PostViewChangeEvent(final DashboardViewType view) {
			this.view = view;
		}

		public DashboardViewType getView() {
			return view;
		}
	}

	/**
	 * 
	 * Chiude tutte le window aperte
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class CloseOpenWindowsEvent {
	}

	/**
	 * 
	 * Aggiorna l'user dopo un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class ProfileUpdatedEvent {
	}

	/**
	 * 
	 * Aggiorna la tabella veicoli dopo un inserimento o un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableVeicoliUpdatedEvent {
		
	}

	/**
	 * 
	 * Aggiorna la tabella missione dopo un inserimento o update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableMissioniUpdateUpdatedEvent {

	}

	/**
	 * Aggiorna la tabella rimborso dopo un inserimento o update
	 */
	public static class MenuUpdateEvent {
	}

	public static class TableRimborsiUpdatedEvent {


	}

	/**
	 * 
	 * Aggiorna la tabella missione dopo un inserimento o update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableUserUpdatedEvent {


	}

	/**
	 * Aggiorna il calendario
	 */
	public static class CalendarUpdateEvent {

		private PrenotazioneEvent prenotazioneEvent;

		/**
		 * @param prenotazioneEvent
		 */
		public CalendarUpdateEvent(PrenotazioneEvent prenotazioneEvent) {
			this.prenotazioneEvent = prenotazioneEvent;
		}

		/**
		 * @return the prenotazioneEvent
		 */
		public PrenotazioneEvent getPrenotazioneEvent() {
			return prenotazioneEvent;
		}

		/**
		 * @param prenotazioneEvent
		 */
		public void setPrenotazioneEvent(PrenotazioneEvent prenotazioneEvent) {
			this.prenotazioneEvent = prenotazioneEvent;
		}

	}

	/**
	 * 
	 * Aggiorna la tabella veicoli CNR dopo un inserimento o un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableVeicoliCNRUpdatedEvent {

	}

	/**
	 * 
	 * Aggiorna la tabella qualifica dopo un inserimento o un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableQualificaUserUpdatedEvent {

	}

	/**
	 * 
	 * Aggiorna la tabella nazione dopo un inserimento o un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableNazioneUpdatedEvent {

	}

	/**
	 * 
	 * Aggiorna la tabella rimborso km dopo un inserimento o un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableRimborsoKmUpdatedEvent {

		private RimborsoKmStore rimborsoKmStore;

		/**
		 * @param veicoloCNRStore
		 */
		public TableRimborsoKmUpdatedEvent(RimborsoKmStore rimborsoKmStore) {
			this.rimborsoKmStore = rimborsoKmStore;
		}

		/**
		 * @return the rimborsoKmStore
		 */
		public RimborsoKmStore getRimborsoKmStore() {
			return rimborsoKmStore;
		}

		/**
		 * @param rimborsoKmStore 
		 */
		public void setRimborsoKmStore(RimborsoKmStore rimborsoKmStore) {
			this.rimborsoKmStore = rimborsoKmStore;
		}


	}
	
	/**
	 * 
	 * Disabilita il button
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class DisableButtonNewEvent {
		
	}
	
	/**
	 * 
	 * Aggiorna la tabella tipologia spesa dopo un inserimento o un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableTipologiaSpesaUpdatedEvent {

	}
	
	/**
	 * 
	 * Aggiorna la tabella massimale dopo un inserimento o un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableMassimaleUpdatedEvent {

	}
	
}
