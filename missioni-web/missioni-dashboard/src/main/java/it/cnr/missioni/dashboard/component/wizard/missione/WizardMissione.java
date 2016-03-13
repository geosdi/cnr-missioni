package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.event.WizardCancelledEvent;
import org.vaadin.teemu.wizards.event.WizardCompletedEvent;
import org.vaadin.teemu.wizards.event.WizardStepActivationEvent;
import org.vaadin.teemu.wizards.event.WizardStepSetChangedEvent;

import it.cnr.missioni.dashboard.component.window.IWizard;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class WizardMissione extends IWizard.AbstractWizard {

	private Missione missione;
	private User user;
	private DatiPeriodoMissioneStep datiPeriodoMissioneStep;
	private FondoGAEStep fondoGAEStep;
	private LocalitaOggettoStep localitaOggettoStep;
	private TipoMissioneStep tipoMissioneStep;
	private RiepilogoDatiMissioneStep ripilogoDatiStep;
	private DatiVeicoloMissioneStep datiVeicoloMissioneStep;

	public WizardMissione() {
	}

	/**
	 * 
	 */
	@Override
	public void build() {

		buildWizard();
		missione.setIdUser(this.user.getId());
		this.datiVeicoloMissioneStep = new DatiVeicoloMissioneStep(missione,isAdmin,enabled,modifica);
		this.tipoMissioneStep = new TipoMissioneStep(missione,isAdmin,enabled,modifica);
		this.localitaOggettoStep = new LocalitaOggettoStep(missione,isAdmin,enabled,modifica);
		this.fondoGAEStep = new FondoGAEStep(missione,isAdmin,enabled,modifica);
		this.datiPeriodoMissioneStep = new DatiPeriodoMissioneStep(missione.getDatiPeriodoMissione(), missione,isAdmin,enabled,modifica);
		ripilogoDatiStep = new RiepilogoDatiMissioneStep(missione,modifica,isAdmin);

		getWizard().addStep(tipoMissioneStep, "tipoMissione");
		getWizard().addStep(localitaOggettoStep, "localitaOggetto");
		getWizard().addStep(fondoGAEStep, "fondoGAE");
		getWizard().addStep(datiVeicoloMissioneStep, "datiGenerali");
		getWizard().addStep(datiPeriodoMissioneStep, "inizioFine");
		getWizard().addStep(ripilogoDatiStep, "riepilogoDati");

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
		// TODO Auto-generated method stub

	}

	/**
	 * @param event
	 */
	@Override
	public void wizardCancelled(WizardCancelledEvent event) {
		if(isAdmin)
			DashboardEventBus.post(new  DashboardEvent.ResetMissioneAdminEvent() );
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
	 * @param modifica
	 */
	@Override
	public void setModifica(boolean modifica) {
		this.modifica = modifica;
	}

	/**
	 * @param isAdmin
	 */
	@Override
	public void isAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}



}
