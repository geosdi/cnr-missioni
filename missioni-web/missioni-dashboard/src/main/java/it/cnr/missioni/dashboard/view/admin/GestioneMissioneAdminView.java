package it.cnr.missioni.dashboard.view.admin;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import it.cnr.missioni.dashboard.component.window.admin.MissioneWindowAdmin;
import it.cnr.missioni.dashboard.component.window.admin.RimborsoWindowAdmin;
import it.cnr.missioni.dashboard.view.GestioneMissioneView;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.StatoEnum;

/**
 * @author Salvia Vito
 */
public class GestioneMissioneAdminView extends GestioneMissioneView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8249728987254454501L;

	public GestioneMissioneAdminView() {
		super();
	}

	protected void inizialize() {
		this.missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder();
	}

	
	protected void addButtonsToLayout(){
		layout.addComponents(buttonDettagli, buttonRimborso, buttonPDF, buttonVeicoloMissionePDF);
	}
	
	@Override
	protected void buildButtonDettagli(){
		buttonDettagli.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4610960850985679736L;

			@Override
			public void buttonClick(ClickEvent event) {

				MissioneWindowAdmin.open(selectedMissione,!selectedMissione.isRimborsoSetted(),true);

			}

		});
	}
	
	protected void aggiornaTable(){
		this.elencoMissioniTable.aggiornaTableAdmin(missioniStore);
	}
	
	protected void buildButtonRimborso(){
		buttonRimborso.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7097735360065445590L;

			@Override
			public void buttonClick(ClickEvent event) {
				RimborsoWindowAdmin.open(selectedMissione,true,selectedMissione.getRimborso().isPagata() ? false : true,true);

			}

		});
	}

	protected void enableButtons() {
		this.buttonDettagli.setEnabled(selectedMissione.getStato() != StatoEnum.RESPINTA);
		this.buttonPDF.setEnabled(true);
		this.buttonRimborso.setEnabled(selectedMissione.isRimborsoSetted());
		buttonVeicoloMissionePDF.setEnabled(selectedMissione.isMezzoProprio());
	}
	


}