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
	private static final long serialVersionUID = 1L;
	@NotNull
	private Long numeroFattura;
	@NotNull
	private DateTime data;
	@NotBlank
	private String tipologiaSpesa;
	@NotNull
	private Double importo;
	@NotBlank
	private String valuta;
	private String altro;

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
	 * @return the tipologiaSpesa
	 */
	public String getTipologiaSpesa() {
		return tipologiaSpesa;
	}

	/**
	 * @param tipologiaSpesa
	 */
	public void setTipologiaSpesa(String tipologiaSpesa) {
		this.tipologiaSpesa = tipologiaSpesa;
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
	 * @return
	 */
	@Override
	public String toString() {
		return "Fattura [numeroFattura=" + numeroFattura + ", data=" + data + ", tipologiaSpesa=" + tipologiaSpesa
				+ ", importo=" + importo + ", valuta=" + valuta + ", altro=" + altro + "]";
	}



}
