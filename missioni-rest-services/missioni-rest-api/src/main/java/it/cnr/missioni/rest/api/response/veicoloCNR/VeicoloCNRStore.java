/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.missioni.rest.api.response.veicoloCNR;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import it.cnr.missioni.model.prenotazione.VeicoloCNR;

/**
 * 
 * @author Salvia Vito
 *
 */
@JsonRootName(value = "VeicoloCNRStore")
public class VeicoloCNRStore implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6427571746594415833L;
	@JsonProperty(value = "veicoliCNR", required = false)
    private List<VeicoloCNR> veicoliCNR;
    private long totale;

    public VeicoloCNRStore() {
    }

    public VeicoloCNRStore(List<VeicoloCNR> veicoliCNR) {
        this.veicoliCNR = veicoliCNR;
    }

	/**
	 * @return the veicoliCNR
	 */
	public List<VeicoloCNR> getVeicoliCNR() {
		return veicoliCNR;
	}

	/**
	 * @param veicoliCNR 
	 */
	public void setVeicoliCNR(List<VeicoloCNR> veicoliCNR) {
		this.veicoliCNR = veicoliCNR;
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
