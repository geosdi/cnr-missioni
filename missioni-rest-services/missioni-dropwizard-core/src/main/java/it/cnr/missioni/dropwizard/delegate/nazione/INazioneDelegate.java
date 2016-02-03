package it.cnr.missioni.dropwizard.delegate.nazione;

import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;

/**
 * 
 * @author Salvia Vito
 *
 */
public interface INazioneDelegate {

	/**
	 * 
	 * @param id
	 * @param from
	 * @param size
	 * @param all
	 * @return
	 * @throws Exception
	 */
	NazioneStore getNazioneByQuery(String id,int from, int size,boolean all) throws Exception;

	/**
	 * 
	 * @param nazione
	 * @return
	 * @throws Exception
	 */
	String addNazione(Nazione nazione) throws Exception;

	/**
	 * 
	 * @param nazione
	 * @return
	 * @throws Exception
	 */
	Boolean updateNazione(Nazione nazione) throws Exception;

	/**
	 * 
	 * @param nazioneID
	 * @return
	 * @throws Exception
	 */
	Boolean deleteNazione(String nazioneID) throws Exception;
}
