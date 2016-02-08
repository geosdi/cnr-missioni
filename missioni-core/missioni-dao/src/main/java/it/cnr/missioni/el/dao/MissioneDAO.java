package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;

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
	public PageResult<Missione> findMissioneByQuery(MissioneSearchBuilder missioneSearchBuilder) throws Exception {
		List<Missione> listaMissioni = new ArrayList<Missione>();

		logger.debug("###############Try to find Missione by Query: {}\n\n");

		Page p = new Page(missioneSearchBuilder.getFrom(), missioneSearchBuilder.getSize());

		SearchResponse searchResponse = p
				.buildPage(this.elastichSearchClient.prepareSearch(getIndexName()).setTypes(getIndexType())
						.setQuery(missioneSearchBuilder.buildQuery()))
				.setFrom(missioneSearchBuilder.getFrom()).setSize(missioneSearchBuilder.getSize())
				.addSort(missioneSearchBuilder.getFieldSort(), missioneSearchBuilder.getSortOrder()).execute()
				.actionGet();

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
		return new PageResult<Missione>(searchResponse.getHits().getTotalHits(), listaMissioni);
	}

	public long getMaxNumeroOrdineRimborso() throws Exception {
		long value = 0;

		SearchResponse sr = this.elastichSearchClient.prepareSearch(getIndexName())
				.addAggregation(
						AggregationBuilders.max("max_numero_ordine_rimborso").field("missione.rimborso.numeroOrdine"))
				.execute().actionGet();
		Max agg = sr.getAggregations().get("max_numero_ordine_rimborso");
		value = (long) agg.getValue() + 1;

		return value;
	}

/**
 * 
 * @return
 * @throws Exception
 */
	@Override
	public StatisticheMissioni getStatisticheMissioni() throws Exception {
		logger.debug("###############Try to find Neet in ALL Comune: {}\n\n");


		SearchResponse searchResponse = this.elastichSearchClient.prepareSearch(getIndexName())

				.addAggregation(
						AggregationBuilders.terms("by_stato").field("missione.stato").size(0))
				.setTypes(getIndexType()).execute().actionGet();

		if (searchResponse.status() != RestStatus.OK) {
			throw new IllegalStateException("Error in Elastic Search Query.");
		}

		StatisticheMissioni statisticheMissioni = new StatisticheMissioni();

		Terms termsByComune = searchResponse.getAggregations().get("by_stato");
		Collection<Terms.Bucket> bucketsByStato = termsByComune.getBuckets();

		bucketsByStato.forEach(bucketByStato -> {

			statisticheMissioni.getMappaStatistiche().put(((StatoEnum)bucketByStato.getKey()).getStato(),
					bucketByStato.getDocCount());

			});



	return statisticheMissioni;

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
