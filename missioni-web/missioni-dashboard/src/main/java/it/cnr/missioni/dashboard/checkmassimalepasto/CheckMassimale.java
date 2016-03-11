package it.cnr.missioni.dashboard.checkmassimalepasto;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.model.user.User;

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
	
	public CheckMassimale(){}
	
	public CheckMassimale(Missione missione,Fattura fattura,Rimborso rimborso,Map<String, Fattura> mappa){
		this.missione = missione;
		this.fattura = fattura;
		this.mappa = mappa;
		this.rimborso = rimborso;
	}
	
	public void initialize() throws Exception{
		DateTime dateTo = new DateTime(fattura.getData().getYear(), fattura.getData().getMonthOfYear(),
				fattura.getData().getDayOfMonth(), 0, 0);
		DateTime datFrom = new DateTime(fattura.getData().getYear(), fattura.getData().getMonthOfYear(),
				fattura.getData().getDayOfMonth(), 23, 59);
		List<Fattura> listaF = rimborso.getNumberOfFatturaInDay(dateTo, datFrom, fattura.getIdTipologiaSpesa(), fattura.getId());
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
	
	private  void buildChain() throws Exception{


		IControlCheckMassimale m1 = new GetAreaGeograficaMissione().newGetAreaGeografica().withCheckMassimale(this);
		IControlCheckMassimale m2 = new GetUserMissione().newGetUser().withCheckMassimale(this);
		IControlCheckMassimale m3 = new GetUserSeguitoMissione().newGetUserSeguito().withCheckMassimale(this);
		IControlCheckMassimale m4 = new GetMassimaleUser().newGetMassimale().withCheckMassimale(this);

		IControlCheckMassimale m5 = new CheckOneFatturaPasto().newCheckOneFattura().withCheckMassimale(this);
		IControlCheckMassimale m6 = new CheckTwoFatturaPasto().newCheckTwoFattura().withCheckMassimale(this);

		m1.setNextControl(m2);
		m2.setNextControl(m3);
		m3.setNextControl(m4);
		m4.setNextControl(m5);
		m5.setNextControl(m6);

		m1.check();
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

