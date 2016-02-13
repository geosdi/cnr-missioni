/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.missioni.rest.api.response.nazione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import it.cnr.missioni.model.configuration.Nazione;

/**
 * 
 * @author Salvia Vito
 *
 */
@JsonRootName(value = "NazioneStore")
public class NazioneStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3414629973051200861L;
	@JsonProperty(value = "nazione", required = false)
	private List<Nazione> nazione = new ArrayList<Nazione>();
	private long totale;

	public NazioneStore() {
	}

	public NazioneStore(List<Nazione> nazione) {
		this.nazione = nazione;
	}

	/**
	 * @return the nazione
	 */
	public List<Nazione> getNazione() {
		return nazione;
	}

	/**
	 * @param nazione
	 */
	public void setNazione(List<Nazione> nazione) {
		this.nazione = nazione;
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

}
