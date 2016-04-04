package it.cnr.missioni.el.dao;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

import it.cnr.missioni.el.model.search.builder.INazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;

/**
 * @author Salvia Vito
 */
public interface INazioneDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<Nazione> {

	PageResult<Nazione> findNazioneByQuery(INazioneSearchBuilder nazioneSearchBuilder) throws Exception;


}
