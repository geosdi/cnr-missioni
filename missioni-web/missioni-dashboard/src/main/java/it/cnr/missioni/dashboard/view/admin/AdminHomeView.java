package it.cnr.missioni.dashboard.view.admin;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.HomeView;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.SearchConstants;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

public final class AdminHomeView extends HomeView {

	protected MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6899889980687530074L;

	public AdminHomeView() {
		super();

	}
	
	@Override
	public void enter(final ViewChangeEvent event) {
		try {
			 MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder(); 
			MissioniStore missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
			elencoMissioniTable.aggiornaTableAdmin(missioniStore);

			missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
					.withFieldExist(SearchConstants.MISSIONE_FIELD_RIMBORSO);
			elencoRimborsiTable.aggiornaTableAdmin(missioniStore);

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}

}
