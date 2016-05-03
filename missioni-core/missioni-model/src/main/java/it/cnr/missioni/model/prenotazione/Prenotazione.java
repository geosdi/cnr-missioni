package it.cnr.missioni.model.prenotazione;

import org.geosdi.geoplatform.experimental.el.api.model.Document;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;

/**
 * @author Salvia Vito
 */
@XmlRootElement(name = "prenotazione")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "dataFrom", "dataTo", "idUser", "idVeicoloCNR", "localita", "descrizione", "allDay", "descriptionVeicoloCNR"})
public class Prenotazione implements Document {

    /**
     *
     */
    private static final long serialVersionUID = -5361616511783560678L;

    private String id;
    @NotNull
    private DateTime dataFrom;
    @NotNull
    private DateTime dataTo;
    private String idUser;
    @NotNull
    @NotBlank
    private String idVeicoloCNR;
    @NotBlank
    private String localita;
    private String descrizione;
    private boolean allDay;
    @NotNull
    @NotBlank
    private String descriptionVeicoloCNR;

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
     * @param theID
     */
    @Override
    public void setId(String theID) {
        this.id = theID;

    }

    /**
     * @return the dataFrom
     */
    public DateTime getDataFrom() {
        return dataFrom;
    }

    /**
     * @param dataFrom
     */
    public void setDataFrom(DateTime dataFrom) {
        this.dataFrom = dataFrom;
    }

    /**
     * @return the dataTo
     */
    public DateTime getDataTo() {
        return dataTo;
    }

    /**
     * @param dataTo
     */
    public void setDataTo(DateTime dataTo) {
        this.dataTo = dataTo;
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
     * @return the idVeicoloCNR
     */
    public String getIdVeicoloCNR() {
        return idVeicoloCNR;
    }

    /**
     * @param idVeicoloCNR
     */
    public void setIdVeicoloCNR(String idVeicoloCNR) {
        this.idVeicoloCNR = idVeicoloCNR;
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
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * @return the allDay
     */
    public boolean isAllDay() {
        return allDay;
    }

    /**
     * @param allDay
     */
    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    /**
     * @return the descriptionVeicoloCNR
     */
    public String getDescriptionVeicoloCNR() {
        return descriptionVeicoloCNR;
    }

    /**
     * @param descriptionVeicoloCNR
     */
    public void setDescriptionVeicoloCNR(String descriptionVeicoloCNR) {
        this.descriptionVeicoloCNR = descriptionVeicoloCNR;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Prenotazione [id=" + id + ", dataFrom=" + dataFrom + ", dataTo=" + dataTo + ", idUser=" + idUser
                + ", idVeicoloCNR=" + idVeicoloCNR + ", localita=" + localita + ", descrizione=" + descrizione
                + ", allDay=" + allDay + ", descriptionVeicoloCNR=" + descriptionVeicoloCNR + "]";
    }

    public String buildStringMessage() {
        StringBuilder s = new StringBuilder();
        s.append("\nUser:" + getDescrizione());
        s.append("\nVeicolo: " + getDescriptionVeicoloCNR());
        s.append("\nDal: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(getDataFrom().toDate()));
        s.append("\nAl: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(getDataTo().toDate()));
        s.append("\nLocalità: " + getLocalita());
        return s.toString();
    }

}
