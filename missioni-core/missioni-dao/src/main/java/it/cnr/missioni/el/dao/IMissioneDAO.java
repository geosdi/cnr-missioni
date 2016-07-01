package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.el.model.search.builder.IMissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;

import java.util.List;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

/**
 * @author Salvia Vito
 */
public interface IMissioneDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<Missione> {

    /**
     * @param missioneSearchBuilder
     * @return
     * @throws Exception
     */
    IPageResult<Missione> findMissioneByQuery(IMissioneSearchBuilder missioneSearchBuilder) throws Exception;

    /**
     * @return
     * @throws Exception
     */
/*    long getMaxNumeroOrdineRimborso() throws Exception;*/

    /**
     * @return
     * @throws Exception
     */
    String getMaxNumeroMissioneAnno() throws Exception;


    /**
     * @return
     * @throws Exception
     */
    public StatisticheMissioni getStatisticheMissioni() throws Exception;
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public List<Missione> getUsersInMissione() throws Exception;
}
