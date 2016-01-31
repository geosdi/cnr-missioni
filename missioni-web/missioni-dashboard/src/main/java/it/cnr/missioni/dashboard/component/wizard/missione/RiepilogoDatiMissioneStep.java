package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.action.MissioneAction;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiAnticipoPagamenti;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
public class RiepilogoDatiMissioneStep implements WizardStep {

	private HorizontalLayout mainLayout;;

	private Missione missione;

	public String getCaption() {
		return "Step 8";
	}

	public RiepilogoDatiMissioneStep(Missione missione) {
		this.missione = missione;

	}

	public Component getContent() {
		return buildPanel();
	}

	private Component buildPanel() {
		HorizontalLayout root = new HorizontalLayout();
		root.setIcon(FontAwesome.MONEY);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		String tipoMissione = missione.isMissioneEstera() ? "Italia" : "Estera";
		String tipoVeicolo = missione.isMezzoProprio() ? "Veicolo Proprio" : "Veicolo CNR";

		details.addComponent(new Label("Tipo Missione: " + tipoMissione));
		details.addComponent(new Label("Località: " + missione.getLocalita()));
		details.addComponent(new Label("Oggetto: " + missione.getOggetto()));
		details.addComponent(new Label("Fondo: " + missione.getFondo()));
		details.addComponent(new Label("GAE: " + missione.getGAE()));
		details.addComponent(new Label("Responsabile Gruppo: " + missione.getShortResponsabileGruppo()));
		details.addComponent(new Label("Distanza: " + missione.getDistanza()));
		details.addComponent(new Label("Veicolo: " + tipoVeicolo));
		if (missione.isMezzoProprio()) {

			Veicolo v = DashboardUI.getCurrentUser().getVeicoloPrincipale();
			details.addComponent(new Label("Motivazione mezzo proprio: " + missione.getMotivazioniMezzoProprio()));
			details.addComponent(new Label(new Label("Veicolo: " + v.getTipo() + " Targa: " + v.getTarga())));

		}
		details.addComponent(
				new Label("Inizio Missione: " + missione.getDatiPeriodoMissione().getInizioMissione().toString()));
		details.addComponent(
				new Label("Fine Missione: " + missione.getDatiPeriodoMissione().getFineMissione().toString()));
		if (missione.isMissioneEstera()) {
			details.addComponent(new Label("Trattamento Rimborso: "
					+ missione.getDatiMissioneEstera().getTrattamentoMissioneEsteraEnum().values()));
			details.addComponent(new Label("Attraversamento Frontiera Andata: "
					+ missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata().toString()));
			details.addComponent(new Label("Attraversamento Frontiera Ritorno: "
					+ missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno().toString()));
		}
		details.addComponent(new Label(
				"Anticipazioni Monetarie: " + missione.getDatiAnticipoPagamenti().isAnticipazioniMonetarie()));

		details.addComponent(new Label("Numero Mandato CNR: " + missione.getDatiAnticipoPagamenti().getMandatoCNR()));
		details.addComponent(new Label("Altre Spese di Missione Anticipate: "
				+ missione.getDatiAnticipoPagamenti().getSpeseMissioniAnticipate()));
		details.addComponent(
				new Label("Rimborso da Terzi: " + missione.getDatiAnticipoPagamenti().getImportoDaTerzi()));
		details.addComponent(new Label("Importo da Terzi: " + missione.getDatiAnticipoPagamenti().getImportoDaTerzi()));

		return root;
	}

	public boolean onAdvance() {

		ConfirmDialog.show(UI.getCurrent(), "Conferma",
				"La missione inserita non potrà essere più modificata. Verrà inviata una mail all'amministratore con i dati della missione inserita.",
				"Ok", "No", new ConfirmDialog.Listener() {

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							DashboardEventBus.post(new MissioneAction(missione, false));
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
