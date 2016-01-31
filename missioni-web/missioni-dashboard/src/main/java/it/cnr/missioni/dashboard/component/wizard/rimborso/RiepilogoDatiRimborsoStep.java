package it.cnr.missioni.dashboard.component.wizard.rimborso;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.RimborsoAction;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.missione.Missione;

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

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
//		root.setExpandRatio(details, 1);



		details.addComponent(new Label("Avviso Pagamento: " + missione.getRimborso().getAvvisoPagamento()));
		details.addComponent(new Label("Anticipazione Pagamento: " + missione.getRimborso().getAnticipazionePagamento()));
		details.addComponent(new Label("Totale: " + missione.getRimborso().getTotale()));
		
		
		ElencoFattureTable elencoFattureTable = new ElencoFattureTable(missione);
		elencoFattureTable.aggiornaTable();
		root.addComponent(elencoFattureTable);
		
		return root;
	}

	public boolean onAdvance() {

		ConfirmDialog.show(UI.getCurrent(), "Conferma",
				"Il rimborso inserito non potrà essere più modificata. Verrà inviata una mail all'amministratore con i dati della missione inserita.",
				"Ok", "No", new ConfirmDialog.Listener() {

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

}
