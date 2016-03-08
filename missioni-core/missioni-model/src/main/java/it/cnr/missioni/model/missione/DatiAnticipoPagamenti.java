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
	private static final long serialVersionUID = 3198450987570600338L;
	private String mandatoCNR;
	@Min(value = 0)
	private double speseMissioniAnticipate;
	private TrattamentoMissioneEsteraEnum trattamentoMissioneEstera;
	private boolean speseAlberghiere;
	private boolean speseViaggioDocumentato;
	private boolean speseViaggioTam;
	private boolean prospetto;

	
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
	 * @return the speseAlberghiere
	 */
	public boolean isSpeseAlberghiere() {
		return speseAlberghiere;
	}

	/**
	 * @param speseAlberghiere 
	 */
	public void setSpeseAlberghiere(boolean speseAlberghiere) {
		this.speseAlberghiere = speseAlberghiere;
	}

	/**
	 * @return the speseViaggioDocumentato
	 */
	public boolean isSpeseViaggioDocumentato() {
		return speseViaggioDocumentato;
	}

	/**
	 * @param speseViaggioDocumentato 
	 */
	public void setSpeseViaggioDocumentato(boolean speseViaggioDocumentato) {
		this.speseViaggioDocumentato = speseViaggioDocumentato;
	}

	/**
	 * @return the speseViaggioTam
	 */
	public boolean isSpeseViaggioTam() {
		return speseViaggioTam;
	}

	/**
	 * @param speseViaggioTam 
	 */
	public void setSpeseViaggioTam(boolean speseViaggioTam) {
		this.speseViaggioTam = speseViaggioTam;
	}

	/**
	 * @return the prospetto
	 */
	public boolean isProspetto() {
		return prospetto;
	}

	/**
	 * @param prospetto 
	 */
	public void setProspetto(boolean prospetto) {
		this.prospetto = prospetto;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "DatiAnticipoPagamenti [mandatoCNR=" + mandatoCNR + ", speseMissioniAnticipate="
				+ speseMissioniAnticipate + ", trattamentoMissioneEstera=" + trattamentoMissioneEstera
				+ ", speseAlberghiere=" + speseAlberghiere + ", speseViaggioDocumentato=" + speseViaggioDocumentato
				+ ", speseViaggioTam=" + speseViaggioTam + ", prospetto=" + prospetto + "]";
	}


}
