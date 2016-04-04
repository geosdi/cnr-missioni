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

import it.cnr.missioni.el.model.search.builder.IMassimaleSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;

/**
 * @author Salvia Vito
 */
@Component(value = "massimaleDAO")
public class MassimaleDAO extends AbstractElasticSearchDAO<Massimale> implements IMassimaleDAO {

	@Resource(name = "massimaleMapper")
	@Override
	public <Mapper extends GPBaseMapper<Massimale>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "massimaleIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Massimale> findLasts() throws Exception {
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
	public PageResult<Massimale> findMassimaleByQuery(IMassimaleSearchBuilder massimaleSearchBuilder) throws Exception {
		List<Massimale> listaMassimale = new ArrayList<Massimale>();
		logger.debug("###############Try to find Massimale by Query: {}\n\n");

		Page p = new Page(massimaleSearchBuilder.getFrom(), massimaleSearchBuilder.getSize());


		SearchResponse searchResponse = (this.elastichSearchClient.prepareSearch(getIndexName())
				.setTypes(getIndexType()).setQuery(massimaleSearchBuilder.buildQuery())
				.setFrom(massimaleSearchBuilder.getFrom()).setSize(massimaleSearchBuilder.getSize())
				.addSort(massimaleSearchBuilder.getFieldSort(), massimaleSearchBuilder.getSortOrder()).execute()
				.actionGet());

		if (searchResponse.status() != RestStatus.OK) {
			throw new IllegalStateException("Error in Elastic Search Query.");
		}

		for (SearchHit searchHit : searchResponse.getHits().hits()) {
			Massimale massimale = this.mapper.read(searchHit.getSourceAsString());
			if (!massimale.isIdSetted()) {
				massimale.setId(searchHit.getId());
			}
			listaMassimale.add(massimale);
		}

		return new PageResult<Massimale>(searchResponse.getHits().getTotalHits(), listaMassimale);
	}

}
