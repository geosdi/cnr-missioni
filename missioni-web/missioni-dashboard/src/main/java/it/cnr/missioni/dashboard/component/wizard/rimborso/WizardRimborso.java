package it.cnr.missioni.dashboard.component.wizard.rimborso;

import org.vaadin.teemu.wizards.event.WizardCancelledEvent;
import org.vaadin.teemu.wizards.event.WizardCompletedEvent;
import org.vaadin.teemu.wizards.event.WizardStepActivationEvent;
import org.vaadin.teemu.wizards.event.WizardStepSetChangedEvent;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.component.window.IWizard;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class WizardRimborso extends IWizard.AbstractWizard {

	private Missione missione;
	private DatiGeneraliRimborsoStep datiGeneraliStep;
	private FatturaRimborsoStep fatturaRimborsoStep;
	private RiepilogoDatiRimborsoStep riepilogoDatiRimborsoStep;
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

		// calcolo del TAM
		try {



			this.datiGeneraliStep = new DatiGeneraliRimborsoStep(missione,0);
			this.datiMissioneEsteraStep = new DatiMissioneEsteraStep(missione);

			this.fatturaRimborsoStep = new FatturaRimborsoStep(missione);
			this.riepilogoDatiRimborsoStep = new RiepilogoDatiRimborsoStep(missione);

			getWizard().addStep(this.datiGeneraliStep, "datiGenerali");
			if(missione.isMissioneEstera())
				getWizard().addStep(this.datiMissioneEsteraStep, "datiMissioneEstera");
			getWizard().addStep(this.fatturaRimborsoStep, "datiFattura");
			getWizard().addStep(this.riepilogoDatiRimborsoStep, "riepilogoDatiRimborso");

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

		if (getWizard().getParent() != null) {

			if (event.getActivatedStep() instanceof FatturaRimborsoStep
					|| event.getActivatedStep() instanceof RiepilogoDatiRimborsoStep)
				getWizard().getParent().setHeight("70%");

			else
				getWizard().getParent().setHeight("45%");
		}
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
		missione.setRimborso(null);
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
		// TODO Auto-generated method stub

	}

}
