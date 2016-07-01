package it.cnr.missioni.el.dao;

import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.el.model.search.builder.IMissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.SearchConstants;
import it.cnr.missioni.model.configuration.MissioneId;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.geosdi.geoplatform.experimental.el.search.bool.IBooleanSearch;
import org.geosdi.geoplatform.experimental.el.search.date.IGPDateQuerySearch;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Salvia Vito
 */
@Component(value = "missioneDAO")
public class MissioneDAO extends AbstractElasticSearchDAO<Missione> implements IMissioneDAO {

    @Resource(name = "missioneIdDAO")
    private IMissioneIdDAO missioneIdDAO;

    /**
     * @param missioneSearchBuilder
     * @return
     * @throws Exception
     */
    @Override
    public IPageResult<Missione> findMissioneByQuery(IMissioneSearchBuilder missioneSearchBuilder) throws Exception {
        List<Missione> listaMissioni = new ArrayList<Missione>();
        logger.debug("###############Try to find Missione by Query: {}\n\n");
        return super.find(new MultiFieldsSearch(missioneSearchBuilder.getFieldSort(), SortOrder.DESC,missioneSearchBuilder.getFrom(),missioneSearchBuilder.getSize(),missioneSearchBuilder.getListAbstractBooleanSearch().toArray(new IBooleanSearch[missioneSearchBuilder.getListAbstractBooleanSearch().size()])));
    }

/*    *//**
     * @return
     * @throws Exception
     *//*
    public long getMaxNumeroOrdineRimborso() throws Exception {
        long value = 0;
        IMissioneSearchBuilder missioneSearchBuilder = IMissioneSearchBuilder.MissioneSearchBuilder.getMissioneSearchBuilder().withFieldExist("missione.rimborso");
        List<Missione> lista = this.findMissioneByQuery(missioneSearchBuilder).getResults();
        return lista.size() + 1;
    }*/

    /**
     * Trova il numero di missioni inserite in un anno per calcolare l'id
     *
     * @return
     * @throws Exception
     */
    public synchronized String getMaxNumeroMissioneAnno() throws Exception {
        DateTime now = new DateTime();
        MissioneId missioneId = this.missioneIdDAO.find(new Page(0,1)).getResults().get(0);
        String id = missioneId.getValue()+"-" + now.getYear();
        missioneId.setValue(Long.parseLong(missioneId.getValue())+1+"");
        this.missioneIdDAO.update(missioneId);
        return id;
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public StatisticheMissioni getStatisticheMissioni() throws Exception {
        logger.debug("###############Try to find Statistiche: {}\n\n");
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
            statisticheMissioni.getMappaStatistiche().put(StatoEnum.getStatoEnum(bucketByStato.getKey().toString().toUpperCase()),
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

	@Override
	public List<Missione> getUsersInMissione() throws Exception {

		DateTime now = new DateTime().now();
		DateTime dateFrom = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0);
		DateTime dateTo = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 23, 59);
		//Prendo tutte le missioni che iniziano nella data odierna
		RangeQueryBuilder queryBuilder = QueryBuilders.rangeQuery(SearchConstants.MISSIONE_FIELD_DATA_INIZIO).gte(dateFrom).lte(dateTo);
		Long size = count(queryBuilder);
	    IPageResult<Missione> pageResult = super.find(new MultiFieldsSearch(0, size.intValue(),
	    		new IGPDateQuerySearch.GPDateQuerySearch(SearchConstants.MISSIONE_FIELD_DATA_INIZIO, IBooleanSearch.BooleanQueryType.MUST,dateFrom,dateTo)));
		 
	    List<Missione> lista = pageResult.getResults();
	    
	    //Prendo tutte le missioni che sono iniziata ma non ancora concluse alla data di oggi
	    QueryBuilder booleanQuery = QueryBuilders.boolQuery()
	    		.must(QueryBuilders.rangeQuery(SearchConstants.MISSIONE_FIELD_DATA_INIZIO).lt(dateFrom).gte(dateFrom.minusDays(30)))
	    		.must(QueryBuilders.rangeQuery(SearchConstants.MISSIONE_FIELD_DATA_FINE).gte(dateFrom).lte(dateTo.plusDays(30)));
	    size = count(booleanQuery);
	   
	    pageResult = super.find(new MultiFieldsSearch(0, size.intValue(),
	    		new IGPDateQuerySearch.GPDateQuerySearch(SearchConstants.MISSIONE_FIELD_DATA_INIZIO, IBooleanSearch.BooleanQueryType.MUST,dateFrom.minusDays(30),dateFrom),
	    		new IGPDateQuerySearch.GPDateQuerySearch(SearchConstants.MISSIONE_FIELD_DATA_FINE, IBooleanSearch.BooleanQueryType.MUST,dateFrom,dateTo.plusDays(30))
	    		));
	    
	    lista.addAll(pageResult.getResults());
		return lista;
	}


}
