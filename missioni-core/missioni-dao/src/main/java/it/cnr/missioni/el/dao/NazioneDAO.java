package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.dao.PageResult;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;

/**
 * @author Salvia Vito
 */
@Component(value = "nazioneDAO")
public class NazioneDAO extends AbstractElasticSearchDAO<Nazione> implements INazioneDAO {

	@Resource(name = "nazioneMapper")
	@Override
	public <Mapper extends GPBaseMapper<Nazione>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "nazioneIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Nazione> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param nazioneSearchBuilder
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageResult<Nazione> findNazioneByQuery(NazioneSearchBuilder nazioneSearchBuilder) throws Exception {
		List<Nazione> listaNazione = new ArrayList<Nazione>();
		logger.debug("###############Try to find Nazione by Query: {}\n\n");

		Page p = new Page(nazioneSearchBuilder.getFrom(), nazioneSearchBuilder.getSize());

		//carico tutte le nazioni per le combobox
		int size = nazioneSearchBuilder.getSize();
		if (nazioneSearchBuilder.isAll())
			size = count().intValue();
		
		SearchResponse searchResponse = (this.elastichSearchClient.prepareSearch(getIndexName())
				.setTypes(getIndexType()).setQuery(nazioneSearchBuilder.buildQuery())
				.setFrom(nazioneSearchBuilder.getFrom()).setSize(size)
				.addSort(nazioneSearchBuilder.getFieldSort(), nazioneSearchBuilder.getSortOrder())
				.execute().actionGet());

		if (searchResponse.status() != RestStatus.OK) {
			throw new IllegalStateException("Error in Elastic Search Query.");
		}

		for (SearchHit searchHit : searchResponse.getHits().hits()) {
			Nazione nazione = this.mapper.read(searchHit.getSourceAsString());
			if (!nazione.isIdSetted()) {
				nazione.setId(searchHit.getId());
			}
			listaNazione.add(nazione);
		}

		return new PageResult<Nazione>(searchResponse.getHits().getTotalHits(), listaNazione);
	}

}
