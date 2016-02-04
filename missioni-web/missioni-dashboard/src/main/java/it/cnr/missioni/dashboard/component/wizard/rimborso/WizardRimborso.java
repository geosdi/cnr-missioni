package it.cnr.missioni.dashboard.component.wizard.rimborso;

import org.joda.time.Days;
import org.vaadin.teemu.wizards.event.WizardCancelledEvent;
import org.vaadin.teemu.wizards.event.WizardCompletedEvent;
import org.vaadin.teemu.wizards.event.WizardStepActivationEvent;
import org.vaadin.teemu.wizards.event.WizardStepSetChangedEvent;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.window.IWizard;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

/**
 * @author Salvia Vito
 */
public class WizardRimborso extends IWizard.AbstractWizard {

	private Missione missione;
	private DatiGeneraliRimborsoStep datiGeneraliStep;
	private FatturaRimborsoStep fatturaRimborsoStep;
	private RiepilogoDatiRimborsoStep riepilogoDatiRimborsoStep;

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

			int days = 0;
			
			if (missione.getDatiMissioneEstera()
					.getTrattamentoMissioneEsteraEnum() == TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO) {
				Nazione nazione = ClientConnector
						.getNazione(NazioneSearchBuilder.getNazioneSearchBuilder().withId(missione.getIdNazione()))
						.getNazione().get(0);
				MassimaleStore massimaleStore = ClientConnector
						.getMassimale(MassimaleSearchBuilder.getMassimaleSearchBuilder()
								.withLivello(DashboardUI.getCurrentUser().getDatiCNR().getLivello().name())
								.withAreaGeografica(nazione.getAreaGeografica().name()));

			//se non inserito il massimale oer quel livello e area geografica non creo il wizard
				if (massimaleStore != null) {


					rimborso.calcolaTotaleTAM(massimaleStore.getMassimale().get(0),
							missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata(),
							missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno());
					
				days = Days.daysBetween(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata(), missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno())
							.getDays();

				}

			}
			
			this.datiGeneraliStep = new DatiGeneraliRimborsoStep(missione.getRimborso(),days,missione.isMezzoProprio());
			this.datiGeneraliStep.bindFieldGroup();

			this.fatturaRimborsoStep = new FatturaRimborsoStep(missione);
			this.fatturaRimborsoStep.bindFieldGroup();

			riepilogoDatiRimborsoStep = new RiepilogoDatiRimborsoStep(missione);

			getWizard().addStep(this.datiGeneraliStep, "datiGenerali");

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
				getWizard().getParent().setHeight("50%");
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
