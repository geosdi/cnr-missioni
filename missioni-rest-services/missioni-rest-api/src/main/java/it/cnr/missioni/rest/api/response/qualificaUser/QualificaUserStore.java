/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.missioni.rest.api.response.qualificaUser;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import it.cnr.missioni.model.configuration.QualificaUser;

/**
 * 
 * @author Salvia Vito
 *
 */
@JsonRootName(value = "QualificaUserStore")
public class QualificaUserStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4920026196206410573L;
	@JsonProperty(value = "qualificaUser", required = false)
	private List<QualificaUser> qualificaUser;
	private long totale;

	public QualificaUserStore() {
	}

	public QualificaUserStore(List<QualificaUser> qualificaUser) {
		this.qualificaUser = qualificaUser;
	}

	/**
	 * @return the qualificaUser
	 */
	public List<QualificaUser> getQualificaUser() {
		return qualificaUser;
	}

	/**
	 * @param qualificaUser
	 */
	public void setQualificaUser(List<QualificaUser> qualificaUser) {
		this.qualificaUser = qualificaUser;
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
