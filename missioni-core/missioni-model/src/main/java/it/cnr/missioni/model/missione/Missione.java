package it.cnr.missioni.model.missione;

import it.cnr.missioni.model.rimborso.Rimborso;
import org.geosdi.geoplatform.experimental.el.api.model.Document;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.*;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "missione")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "localita", "oggetto", "stato", "fondo", "GAE", "missioneEstera", "altro", "idUser", "idVeicolo", "dataInserimento",
        "dateLastModified", "mezzoProprio", "distanza", "datiPeriodoMissione", "datiMissioneEstera", "datiAnticipoPagamenti", "rimborso"})
public class Missione implements Document {

    /**
     *
     */
    private static final long serialVersionUID = 2011330125118118995L;
    private String id;
    @NotBlank
    private String localita;
    @NotBlank
    private String oggetto;
    private StatoEnum stato;
    private String fondo;
    private String GAE;
    private boolean missioneEstera = false;
    private String altro;
    private String idUser;
    private String idVeicolo;
    private DateTime dataInserimento;
    private DateTime dateLastModified;
    private boolean mezzoProprio;
    @Min(value = 0)
    private double distanza;
    @Valid
    private DatiPeriodoMissione datiPeriodoMissione = new DatiPeriodoMissione();
    @Valid
    private DatiMissioneEstera datiMissioneEstera = new DatiMissioneEstera();
    @Valid
    private DatiAnticipoPagamenti datiAnticipoPagamenti = new DatiAnticipoPagamenti();
    @Valid
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
     * @param idUser
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
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
    public double getDistanza() {
        return distanza;
    }


    /**
     * @param distanza
     */
    public void setDistanza(double distanza) {
        this.distanza = distanza;
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

    /**
     * @return
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id=" + id + ", localita=" + localita + ", oggetto=" + oggetto + ", stato=" + stato
                + ", fondo=" + fondo + ", GAE=" + GAE + ", missioneEstera=" + missioneEstera + ", altro=" + altro
                + ", idUser=" + idUser + ", idVeicolo=" + idVeicolo + ", dataInserimento=" + dataInserimento
                + ", dateLastModified=" + dateLastModified + ", mezzoProprio=" + mezzoProprio + ", distanza=" + distanza
                + ", datiPeriodoMissione=" + datiPeriodoMissione + ", datiMissioneEstera=" + datiMissioneEstera
                + ", datiAnticipoPagamenti=" + datiAnticipoPagamenti + ", rimborso=" + rimborso + "]";
    }
}
