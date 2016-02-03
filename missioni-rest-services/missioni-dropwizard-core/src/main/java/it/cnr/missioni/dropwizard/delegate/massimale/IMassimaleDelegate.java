package it.cnr.missioni.dropwizard.delegate.massimale;

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

/**
 * 
 * @author Salvia Vito
 *
 */
public interface IMassimaleDelegate {

	/**
	 * 
	 * @param from
	 * @param size
	 * @param livello
	 * @param areaGeografica
	 * @param notId
	 * @return
	 * @throws Exception
	 */
	MassimaleStore getMassimaleByQuery(int from, int size, String livello, String areaGeografica, String notId)
			throws Exception;

	/**
	 * 
	 * @param massimale
	 * @return
	 * @throws Exception
	 */
	String addMassimale(Massimale massimale) throws Exception;

	/**
	 * 
	 * @param massimale
	 * @return
	 * @throws Exception
	 */
	Boolean updateMassimale(Massimale massimale) throws Exception;

	/**
	 * 
	 * @param massimaleID
	 * @return
	 * @throws Exception
	 */
	Boolean deleteMassimale(String massimaleID) throws Exception;
}
