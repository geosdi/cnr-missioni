package it.cnr.missioni.model.missione;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.elasticsearch.common.geo.GeoPoint;
import org.geosdi.geoplatform.experimental.el.api.model.Document;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import it.cnr.missioni.model.rimborso.Rimborso;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "missione")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "localita", "altreLocalita","progressivo", "oggetto", "responsabileGruppo", "shortResponsabileGruppo",
		"shortUser", "motivazioni", "altreDisposizioni", "stato", "fondo", "GAE", "missioneEstera", "idUser",
		"idUserSeguito", "shortUserSeguito", "motivazioneSeguito", "idVeicolo", "shortDescriptionVeicolo",
		"dataInserimento", "dateLastModified", "mezzoProprio", "distanza", "geoPoint", "idNazione",
		"shortDescriptionNazione", "datiPeriodoMissione", "datiMissioneEstera", "datiAnticipoPagamenti", "rimborso" })
public class Missione implements Document {

	/**
	 *
	 */
	private static final long serialVersionUID = 2011330125118118995L;
	private String id;
	@NotBlank
	private String localita;
	private String altreLocalita;
	private String progressivo;
	@NotBlank
	private String oggetto;
	@NotBlank
	private String responsabileGruppo;
	private String shortResponsabileGruppo;
	private String shortUser;
	private String motivazioni;
	private String altreDisposizioni;
	@NotNull
	private StatoEnum stato;
	private String fondo;
	private String GAE;
	private boolean missioneEstera = false;
	private String idUser;
	private String idUserSeguito;
	private String shortUserSeguito;
	private String motivazioneSeguito;
	private String idVeicolo;
	private String shortDescriptionVeicolo;
	private DateTime dataInserimento;
	private DateTime dateLastModified;
	private boolean mezzoProprio;
	@NotBlank
	private String distanza;
	private GeoPoint geoPoint;
	private String idNazione;
	private String shortDescriptionNazione;

	private TipoVeicoloEnum tipoVeicolo = TipoVeicoloEnum.AUTOVETTURA_DI_SERVIZIO;
	@Valid
	private DatiPeriodoMissione datiPeriodoMissione = new DatiPeriodoMissione();
	@Valid
	private DatiMissioneEstera datiMissioneEstera = new DatiMissioneEstera();
	@Valid
	private DatiAnticipoPagamenti datiAnticipoPagamenti = new DatiAnticipoPagamenti();
	@Valid
	private Rimborso rimborso;

	public enum TipoVeicoloEnum {
		AUTOVETTURA_DI_SERVIZIO("Autovettura di servizio"), VEICOLO_PROPRIO("Veicolo Proprio"), NESSUNO("Nessuno"), NOLEGGIO(
				"Noleggio");

		private String value;

