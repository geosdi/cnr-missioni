package it.cnr.missioni.dropwizard.delegate.tipologiaSpesa;

import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * 
 * @author Salvia Vito
 *
 */
public interface ITipologiaSpesaDelegate {

	/**
	 * 
	 * @param id
	 * @param tipo
	 * @param from
	 * @param size
	 * @param all
	 * @return
	 * @throws Exception
	 */
	TipologiaSpesaStore getTipologiaSpesaByQuery(String id,String tipo,int from, int size, boolean all) throws Exception;

	/**
	 * 
	 * @param tipologiaSpesa
	 * @return
	 * @throws Exception
	 */
	String addTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception;

	/**
	 * 
	 * @param tipologiaSpesa
	 * @return
	 * @throws Exception
	 */
	Boolean updateTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception;

	/**
	 * 
	 * @param tipologiaSpesaID
	 * @return
	 * @throws Exception
	 */
	Boolean deleteTipologiaSpesa(String tipologiaSpesaID) throws Exception;
}
