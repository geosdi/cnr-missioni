package it.cnr.missioni.model.utente;

/**
 * @author Salvia Vito
 */
public class Residenza {

	private String comune;
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
	public String getIndrizzo() {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Residenza [comune=" + comune + ", indirizzo=" + indirizzo + ", domicilioFiscale="
				+ domicilioFiscale + "]";
	}

}