		TipoVeicoloEnum(String value) {
			this.setValue(value);
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

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

	public String getAltreLocalita() {
		return altreLocalita;
	}

	public void setAltreLocalita(String altreLocalita) {
		this.altreLocalita = altreLocalita;
	}

	public String getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
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
	 * @return the responsabileGruppo
	 */
	public String getResponsabileGruppo() {
		return responsabileGruppo;
	}

	/**
	 * @param responsabileGruppo
	 */
	public void setResponsabileGruppo(String responsabileGruppo) {
		this.responsabileGruppo = responsabileGruppo;
	}

	/**
	 * @return the shortResponsabileGruppo
	 */
	public String getShortResponsabileGruppo() {
		return shortResponsabileGruppo;
	}

	/**
	 * @param shortResponsabileGruppo
	 */
	public void setShortResponsabileGruppo(String shortResponsabileGruppo) {
		this.shortResponsabileGruppo = shortResponsabileGruppo;
	}

	/**
	 * @return the shortUser
	 */
	public String getShortUser() {
		return shortUser;
	}

	/**
	 * @param shortUser
	 */
	public void setShortUser(String shortUser) {
		this.shortUser = shortUser;
	}

	/**
	 * @return the motivazioniMezzoProprio
	 */
	public String getMotivazioni() {
		return motivazioni;
	}

	/**
	 * @param motivazioniMezzoProprio
	 */
	public void setMotivazioni(String motivazioni) {
		this.motivazioni = motivazioni;
	}

	/**
	 * @return the altreDisposizioni
	 */
	public String getAltreDisposizioni() {
		return altreDisposizioni;
	}

	/**
	 * @param altreDisposizioni
	 */
	public void setAltreDisposizioni(String altreDisposizioni) {
		this.altreDisposizioni = altreDisposizioni;
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
	 * @return the fondo
	 */
	public String getFondo() {
		return fondo;
	}

	/**
	 * @param fondo
	 */
	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	/**
	 * @return the gAE
	 */
	public String getGAE() {
		return GAE;
	}

	/**
	 * @param gAE
	 */
	public void setGAE(String gAE) {
		GAE = gAE;
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
	 * @return the idUser
	 */
	public String getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser
	 */
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the idUserSeguito
	 */
	public String getIdUserSeguito() {
		return idUserSeguito;
	}

	/**
	 * @param idUserSeguito
	 */
	public void setIdUserSeguito(String idUserSeguito) {
		this.idUserSeguito = idUserSeguito;
	}

	public String getMotivazioneSeguito() {
		return motivazioneSeguito;
	}

	public void setMotivazioneSeguito(String motivazioneSeguito) {
		this.motivazioneSeguito = motivazioneSeguito;
	}

	/**
	 * @return the shortUserSeguito
	 */
	public String getShortUserSeguito() {
		return shortUserSeguito;
	}

	/**
	 * @param shortUserSeguito
	 */
	public void setShortUserSeguito(String shortUserSeguito) {
		this.shortUserSeguito = shortUserSeguito;
	}

	/**
	 * @return the idVeicolo
	 */
	public String getIdVeicolo() {
		return idVeicolo;
	}

	/**
	 * @param idVeicolo
	 */
	public void setIdVeicolo(String idVeicolo) {
		this.idVeicolo = idVeicolo;
	}

	/**
	 * @return the shortDescriptionVeicolo
	 */
	public String getShortDescriptionVeicolo() {
		return shortDescriptionVeicolo;
	}

	/**
	 * @param shortDescriptionVeicolo
	 */
	public void setShortDescriptionVeicolo(String shortDescriptionVeicolo) {
		this.shortDescriptionVeicolo = shortDescriptionVeicolo;
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
	 * @return the dateLastModified
	 */
	public DateTime getDateLastModified() {
		return dateLastModified;
	}

	/**
	 * @param dateLastModified
	 */
	public void setDateLastModified(DateTime dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	/**
	 * @return the mezzoProprio
	 */
	public boolean isMezzoProprio() {
		return mezzoProprio;
	}

	/**
	 * @param mezzoProprio
	 */
	public void setMezzoProprio(boolean mezzoProprio) {
		this.mezzoProprio = mezzoProprio;
	}

	/**
	 * @return the distanza
	 */
	public String getDistanza() {
		return distanza;
	}

	/**
	 * @param distanza
	 */
	public void setDistanza(String distanza) {
		this.distanza = distanza;
	}

	/**
	 * @return the geoPoint
	 */
	public GeoPoint getGeoPoint() {
		return geoPoint;
	}

	/**
	 * @param geoPoint
	 */
	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}

	/**
	 * @return the idNazione
	 */
	public String getIdNazione() {
		return idNazione;
	}

	/**
	 * @param idNazione
	 */
	public void setIdNazione(String idNazione) {
		this.idNazione = idNazione;
	}

	/**
	 * @return the shortDescriptionNazione
	 */
	public String getShortDescriptionNazione() {
		return shortDescriptionNazione;
	}

	/**
	 * @param shortDescriptionNazione
	 */
	public void setShortDescriptionNazione(String shortDescriptionNazione) {
		this.shortDescriptionNazione = shortDescriptionNazione;
	}

	/**
	 * @return the tipoVeicolo
	 */
	public TipoVeicoloEnum getTipoVeicolo() {
		return tipoVeicolo;
	}

	/**
	 * @param tipoVeicolo
	 */
	public void setTipoVeicolo(TipoVeicoloEnum tipoVeicolo) {
		this.tipoVeicolo = tipoVeicolo;
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
	 * @return {@link Boolean}
	 */
	@XmlTransient
	public Boolean isRimborsoSetted() {
		return this.rimborso != null;
	}

	@Override
	public String toString() {
		return "Missione [id=" + id + ", localita=" + localita + ", altreLocalita=" + altreLocalita + ", oggetto="
				+ oggetto + ", responsabileGruppo=" + responsabileGruppo + ", shortResponsabileGruppo="
				+ shortResponsabileGruppo + ", shortUser=" + shortUser + ", motivazioni=" + motivazioni
				+ ", altreDisposizioni=" + altreDisposizioni + ", stato=" + stato + ", fondo=" + fondo + ", GAE=" + GAE
				+ ", missioneEstera=" + missioneEstera + ", idUser=" + idUser + ", idUserSeguito=" + idUserSeguito
				+ ", shortUserSeguito=" + shortUserSeguito + ", motivazioneSeguito=" + motivazioneSeguito
				+ ", idVeicolo=" + idVeicolo + ", shortDescriptionVeicolo=" + shortDescriptionVeicolo
				+ ", dataInserimento=" + dataInserimento + ", dateLastModified=" + dateLastModified + ", mezzoProprio="
				+ mezzoProprio + ", distanza=" + distanza + ", geoPoint=" + geoPoint + ", idNazione=" + idNazione
				+ ", shortDescriptionNazione=" + shortDescriptionNazione + ", tipoVeicolo=" + tipoVeicolo
				+ ", datiPeriodoMissione=" + datiPeriodoMissione + ", datiMissioneEstera=" + datiMissioneEstera
				+ ", datiAnticipoPagamenti=" + datiAnticipoPagamenti + ", rimborso=" + rimborso + "]";
	}

}