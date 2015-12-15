package it.cnr.missioni.model.utente;

/**
 * @author Salvia Vito
 */
public class Residenza {

	private String residenza;
	private String viaResidenza;
	private String domicilioFiscale;

	/**
	 * @return the residenza
	 */
	public String getResidenza() {
		return residenza;
	}

	/**
	 * @param residenza
	 */
	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	/**
	 * @return the viaResidenza
	 */
	public String getViaResidenza() {
		return viaResidenza;
	}

	/**
	 * @param viaResidenza
	 */
	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
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
		return "Residenza [residenza=" + residenza + ", viaResidenza=" + viaResidenza + ", domicilioFiscale="
				+ domicilioFiscale + "]";
	}

}
