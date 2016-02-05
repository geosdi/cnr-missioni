package it.cnr.missioni.dashboard.event;

import java.util.List;

import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.menu.DashboardViewType;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;
import it.cnr.missioni.rest.api.response.qualificaUser.QualificaUserStore;
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
		
		private List<Veicolo> lista;

		/**
		 * @param lista
		 */
		public TableVeicoliUpdatedEvent(List<Veicolo> lista) {
			this.lista = lista;
		}

		/**
		 * @return the lista
		 */
		public List<Veicolo> getLista() {
			return lista;
		}

		/**
		 * @param lista 
		 */
		public void setLista(List<Veicolo> lista) {
			this.lista = lista;
		}
		
		
	}

	/**
	 * 
	 * Aggiorna la tabella missione dopo un inserimento o update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableMissioniUpdateUpdatedEvent {

		private MissioniStore missioniStore;

		/**
		 * @param missioniStore
		 */
		public TableMissioniUpdateUpdatedEvent(MissioniStore missioniStore) {
			this.missioniStore = missioniStore;
		}

		/**
		 * @return the missioniStore
		 */
		public MissioniStore getMissioniStore() {
			return missioniStore;
		}

		/**
		 * @param missioniStore
		 */
		public void setMissioniStore(MissioniStore missioniStore) {
			this.missioniStore = missioniStore;
		}

	}

	/**
	 * Aggiorna la tabella rimborso dopo un inserimento o update
	 */
	public static class MenuUpdateEvent {
	}

	public static class TableRimborsiUpdatedEvent {

		private MissioniStore missioniStore;

		/**
		 * @param missioniStore
		 */
		public TableRimborsiUpdatedEvent(MissioniStore missioniStore) {
			this.missioniStore = missioniStore;
		}

		/**
		 * @return the missioniStore
		 */
		public MissioniStore getMissioniStore() {
			return missioniStore;
		}

		/**
		 * @param missioniStore
		 */
		public void setMissioniStore(MissioniStore missioniStore) {
			this.missioniStore = missioniStore;
		}

	}

	/**
	 * 
	 * Aggiorna la tabella missione dopo un inserimento o update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableUserUpdatedEvent {

		private UserStore userStore;

		/**
		 * @param missioniStore
		 */
		public TableUserUpdatedEvent(UserStore userStore) {
			this.setUserStore(userStore);
		}

		/**
		 * @return the userStore
		 */
		public UserStore getUserStore() {
			return userStore;
		}

		/**
		 * @param userStore
		 */
		public void setUserStore(UserStore userStore) {
			this.userStore = userStore;
		}

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

		private VeicoloCNRStore veicoloCNRStore;

		/**
		 * @param veicoloCNRStore
		 */
		public TableVeicoliCNRUpdatedEvent(VeicoloCNRStore veicoloCNRStore) {
			this.veicoloCNRStore = veicoloCNRStore;
		}

		/**
		 * @return the veicoloCNRStore
		 */
		public VeicoloCNRStore getVeicoloCNRStore() {
			return veicoloCNRStore;
		}

		/**
		 * @param veicoloCNRStore
		 */
		public void setVeicoloCNRStore(VeicoloCNRStore veicoloCNRStore) {
			this.veicoloCNRStore = veicoloCNRStore;
		}

	}

	/**
	 * 
	 * Aggiorna la tabella qualifica dopo un inserimento o un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableQualificaUserUpdatedEvent {

		private QualificaUserStore qualificaStore;

		/**
		 * @param veicoloCNRStore
		 */
		public TableQualificaUserUpdatedEvent(QualificaUserStore qualificaStore) {
			this.setQualificaStore(qualificaStore);
		}

		/**
		 * @return the qualificaStore
		 */
		public QualificaUserStore getQualificaStore() {
			return qualificaStore;
		}

		/**
		 * @param qualificaStore
		 */
		public void setQualificaStore(QualificaUserStore qualificaStore) {
			this.qualificaStore = qualificaStore;
		}

	}

	/**
	 * 
	 * Aggiorna la tabella nazione dopo un inserimento o un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableNazioneUpdatedEvent {

		private NazioneStore nazioneStore;

		/**
		 * @param veicoloCNRStore
		 */
		public TableNazioneUpdatedEvent(NazioneStore nazioneStore) {
			this.setNazioneStore(nazioneStore);
		}

		/**
		 * @return the nazioneStore
		 */
		public NazioneStore getNazioneStore() {
			return nazioneStore;
		}

		/**
		 * @param nazioneStore
		 */
		public void setNazioneStore(NazioneStore nazioneStore) {
			this.nazioneStore = nazioneStore;
		}

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

		private TipologiaSpesaStore tipologiaSpesaStore;

		/**
		 * @param tipologiaSpesaStore
		 */
		public TableTipologiaSpesaUpdatedEvent(TipologiaSpesaStore tipologiaSpesaStore) {
			this.tipologiaSpesaStore = tipologiaSpesaStore;
		}

		/**
		 * @return the tipologiaSpesaStore
		 */
		public TipologiaSpesaStore getTipologiaSpesaStore() {
			return tipologiaSpesaStore;
		}

		/**
		 * @param tipologiaSpesaStore 
		 */
		public void setTipologiaSpesaStore(TipologiaSpesaStore tipologiaSpesaStore) {
			this.tipologiaSpesaStore = tipologiaSpesaStore;
		}

	}
	
	/**
	 * 
	 * Aggiorna la tabella massimale dopo un inserimento o un update
	 * 
	 * @author Salvia Vito
	 *
	 */
	public static class TableMassimaleUpdatedEvent {

		private MassimaleStore massimaleStore;

		/**
		 * @param massimaleStore
		 */
		public TableMassimaleUpdatedEvent(MassimaleStore massimaleStore) {
			this.massimaleStore = massimaleStore;
		}

		/**
		 * @return the massimaleStore
		 */
		public MassimaleStore getMassimaleStore() {
			return massimaleStore;
		}

		/**
		 * @param massimaleStore 
		 */
		public void setMassimaleStore(MassimaleStore massimaleStore) {
			this.massimaleStore = massimaleStore;
		}



	}
	
}
