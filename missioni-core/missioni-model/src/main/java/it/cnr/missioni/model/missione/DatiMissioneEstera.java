package it.cnr.missioni.model.missione;

import java.io.Serializable;

/**
 * @author Salvia Vito
 */
public class DatiMissioneEstera implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 734443544980437567L;
	private boolean missioneEstera;
	private boolean rimborsoDocumentato;
	private boolean trattamentoAlternativoMissione;

	/**
	 * @return the missioneEstera
	 */
	public boolean isMissioneEstera() {
		return missioneEstera;
	}

	/**
	 * @param missioneEstera
	 */
	public void setMissioneEstera(boolean missioneEstera) {
		this.missioneEstera = missioneEstera;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DatiMissioneEstera [missioneEstera=" + missioneEstera + ", rimborsoDocumentato=" + rimborsoDocumentato
				+ ", trattamentoAlternativoMissione=" + trattamentoAlternativoMissione + "]";
	}

}
