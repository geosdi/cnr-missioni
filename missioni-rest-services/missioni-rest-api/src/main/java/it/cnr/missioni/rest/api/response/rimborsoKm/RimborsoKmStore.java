/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.missioni.rest.api.response.rimborsoKm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import it.cnr.missioni.model.configuration.RimborsoKm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Salvia Vito
 *
 */
@JsonRootName(value = "RimborsoKmStore")
public class RimborsoKmStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8192902462971190108L;
	/**
	 * 
	 */
	@JsonProperty(value = "rimborsoKm", required = false)
	private List<RimborsoKm> rimborsoKm = new ArrayList<RimborsoKm>();
	private long totale;

	public RimborsoKmStore() {
	}

	public RimborsoKmStore(List<RimborsoKm> rimborsoKm) {
		this.setRimborsoKm(rimborsoKm);
	}

	/**
	 * @return the rimborsoKm
	 */
	public List<RimborsoKm> getRimborsoKm() {
		return rimborsoKm;
	}

	/**
	 * @param rimborsoKm 
	 */
	public void setRimborsoKm(List<RimborsoKm> rimborsoKm) {
		this.rimborsoKm = rimborsoKm;
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
