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
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
@Component(value = "userDAO")
public class UserDAO extends AbstractElasticSearchDAO<User> implements IUserDAO {

	/**
	 * 
	 * @param p
	 * @param userSearchBuilder
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<User> findUtenteByQuery(Page p, UserSearchBuilder userSearchBuilder) throws Exception {
		
		List<User> listaUtenti = new ArrayList<User>();

		logger.debug("###############Try to find Users by Query: {}\n\n");
		SearchResponse searchResponse = p.buildPage(this.elastichSearchClient.prepareSearch(getIndexName())
				.setTypes(getIndexType()).setQuery(userSearchBuilder.buildQuery())).execute().actionGet();

		if (searchResponse.status() != RestStatus.OK) {
			throw new IllegalStateException("Error in Elastic Search Query.");
		}

		for (SearchHit searchHit : searchResponse.getHits().hits()) {
			User utente = this.mapper.read(searchHit.getSourceAsString());
			if (!utente.isIdSetted()) {
				utente.setId(searchHit.getId());
			}
			listaUtenti.add(utente);
		}

		return listaUtenti;
	}

	@Override
	public List<User> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Resource(name = "userMapper")
	@Override
	public <Mapper extends GPBaseMapper<User>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "userIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}

}
