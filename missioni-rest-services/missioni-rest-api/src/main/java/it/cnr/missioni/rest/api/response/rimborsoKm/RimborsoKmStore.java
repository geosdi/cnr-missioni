/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.missioni.rest.api.response.rimborsoKm;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.RimborsoKm;

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
	private RimborsoKm rimborsoKm;

	public RimborsoKmStore() {
	}

	public RimborsoKmStore(RimborsoKm rimborsoKm) {
		this.setRimborsoKm(rimborsoKm);
	}

	/**
	 * @return the rimborsoKm
	 */
	public RimborsoKm getRimborsoKm() {
		return rimborsoKm;
	}

	/**
	 * @param rimborsoKm 
	 */
	public void setRimborsoKm(RimborsoKm rimborsoKm) {
		this.rimborsoKm = rimborsoKm;
	}


}
