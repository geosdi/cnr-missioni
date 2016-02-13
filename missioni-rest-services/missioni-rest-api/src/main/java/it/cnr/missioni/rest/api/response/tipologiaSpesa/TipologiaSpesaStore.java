package it.cnr.missioni.rest.api.response.tipologiaSpesa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import it.cnr.missioni.model.configuration.TipologiaSpesa;

/**
 * 
 * @author Salvia Vito
 *
 */
@JsonRootName(value = "TipologiaStore")
public class TipologiaSpesaStore implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3124640562143124819L;
	@JsonProperty(value = "tipologiaSpesa", required = false)
    private List<TipologiaSpesa> tipologiaSpesa = new ArrayList<TipologiaSpesa>();
    private long totale;

    public TipologiaSpesaStore() {
    }

    public TipologiaSpesaStore(List<TipologiaSpesa> tipologiaSpesa) {
        this.setTipologiaSpesa(tipologiaSpesa);
    }



    /**
	 * @return the tipologiaSpesa
	 */
	public List<TipologiaSpesa> getTipologiaSpesa() {
		return tipologiaSpesa;
	}

	/**
	 * @param tipologiaSpesa 
	 */
	public void setTipologiaSpesa(List<TipologiaSpesa> tipologiaSpesa) {
		this.tipologiaSpesa = tipologiaSpesa;
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
		return "TipologiaSpesaStore [tipologiaSpesa=" + tipologiaSpesa + ", totale=" + totale + "]";
	}


}
