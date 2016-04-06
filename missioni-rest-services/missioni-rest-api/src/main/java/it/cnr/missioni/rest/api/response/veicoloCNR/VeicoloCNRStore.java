/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.missioni.rest.api.response.veicoloCNR;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private List<VeicoloCNR> veicoliCNR = new ArrayList<VeicoloCNR>();
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
