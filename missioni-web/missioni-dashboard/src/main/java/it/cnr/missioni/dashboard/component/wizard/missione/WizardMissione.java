package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.event.WizardCancelledEvent;
import org.vaadin.teemu.wizards.event.WizardCompletedEvent;
import org.vaadin.teemu.wizards.event.WizardStepActivationEvent;
import org.vaadin.teemu.wizards.event.WizardStepSetChangedEvent;

import it.cnr.missioni.dashboard.component.window.AbstractWizard;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class WizardMissione extends AbstractWizard {

	private Missione missione;
	private DatiMissioneEsteraStep missioneEsteraStep;
	private DatiPeriodoMissioneStep datiPeriodoMissioneStep;
	private FondoGAEStep fondoGAEStep;
	private LocalitaOggettoStep localitaOggettoStep;
	private TipoMissioneStep tipoMissioneStep;
	private DatiVeicoloMissioneStep datiGeneraliStep;
	private AnticipazioniPagamentoStep anticipazioniPagamentoStep;
	private RiepilogoDatiMissioneStep ripilogoDatiStep;

	public WizardMissione() {
	}

	/**
	 * 
	 */
	@Override
	public void build() {

		buildWizard();

		DatiVeicoloMissioneStep datiGeneraliStep = new DatiVeicoloMissioneStep(missione);
		datiGeneraliStep.bindFieldGroup();

		this.tipoMissioneStep = new TipoMissioneStep(missione);

		this.localitaOggettoStep = new LocalitaOggettoStep(missione);
		this.localitaOggettoStep.bindFieldGroup();

		this.fondoGAEStep = new FondoGAEStep(missione);
		this.fondoGAEStep.bindFieldGroup();

		this.datiPeriodoMissioneStep = new DatiPeriodoMissioneStep(missione.getDatiPeriodoMissione(), missione);
		this.datiPeriodoMissioneStep.bindFieldGroup();

		this.missioneEsteraStep = new DatiMissioneEsteraStep(missione.getDatiMissioneEstera(), missione);
		this.missioneEsteraStep.bindFieldGroup();

		this.anticipazioniPagamentoStep = new AnticipazioniPagamentoStep(missione);
		this.anticipazioniPagamentoStep.bindFieldGroup();
		ripilogoDatiStep = new RiepilogoDatiMissioneStep(missione);

		getWizard().addStep(tipoMissioneStep, "tipoMissione");
		getWizard().addStep(localitaOggettoStep, "localitaOggetto");
		getWizard().addStep(fondoGAEStep, "fondoGAE");
		getWizard().addStep(datiGeneraliStep, "datiGenerali");
		getWizard().addStep(datiPeriodoMissioneStep, "inizioFine");
		getWizard().addStep(missioneEsteraStep, "missioneEstera");
		getWizard().addStep(anticipazioniPagamentoStep, "anticipazioniPagamento");
		getWizard().addStep(ripilogoDatiStep, "riepilogoDati");

	}

	/**
	 * @param event
	 */
	@Override
	public void activeStepChanged(WizardStepActivationEvent event) {
		if (event.getActivatedStep() instanceof DatiMissioneEsteraStep) {
			this.missioneEsteraStep.addDateRange();
			this.missioneEsteraStep.addValidator();
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
		// TODO Auto-generated method stub

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
