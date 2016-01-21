package it.cnr.missioni.dashboard.event;

import java.util.List;

import it.cnr.missioni.dashboard.menu.DashboardViewType;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

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

	public static final class ReportsCountUpdatedEvent {
		private final int count;

		public ReportsCountUpdatedEvent(final int count) {
			this.count = count;
		}

		public int getCount() {
			return count;
		}

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
	public static class TableMissioniUpdatedEvent {

		private MissioniStore missioniStore;

		/**
		 * @param missioniStore
		 */
		public TableMissioniUpdatedEvent(MissioniStore missioniStore) {
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
	

	
}
