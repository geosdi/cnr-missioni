package it.cnr.missioni.model.missione;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class DatiPeriodoMissione implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3755222349066146562L;

	private DateTime inizioMissione;
	private DateTime fineMissione;


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

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "DatiPeriodoMissione [inizioMissione=" + inizioMissione + ", fineMissione=" + fineMissione + "]";
	}



}
