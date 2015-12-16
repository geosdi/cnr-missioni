package it.cnr.missioni.el.dao;

import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import it.cnr.missioni.model.utente.Utente;

/**
 * @author Salvia Vito
 */
@Component(value = "utenteDAO")
public class UtenteDAO extends AbstractElasticSearchDAO<Utente>  implements IUtenteDAO{

	
	/**
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@Override
	public Utente findUtenteByUsername(String username) throws Exception {
		Utente utente = null;

		logger.debug("###############Try to find Utente with username: {}\n\n", username);
		SearchResponse searchResponse = this.elastichSearchClient.prepareSearch(getIndexName()).setTypes(getIndexType())
				.setQuery(QueryBuilders.matchQuery("utente.credenziali.username", username).cutoffFrequency(0.001f)
						.operator(MatchQueryBuilder.Operator.AND))
				.execute().actionGet();

		if (searchResponse.status() != RestStatus.OK) {
			throw new IllegalStateException("Error in Elastic Search Query.");
		}

		for (SearchHit searchHit : searchResponse.getHits().hits()) {
			utente = this.mapper.read(searchHit.getSourceAsString());
			if (!utente.isIdSetted()) {
				utente.setId(searchHit.getId());
			}
		}

		return utente;
	}
	
	@Override
	public List<Utente> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Resource(name = "utenteMapper")
	@Override
	public <Mapper extends GPBaseMapper<Utente>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "utenteIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}







}
