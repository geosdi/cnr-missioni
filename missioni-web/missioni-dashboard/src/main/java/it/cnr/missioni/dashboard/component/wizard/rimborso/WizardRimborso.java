package it.cnr.missioni.dashboard.component.wizard.rimborso;

import org.vaadin.teemu.wizards.event.WizardCancelledEvent;
import org.vaadin.teemu.wizards.event.WizardCompletedEvent;
import org.vaadin.teemu.wizards.event.WizardStepActivationEvent;
import org.vaadin.teemu.wizards.event.WizardStepSetChangedEvent;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.component.window.IWizard;
import it.cnr.missioni.dashboard.event.DashboardEvent.IEventResetSelectedMissione;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class WizardRimborso extends IWizard.AbstractWizard {

	private Missione missione;
	private User user;
	private DatiGeneraliRimborsoStep datiGeneraliStep;
	private FatturaRimborsoStep fatturaRimborsoStep;
	private RiepilogoDatiRimborsoStep riepilogoDatiRimborsoStepFirst;
	private RiepilogoDatiRimborsoStep riepilogoDatiRimborsoStepLast;
	private IEventResetSelectedMissione resetEvent;

//	private AnticipazioniPagamentoStep anticipazioniPagamentoStep;
	private DatiMissioneEsteraStep datiMissioneEsteraStep;

	public WizardRimborso() {
	}

	/**
	 * 
	 */
	@Override
	public void build() {

		buildWizard();

		Rimborso rimborso = missione.getRimborso();
		missione.setIdUser(this.user.getId());
		// calcolo del TAM
		try {



			this.datiGeneraliStep = new DatiGeneraliRimborsoStep(missione,isAdmin,enabled,modifica);
			

			this.fatturaRimborsoStep = new FatturaRimborsoStep(missione,isAdmin,enabled,modifica);
			this.riepilogoDatiRimborsoStepFirst = new RiepilogoDatiRimborsoStep(missione,modifica,isAdmin,true);
			this.riepilogoDatiRimborsoStepLast = new RiepilogoDatiRimborsoStep(missione,modifica,isAdmin,false);


			//se admin il wizard parte con il riepilogo dei dati
			if(isAdmin)
				getWizard().addStep(this.riepilogoDatiRimborsoStepFirst, "riepilogoDatiRimborsoPre");

			getWizard().addStep(this.datiGeneraliStep, "datiGenerali");
			if(missione.isMissioneEstera()){
				this.datiMissioneEsteraStep = new DatiMissioneEsteraStep(missione,isAdmin,enabled,modifica);
				getWizard().addStep(this.datiMissioneEsteraStep, "datiMissioneEstera");
			}
			getWizard().addStep(this.fatturaRimborsoStep, "datiFattura");
			getWizard().addStep(this.riepilogoDatiRimborsoStepLast, "riepilogoDatiRimborso");

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}

	/**
	 * @param event
	 */
	@Override
	public void activeStepChanged(WizardStepActivationEvent event) {
	}

	/**
	 * @param event
	 */
	@Override
	public void stepSetChanged(WizardStepSetChangedEvent event) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param event
	 */
	@Override
	public void wizardCompleted(WizardCompletedEvent event) {
	}

	/**
	 * @param event
	 */
	@Override
	public void wizardCancelled(WizardCancelledEvent event) {
		if(modifica)
			DashboardEventBus.post(resetEvent);
		endWizard();
	}

	/**
	 * @param missione
	 */
	@Override
	public void setMissione(Missione missione) {
		this.missione = missione;
	}

	/**
	 * @param user
	 */
	@Override
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param enabled
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param isAdmin
	 */
	@Override
	public void isAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @param modifica
	 */
	@Override
	public void setModifica(boolean modifica) {
		this.modifica = modifica;
	}

	/**
	 * @param event
	 */
	@Override
	public void setEvent(IEventResetSelectedMissione resetEvent) {
		this.resetEvent = resetEvent;		
	}

}
