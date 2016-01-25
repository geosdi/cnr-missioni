package it.cnr.missioni.dropwizard.delegate.veicoloCNR;

import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.rest.api.response.user.UserStore;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;

/**
 * 
 * @author Salvia Vito
 *
 */
public interface IVeicoloCNRDelegate {

	/**
	 * 
	 * @param stato
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	VeicoloCNRStore getVeicoloCNRByQuery(String stato, int from, int size) throws Exception;

	/**
	 * 
	 * @param veicoloCNR
	 * @return
	 * @throws Exception
	 */
	String addVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception;

	/**
	 * 
	 * @param veicoloCNR
	 * @return
	 * @throws Exception
	 */
	Boolean updateVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception;

	/**
	 * 
	 * @param veicoloCNRID
	 * @return
	 * @throws Exception
	 */
	Boolean deleteVeicoloCNR(String veicoloCNRID) throws Exception;
}
