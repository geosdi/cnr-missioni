package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.search.MultiMatchQuery.QueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.GPElasticSearchDAO.Page;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
@Component(value = "missioneDAO")
public class MissioneDAO extends AbstractElasticSearchDAO<Missione> implements IMissioneDAO {

	/**
	 * 
	 * @param p
	 * @param missioneModelSearch
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Missione> findMissioneByQuery(Page p, MissioneSearchBuilder missioneSearchBuilder) throws Exception {
		List<Missione> listaMissioni = new ArrayList<Missione>();

		logger.debug("###############Try to find Missione by Query: {}\n\n");
		SearchResponse searchResponse = p.buildPage(this.elastichSearchClient.prepareSearch(getIndexName())
				.setTypes(getIndexType()).setQuery(missioneSearchBuilder.buildQuery())).execute().actionGet();

		if (searchResponse.status() != RestStatus.OK) {
			throw new IllegalStateException("Error in Elastic Search Query.");
		}

		
		
		for (SearchHit searchHit : searchResponse.getHits().hits()) {
			Missione missione = this.mapper.read(searchHit.getSourceAsString());
			if (!missione.isIdSetted()) {
				missione.setId(searchHit.getId());
			}
			listaMissioni.add(missione);
		}

		return listaMissioni;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Missione> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Resource(name = "missioneMapper")
	@Override
	public <Mapper extends GPBaseMapper<Missione>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "missioneIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}

}
