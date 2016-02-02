package it.cnr.missioni.dropwizard.delegate.rimborsoKm;

import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;

/**
 * 
 * @author Salvia Vito
 *
 */
public interface IRimborsoKmDelegate {

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	RimborsoKmStore getRimborsoKmByQuery() throws Exception;

	/**
	 * 
	 * @param rimborsoKm
	 * @return
	 * @throws Exception
	 */
	String addRimborsoKm(RimborsoKm rimborsoKm) throws Exception;

	/**
	 * 
	 * @param rimborsoKm
	 * @return
	 * @throws Exception
	 */
	Boolean updateRimborsoKm(RimborsoKm rimborsoKm) throws Exception;

	/**
	 * 
	 * @param rimborsoKmID
	 * @return
	 * @throws Exception
	 */
	Boolean deleteRimborsoKm(String rimborsoKmID) throws Exception;
}
