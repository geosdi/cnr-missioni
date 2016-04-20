package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.ITipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

/**
 * @author Salvia Vito
 */
public interface ITipologiaSpesaDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<TipologiaSpesa> {

    IPageResult<TipologiaSpesa> findTipologiaSpesaByQuery(ITipologiaSpesaSearchBuilder tipologiaSpesaearchBuilder) throws Exception;


}
