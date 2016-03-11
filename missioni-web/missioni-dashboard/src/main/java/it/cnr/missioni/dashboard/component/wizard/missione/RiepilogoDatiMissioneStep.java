package it.cnr.missioni.dashboard.component.wizard.missione;

import java.text.SimpleDateFormat;

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
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.action.MissioneAction;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
public class RiepilogoDatiMissioneStep implements WizardStep {

	private HorizontalLayout mainLayout;;

	private Missione missione;
	private boolean modifica;
	public String getCaption() {
		return "Step 8";
	}

	public RiepilogoDatiMissioneStep(Missione missione,boolean modifica) {
		this.missione = missione;
		this.modifica = modifica;

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

		CssLayout details = new CssLayout();
		details.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		root.addComponent(details);
//		root.setExpandRatio(details, 1);

		String tipoMissione = missione.isMissioneEstera() ? "Estera" : "Italia";
		String tipoVeicolo = missione.getTipoVeicolo();

		

		details.addComponent(buildLabel("Tipo Missione: ",tipoMissione));
		details.addComponent(buildLabel("Località: " , missione.getLocalita()));
		if(missione.isMissioneEstera()){
			details.addComponent(buildLabel("Nazione: " , missione.getShortDescriptionNazione()));
			details.addComponent(buildLabel("Trattamento Rimbroso : " , missione.getDatiMissioneEstera().getTrattamentoMissioneEsteraEnum().getStato()));
		}
		details.addComponent(buildLabel("Oggetto: " , missione.getOggetto()));
		details.addComponent(buildLabel("Fondo: " , missione.getFondo() != null ? missione.getFondo() : ""));
		details.addComponent(buildLabel("GAE: " , missione.getGAE() != null ? missione.getGAE() : ""));
		details.addComponent(buildLabel("Responsabile Gruppo: " , missione.getShortResponsabileGruppo()));
		details.addComponent(buildLabel("Distanza: " , missione.getDistanza()));
		details.addComponent(buildLabel("Veicolo: " , tipoVeicolo));
		if (missione.getTipoVeicolo().equals("Veicolo Proprio") || missione.getTipoVeicolo().equals("Noleggio")) {

			Veicolo v = DashboardUI.getCurrentUser().getVeicoloPrincipale();
			details.addComponent(buildLabel("Motivazione: " , missione.getMotivazioni()));
			if(missione.getTipoVeicolo().equals("Veicolo Proprio"))
				details.addComponent(buildLabel("Veicolo: " , v.getTipo() + " Targa: " + v.getTarga()));

		}
		details.addComponent(
				buildLabel("Inizio Missione: " , new SimpleDateFormat("dd/MM/yyyy HH:mm").format(missione.getDatiPeriodoMissione().getInizioMissione().toDate())));
		details.addComponent(
				buildLabel("Presunta Fine Missione: " ,  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(missione.getDatiPeriodoMissione().getFineMissione().toDate())));
		details.addComponent(
				buildLabel("A seguito di: " , missione.getShortUserSeguito() != null ? missione.getShortUserSeguito() : ""));
//		if (missione.isMissioneEstera()) {
//			details.addComponent(buildLabel("Trattamento Rimborso: ", missione.getDatiMissioneEstera().getTrattamentoMissioneEsteraEnum().getStato()));
//			details.addComponent(buildLabel("Attraversamento Frontiera Andata: "
//					, new SimpleDateFormat("dd/MM/yyyy HH:mm").format(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata().toDate())));
//			details.addComponent(buildLabel("Attraversamento Frontiera Ritorno: "
//					, new SimpleDateFormat("dd/MM/yyyy HH:mm").format(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno().toDate())));
//		}
//		details.addComponent(buildLabel(
//				"Anticipazioni Monetarie: " , missione.getDatiAnticipoPagamenti().isAnticipazioniMonetarie() ? "Si" : "No"));
//
//		details.addComponent(buildLabel("Numero Mandato CNR: " , missione.getDatiAnticipoPagamenti().getMandatoCNR() != null ? missione.getDatiAnticipoPagamenti().getMandatoCNR() : "" ));
//		details.addComponent(buildLabel("Altre Spese di Missione Anticipate: ", Double.toString(missione.getDatiAnticipoPagamenti().getSpeseMissioniAnticipate())));
//		details.addComponent(
//				buildLabel("Rimborso da Terzi: " ,  Double.toString(missione.getDatiAnticipoPagamenti().getImportoDaTerzi())));
//		details.addComponent(buildLabel("Importo da Terzi: " , Double.toString(missione.getDatiAnticipoPagamenti().getImportoDaTerzi())));

		return root;
	}

	public boolean onAdvance() {

		ConfirmDialog.show(UI.getCurrent(), "Conferma",
				"La missione inserita non potrà essere più modificata. Verrà inviata una mail all'amministratore con i dati della missione inserita.",
				"Ok", "No", new ConfirmDialog.Listener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -7447338746066450343L;

					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							DashboardEventBus.post(new MissioneAction(missione, modifica));
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
