package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.User;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

/**
 * @author Salvia Vito
 */
public interface IUserDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<User> {

    /**
     * @param userSearchBuilder
     * @return
     * @throws Exception
     */
    PageResult<User> findUserByQuery(IUserSearchBuilder userSearchBuilder) throws Exception;

}
