package it.cnr.missioni.el.utility;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import it.cnr.missioni.model.prenotazione.Prenotazione;

/**
 * @author Salvia Vito
 */
public class PrenotazioneFunction{


	public static List<Prenotazione> creaMassivePrenotazioni(){
		
		List<Prenotazione> listaPrenotazioni = new ArrayList<Prenotazione>();
		
		Prenotazione p = new Prenotazione();
		p.setId("01");
		p.setDataFrom(new DateTime(2016, 1, 21, 0, 0));
		p.setDataTo(new DateTime(2016, 1, 22, 23, 59));
		p.setIdUser("01");
		p.setIdVeicoloCNR("01");
		p.setDescrizione("Citroen 56654 - Salvia Vito");
		listaPrenotazioni.add(p);
		p.setAllDay(true);

		p = new Prenotazione();
		p.setId("02");
		p.setDataFrom(new DateTime(2016, 1, 23, 8, 0));
		p.setDataTo(new DateTime(2016, 1, 24, 18, 0));
		p.setIdUser("02");
		p.setIdVeicoloCNR("01");
		p.setDescrizione("Citroen 56654 - Rossi Paolo");
		listaPrenotazioni.add(p);
		p.setAllDay(false);
		
		return listaPrenotazioni;
	}


}
