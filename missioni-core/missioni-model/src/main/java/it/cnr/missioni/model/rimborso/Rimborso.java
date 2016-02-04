package it.cnr.missioni.model.rimborso;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Period;

import it.cnr.missioni.model.adapter.FatturaMapAdapter;
import it.cnr.missioni.model.configuration.Massimale;

/**
 * @author Salvia Vito
 */
public class Rimborso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8658916823848446391L;
	private Long numeroOrdine;
	private DateTime dataRimborso;
	private DateTime dateLastModified;
	private Double totale;
	private Double totaleTAM;
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
		for (Fattura f : this.mappaFattura.values())
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
	 * @return the totaleTAM
	 */
	public Double getTotaleTAM() {
		return totaleTAM;
	}

	/**
	 * @param totaleTAM
	 */
	public void setTotaleTAM(Double totaleTAM) {
		this.totaleTAM = totaleTAM;
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

	public double calcolaTotaleTAM(Massimale massimale, DateTime dataAttraversamentoFrontieraAndata,
			DateTime dataAttraversamentoFrontieraRitorno) {
		double t = 0.0;

		// Calcolo delle ore della missione all'estero
		int hours = Hours.hoursBetween(dataAttraversamentoFrontieraAndata, dataAttraversamentoFrontieraRitorno)
				.getHours();

		// a partire da un giorno
		if (hours >= 24) {
			// ogni 12 ore
			int num = hours / 12;
			t = (massimale.getValue() * num) / 2.0;
		}

		return t;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "Rimborso [numeroOrdine=" + numeroOrdine + ", dataRimborso=" + dataRimborso + ", dateLastModified="
				+ dateLastModified + ", totale=" + totale + ", avvisoPagamento=" + avvisoPagamento
				+ ", anticipazionePagamento=" + anticipazionePagamento + ", mappaFattura=" + mappaFattura + "]";
	}

}
