package it.cnr.missioni.model.rimborso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class Rimborso  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8658916823848446391L;
	private long numeroOrdine;
	private DateTime dataRimborso;
	private List<Fattura> listaFatture = new ArrayList<Fattura>();
	private double totale;
	private String avvisoPagamento;
	private double anticipazionePagamento;
	

	/**
	 * @return the numeroOrdine
	 */
	public long getNumeroOrdine() {
		return numeroOrdine;
	}

	/**
	 * @param numeroOrdine 
	 */
	public void setNumeroOrdine(long numeroOrdine) {
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
	public double getTotale() {
		return totale;
	}

	/**
	 * @param totale 
	 */
	public void setTotale(double totale) {
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
	public double getAnticipazionePagamento() {
		return anticipazionePagamento;
	}

	/**
	 * @param anticipazionePagamento 
	 */
	public void setAnticipazionePagamento(double anticipazionePagamento) {
		this.anticipazionePagamento = anticipazionePagamento;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "Rimborso [numeroOrdine=" + numeroOrdine + ", dataRimborso=" + dataRimborso + ", listaFatture="
				+ listaFatture + ", totale=" + totale + ", avvisoPagamento=" + avvisoPagamento
				+ ", anticipazionePagamento=" + anticipazionePagamento + "]";
	}





}
