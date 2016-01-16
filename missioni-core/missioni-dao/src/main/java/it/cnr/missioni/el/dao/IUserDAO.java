package it.cnr.missioni.el.dao;

import java.util.List;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

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
	List<User> findUserByQuery(Page p, UserSearchBuilder userSearchBuilder) throws Exception;

}
