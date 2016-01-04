package it.cnr.missioni.model.user;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class Patente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numeroPatente;
	private DateTime dataRilascio;
	private DateTime validaFinoAl;
	private String rilasciataDa;

	/**
	 * @return the numeroPatente
	 */
	public String getNumeroPatente() {
		return numeroPatente;
	}

	/**
	 * @param numeroPatente
	 */
	public void setNumeroPatente(String numeroPatente) {
		this.numeroPatente = numeroPatente;
	}

	/**
	 * @return the dataRilascio
	 */
	public DateTime getDataRilascio() {
		return dataRilascio;
	}

	/**
	 * @param dataRilascio
	 */
	public void setDataRilascio(DateTime dataRilascio) {
		this.dataRilascio = dataRilascio;
	}

	/**
	 * @return the validaFinoAl
	 */
	public DateTime getValidaFinoAl() {
		return validaFinoAl;
	}

	/**
	 * @param validaFinoAl
	 */
	public void setValidaFinoAl(DateTime validaFinoAl) {
		this.validaFinoAl = validaFinoAl;
	}

	/**
	 * @return the rilasciataDa
	 */
	public String getRilasciataDa() {
		return rilasciataDa;
	}

	/**
	 * @param rilasciataDa
	 */
	public void setRilasciataDa(String rilasciataDa) {
		this.rilasciataDa = rilasciataDa;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Patente [numeroPatente=" + numeroPatente + ", dataRilascio=" + dataRilascio + ", validaFinoAl="
				+ validaFinoAl + ", rilasciataDa=" + rilasciataDa + "]";
	}

}
