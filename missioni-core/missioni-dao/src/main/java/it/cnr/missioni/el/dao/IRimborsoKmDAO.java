package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IRimborsoKmSearchBuilder;
import it.cnr.missioni.model.configuration.RimborsoKm;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

/**
 * @author Salvia Vito
 */
public interface IRimborsoKmDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<RimborsoKm> {

    IPageResult<RimborsoKm> findRimborsoKmByQuery(IRimborsoKmSearchBuilder rimborsoKmSearchBuilder) throws Exception;


}
