package it.cnr.missioni.dashboard.component.wizard.rimborso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.RimborsoAction;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Fattura;

/**
 * @author Salvia Vito
 */
public class RiepilogoDatiRimborsoStep implements WizardStep {

	private HorizontalLayout mainLayout;;

	private Missione missione;
	private boolean modifica;
	private boolean isAdmin;
	private boolean firstStep;

	public RiepilogoDatiRimborsoStep(Missione missione, boolean modifica, boolean isAdmin, boolean firstStep) {
		this.missione = missione;
		this.modifica = modifica;
		this.isAdmin = isAdmin;
		this.firstStep = firstStep;
	}

	public String getCaption() {
		return "Step 3";
	}

	public Component getContent() {
		return buildPanel();
	}

	private Component buildPanel() {
		VerticalLayout root = new VerticalLayout();
		root.setIcon(FontAwesome.MONEY);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);
		CssLayout details = new CssLayout();
		details.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		root.addComponent(details);
		// root.setExpandRatio(details, 1);
		if (isAdmin) {
			details.addComponent(Utility.buildLabel("Pagata: ", missione.getRimborso().isPagata() ? "Si" : "No"));
			details.addComponent(
					Utility.buildLabel("Mandato di pagamento: ", missione.getRimborso().getMandatoPagamento()));
		}
		details.addComponent(Utility.buildLabel("Importo da Terzi: ",
				Double.toString(missione.getRimborso().getImportoDaTerzi()) + " €"));
		details.addComponent(
				Utility.buildLabel("Avviso Pagamento: ", (missione.getRimborso().getAvvisoPagamento() != null
						? missione.getRimborso().getAvvisoPagamento() : "")));
		details.addComponent(Utility.buildLabel("Anticipazione Pagamento: ",
				missione.getRimborso().getAnticipazionePagamento() != null
						? Utility.getStringDecimalFormat(missione.getRimborso().getAnticipazionePagamento()) + " €"
						: "0.0 €"));
		if (missione.isMezzoProprio())
			details.addComponent(Utility.buildLabel("Rimborso KM: ",
					Utility.getStringDecimalFormat(missione.getRimborso().getRimborsoKm()) + " €"));
		details.addComponent(Utility.buildLabel("Totale: ",
				Utility.getStringDecimalFormat(missione.getRimborso().getTotale()) + " €"));
		details.addComponent(Utility.buildLabel("Totale Spettante: ",
				Utility.getStringDecimalFormat(missione.getRimborso().getTotaleSpettante()) + " €"));

		if (missione.isMissioneEstera()) {
			if (missione.getDatiMissioneEstera()
					.getTrattamentoMissioneEsteraEnum() == TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO) {
				details.addComponent(
						Utility.buildLabel("GG all'estero: ", Integer.toString(Utility.getDaysEstero(missione))));
				details.addComponent(Utility.buildLabel("Tot. lordo TAM: ",
						Utility.getStringDecimalFormat(missione.getRimborso().getTotaleTAM()) + " €"));
			}
			details.addComponent(
					Utility.buildLabel("Attraversamento Frontiera Andata: ", new SimpleDateFormat("dd/MM/yyyy HH:mm")
							.format(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata().toDate())));
			details.addComponent(
					Utility.buildLabel("Attraversamento Frontiera Ritorno: ", new SimpleDateFormat("dd/MM/yyyy HH:mm")
							.format(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno().toDate())));
		}
		ElencoFattureTable elencoFattureTable = new ElencoFattureTable(missione);
		elencoFattureTable.setStyleName("margin-table_fatture");
		elencoFattureTable
				.aggiornaTableRiepilogo(new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
		root.addComponent(elencoFattureTable);
		return root;
	}

	public boolean onAdvance() {
		if (!firstStep) {

			if(!isAdmin){
			String conferma ="Selezionando 'Salva e invia' il rimborso inserito non potrà essere più modificato. Verrà inviata una mail all'amministratore con i dati del rimborso inserita. "
					+ "Selezionando 'Salva' sarà possibile modificare il rimborso";
			ConfirmDialog.show(UI.getCurrent(), "Conferma", conferma, "Salva e invia", "Annulla", "Salva",
					new ConfirmDialog.Listener() {

						/**
						 *
						 */
						private static final long serialVersionUID = -4768987961330740317L;

						public void onClose(ConfirmDialog dialog) {
							missione.setRimborsoCompleted(dialog.isConfirmed());
							if(!dialog.isCanceled()){
								DashboardEventBus.post(new RimborsoAction(missione,isAdmin));
								DashboardEventBus.post(new CloseOpenWindowsEvent());
							}
						}

					});
			}else{
				String conferma = missione.getRimborso().isPagata() ?"Rimborso pagato. Non sarà più possibile modificarlo.": "Rimborso non pagato. In seguito è possibile modificare il rimborso";
				ConfirmDialog.show(UI.getCurrent(), "Conferma", conferma, "Salva", "Annulla",
						new ConfirmDialog.Listener() {

							/**
							 *
							 */
							private static final long serialVersionUID = -4768987961330740317L;

							public void onClose(ConfirmDialog dialog) {
								if(dialog.isConfirmed()){
									DashboardEventBus.post(new RimborsoAction(missione,isAdmin));
									DashboardEventBus.post(new CloseOpenWindowsEvent());
								}
							}

						});
			}
		}
		return true;
	}

	public boolean onBack() {
		return true;
	}

	public HorizontalLayout getMainLayout() {
		return mainLayout;
	}

}
