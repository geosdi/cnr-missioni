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

import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
@Component(value = "veicoloCNRDAO")
public class VeicoloCNRDAO extends AbstractElasticSearchDAO<VeicoloCNR> implements IVeicoloCNRDAO {

	@Resource(name = "veicoloCNRMapper")
	@Override
	public <Mapper extends GPBaseMapper<VeicoloCNR>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "veicoloCNRIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<VeicoloCNR> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param veicoloCNRSearchBuilder
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageResult<VeicoloCNR> findVeicoloCNRByQuery(VeicoloCNRSearchBuilder veicoloCNRSearchBuilder) throws Exception {
		List<VeicoloCNR> listaVeicoliCNR = new ArrayList<VeicoloCNR>();
		logger.debug("###############Try to find VeicoloCNR by Query: {}\n\n");

		Page p = new Page(veicoloCNRSearchBuilder.getFrom(), veicoloCNRSearchBuilder.getSize());

		SearchResponse searchResponse = (this.elastichSearchClient.prepareSearch(getIndexName())
				.setTypes(getIndexType()).setQuery(veicoloCNRSearchBuilder.buildQuery())
				.setFrom(veicoloCNRSearchBuilder.getFrom())
				.setSize(veicoloCNRSearchBuilder.getSize())
				.execute().actionGet());

		if (searchResponse.status() != RestStatus.OK) {
			throw new IllegalStateException("Error in Elastic Search Query.");
		}

		for (SearchHit searchHit : searchResponse.getHits().hits()) {
			VeicoloCNR veicoloCNR = this.mapper.read(searchHit.getSourceAsString());
			if (!veicoloCNR.isIdSetted()) {
				veicoloCNR.setId(searchHit.getId());
			}
			listaVeicoliCNR.add(veicoloCNR);
		}

		return new PageResult<VeicoloCNR>(searchResponse.getHits().getTotalHits(), listaVeicoliCNR);
	}

}
