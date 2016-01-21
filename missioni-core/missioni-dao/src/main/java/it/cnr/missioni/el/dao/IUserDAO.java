package it.cnr.missioni.el.dao;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
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
	PageResult<User> findUserByQuery(UserSearchBuilder userSearchBuilder) throws Exception;

}
