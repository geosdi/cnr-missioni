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

import it.cnr.missioni.el.model.search.builder.PrenotazioneSearchBuilder;
import it.cnr.missioni.model.prenotazione.Prenotazione;

/**
 * @author Salvia Vito
 */
@Component(value = "prenotazioneDAO")
public class PrenotazioneDAO extends AbstractElasticSearchDAO<Prenotazione> implements IPrenotazioneDAO {

	@Resource(name = "prenotazioneMapper")
	@Override
	public <Mapper extends GPBaseMapper<Prenotazione>> void setMapper(Mapper theMapper) {
		this.mapper = theMapper;
	}

	@Resource(name = "prenotazioneIndexCreator")
	@Override
	public <IC extends GPIndexCreator> void setIndexCreator(IC ic) {
		super.setIndexCreator(ic);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Prenotazione> findLasts() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param dataFrom
	 * @param dataTo
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageResult<Prenotazione> findPrenotazioneByQuery(PrenotazioneSearchBuilder prenotazioneSearchBuilder)
			throws Exception {
		List<Prenotazione> listaPrenotazioni = new ArrayList<Prenotazione>();
		logger.debug("###############Try to find Prenotazioni by Query: {}\n\n");

		Long total = count();

		SearchResponse searchResponse = (this.elastichSearchClient.prepareSearch(getIndexName())
				.setTypes(getIndexType()).setQuery(prenotazioneSearchBuilder.buildQuery()).setSize(total.intValue())
				.execute().actionGet());

		if (searchResponse.status() != RestStatus.OK) {
			throw new IllegalStateException("Error in Elastic Search Query.");
		}

		for (SearchHit searchHit : searchResponse.getHits().hits()) {
			Prenotazione prenotazione = this.mapper.read(searchHit.getSourceAsString());
			if (!prenotazione.isIdSetted()) {
				prenotazione.setId(searchHit.getId());
			}
			listaPrenotazioni.add(prenotazione);
		}

		return new PageResult<Prenotazione>(searchResponse.getHits().getTotalHits(), listaPrenotazioni);
	}

}
