package it.cnr.missioni.model.rimborso;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import it.cnr.missioni.model.adapter.FatturaMapAdapter;

/**
 * @author Salvia Vito
 */
public class Rimborso  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8658916823848446391L;
	private Long numeroOrdine;
	private DateTime dataRimborso;
	private DateTime dateLastModified;
	private Double totale;
	private String avvisoPagamento;
	@Min(value = 0)
	private Double anticipazionePagamento;
	@XmlJavaTypeAdapter(value = FatturaMapAdapter.class)
	private Map<String, Fattura> mappaFattura = new HashMap<String, Fattura>();
	

	/**
	 * @return the numeroOrdine
	 */
	public Long getNumeroOrdine() {
		return numeroOrdine;
	}

	/**
	 * @param numeroOrdine 
	 */
	public void setNumeroOrdine(Long numeroOrdine) {
		this.numeroOrdine = numeroOrdine;
	}

	/**
	 * @return the dataRimborso
	 */
	public DateTime getDataRimborso() {
		return dataRimborso;
	}

	/**
	 * @param dataRimborso
	 */
	public void setDataRimborso(DateTime dataRimborso) {
		this.dataRimborso = dataRimborso;
	}

	/**
	 * @return the dateLastModified
	 */
	public DateTime getDateLastModified() {
		return dateLastModified;
	}

	/**
	 * @param dateLastModified 
	 */
	public void setDateLastModified(DateTime dateLastModified) {
		this.dateLastModified = dateLastModified;
	}


	/**
	 * @return the totale
	 */
	public Double getTotale() {
		double t = 0.0;
		for(Fattura f : this.mappaFattura.values())
			t += f.getImporto();
		
		return t;
	}

	/**
	 * @param totale 
	 */
	public void setTotale(Double totale) {
		this.totale = totale;
	}

	/**
	 * @return the avvisoPagamento
	 */
	public String getAvvisoPagamento() {
		return avvisoPagamento;
	}

	/**
	 * @param avvisoPagamento 
	 */
	public void setAvvisoPagamento(String avvisoPagamento) {
		this.avvisoPagamento = avvisoPagamento;
	}

	/**
	 * @return the anticipazionePagamento
	 */
	public Double getAnticipazionePagamento() {
		return anticipazionePagamento;
	}

	/**
	 * @param anticipazionePagamento 
	 */
	public void setAnticipazionePagamento(Double anticipazionePagamento) {
		this.anticipazionePagamento = anticipazionePagamento;
	}

	/**
	 * @return the mappaFattura
	 */
	public Map<String, Fattura> getMappaFattura() {
		return mappaFattura;
	}

	/**
	 * @param mappaFattura 
	 */
	public void setMappaFattura(Map<String, Fattura> mappaFattura) {
		this.mappaFattura = mappaFattura;
	}



	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "Rimborso [numeroOrdine=" + numeroOrdine + ", dataRimborso=" + dataRimborso + ", dateLastModified="
				+ dateLastModified + ", totale=" + totale + ", avvisoPagamento=" + avvisoPagamento
				+ ", anticipazionePagamento=" + anticipazionePagamento + ", mappaFattura=" + mappaFattura+"]";
	}







}
