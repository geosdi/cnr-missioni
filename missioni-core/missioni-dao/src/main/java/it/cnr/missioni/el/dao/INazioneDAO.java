package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.INazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

/**
 * @author Salvia Vito
 */
public interface INazioneDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<Nazione> {

    IPageResult<Nazione> findNazioneByQuery(INazioneSearchBuilder nazioneSearchBuilder) throws Exception;


}
