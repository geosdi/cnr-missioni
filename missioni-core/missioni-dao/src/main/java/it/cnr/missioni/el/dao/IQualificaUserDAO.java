package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IQualificaUserSearchBuilder;
import it.cnr.missioni.model.configuration.QualificaUser;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

/**
 * @author Salvia Vito
 */
public interface IQualificaUserDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<QualificaUser> {

    PageResult<QualificaUser> findQualificaUserByQuery(IQualificaUserSearchBuilder qualificaSearchBuilder) throws Exception;


}
