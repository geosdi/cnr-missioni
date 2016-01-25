package it.cnr.missioni.dashboard.component.calendar;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.addon.calendar.event.CalendarEditableEventProvider;
import com.vaadin.addon.calendar.event.CalendarEvent;

/**
 * @author Salvia Vito
 */
public class PrenotazioneEvent extends BasicEvent implements CalendarEditableEventProvider {

	private static final long serialVersionUID = 2820133201983036866L;
	
	private String id;
	private String idUser;
	private String descrizione;
	@NotBlank
	private String veicolo;
	
	@NotNull
	private Date end;
	@NotNull
	private Date start;
	
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
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param event
	 */
	@Override
	public void addEvent(CalendarEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param event
	 */
	@Override
	public void removeEvent(CalendarEvent event) {
		// TODO Auto-generated method stub
		
	}





}