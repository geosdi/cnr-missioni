package it.cnr.missioni.dashboard.event;

import org.joda.time.DateTime;

import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.menu.DashboardViewType;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;

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
	
	public static class ComboBoxListaFatturaUpdatedEvent {

		private Fattura fattura;

		/**
		 * @return the fattura
		 */
		public Fattura getFattura() {
			return fattura;
		}

		/**
		 * @param fattura 
		 */
		public void setFattura(Fattura fattura) {
			this.fattura = fattura;
		}

		/**
		 * @param data
		 */
		public ComboBoxListaFatturaUpdatedEvent(Fattura fattura) {
			this.fattura = fattura;
		}


		
		
		
		
	}
	
	public static class ResetMissioneEvent {

	}
	
	public static class ResetMissioneAdminEvent {

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
	
	/**
	 * 
	 * Aggiorna la tabella massimale dopo un inserimento o un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class AggiornaDatiMissioneEsteraEvent {

		private boolean status;

		/**
		 * @param status
		 */
		public AggiornaDatiMissioneEsteraEvent(boolean status) {
			this.status = status;
		}

		/**
		 * @return the status
		 */
		public boolean isStatus() {
			return status;
		}

		/**
		 * @param status 
		 */
		public void setStatus(boolean status) {
			this.status = status;
		}
		
	}
	
}
