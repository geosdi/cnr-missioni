package it.cnr.missioni.el.dao;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

import it.cnr.missioni.el.model.search.builder.QualificaUserSearchBuilder;
import it.cnr.missioni.model.configuration.QualificaUser;

/**
 * @author Salvia Vito
 */
public interface IQualificaUserDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<QualificaUser> {

	PageResult<QualificaUser> findQualificaUserByQuery(QualificaUserSearchBuilder qualificaSearchBuilder) throws Exception;


}
