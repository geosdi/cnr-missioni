package it.cnr.missioni.model.rimborso;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class Fattura implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long numeroFattura;
	private DateTime data;
	private String tipologiaSpesa;
	private double importo;
	private double kmPercorsi;

	/**
	 * @return the numeroFattura
	 */
	public long getNumeroFattura() {
		return numeroFattura;
	}

	/**
	 * @param numeroFattura
	 */
	public void setNumeroFattura(long numeroFattura) {
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
	public double getImporto() {
		return importo;
	}

	/**
	 * @param importo
	 */
	public void setImporto(double importo) {
		this.importo = importo;
	}

	/**
	 * @return the kmPercorsi
	 */
	public double getKmPercorsi() {
		return kmPercorsi;
	}

	/**
	 * @param kmPercorsi
	 */
	public void setKmPercorsi(double kmPercorsi) {
		this.kmPercorsi = kmPercorsi;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Fattura [numeroFattura=" + numeroFattura + ", data=" + data + ", tipologiaSpesa=" + tipologiaSpesa
				+ ", importo=" + importo + ", kmPercorsi=" + kmPercorsi + "]";
	}

}
