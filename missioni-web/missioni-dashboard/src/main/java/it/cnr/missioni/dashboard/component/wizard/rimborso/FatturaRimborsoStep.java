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
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.TipologiaSpesa.VoceSpesaEnum;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.validator.ICheckMassimale;
import it.cnr.missioni.model.validator.ICheckMassimale.CheckMassimale;
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
		try {
			User userASeguito = null;
			User user = ClientConnector.getUser(UserSearchBuilder.getUserSearchBuilder().withId(missione.getIdUser())).getUsers().get(0);
			String livello = user.getDatiCNR().getLivello().name();
			//se è a seguito
			if(missione.getIdUserSeguito() != null){
				userASeguito = ClientConnector.getUser(UserSearchBuilder.getUserSearchBuilder().withId(missione.getIdUser())).getUsers().get(0);
				livello = user.getDatiCNR().getLivello().getStato() >= userASeguito.getDatiCNR().getLivello().getStato() ? user.getDatiCNR().getLivello().name() : userASeguito.getDatiCNR().getLivello().name();
			}
			Massimale massimaleItalia  = getMassimale(AreaGeograficaEnum.ITALIA.name(), livello);
			Massimale massimaleEstero = null;
			if(missione.isMissioneEstera()){
				Nazione n = ClientConnector.getNazione(NazioneSearchBuilder.getNazioneSearchBuilder().withId(missione.getIdNazione())).getNazione().get(0);
				massimaleEstero = getMassimale(n.getAreaGeografica().name(), livello);
			}
			ICheckMassimale checkMassimale = CheckMassimale.getCheckMassimale().withMissione(missione).withMassimale(massimaleItalia).withMassimaleEstero(massimaleEstero).initialize();
			rimborso.getMappaFattura().values().stream().filter(f->!mappa.containsKey(f.getId())).forEach(f -> {
					try {
						TipologiaSpesa tipologiaSpesa = getTipologia(f);
						checkMassimale.withFattura(f).withMappa(mappa);
						if (tipologiaSpesa != null && tipologiaSpesa.getVoceSpesa() == VoceSpesaEnum.PASTO)
							checkMassimale.initSteps();
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					mappa.put(f.getId(), f);
			});
		} catch (Exception e1) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Recupera la tipologia spesa per la fattura
	 * 
	 * @param areaGeografica
	 * @param livello
	 * @return
	 * @throws Exception
	 */
	private TipologiaSpesa getTipologia(Fattura fattura) throws Exception{
		TipologiaSpesa tipologiaSpesa= null;
		TipologiaSpesaStore tipologiaSpesaStore = ClientConnector.getTipologiaSpesa(TipologiaSpesaSearchBuilder
				.getTipologiaSpesaSearchBuilder().withId(fattura.getIdTipologiaSpesa()));
		if (tipologiaSpesaStore.getTotale() > 0)
			tipologiaSpesa = tipologiaSpesaStore.getTipologiaSpesa().get(0);
		return tipologiaSpesa;
	}
	
	/**
	 * Recupera il massimale per quella missione
	 * 
	 * @param areaGeografica
	 * @param livello
	 * @return
	 * @throws Exception
	 */
	private Massimale getMassimale(String areaGeografica,String livello) throws Exception{
		Massimale massimale = null;
		MassimaleStore massimaleStore = ClientConnector.getMassimale(MassimaleSearchBuilder
				.getMassimaleSearchBuilder().withLivello(livello).withAreaGeografica(areaGeografica)
				.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name()));
		if (massimaleStore.getTotale() > 0) 
			massimale = massimaleStore.getMassimale().get(0);
		return massimale;
	}

	public FatturaRimborsoStep(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
		this.missione = missione;
		this.fatturaRimborsoForm = new FatturaRimborsoForm(missione, isAdmin, enabled, modifica);
	}

	public Component getContent() {
		this.fatturaRimborsoForm.setRangeDate();
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
