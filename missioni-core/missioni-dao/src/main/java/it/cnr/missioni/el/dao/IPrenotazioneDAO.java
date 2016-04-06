package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.search.builder.IPrenotazioneSearchBuilder;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

/**
 * @author Salvia Vito
 */
public interface IPrenotazioneDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<Prenotazione> {


    /**
     * @param prenotazioneSearchBuilder
     * @return
     * @throws Exception
     */
    PageResult<Prenotazione> findPrenotazioneByQuery(IPrenotazioneSearchBuilder prenotazioneSearchBuilder) throws Exception;

}
