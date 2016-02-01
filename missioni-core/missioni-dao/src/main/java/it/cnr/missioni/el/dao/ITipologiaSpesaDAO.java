package it.cnr.missioni.el.dao;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;

/**
 * @author Salvia Vito
 */
public interface ITipologiaSpesaDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<TipologiaSpesa> {

	PageResult<TipologiaSpesa> findTipologiaSpesaByQuery(TipologiaSpesaSearchBuilder tipologiaSpesaearchBuilder) throws Exception;


}
