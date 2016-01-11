package it.cnr.missioni.model.missione;

import java.io.Serializable;

import javax.validation.constraints.Min;

/**
 * @author Salvia Vito
 */
public class DatiAnticipoPagamenti implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6596717379934379754L;
	private boolean anticipazioniMonetarie;
	private String mandatoCNR;
	@Min(value = 0)
	private double speseMissioniAnticipate;
	private boolean rimborsoDaTerzi;
	@Min(value = 0)
	private double importoDaTerzi;

	/**
	 * @return the anticipazioniMonetarie
	 */
	public boolean isAnticipazioniMonetarie() {
		return anticipazioniMonetarie;
	}

	/**
	 * @param anticipazioniMonetarie
	 */
	public void setAnticipazioniMonetarie(boolean anticipazioniMonetarie) {
		this.anticipazioniMonetarie = anticipazioniMonetarie;
	}

	/**
	 * @return the mandatoCNR
	 */
	public String getMandatoCNR() {
		return mandatoCNR;
	}

	/**
	 * @param mandatoCNR
	 */
	public void setMandatoCNR(String mandatoCNR) {
		this.mandatoCNR = mandatoCNR;
	}

	/**
	 * @return the speseMissioniAnticipate
	 */
	public double getSpeseMissioniAnticipate() {
		return speseMissioniAnticipate;
	}

	/**
	 * @param speseMissioniAnticipate
	 */
	public void setSpeseMissioniAnticipate(double speseMissioniAnticipate) {
		this.speseMissioniAnticipate = speseMissioniAnticipate;
	}

	/**
	 * @return the rimborsoDaTerzi
	 */
	public boolean isRimborsoDaTerzi() {
		return rimborsoDaTerzi;
	}

	/**
	 * @param rimborsoDaTerzi
	 */
	public void setRimborsoDaTerzi(boolean rimborsoDaTerzi) {
		this.rimborsoDaTerzi = rimborsoDaTerzi;
	}

	/**
	 * @return the importoDaTerzi
	 */
	public double getImportoDaTerzi() {
		return importoDaTerzi;
	}

	/**
	 * @param importoDaTerzi
	 */
	public void setImportoDaTerzi(double importoDaTerzi) {
		this.importoDaTerzi = importoDaTerzi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DatiAnticipoPagamenti [anticipazioniMonetarie=" + anticipazioniMonetarie + ", mandatoCNR=" + mandatoCNR
				+ ", speseMissioniAnticipate=" + speseMissioniAnticipate + ", rimborsoDaTerzi=" + rimborsoDaTerzi
				+ ", importoDaTerzi=" + importoDaTerzi + "]";
	}
}
