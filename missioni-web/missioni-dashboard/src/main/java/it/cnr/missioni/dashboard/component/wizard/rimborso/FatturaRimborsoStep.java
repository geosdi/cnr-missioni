package it.cnr.missioni.dashboard.component.wizard.rimborso;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.rimborso.FatturaRimborsoForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * @author Salvia Vito
 */
public class FatturaRimborsoStep implements WizardStep {


	private Missione missione;
	private FatturaRimborsoForm fatturaRimborsoForm;

	public String getCaption() {
		return "Step 3";
	}
	
	/*
	 * Per ogni fattura verifica il massimale
	 */
	private void checkMassimale(Rimborso rimborso) {

		Map<String, Fattura> mappa = new HashMap<String, Fattura>();

		rimborso.getMappaFattura().values().forEach(f -> {
			String id = f.getId();
			if (!mappa.containsKey(id)) {

				try {
					TipologiaSpesaStore tipologiaStore = ClientConnector.getTipologiaSpesa(TipologiaSpesaSearchBuilder
							.getTipologiaSpesaSearchBuilder().withId(f.getIdTipologiaSpesa()));
					TipologiaSpesa tipologiaSpesa = tipologiaStore.getTipologiaSpesa().get(0);
					if (tipologiaSpesa.isCheckMassimale()) {
						String areaGeografica;

						if (missione.isMissioneEstera()) {

							if (f.getData()
									.isBefore(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata())
									|| f.getData().isAfter(
											missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno()))
								areaGeografica = AreaGeograficaEnum.ITALIA.name();
							else {
								Nazione nazione = ClientConnector.getNazione(
										NazioneSearchBuilder.getNazioneSearchBuilder().withId(missione.getIdNazione()))
										.getNazione().get(0);
								areaGeografica = nazione.getAreaGeografica().name();
							}
						} else {
							areaGeografica = AreaGeograficaEnum.ITALIA.name();
						}
						User user = ClientConnector
								.getUser(UserSearchBuilder.getUserSearchBuilder().withId(missione.getIdUser()))
								.getUsers().get(0);
						String livello = user.getDatiCNR().getLivello().name();

						if (missione.getIdUserSeguito() != null) {
							User userSeguito = ClientConnector.getUser(
									UserSearchBuilder.getUserSearchBuilder().withId(missione.getIdUserSeguito()))
									.getUsers().get(0);
							if (userSeguito.getDatiCNR().getLivello().getStato() < user.getDatiCNR().getLivello()
									.getStato())
								livello = userSeguito.getDatiCNR().getLivello().name();
						}

						MassimaleStore massimaleStore = ClientConnector.getMassimale(MassimaleSearchBuilder
								.getMassimaleSearchBuilder().withLivello(livello).withAreaGeografica(areaGeografica)
								.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name()));

						if(massimaleStore.getTotale() > 0)
							rimborso.checkMassimale(f, massimaleStore.getMassimale().get(0), mappa,
								missione.isMissioneEstera());

					}
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}

			} else
				mappa.put(f.getId(), f);
		});

	}

	public FatturaRimborsoStep(Missione missione,boolean isAdmin,boolean enabled,boolean modifica) {
		this.missione = missione;
		this.fatturaRimborsoForm = new FatturaRimborsoForm(missione,isAdmin,enabled,modifica);
	}

	public Component getContent() {
		this.fatturaRimborsoForm .setRangeDate();
		return fatturaRimborsoForm;
	}


	public boolean onAdvance() {
		checkMassimale(missione.getRimborso());
		return true;
	}

	public boolean onBack() {
		return true;
	}

}
