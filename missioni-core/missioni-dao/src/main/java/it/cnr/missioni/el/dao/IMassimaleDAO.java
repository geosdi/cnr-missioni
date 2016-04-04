package it.cnr.missioni.el.dao;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

import it.cnr.missioni.el.model.search.builder.IMassimaleSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;

/**
 * @author Salvia Vito
 */
public interface IMassimaleDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<Massimale> {

	PageResult<Massimale> findMassimaleByQuery(IMassimaleSearchBuilder massimaleSearchBuilder) throws Exception;


}
