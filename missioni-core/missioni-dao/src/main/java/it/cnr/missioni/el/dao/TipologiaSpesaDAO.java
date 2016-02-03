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

import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;

/**
 * @author Salvia Vito
 */
@Component(value = "tipologiaSpesaDAO")
public class TipologiaSpesaDAO extends AbstractElasticSearchDAO<TipologiaSpesa> implements ITipologiaSpesaDAO {

	@Resource(name = "tipologiaSpesaMapper")
	@Override
	public <Mapper extends GPBaseMapper<TipologiaSpesa>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "tipologiaSpesaIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TipologiaSpesa> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

/**
 * 
 * @param tipologiaSpesaSearchBuilder
 * @return
 * @throws Exception
 */
	@Override
	public PageResult<TipologiaSpesa> findTipologiaSpesaByQuery(TipologiaSpesaSearchBuilder tipologiaSpesaSearchBuilder)
			throws Exception {
		List<TipologiaSpesa> listaTipologiaSpesa = new ArrayList<TipologiaSpesa>();
		logger.debug("###############Try to find Tipologia Spesa by Query: {}\n\n");

		Page p = new Page(tipologiaSpesaSearchBuilder.getFrom(), tipologiaSpesaSearchBuilder.getSize());

		
		//carico tutte le nazioni per le combobox
		int size = tipologiaSpesaSearchBuilder.getSize();
		if (tipologiaSpesaSearchBuilder.isAll())
			size = count().intValue();
		
		SearchResponse searchResponse = (this.elastichSearchClient.prepareSearch(getIndexName())
				.setTypes(getIndexType()).setQuery(tipologiaSpesaSearchBuilder.buildQuery())
				.setFrom(tipologiaSpesaSearchBuilder.getFrom()).setSize(size)
				.addSort(tipologiaSpesaSearchBuilder.getFieldSort(), tipologiaSpesaSearchBuilder.getSortOrder())
				.execute()
				.actionGet());

		if (searchResponse.status() != RestStatus.OK) {
			throw new IllegalStateException("Error in Elastic Search Query.");
		}

		for (SearchHit searchHit : searchResponse.getHits().hits()) {
			TipologiaSpesa tipologiaSpesa = this.mapper.read(searchHit.getSourceAsString());
			if (!tipologiaSpesa.isIdSetted()) {
				tipologiaSpesa.setId(searchHit.getId());
			}
			listaTipologiaSpesa.add(tipologiaSpesa);
		}

		return new PageResult<TipologiaSpesa>(searchResponse.getHits().getTotalHits(), listaTipologiaSpesa);
	}

}
