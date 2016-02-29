package it.cnr.missioni.dashboard.component.wizard.user;

import org.vaadin.teemu.wizards.event.WizardCancelledEvent;
import org.vaadin.teemu.wizards.event.WizardCompletedEvent;
import org.vaadin.teemu.wizards.event.WizardStepActivationEvent;
import org.vaadin.teemu.wizards.event.WizardStepSetChangedEvent;

import com.vaadin.server.Page;

import it.cnr.missioni.dashboard.component.window.IWizard;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class WizardUser extends IWizard.AbstractWizard {

	private User user;

	public WizardUser() {
	}

	/**
	 * 
	 */
	@Override
	public void build() {
		
		
		buildWizard();
		
		AnagraficaUserStep anagraficaStep = new AnagraficaUserStep(user);
		ResidenzaUserStep residenzaUserStep = new ResidenzaUserStep(user);
		PatenteUserStep patenteUserStep = new PatenteUserStep(user);
		DatiCNRUserStep datiCNRUserStep = new DatiCNRUserStep(user);

		getWizard().addStep(anagraficaStep, "anagrafica");
		getWizard().addStep(residenzaUserStep, "residenza");
		getWizard().addStep(patenteUserStep, "patente");
		getWizard().addStep(datiCNRUserStep, "datiCNR");

	}

	/**
	 * @param event
	 */
	@Override
	public void activeStepChanged(WizardStepActivationEvent event) {
		Page.getCurrent().setTitle(event.getActivatedStep().getCaption());

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
		endWizard();
	}

	/**
	 * @param event
	 */
	@Override
	public void wizardCancelled(WizardCancelledEvent event) {
		endWizard();
	}

	/**
	 * @param missione
	 */
	@Override
	public void setMissione(Missione missione) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param user
	 */
	@Override
	public void setUser(User user) {
		this.user = user;
	}

}
