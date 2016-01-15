package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.event.WizardCancelledEvent;
import org.vaadin.teemu.wizards.event.WizardCompletedEvent;
import org.vaadin.teemu.wizards.event.WizardProgressListener;
import org.vaadin.teemu.wizards.event.WizardStepActivationEvent;
import org.vaadin.teemu.wizards.event.WizardStepSetChangedEvent;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

import it.cnr.missioni.dashboard.component.wizard.rimborso.DatiGeneraliRimborsoStep;
import it.cnr.missioni.dashboard.component.wizard.rimborso.FatturaRimborsoStep;
import it.cnr.missioni.dashboard.component.wizard.user.AnagraficaUserStep;
import it.cnr.missioni.dashboard.component.wizard.user.DatiCNRUserStep;
import it.cnr.missioni.dashboard.component.wizard.user.PatenteUserStep;
import it.cnr.missioni.dashboard.component.wizard.user.ResidenzaUserStep;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
@Theme("valo")
public class WizardSetup implements WizardProgressListener {

	private Wizard wizard;
	private VerticalLayout mainLayout;
	private boolean modifica;
	private Missione missione;

	public VerticalLayout getComponent() {
		return mainLayout;
	}

	public WizardSetup(boolean modifica, final Missione missione, String tipo) {
		this.modifica = modifica;
		this.missione = missione;
		init();
		if (tipo.equals("missione"))
			buildWizardMissione();
		if (tipo.equals("rimborso"))
			buildWizardRimborso();
	}

	public WizardSetup(User user) {
		init();
		buildWizardUser(user);
	}

	/**
	 * Costruisce il wizard per una missione
	 */
	private void buildWizardMissione() {
		DatiGeneraliMissioneStep datiGeneraliStep = new DatiGeneraliMissioneStep(missione);
		datiGeneraliStep.bindFieldGroup();

		DatiPeriodoMissioneStep datiPeriodoMissioneStep = new DatiPeriodoMissioneStep(missione.getDatiPeriodoMissione(),
				missione);
		datiPeriodoMissioneStep.bindFieldGroup();

		DatiMissioneEsteraStep missioneEsteraStep = new DatiMissioneEsteraStep(missione.getDatiMissioneEstera(),
				missione);
		missioneEsteraStep.bindFieldGroup();

		AnticipazioniPagamentoStep anticipazioniPagamentoStep = new AnticipazioniPagamentoStep(
				missione.getDatiAnticipoPagamenti(), missione, modifica);
		anticipazioniPagamentoStep.bindFieldGroup();

		wizard.addStep(datiGeneraliStep, "datiGenerali");
		wizard.addStep(datiPeriodoMissioneStep, "inizioFine");
		wizard.addStep(missioneEsteraStep, "missioneEstera");
		wizard.addStep(anticipazioniPagamentoStep, "anticipazioniPagamento");
	}

	/**
	 * Costruisce il wizard per l'user
	 */
	private void buildWizardUser(User user) {
		AnagraficaUserStep anagraficaStep = new AnagraficaUserStep(user);
		anagraficaStep.bindFieldGroup();

		ResidenzaUserStep residenzaUserStep = new ResidenzaUserStep(user);
		residenzaUserStep.bindFieldGroup();

		PatenteUserStep patenteUserStep = new PatenteUserStep(user);
		patenteUserStep.bindFieldGroup();

		DatiCNRUserStep datiCNRUserStep = new DatiCNRUserStep(user);
		datiCNRUserStep.bindFieldGroup();

		wizard.addStep(anagraficaStep, "anagrafica");
		wizard.addStep(residenzaUserStep, "residenza");
		wizard.addStep(patenteUserStep, "patente");
		wizard.addStep(datiCNRUserStep, "datiCNR");
	}

	/**
	 * Costruisce il wizard per un rimborso
	 */
	private void buildWizardRimborso() {
		DatiGeneraliRimborsoStep datiGeneraliStep = new DatiGeneraliRimborsoStep(missione.getRimborso());
		datiGeneraliStep.bindFieldGroup();

		FatturaRimborsoStep fatturaRimborsoStep = new FatturaRimborsoStep(missione);
		fatturaRimborsoStep.bindFieldGroup();

		wizard.addStep(datiGeneraliStep, "datiGenerali");
		wizard.addStep(fatturaRimborsoStep, "datiFattura");
		wizard.addListener(new WizardProgressListener() {

			@Override
			public void wizardCompleted(WizardCompletedEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void wizardCancelled(WizardCancelledEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void stepSetChanged(WizardStepSetChangedEvent event) {
				// TODO Auto-generated method stub
			}

			@Override
			public void activeStepChanged(WizardStepActivationEvent event) {
				// TODO Auto-generated method stub
				if (event.getActivatedStep().getCaption().equals("Fatture"))
					wizard.getParent().getParent().setHeight("70%");

				else
					wizard.getParent().getParent().setHeight("50%");

			}
		});
	}

	private void init() {

		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.setMargin(true);
		mainLayout.addStyleName("wizard");
		wizard = new Wizard();
		wizard.setUriFragmentEnabled(true);
		wizard.addListener(this);
		// if(tipo.equals("missione"))
		// buildWizardMissione();
		// if(tipo.equals("rimborso"))
		// buildWizardRimborso();
		mainLayout.addComponent(wizard);
		mainLayout.setComponentAlignment(wizard, Alignment.TOP_CENTER);
		mainLayout.setExpandRatio(wizard, 1f);
		wizard.getBackButton().setCaption("Indietro");
		wizard.getCancelButton().setCaption("Cancella");
		wizard.getNextButton().setCaption("Avanti");
		wizard.getFinishButton().setCaption("Concludi");
		wizard.setWidth("95%");
	}

	public void wizardCompleted(WizardCompletedEvent event) {
		endWizard();
	}

	public void activeStepChanged(WizardStepActivationEvent event) {
		// display the step caption as the window title
		Page.getCurrent().setTitle(event.getActivatedStep().getCaption());
	}

	public void stepSetChanged(WizardStepSetChangedEvent event) {
		// NOP, not interested on this event
	}

	public void wizardCancelled(WizardCancelledEvent event) {
		endWizard();
	}

	private void endWizard() {
		wizard.setVisible(false);
		DashboardEventBus.post(new CloseOpenWindowsEvent());
	}

}
