package it.cnr.missioni.dashboard.view.admin;

import it.cnr.missioni.dashboard.component.window.AnticipoPagamentiWindow;
import it.cnr.missioni.dashboard.component.window.admin.MissioneWindowAdmin;
import it.cnr.missioni.dashboard.component.window.admin.RimborsoWindowAdmin;
import it.cnr.missioni.dashboard.utility.AdvancedFileDownloader;
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

	protected void addButtonsToLayout() {
		layout.addComponents(buttonDettagli,buttonAnticipoPagamento,buttonAnticipoPagamentoPdf, buttonRimborso, buttonPDF, buttonVeicoloMissionePDF);
	}

	@Override
	protected void addActionButtonDettagli() {
		MissioneWindowAdmin.open(selectedMissione, true, !selectedMissione.isRimborsoSetted(), true);
	}

	protected void aggiornaTable() {
		this.elencoMissioniTable.aggiornaTableAdmin(missioniStore);
	}

//	@Override
//	protected void addActionButtonRimborso() {
//		RimborsoWindowAdmin.open(selectedMissione, true, selectedMissione.getRimborso().isPagata() ? false : true,
//				true);
//	}
	
	protected void openWindowAnticipoPagamenti(){
		AnticipoPagamentiWindow.open(selectedMissione, true, true, false);
	}
	
	protected boolean enableButtonWindowAnticipoPagamento(){
		return selectedMissione != null && selectedMissione.isMissioneEstera() && selectedMissione.getDatiAnticipoPagamenti().isInserted() && !selectedMissione.isRimborsoSetted();
	}
	
	protected boolean enableButtonDownloadPdf(){
		return selectedMissione != null && selectedMissione.isMissioneEstera()   && selectedMissione.isRimborsoSetted();
	}
	
	protected void downloadAnticipoPagamentoAsPdf(AdvancedFileDownloader anticipoPagamentoDownloaderForLink){
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
		if(!buttonAnticipoPagamentoPdf.isVisible())
			buttonAnticipoPagamento.setVisible(true);

	}

}