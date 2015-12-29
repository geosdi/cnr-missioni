package it.cnr.missioni.el.dao;

import java.util.List;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.model.utente.User;

/**
 * @author Salvia Vito
 */
public interface IUserDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<User> {

	/**
	 * 
	 * @param p
	 * @param utenteModelSearch
	 * @return
	 * @throws Exception
	 */
	List<User> findUtenteByQuery(Page p, BooleanModelSearch booleanModelSearch) throws Exception;

}
