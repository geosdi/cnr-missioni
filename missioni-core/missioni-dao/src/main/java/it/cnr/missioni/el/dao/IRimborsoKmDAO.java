package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IRimborsoKmSearchBuilder;
import it.cnr.missioni.model.configuration.RimborsoKm;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

/**
 * @author Salvia Vito
 */
public interface IRimborsoKmDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<RimborsoKm> {

    PageResult<RimborsoKm> findRimborsoKmByQuery(IRimborsoKmSearchBuilder rimborsoKmSearchBuilder) throws Exception;


}
