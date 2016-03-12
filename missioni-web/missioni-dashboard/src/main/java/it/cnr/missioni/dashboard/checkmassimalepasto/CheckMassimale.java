package it.cnr.missioni.dashboard.checkmassimalepasto;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.configuration.TipologiaSpesa.VoceSpesaEnum;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.validator.IValidator;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * 
 * Chain of Responsiblity per verificare il massimale di una fattura
 * 
 * @author Salvia Vito
 */
public class CheckMassimale {

	private double totaleFattureGiornaliera = 0.0;
	private Fattura fattura;
	private Massimale massimale;
	private Rimborso rimborso;
	private Map<String, Fattura> mappa;
	private Fattura otherFattura;
	private Missione missione;
	private String livello;
	private String areaGeografica;
	private User user;

	public CheckMassimale() {
	}

	public CheckMassimale(Missione missione, Fattura fattura, Rimborso rimborso, Map<String, Fattura> mappa) {
		this.missione = missione;
		this.fattura = fattura;
		this.mappa = mappa;
		this.rimborso = rimborso;
	}

	public void initialize() throws Exception {
		DateTime dateTo = new DateTime(fattura.getData().getYear(), fattura.getData().getMonthOfYear(),
				fattura.getData().getDayOfMonth(), 0, 0);
		DateTime datFrom = new DateTime(fattura.getData().getYear(), fattura.getData().getMonthOfYear(),
				fattura.getData().getDayOfMonth(), 23, 59);
		List<Fattura> listaF = rimborso.getNumberOfFatturaInDay(dateTo, datFrom, fattura.getIdTipologiaSpesa(),
				fattura.getId());
		if (!listaF.isEmpty()) {
			otherFattura = listaF.get(0);

		}
		List<Fattura> lista = rimborso.getNumberOfFatturaInDay(dateTo, datFrom, fattura.getIdTipologiaSpesa(), null);
		// calcolo del totale delle fatture per giorno
		lista.forEach(ff -> {
			mappa.put(ff.getId(), ff);
			setTotaleFattureGiornaliera(getTotaleFattureGiornaliera() + ff.getImporto());
		});
		buildChain();
	}

	private void buildChain() throws Exception {

		IValidator m1 = new Step1();
		IValidator m2 = new Step2();
		IValidator m3 = new Step3();
		IValidator m4 = new Step4();

		IValidator m5 = new Step5();

		IValidator m6 = new Step6();
		IValidator m7 = new Step7();

		m1.setNextValidator(m2);
		m2.setNextValidator(m3);
		m3.setNextValidator(m4);
		m4.setNextValidator(m5);
		m5.setNextValidator(m6);
		m6.setNextValidator(m7);

		m1.check();
	}

	public class Step1 extends IValidator.AbstractValidator {

		/**
		 * @throws Exception
		 * 
		 */
		@Override
		public void check() throws Exception {
			if (fattura.getData().isBefore(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata())
					|| fattura.getData().isAfter(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno())
					|| !missione.isMissioneEstera())
				setAreaGeografica(AreaGeograficaEnum.ITALIA.name());
			else {
				Nazione nazione = ClientConnector
						.getNazione(NazioneSearchBuilder.getNazioneSearchBuilder().withId(missione.getIdNazione()))
						.getNazione().get(0);
				setAreaGeografica(nazione.getAreaGeografica().name());
			}
			this.nextValidator.check();

		}
	}
	
	public class Step2 extends IValidator.AbstractValidator {
		/**
		 * @throws Exception
		 * 
		 */
		@Override
		public void check() throws Exception {
			User user = ClientConnector.getUser(UserSearchBuilder.getUserSearchBuilder().withId(missione.getIdUser()))
					.getUsers().get(0);
			setLivello(user.getDatiCNR().getLivello().name());
			setUser(user);
			this.nextValidator.check();

		}

	}
	
	public class Step3 extends IValidator.AbstractValidator {

		/**
		 * @throws Exception
		 * 
		 */
		@Override
		public void check() throws Exception {
			if (missione.getIdUserSeguito() != null) {
				User userSeguito = ClientConnector
						.getUser(UserSearchBuilder.getUserSearchBuilder().withId(missione.getIdUserSeguito()))
						.getUsers().get(0);
				if (userSeguito.getDatiCNR().getLivello().getStato() < getUser().getDatiCNR().getLivello().getStato())
					setLivello(userSeguito.getDatiCNR().getLivello().name());
			}
			this.nextValidator.check();

		}

	}
	
	public class Step4 extends IValidator.AbstractValidator {

