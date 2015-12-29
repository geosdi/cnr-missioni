package it.cnr.missioni.model.missione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.geosdi.geoplatform.experimental.el.api.model.Document;
import org.joda.time.DateTime;

import it.cnr.missioni.model.rimborso.Rimborso;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "missione")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "localita", "oggetto", "stato", "missioneEstera", "altro", "idUser", "dataInserimento",
		"datiPeriodoMissione", "datiMissioneEstera", "datiAnticipoPagamenti" ,"rimborso"})
public class Missione implements Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2011330125118118995L;
	private String id;
	private String localita;
	private String oggetto;
	private StatoEnum stato;
	private boolean missioneEstera;
	private String altro;
	private String idUser;
	private DateTime dataInserimento;
	private DatiPeriodoMissione datiPeriodoMissione;
	private DatiMissioneEstera datiMissioneEstera;
	private DatiAnticipoPagamenti datiAnticipoPagamenti;
	private Rimborso rimborso;


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.geosdi.geoplatform.experimental.el.api.model.Document#isIdSetted()
	 */
	@Override
	public Boolean isIdSetted() {
		return ((this.id != null) && !(this.id.isEmpty()));
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the localita
	 */
	public String getLocalita() {
		return localita;
	}


	/**
	 * @param localita 
	 */
	public void setLocalita(String localita) {
		this.localita = localita;
	}


	/**
	 * @return the oggetto
	 */
	public String getOggetto() {
		return oggetto;
	}


	/**
	 * @param oggetto 
	 */
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}


	/**
	 * @return the stato
	 */
	public StatoEnum getStato() {
		return stato;
	}


	/**
	 * @param stato 
	 */
	public void setStato(StatoEnum stato) {
		this.stato = stato;
	}


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
	 * @return the altro
	 */
	public String getAltro() {
		return altro;
	}


	/**
	 * @param altro 
	 */
	public void setAltro(String altro) {
		this.altro = altro;
	}


	/**
	 * @return the idUtente
	 */
	public String getIdUser() {
		return idUser;
	}


	/**
	 * @param idUtente 
	 */
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}


	/**
	 * @return the dataInserimento
	 */
	public DateTime getDataInserimento() {
		return dataInserimento;
	}


	/**
	 * @param dataInserimento 
	 */
	public void setDataInserimento(DateTime dataInserimento) {
		this.dataInserimento = dataInserimento;
	}


	/**
	 * @return the datiPeriodoMissione
	 */
	public DatiPeriodoMissione getDatiPeriodoMissione() {
		return datiPeriodoMissione;
	}


	/**
	 * @param datiPeriodoMissione 
	 */
	public void setDatiPeriodoMissione(DatiPeriodoMissione datiPeriodoMissione) {
		this.datiPeriodoMissione = datiPeriodoMissione;
	}


	/**
	 * @return the datiMissioneEstera
	 */
	public DatiMissioneEstera getDatiMissioneEstera() {
		return datiMissioneEstera;
	}


	/**
	 * @param datiMissioneEstera 
	 */
	public void setDatiMissioneEstera(DatiMissioneEstera datiMissioneEstera) {
		this.datiMissioneEstera = datiMissioneEstera;
	}


	/**
	 * @return the datiAnticipoPagamenti
	 */
	public DatiAnticipoPagamenti getDatiAnticipoPagamenti() {
		return datiAnticipoPagamenti;
	}


	/**
	 * @param datiAnticipoPagamenti 
	 */
	public void setDatiAnticipoPagamenti(DatiAnticipoPagamenti datiAnticipoPagamenti) {
		this.datiAnticipoPagamenti = datiAnticipoPagamenti;
	}


	/**
	 * @return the rimborso
	 */
	public Rimborso getRimborso() {
		return rimborso;
	}


	/**
	 * @param rimborso 
	 */
	public void setRimborso(Rimborso rimborso) {
		this.rimborso = rimborso;
	}


	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "Missione [id=" + id + ", localita=" + localita + ", oggetto=" + oggetto + ", stato=" + stato
				+ ", missioneEstera=" + missioneEstera + ", altro=" + altro + ", idUser=" + idUser
				+ ", dataInserimento=" + dataInserimento + ", datiPeriodoMissione=" + datiPeriodoMissione
				+ ", datiMissioneEstera=" + datiMissioneEstera + ", datiAnticipoPagamenti=" + datiAnticipoPagamenti
				+ ", rimborso=" + rimborso + "]";
	}

	

}
