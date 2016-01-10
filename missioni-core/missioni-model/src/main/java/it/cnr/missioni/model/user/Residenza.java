package it.cnr.missioni.model.user;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Salvia Vito
 */
public class Residenza implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8733345187478501883L;
	@NotBlank
	private String comune;
	@NotBlank
	private String indirizzo;
	private String domicilioFiscale;

	/**
	 * @return the comune
	 */
	public String getComune() {
		return comune;
	}

	/**
	 * @param comune
	 */
	public void setComune(String comune) {
		this.comune = comune;
	}

	/**
	 * @return the indirizzo
	 */
	public String getIndirizzo() {
		return indirizzo;
	}

	/**
	 * @param indirizzo
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	/**
	 * @return the domicilioFiscale
	 */
	public String getDomicilioFiscale() {
		return domicilioFiscale;
	}

	/**
	 * @param domicilioFiscale
	 */
	public void setDomicilioFiscale(String domicilioFiscale) {
		this.domicilioFiscale = domicilioFiscale;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "Residenza [comune=" + comune + ", indirizzo=" + indirizzo + ", domicilioFiscale=" + domicilioFiscale
				+ "]";
	}

}
