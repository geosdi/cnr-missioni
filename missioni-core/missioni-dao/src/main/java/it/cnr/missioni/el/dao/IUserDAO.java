package it.cnr.missioni.el.dao;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.User;

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
	PageResult<User> findUserByQuery(IUserSearchBuilder userSearchBuilder) throws Exception;

}
