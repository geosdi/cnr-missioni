package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.el.model.search.builder.IMissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

/**
 * @author Salvia Vito
 */
public interface IMissioneDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<Missione> {

    /**
     * @param p
     * @param missioneModelSearch
     * @return
     * @throws Exception
     */
    PageResult<Missione> findMissioneByQuery(IMissioneSearchBuilder missioneSearchBuilder) throws Exception;

    /**
     * @return
     * @throws Exception
     */
    long getMaxNumeroOrdineRimborso() throws Exception;

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
}
