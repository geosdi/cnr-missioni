/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.missioni.rest.api.response.massimale;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import it.cnr.missioni.model.configuration.Massimale;

/**
 * 
 * @author Salvia Vito
 *
 */
@JsonRootName(value = "MassimaleStore")
public class MassimaleStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7335393275686326840L;
	/**
	 * 
	 */
	@JsonProperty(value = "massimale", required = false)
	private List<Massimale> massimale;
	private long totale;

	/**
	 * @param massimale
	 */
	public MassimaleStore(List<Massimale> massimale) {
		this.massimale = massimale;
	}


	public MassimaleStore() {
	}


	/**
	 * @return the massimale
	 */
	public List<Massimale> getMassimale() {
		return massimale;
	}


	/**
	 * @param massimale 
	 */
	public void setMassimale(List<Massimale> massimale) {
		this.massimale = massimale;
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
