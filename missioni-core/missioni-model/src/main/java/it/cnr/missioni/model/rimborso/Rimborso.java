package it.cnr.missioni.model.rimborso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class Rimborso  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8658916823848446391L;
	@NotNull
	private Long numeroOrdine;
	@NotNull
	private DateTime dataRimborso;
	private DateTime dateLastModified;
	private List<Fattura> listaFatture = new ArrayList<Fattura>();
	private Double totale;
	private String avvisoPagamento;
	private Double anticipazionePagamento;
	

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
	 * @return the listaFatture
	 */
	public List<Fattura> getListaFatture() {
		return listaFatture;
	}

	/**
	 * @param listaFatture
	 */
	public void setListaFatture(List<Fattura> listaFatture) {
		this.listaFatture = listaFatture;
	}

	/**
	 * @return the totale
	 */
	public Double getTotale() {
		return totale;
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
	 * @return
	 */
	@Override
	public String toString() {
		return "Rimborso [numeroOrdine=" + numeroOrdine + ", dataRimborso=" + dataRimborso + ", dateLastModified="
				+ dateLastModified + ", listaFatture=" + listaFatture + ", totale=" + totale + ", avvisoPagamento="
				+ avvisoPagamento + ", anticipazionePagamento=" + anticipazionePagamento + "]";
	}





}
