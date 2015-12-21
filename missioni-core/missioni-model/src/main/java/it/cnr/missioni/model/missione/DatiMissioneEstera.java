package it.cnr.missioni.model.missione;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class DatiMissioneEstera implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 734443544980437567L;

	private boolean rimborsoDocumentato;
	private boolean trattamentoAlternativoMissione;
	private DateTime attraversamentoFrontieraAndata;
	private DateTime attraversamentoFrontieraRitorno;


	/**
	 * @return the rimborsoDocumentato
	 */
	public boolean isRimborsoDocumentato() {
		return rimborsoDocumentato;
	}

	/**
	 * @param rimborsoDocumentato
	 */
	public void setRimborsoDocumentato(boolean rimborsoDocumentato) {
		this.rimborsoDocumentato = rimborsoDocumentato;
	}

	/**
	 * @return the trattamentoAlternativoMissione
	 */
	public boolean isTrattamentoAlternativoMissione() {
		return trattamentoAlternativoMissione;
	}

	/**
	 * @param trattamentoAlternativoMissione
	 */
	public void setTrattamentoAlternativoMissione(boolean trattamentoAlternativoMissione) {
		this.trattamentoAlternativoMissione = trattamentoAlternativoMissione;
	}

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
	 * @return
	 */
	@Override
	public String toString() {
		return "DatiMissioneEstera [rimborsoDocumentato=" + rimborsoDocumentato + ", trattamentoAlternativoMissione="
				+ trattamentoAlternativoMissione + ", attraversamentoFrontieraAndata=" + attraversamentoFrontieraAndata
				+ ", attraversamentoFrontieraRitorno=" + attraversamentoFrontieraRitorno + "]";
	}





}
