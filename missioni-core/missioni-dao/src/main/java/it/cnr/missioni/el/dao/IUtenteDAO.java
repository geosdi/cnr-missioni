package it.cnr.missioni.el.dao;

import java.util.List;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.model.utente.Utente;

/**
 * @author Salvia Vito
 */
public interface IUtenteDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<Utente> {

	/**
	 * 
	 * @param p
	 * @param utenteModelSearch
	 * @return
	 * @throws Exception
	 */
	List<Utente> findUtenteByQuery(Page p, BooleanModelSearch booleanModelSearch) throws Exception;

}
