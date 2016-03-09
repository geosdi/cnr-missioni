package it.cnr.missioni.model.rimborso;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class Fattura implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4452123603076021709L;
	/**
	 * 
	 */
	private String id;
	@NotNull
	private Long numeroFattura;
	@NotNull
	private DateTime data;
	@NotNull
	private Double importo;
	@NotNull
	private Double importoSpettante;
	@NotBlank
	private String valuta;
	private String altro;
	@NotBlank
	private String idTipologiaSpesa;
	private String shortDescriptionTipologiaSpesa;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the numeroFattura
	 */
	public Long getNumeroFattura() {
		return numeroFattura;
	}

	/**
	 * @param numeroFattura
	 */
	public void setNumeroFattura(Long numeroFattura) {
		this.numeroFattura = numeroFattura;
	}

	/**
	 * @return the data
	 */
	public DateTime getData() {
		return data;
	}

	/**
	 * @param data
	 */
	public void setData(DateTime data) {
		this.data = data;
	}


	/**
	 * @return the importo
	 */
	public Double getImporto() {
		return importo;
	}

	/**
	 * @param importo
	 */
	public void setImporto(Double importo) {
		this.importo = importo;
	}



	/**
	 * @return the importoSpettante
	 */
	public Double getImportoSpettante() {
		return importoSpettante;
	}

	/**
	 * @param importoSpettante 
	 */
	public void setImportoSpettante(Double importoSpettante) {
		this.importoSpettante = importoSpettante;
	}

	/**
	 * @return the valuta
	 */
	public String getValuta() {
		return valuta;
	}

	/**
	 * @param valuta 
	 */
	public void setValuta(String valuta) {
		this.valuta = valuta;
	}

	/**
	 * @return the altro
	 */
	public String getAltro() {
		return altro;
	}

	/**
	 * @param altro 
	 */
	public void setAltro(String altro) {
		this.altro = altro;
	}

	/**
	 * @return the idTipologiaSpesa
	 */
	public String getIdTipologiaSpesa() {
		return idTipologiaSpesa;
	}

	/**
	 * @param idTipologiaSpesa 
	 */
	public void setIdTipologiaSpesa(String idTipologiaSpesa) {
		this.idTipologiaSpesa = idTipologiaSpesa;
	}

	/**
	 * @return the shortDescriptionTipologiaSpesa
	 */
	public String getShortDescriptionTipologiaSpesa() {
		return shortDescriptionTipologiaSpesa;
	}

	/**
	 * @param shortDescriptionTipologiaSpesa 
	 */
	public void setShortDescriptionTipologiaSpesa(String shortDescriptionTipologiaSpesa) {
		this.shortDescriptionTipologiaSpesa = shortDescriptionTipologiaSpesa;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "Fattura [id=" + id + ", numeroFattura=" + numeroFattura + ", data=" + data + ", importo=" + importo
				+ ", importoSpettante=" + importoSpettante + ", valuta=" + valuta + ", altro=" + altro
				+ ", idTipologiaSpesa=" + idTipologiaSpesa + ", shortDescriptionTipologiaSpesa="
				+ shortDescriptionTipologiaSpesa + "]";
	}



}
