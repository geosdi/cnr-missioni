package it.cnr.missioni.dashboard.view.admin;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Button.ClickEvent;

import it.cnr.missioni.dashboard.component.window.DettagliMissioneWindow;
import it.cnr.missioni.dashboard.component.window.DettagliRimborsoWindow;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.component.window.admin.MissioneWindowAdmin;
import it.cnr.missioni.dashboard.component.wizard.rimborso.WizardRimborso;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.AdvancedDownloaderListener;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader.DownloaderEvent;
import it.cnr.missioni.dashboard.view.GestioneMissioneView;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.rimborso.Rimborso;

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

	protected GridLayout addActionButtons() {
		GridLayout layout = new GridLayout(4, 1);
		layout.setSpacing(true);
		buttonDettagli = buildButton("Dettagli", "Visualizza i dettagli della Missione", FontAwesome.EDIT);

		buttonDettagli.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2762532096338441183L;

			@Override
			public void buttonClick(ClickEvent event) {

				MissioneWindowAdmin.open(selectedMissione);

			}

		});

		layout.addComponents(buttonDettagli);

		enableDisableButtons(false);

		return layout;

	}
	
	protected void enableDisableButtons(boolean enabled) {
		this.buttonDettagli.setEnabled(enabled);
//		this.buttonPDF.setEnabled(enabled);
//		this.buttonRimborso.setEnabled(enabled);
//
//		if (selectedMissione != null && selectedMissione.isMezzoProprio())
//			buttonVeicoloMissionePDF.setEnabled(true);
//		if (selectedMissione == null || !selectedMissione.isMezzoProprio())
//			buttonVeicoloMissionePDF.setEnabled(false);

	}

}