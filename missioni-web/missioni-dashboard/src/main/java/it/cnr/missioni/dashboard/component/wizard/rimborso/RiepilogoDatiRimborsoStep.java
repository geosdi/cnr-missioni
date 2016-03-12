package it.cnr.missioni.dashboard.component.wizard.rimborso;

import java.util.ArrayList;

import org.joda.time.Days;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.action.RimborsoAction;
import it.cnr.missioni.dashboard.action.admin.UpdateRimborsoAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

/**
 * @author Salvia Vito
 */
public class RiepilogoDatiRimborsoStep implements WizardStep {

	private HorizontalLayout mainLayout;;

	private Missione missione;
	private boolean modifica;
	private boolean isAdmin;

	public String getCaption() {
		return "Step 3";
	}

	public RiepilogoDatiRimborsoStep(Missione missione, boolean modifica, boolean isAdmin) {
		this.missione = missione;
		this.modifica = modifica;
		this.isAdmin = isAdmin;
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
			details.addComponent(Utility.buildLabel("Mandato di pagamento: ", missione.getRimborso().getMandatoPagamento()));
		}
		details.addComponent(
				Utility.buildLabel("Importo da Terzi: ", Double.toString(missione.getRimborso().getImportoDaTerzi()) + " €"));
		details.addComponent(Utility.buildLabel("Avviso Pagamento: ", (missione.getRimborso().getAvvisoPagamento() != null
				? missione.getRimborso().getAvvisoPagamento() : "")));
		details.addComponent(
				Utility.buildLabel("Anticipazione Pagamento: ", missione.getRimborso().getAnticipazionePagamento() != null
						? Double.toString(missione.getRimborso().getAnticipazionePagamento()) + " €" : "0.0 €"));
		if (missione.isMezzoProprio())
			details.addComponent(
					Utility.buildLabel("Rimborso KM: ", Double.toString(missione.getRimborso().getRimborsoKm()) + " €"));
		if (missione.isMissioneEstera())
			details.addComponent(
					Utility.buildLabel("Tot. lordo TAM: ", Double.toString(missione.getRimborso().getTotaleTAM()) + " €"));
		details.addComponent(
				Utility.buildLabel("Totale Fatture: ", Double.toString(missione.getRimborso().getTotale()) + " €"));

		if (missione.isMissioneEstera()) {
			details.addComponent(Utility.buildLabel("GG all'estero: ", Integer.toString(Utility.getDaysEstero(missione))));
			details.addComponent(Utility.buildLabel("Tot. lordo TAM: ", missione.getRimborso().getTotaleTAM().toString()));
			details.addComponent(Utility.buildLabel("Attraversamento Frontiera Andata: ",
					missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata().toString()));
			details.addComponent(Utility.buildLabel("Attraversamento Frontiera Ritorno: ",
					missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata().toString()));
		}

		ElencoFattureTable elencoFattureTable = new ElencoFattureTable(missione);
		elencoFattureTable.setStyleName("margin-table_fatture");
		elencoFattureTable
				.aggiornaTableRiepilogo(new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
		root.addComponent(elencoFattureTable);

		return root;
	}

	public boolean onAdvance() {

		String conferma = "";

		if (isAdmin)
			conferma = "Verrà inviata una mail all'utente con i dati dell rimborso inserita.";
		else
			conferma = "Il rimborso inserito non potrà essere più modificato. Verrà inviata una mail all'amministratore con i dati del rimborso inserita.";

		ConfirmDialog.show(UI.getCurrent(), "Conferma", conferma, "Ok", "No", new ConfirmDialog.Listener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4768987961330740317L;

			public void onClose(ConfirmDialog dialog) {
				if (dialog.isConfirmed()) {
					confirmed();
				} else {

				}
			}
		});
		return true;

	}

	private void confirmed() {
		if (!modifica)
			DashboardEventBus.post(new RimborsoAction(missione));
		else
			DashboardEventBus.post(new UpdateRimborsoAction(missione));

		DashboardEventBus.post(new CloseOpenWindowsEvent());
	}

	public boolean onBack() {
		return true;
	}

	public HorizontalLayout getMainLayout() {
		return mainLayout;
	}



}
