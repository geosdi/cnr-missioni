package it.cnr.missioni.dashboard.view.admin;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.window.AnticipoPagamentiWindow;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.component.window.admin.RimborsoWindowAdmin;
import it.cnr.missioni.dashboard.component.wizard.missione.WizardMissione;
import it.cnr.missioni.dashboard.component.wizard.rimborso.WizardRimborso;
import it.cnr.missioni.dashboard.event.DashboardEvent.ResetMissioneAdminEvent;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneMissioneView;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.user.User;

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

	protected User getUser() {
		try {
			return ClientConnector
					.getUser(UserSearchBuilder.getUserSearchBuilder().withId(selectedMissione.getIdUser())).getUsers()
					.get(0);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);

		}
		return null;
	}

	protected void addButtonsToLayout() {
		layout.addComponents(buttonDettagli, buttonAnticipoPagamento, buttonAnticipoPagamentoPdf, buttonRimborso,
				buttonPDF, buttonVeicoloMissionePDF);
	}

	protected void openWizardMissione() {
		WizardSetupWindow.getWizardSetup().withTipo(new WizardMissione()).withMissione(new Missione())
				.withUser(getUser()).withIsAdmin(true).withEnabled(true).withModifica(true).build();

	}

	protected void addActionButtonDettagli() {
		if (!selectedMissione.isRimborsoSetted())
			WizardSetupWindow.getWizardSetup().withTipo(new WizardMissione()).withMissione(selectedMissione)
					.withUser(getUser()).withIsAdmin(true).withEnabled(true).withModifica(true).build();
		else
			super.addActionButtonDettagli();
	}

	protected void addActionButtonRimborso() {
		// se è già associato il rimborso
		if (selectedMissione.getRimborso().isPagata())
			RimborsoWindowAdmin.open(selectedMissione, true, false, false);
		else
			WizardSetupWindow.getWizardSetup().withTipo(new WizardRimborso()).withMissione(selectedMissione)
					.withUser(this.getUser()).withEnabled(true).withIsAdmin(true).withModifica(true).build();

	}

	protected void aggiornaTable() {
		this.elencoMissioniTable.aggiornaTableAdmin(missioniStore);
	}

	protected void openWindowAnticipoPagamenti() {
		AnticipoPagamentiWindow.open(selectedMissione, true, true, false);
	}

	protected boolean enableButtonWindowAnticipoPagamento() {
		return selectedMissione != null && selectedMissione.isMissioneEstera()
				&& selectedMissione.getDatiAnticipoPagamenti().isInserted() && !selectedMissione.isRimborsoSetted();
	}

	protected boolean enableButtonDownloadPdf() {
		return selectedMissione != null && selectedMissione.isMissioneEstera() && selectedMissione.isRimborsoSetted();
	}

	protected void downloadAnticipoPagamentoAsPdf(AdvancedFileDownloader anticipoPagamentoDownloaderForLink) {
		anticipoPagamentoDownloaderForLink.setFileDownloadResource(getResourceAnticipoPagamento());
	}

	protected void enableButtons() {
		this.buttonDettagli.setEnabled(selectedMissione.getStato() != StatoEnum.RESPINTA);
		this.buttonPDF.setEnabled(true);
		this.buttonRimborso.setEnabled(selectedMissione.isRimborsoSetted());
		buttonVeicoloMissionePDF.setEnabled(selectedMissione.isMezzoProprio());
		this.buttonAnticipoPagamento.setVisible(enableButtonWindowAnticipoPagamento());
		this.buttonAnticipoPagamentoPdf.setVisible(enableButtonDownloadPdf());
		this.buttonAnticipoPagamento.setEnabled(enableButtonWindowAnticipoPagamento());
		this.buttonAnticipoPagamentoPdf.setEnabled(enableButtonDownloadPdf());
		if (!buttonAnticipoPagamentoPdf.isVisible())
			buttonAnticipoPagamento.setVisible(true);

	}
	
	/**
	 * Reset missione se il wizard missione viene cancellato dall'admin
	 */
	@Subscribe
	public void resetSelectedMissione(final ResetMissioneAdminEvent event) {
		try {
			this.selectedMissione = ClientConnector
					.getMissione(
							MissioneSearchBuilder.getMissioneSearchBuilder().withIdMissione(selectedMissione.getId()))
					.getMissioni().get(0);

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
	}

}