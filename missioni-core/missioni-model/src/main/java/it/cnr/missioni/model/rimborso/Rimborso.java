package it.cnr.missioni.model.rimborso;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;
import org.joda.time.Hours;

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
	@NotNull
	private String mandatoPagamento;
	private Long numeroOrdine;
	private DateTime dataRimborso;
	private DateTime dateLastModified;
	private Double totale = 0.0;
	private Double totaleTAM = 0.0;
	private String avvisoPagamento;
	@Min(value = 0)
	private Double totKm;
	private Double rimborsoKm = 0.0;
	@Min(value = 0)
	private Double anticipazionePagamento;
	private Double totaleDovuto;
	private boolean pagata;
	private boolean rimborsoDaTerzi;
	@Min(value = 0)
	private double importoDaTerzi;
	@XmlJavaTypeAdapter(value = FatturaMapAdapter.class)
	private Map<String, Fattura> mappaFattura = new HashMap<String, Fattura>();
	

	
	public int getNumberOfFatturaInDay(DateTime from,DateTime to,String idTipologiaSpesa){
		return (getMappaFattura().values().stream().filter(f -> f.getData().isAfter(from)).filter(f -> f.getData().isBefore(to)).filter(f->f.getIdTipologiaSpesa().equals(idTipologiaSpesa)).collect(Collectors.toList())).size();
	}
	

	/**
	 * @return the mandatoPagamento
	 */
	public String getMandatoPagamento() {
		return mandatoPagamento;
	}

	/**
	 * @param mandatoPagamento 
	 */
	public void setMandatoPagamento(String mandatoPagamento) {
		this.mandatoPagamento = mandatoPagamento;
	}

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
	 * @return the totKm
	 */
	public Double getTotKm() {
		return totKm;
	}

	/**
	 * @param totKm 
	 */
	public void setTotKm(Double totKm) {
		this.totKm = totKm;
	}

	/**
	 * @return the rimborsoKm
	 */
	public Double getRimborsoKm() {
		return rimborsoKm;
	}

	/**
	 * @param rimborsoKm 
	 */
	public void setRimborsoKm(Double rimborsoKm) {
		this.rimborsoKm = rimborsoKm;
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
	 * @return the totaleDovuto
	 */
	public Double getTotaleDovuto() {
		return totaleDovuto;
	}

	/**
	 * @param totaleDovuto 
	 */
	public void setTotaleDovuto(Double totaleDovuto) {
		this.totaleDovuto = totaleDovuto;
	}

	/**
	 * @return the pagata
	 */
	public boolean isPagata() {
		return pagata;
	}

	/**
	 * @param pagata 
	 */
	public void setPagata(boolean pagata) {
		this.pagata = pagata;
	}

	/**
	 * @return the rimborsoDaTerzi
	 */
	public boolean isRimborsoDaTerzi() {
		return rimborsoDaTerzi;
	}

	/**
	 * @param rimborsoDaTerzi 
	 */
	public void setRimborsoDaTerzi(boolean rimborsoDaTerzi) {
		this.rimborsoDaTerzi = rimborsoDaTerzi;
	}

	/**
	 * @return the importoDaTerzi
	 */
	public double getImportoDaTerzi() {
		return importoDaTerzi;
	}

	/**
	 * @param importoDaTerzi 
	 */
	public void setImportoDaTerzi(double importoDaTerzi) {
		this.importoDaTerzi = importoDaTerzi;
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
	 * 
	 * Calcolo del TAM per le missioni estere
	 * 
	 * @param massimale
	 * @param dataAttraversamentoFrontieraAndata
	 * @param dataAttraversamentoFrontieraRitorno
	 */
	public void calcolaTotaleTAM(Massimale massimale, DateTime dataAttraversamentoFrontieraAndata,
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

		this.totaleTAM = t;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "Rimborso [mandatoPagamento=" + mandatoPagamento + ", numeroOrdine=" + numeroOrdine + ", dataRimborso="
				+ dataRimborso + ", dateLastModified=" + dateLastModified + ", totale=" + totale + ", totaleTAM="
				+ totaleTAM + ", avvisoPagamento=" + avvisoPagamento + ", totKm=" + totKm + ", rimborsoKm=" + rimborsoKm
				+ ", anticipazionePagamento=" + anticipazionePagamento + ", totaleDovuto=" + totaleDovuto + ", pagata="
				+ pagata + ", rimborsoDaTerzi=" + rimborsoDaTerzi + ", importoDaTerzi=" + importoDaTerzi
				+ ", mappaFattura=" + mappaFattura + "]";
	}

}
