package it.cnr.missioni.dashboard.view.admin;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.component.wizard.rimborso.WizardRimborso;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneRimborsoView;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.SearchConstants;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;

/**
 * @author Salvia Vito
 */
public class GestioneRimborsoAdminView extends GestioneRimborsoView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8249728987254454501L;

	public GestioneRimborsoAdminView() {
		super();
	}

	protected void inizialize() {
		this.missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withFieldExist("missione.rimborso")
				.withSortField(SearchConstants.MISSIONE_FIELD_RIMBORSO_DATA_RIMBORSO);
		try{
			this.user = ClientConnector.getUser(UserSearchBuilder.getUserSearchBuilder().withId(selectedMissione.getIdUser())).getUsers().get(0);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}
	
	protected void openWizardRimborso(){
		WizardSetupWindow.getWizardSetup().withTipo(new WizardRimborso()).withMissione(selectedMissione).withUser(user).withIsAdmin(true).withEnabled(true).withModifica(true)
		.build();
	}



}