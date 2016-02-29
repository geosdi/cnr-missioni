package it.cnr.missioni.dashboard.view.admin;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import it.cnr.missioni.dashboard.component.window.admin.RimborsoWindowAdmin;
import it.cnr.missioni.dashboard.view.GestioneRimborsoView;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.SearchConstants;

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

	}
	
	@Override
	protected void buildButtonDettagli(){
		buttonDettagli.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8783796549904544814L;

			@Override
			public void buttonClick(ClickEvent event) {
				RimborsoWindowAdmin.open(selectedMissione,true,selectedMissione.getRimborso().isPagata() ? false : true,true);

			}

		});
	}



}