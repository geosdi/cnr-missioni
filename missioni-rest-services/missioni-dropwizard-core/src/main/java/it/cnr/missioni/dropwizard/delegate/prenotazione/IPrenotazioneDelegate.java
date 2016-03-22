package it.cnr.missioni.dropwizard.delegate.prenotazione;

import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.rest.api.response.prenotazione.PrenotazioniStore;

/**
 * 
 * @author Salvia Vito
 *
 */
public interface IPrenotazioneDelegate {

	/**
	 * 
	 * @param dataFrom
	 * @param dataTo
	 * @return
	 * @throws Exception
	 */
	PrenotazioniStore getPrenotazioneByQuery(Long dataFrom,Long dataTo) throws Exception;

	/**
	 * 
	 * @param prenotazione
	 * @return
	 * @throws Exception
	 */
	String addPrenotazione(Prenotazione prenotazione) throws Exception;

	/**
	 * 
	 * @param prenotazione
	 * @return
	 * @throws Exception
	 */
	Boolean updatePrenotazione(Prenotazione prenotazione) throws Exception;

	/**
	 * 
	 * @param prenotazioneID
	 * @return
	 * @throws Exception
	 */
	Boolean deletePrenotazione(String prenotazioneID) throws Exception;
}
