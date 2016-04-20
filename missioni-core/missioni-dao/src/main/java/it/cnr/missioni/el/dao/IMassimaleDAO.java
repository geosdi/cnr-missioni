package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IMassimaleSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

/**
 * @author Salvia Vito
 */
public interface IMassimaleDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<Massimale> {

    IPageResult<Massimale> findMassimaleByQuery(IMassimaleSearchBuilder massimaleSearchBuilder) throws Exception;

}
