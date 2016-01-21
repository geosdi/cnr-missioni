package it.cnr.missioni.el.dao;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;

import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public interface IMissioneDAO extends GPElasticSearchDAO.GPElasticSearchBaseDAO<Missione> {

	/**
	 * 
	 * @param p
	 * @param missioneModelSearch
	 * @return
	 * @throws Exception
	 */
	PageResult<Missione> findMissioneByQuery(MissioneSearchBuilder missioneSearchBuilder) throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	long getMaxNumeroOrdineRimborso() throws Exception;
}
