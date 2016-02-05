package it.cnr.missioni.dashboard.component.wizard.rimborso;

import java.util.ArrayList;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
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
import it.cnr.missioni.model.rimborso.Fattura;

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
//		root.setExpandRatio(details, 1);



		details.addComponent(buildLabel("Avviso Pagamento: " , (missione.getRimborso().getAvvisoPagamento() != null ? missione.getRimborso().getAvvisoPagamento() : "")));
		details.addComponent(buildLabel("Anticipazione Pagamento: " ,missione.getRimborso().getAnticipazionePagamento() != null ?  Double.toString(missione.getRimborso().getAnticipazionePagamento()) : "0.0"));
		if(missione.isMezzoProprio())
			details.addComponent(buildLabel("Rimborso KM: ",Double.toString(missione.getRimborso().getRimborsoKm())));
		details.addComponent(buildLabel("Tot. lordo TAM: " , Double.toString(missione.getRimborso().getTotaleTAM())));
		details.addComponent(buildLabel("Totale Fatture: " , Double.toString(missione.getRimborso().getTotale())));
		
		
		ElencoFattureTable elencoFattureTable = new ElencoFattureTable(missione);
		elencoFattureTable.setStyleName("margin-table_fatture");
		elencoFattureTable.aggiornaTable(new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
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
	
	private Label buildLabel(String caption,String value){		
		Label labelValue = new Label("<b>"+caption+"</b>"+value,ContentMode.HTML);
		labelValue.setStyleName(ValoTheme.LABEL_LIGHT);
		
		labelValue.setWidth("50%");
		return labelValue;
	}

}
