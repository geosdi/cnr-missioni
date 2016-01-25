package it.cnr.missioni.rest.api.response.prenotazione;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import it.cnr.missioni.model.prenotazione.Prenotazione;

/**
 * 
 * @author Salvia Vito
 *
 */
@JsonRootName(value = "PrenotazioniStore")
@JsonPropertyOrder(value = { "prenotazioni", "totale" })
public class PrenotazioniStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6233708924211248278L;
	@JsonProperty(value = "prenotazioni", required = false)
	private List<Prenotazione> prenotazioni;
	private long totale;

	public PrenotazioniStore() {
	}

	public PrenotazioniStore(List<Prenotazione> thePrenotazioni) {
		this.prenotazioni = thePrenotazioni;
	}

	/**
	 * @return the prenotazioni
	 */
	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	/**
	 * @param prenotazioni
	 */
	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	/**
	 * @return the totale
	 */
	public long getTotale() {
		return totale;
	}

	/**
	 * @param totale
	 */
	public void setTotale(long totale) {
		this.totale = totale;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "PrenotazioniStore [prenotazioni=" + prenotazioni + ", totale=" + totale + "]";
	}

}
