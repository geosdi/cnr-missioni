package it.cnr.missioni.dashboard.component.wizard.missione;

import java.text.SimpleDateFormat;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.action.MissioneAction;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
public class RiepilogoDatiMissioneStep implements WizardStep {

	private HorizontalLayout mainLayout;;

	private Missione missione;
	private boolean modifica;
	private boolean isAdmin;
	
	public String getCaption() {
		return "Step 8";
	}

	public RiepilogoDatiMissioneStep(Missione missione,boolean modifica,boolean isAdmin) {
		this.missione = missione;
		this.modifica = modifica;
		this.isAdmin = isAdmin;
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

		

		details.addComponent(Utility.buildLabel("Tipo Missione: ",tipoMissione));
		details.addComponent(Utility.buildLabel("Località: " , missione.getLocalita()));
		if(missione.isMissioneEstera()){
			details.addComponent(Utility.buildLabel("Nazione: " , missione.getShortDescriptionNazione()));
			details.addComponent(Utility.buildLabel("Trattamento Rimbroso : " , missione.getDatiMissioneEstera().getTrattamentoMissioneEsteraEnum().getStato()));
		}
		details.addComponent(Utility.buildLabel("Oggetto: " , missione.getOggetto()));
		details.addComponent(Utility.buildLabel("Fondo: " , missione.getFondo() != null ? missione.getFondo() : ""));
		details.addComponent(Utility.buildLabel("GAE: " , missione.getGAE() != null ? missione.getGAE() : ""));
		details.addComponent(Utility.buildLabel("Responsabile Gruppo: " , missione.getShortResponsabileGruppo()));
		details.addComponent(Utility.buildLabel("Distanza: " , missione.getDistanza()));
		details.addComponent(Utility.buildLabel("Veicolo: " , tipoVeicolo));
		if (missione.getTipoVeicolo().equals("Veicolo Proprio") || missione.getTipoVeicolo().equals("Noleggio")) {

			Veicolo v = DashboardUI.getCurrentUser().getVeicoloPrincipale();
			details.addComponent(Utility.buildLabel("Motivazione: " , missione.getMotivazioni()));
			if(missione.getTipoVeicolo().equals("Veicolo Proprio"))
				details.addComponent(Utility.buildLabel("Veicolo: " , v.getTipo() + " Targa: " + v.getTarga()));

		}
		details.addComponent(
				Utility.buildLabel("Inizio Missione: " , new SimpleDateFormat("dd/MM/yyyy HH:mm").format(missione.getDatiPeriodoMissione().getInizioMissione().toDate())));
		details.addComponent(
				Utility.buildLabel("Presunta Fine Missione: " ,  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(missione.getDatiPeriodoMissione().getFineMissione().toDate())));
		details.addComponent(
				Utility.buildLabel("A seguito di: " , missione.getShortUserSeguito() != null ? missione.getShortUserSeguito() : ""));

		return root;
	}

	public boolean onAdvance() {

		String conferma = "";
		
		if(!isAdmin)
			conferma = "La missione inserita non potrà essere più modificata. Verrà inviata una mail all'amministratore con i dati della missione inserita.";
		else
			conferma = "Verrà inviata una mail all'utente con i dati della missione inserita.";
			
		ConfirmDialog.show(UI.getCurrent(), "Conferma",
				conferma,
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


}
