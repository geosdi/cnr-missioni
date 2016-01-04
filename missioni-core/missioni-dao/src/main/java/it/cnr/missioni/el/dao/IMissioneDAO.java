package it.cnr.missioni.el.dao;

import java.util.List;

import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
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
	List<Missione> findMissioneByQuery(Page p, MissioneSearchBuilder missioneSearchBuilder) throws Exception;

}
