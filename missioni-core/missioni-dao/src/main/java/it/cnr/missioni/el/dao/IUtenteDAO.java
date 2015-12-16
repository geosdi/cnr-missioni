package it.cnr.missioni.el.dao;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

import it.cnr.missioni.model.utente.Utente;


/**
 * @author Salvia Vito
 */
public interface IUtenteDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<Utente>{
	
	/**
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	Utente findUtenteByUsername(String username) throws Exception;

}
