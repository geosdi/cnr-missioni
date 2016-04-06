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
 * @param id
 * @param stato
 * @param targa
 * @param cartaCircolazione
 * @param polizzaAsscurativa
 * @param notId
 * @param from
 * @param size
 * @param all
 * @return
 * @throws Exception
 */
	VeicoloCNRStore getVeicoloCNRByQuery(String id,String stato,String targa,String cartaCircolazione,String polizzaAsscurativa,String notId, int from, int size,boolean all) throws Exception;

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