		/**
		 * @throws Exception
		 * 
		 */
		@Override
		public void check() throws Exception {
			MassimaleStore massimaleStore = ClientConnector.getMassimale(MassimaleSearchBuilder
					.getMassimaleSearchBuilder().withLivello(getLivello()).withAreaGeografica(getAreaGeografica())
					.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name()));
			if (massimaleStore.getTotale() > 0) {
				setMassimale(massimaleStore.getMassimale().get(0));
				this.nextValidator.check();
			}

		}

	}
	
	// Verifica che la fattura sia di tipo pasto
	class Step5 extends IValidator.AbstractValidator {

		/**
		 * @throws Exception
		 * 
		 */
		@Override
		public void check() throws Exception {
			TipologiaSpesaStore tipologiaSpesaStore = ClientConnector.getTipologiaSpesa(TipologiaSpesaSearchBuilder
					.getTipologiaSpesaSearchBuilder().withId(getFattura().getIdTipologiaSpesa()));
			if (tipologiaSpesaStore.getTotale() > 0
					&& tipologiaSpesaStore.getTipologiaSpesa().get(0).getVoceSpesa() == VoceSpesaEnum.PASTO) {
				this.nextValidator.check();
			}

		}

	}
	
	// Step nel caso ci sia una sola fattura di tipo Pasto in un giorno
	class Step6 extends IValidator.AbstractValidator {

		/**
		 * 
		 */
		@Override
		public void check() throws Exception {

			if (!missione.isMissioneEstera() && otherFattura == null)
				checkItalia();
			else if (missione.isMissioneEstera() && otherFattura == null)
				checkEstera();
			else
				this.nextValidator.check();
		}

		private void checkEstera() {
			if (totaleFattureGiornaliera > massimale.getValue())
				fattura.setImportoSpettante(fattura.getImporto() - (massimale.getValue()));
		}

		private void checkItalia() {
			if (totaleFattureGiornaliera > massimale.getValue() / 2.0)
				fattura.setImportoSpettante(massimale.getValue() / 2.0);
		}

	}



	// Step nel caso ci siano due fatture di tipo Pasto in un giorno
	public class Step7 extends IValidator.AbstractValidator {

		/**
		 * 
		 */
		@Override
		public void check() throws Exception {
			if (totaleFattureGiornaliera > massimale.getValue()) {

				if (fattura.getImporto() >= otherFattura.getImporto()) {
					fattura.setImportoSpettante(massimale.getValue());
					otherFattura.setImportoSpettante(0.0);
				} else {
					otherFattura.setImportoSpettante(massimale.getValue());
					fattura.setImportoSpettante(0.0);
				}
			}
		}

	}


	/**
	 * @return the fattura
	 */
	public Fattura getFattura() {
		return fattura;
	}

	/**
	 * @param fattura
	 */
	public void setFattura(Fattura fattura) {
		this.fattura = fattura;
	}

	/**
	 * @return the massimale
	 */
	public Massimale getMassimale() {
		return massimale;
	}

	/**
	 * @param massimale
	 */
	public void setMassimale(Massimale massimale) {
		this.massimale = massimale;
	}

	/**
	 * @return the rimborso
	 */
	public Rimborso getRimborso() {
		return rimborso;
	}

	/**
	 * @param rimborso
	 */
	public void setRimborso(Rimborso rimborso) {
		this.rimborso = rimborso;
	}

	/**
	 * @return the mappa
	 */
	public Map<String, Fattura> getMappa() {
		return mappa;
	}

	/**
	 * @param mappa
	 */
	public void setMappa(Map<String, Fattura> mappa) {
		this.mappa = mappa;
	}

	/**
	 * @return the otherFattura
	 */
	public Fattura getOtherFattura() {
		return otherFattura;
	}

	/**
	 * @param otherFattura
	 */
	public void setOtherFattura(Fattura otherFattura) {
		this.otherFattura = otherFattura;
	}

	/**
	 * @return the missione
	 */
	public Missione getMissione() {
		return missione;
	}

	/**
	 * @param missione
	 */
	public void setMissione(Missione missione) {
		this.missione = missione;
	}

	/**
	 * @return the livello
	 */
	public String getLivello() {
		return livello;
	}

	/**
	 * @param livello
	 */
	public void setLivello(String livello) {
		this.livello = livello;
	}

	/**
	 * @return the areaGeografica
	 */
	public String getAreaGeografica() {
		return areaGeografica;
	}

	/**
	 * @param areaGeografica
	 */
	public void setAreaGeografica(String areaGeografica) {
		this.areaGeografica = areaGeografica;
	}

	/**
	 * @return the totaleFattureGiornaliera
	 */
	public double getTotaleFattureGiornaliera() {
		return totaleFattureGiornaliera;
	}

	/**
	 * @param totaleFattureGiornaliera
	 */
	public void setTotaleFattureGiornaliera(double totaleFattureGiornaliera) {
		this.totaleFattureGiornaliera = totaleFattureGiornaliera;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
