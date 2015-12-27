package it.cnr.missioni.el.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.geosdi.geoplatform.experimental.el.api.mapper.GPBaseMapper;
import org.geosdi.geoplatform.experimental.el.dao.AbstractElasticSearchDAO;
import org.geosdi.geoplatform.experimental.el.index.GPIndexCreator;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.model.search.BooleanModelSearch;
import it.cnr.missioni.model.utente.Utente;

/**
 * @author Salvia Vito
 */
@Component(value = "utenteDAO")
public class UtenteDAO extends AbstractElasticSearchDAO<Utente> implements IUtenteDAO {

	/**
	 * 
	 * @param p
	 * @param utenteModelSearch
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Utente> findUtenteByQuery(Page p, BooleanModelSearch booleanModelSearch) throws Exception {
		List<Utente> listaUtenti = new ArrayList<Utente>();

		logger.debug("###############Try to find Utenti: {}\n\n");
		SearchResponse searchResponse = p.buildPage(this.elastichSearchClient.prepareSearch(getIndexName())
				.setTypes(getIndexType()).setQuery(booleanModelSearch.buildQuery())).execute().actionGet();

		if (searchResponse.status() != RestStatus.OK) {
			throw new IllegalStateException("Error in Elastic Search Query.");
		}

		for (SearchHit searchHit : searchResponse.getHits().hits()) {
			Utente utente = this.mapper.read(searchHit.getSourceAsString());
			if (!utente.isIdSetted()) {
				utente.setId(searchHit.getId());
			}
			listaUtenti.add(utente);
		}

		return listaUtenti;
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
