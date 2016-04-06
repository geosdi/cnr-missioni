package it.cnr.missioni.dashboard.component.calendar;

import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.addon.calendar.event.CalendarEditableEventProvider;
import com.vaadin.addon.calendar.event.CalendarEvent;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Salvia Vito
 */
public class PrenotazioneEvent extends BasicEvent implements CalendarEditableEventProvider {

    private static final long serialVersionUID = 2820133201983036866L;

    private String id;
    private String idUser;
    private String descrizione;
    @NotBlank(message = "Obbligatorio")
    private String veicolo;
    @NotNull(message = "Obbligatorio")
    private Date end;
    @NotNull(message = "Obbligatorio")
    private Date start;
    @NotNull(message = "Obbligatorio")
    private String localita;
    private String veicoloDescription;
    private boolean allDay;

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
     * @return the veicolo
     */
    public String getVeicolo() {
        return veicolo;
    }

    /**
     * @param veicolo
     */
    public void setVeicolo(String veicolo) {
        this.veicolo = veicolo;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param end
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start
     */
    public void setStart(Date start) {
        this.start = start;
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
     * @return the veicoloDescription
     */
    public String getVeicoloDescription() {
        return veicoloDescription;
    }

    /**
     * @param veicoloDescription
     */
    public void setVeicoloDescription(String veicoloDescription) {
        this.veicoloDescription = veicoloDescription;
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
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
        return null;
    }

    /**
     * @param event
     */
    @Override
    public void addEvent(CalendarEvent event) {
    }

    /**
     * @param event
     */
    @Override
    public void removeEvent(CalendarEvent event) {
    }


}