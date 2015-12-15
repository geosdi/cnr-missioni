package it.cnr.missioni.model.missione;

import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class DatiPeriodoMissione {

	private DateTime attraversamentoFrontieraAndata;
	private DateTime attraversamentoFrontieraRitorno;
	private DateTime inizioMissione;
	private DateTime fineMissione;

	/**
	 * @return the attraversamentoFrontieraAndata
	 */
	public DateTime getAttraversamentoFrontieraAndata() {
		return attraversamentoFrontieraAndata;
	}

	/**
	 * @param attraversamentoFrontieraAndata
	 */
	public void setAttraversamentoFrontieraAndata(DateTime attraversamentoFrontieraAndata) {
		this.attraversamentoFrontieraAndata = attraversamentoFrontieraAndata;
	}

	/**
	 * @return the attraversamentoFrontieraRitorno
	 */
	public DateTime getAttraversamentoFrontieraRitorno() {
		return attraversamentoFrontieraRitorno;
	}

	/**
	 * @param attraversamentoFrontieraRitorno
	 */
	public void setAttraversamentoFrontieraRitorno(DateTime attraversamentoFrontieraRitorno) {
		this.attraversamentoFrontieraRitorno = attraversamentoFrontieraRitorno;
	}

	/**
	 * @return the inizioMissione
	 */
	public DateTime getInizioMissione() {
		return inizioMissione;
	}

	/**
	 * @param inizioMissione
	 */
	public void setInizioMissione(DateTime inizioMissione) {
		this.inizioMissione = inizioMissione;
	}

	/**
	 * @return the fineMissione
	 */
	public DateTime getFineMissione() {
		return fineMissione;
	}

	/**
	 * @param fineMissione
	 */
	public void setFineMissione(DateTime fineMissione) {
		this.fineMissione = fineMissione;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DatiPeriodoMissione [attraversamentoFrontieraAndata=" + attraversamentoFrontieraAndata
				+ ", attraversamentoFrontieraRitorno=" + attraversamentoFrontieraRitorno + ", inizioMissione="
				+ inizioMissione + ", fineMissione=" + fineMissione + "]";
	}

}
