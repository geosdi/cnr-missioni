package it.cnr.missioni.dashboard.component.wizard.rimborso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * @author Salvia Vito
 */
public class RiepilogoDatiRimborsoStep implements WizardStep {

	private HorizontalLayout mainLayout;;

	private Missione missione;

	public String getCaption() {
		return "Step 3";
	}

	public RiepilogoDatiRimborsoStep(Missione missione) {
		this.missione = missione;

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

		details.addComponent(
				buildLabel("Importo da Terzi: ", Double.toString(missione.getRimborso().getImportoDaTerzi())));
		details.addComponent(buildLabel("Avviso Pagamento: ", (missione.getRimborso().getAvvisoPagamento() != null
				? missione.getRimborso().getAvvisoPagamento() : "")));
		details.addComponent(
				buildLabel("Anticipazione Pagamento: ", missione.getRimborso().getAnticipazionePagamento() != null
						? Double.toString(missione.getRimborso().getAnticipazionePagamento()) : "0.0"));
		if (missione.isMezzoProprio())
			details.addComponent(buildLabel("Rimborso KM: ", Double.toString(missione.getRimborso().getRimborsoKm())));
		details.addComponent(buildLabel("Tot. lordo TAM: ", Double.toString(missione.getRimborso().getTotaleTAM())));
		details.addComponent(buildLabel("Totale Fatture: ", Double.toString(missione.getRimborso().getTotale())));

		if (missione.isMissioneEstera()) {

			int days = 0;
			try {
				Nazione nazione = ClientConnector
						.getNazione(NazioneSearchBuilder.getNazioneSearchBuilder().withId(missione.getIdNazione()))
						.getNazione().get(0);
				MassimaleStore massimaleStore = ClientConnector
						.getMassimale(MassimaleSearchBuilder.getMassimaleSearchBuilder()
								.withLivello(DashboardUI.getCurrentUser().getDatiCNR().getLivello().name())
								.withAreaGeografica(nazione.getAreaGeografica().name())
								.withTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name()));

				if (massimaleStore.getTotale() > 0) {
					missione.getRimborso().calcolaTotaleTAM(massimaleStore.getMassimale().get(0),
							missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata(),
							missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno());

				}
				days = Days.daysBetween(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata(),
						missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno()).getDays();

				details.addComponent(buildLabel("GG all'estero: ", Integer.toString(days)));
				details.addComponent(buildLabel("Tot. lordo TAM: ", missione.getRimborso().getTotaleTAM().toString()));
				details.addComponent(buildLabel("Attraversamento Frontiera Andata: ",
						missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata().toString()));
				details.addComponent(buildLabel("Attraversamento Frontiera Ritorno: ",
						missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata().toString()));
			} catch (Exception e) {
				Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
						Type.ERROR_MESSAGE);
			}

		}

		ElencoFattureTable elencoFattureTable = new ElencoFattureTable(missione);
		elencoFattureTable.setStyleName("margin-table_fatture");
		elencoFattureTable
				.aggiornaTableRiepilogo(new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
		root.addComponent(elencoFattureTable);

		return root;
	}

	public boolean onAdvance() {

		ConfirmDialog.show(UI.getCurrent(), "Conferma",
				"Il rimborso inserito non potrà essere più modificata. Verrà inviata una mail all'amministratore con i dati della missione inserita.",
				"Ok", "No", new ConfirmDialog.Listener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -4768987961330740317L;

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							DashboardEventBus.post(new RimborsoAction(missione));
							DashboardEventBus.post(new CloseOpenWindowsEvent());
						} else {

						}
					}
				});

		return true;

	}

	public boolean onBack() {
		return true;
	}

	public HorizontalLayout getMainLayout() {
		return mainLayout;
	}

	private Label buildLabel(String caption, String value) {
		Label labelValue = new Label("<b>" + caption + "</b>" + value, ContentMode.HTML);
		labelValue.setStyleName(ValoTheme.LABEL_LIGHT);

		labelValue.setWidth("50%");
		return labelValue;
	}

}
