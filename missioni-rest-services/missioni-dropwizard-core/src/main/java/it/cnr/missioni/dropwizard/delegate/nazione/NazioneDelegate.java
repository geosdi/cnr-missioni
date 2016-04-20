package it.cnr.missioni.dropwizard.delegate.nazione;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import it.cnr.missioni.el.dao.INazioneDAO;
import it.cnr.missioni.el.model.search.builder.INazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.experimental.el.dao.GPPageableElasticSearchDAO;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;

import javax.annotation.Resource;

/**
 * 
 * @author Salvia Vito
 *
 */
class NazioneDelegate implements INazioneDelegate {

	static {
		gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}

	private static final TimeBasedGenerator gen;
	//
	@GeoPlatformLog
	private static Logger logger;
	//
	@Resource(name = "nazioneDAO")
	private INazioneDAO nazioneDAO;

	/**
	 * 
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@Override
	public NazioneStore getNazioneByQuery(String id, int from, int size, boolean all) throws Exception {

		INazioneSearchBuilder nazioneUserSearchBuilder = INazioneSearchBuilder.NazioneSearchBuilder.getNazioneSearchBuilder().withFrom(from)
				.withSize(size).withAll(all).withId(id);

		GPPageableElasticSearchDAO.IPageResult<Nazione> pageResult = this.nazioneDAO.findNazioneByQuery(nazioneUserSearchBuilder);
		NazioneStore nazioneStore = new NazioneStore();
		nazioneStore.setNazione(pageResult.getResults());
		nazioneStore.setTotale(pageResult.getTotal());
		return nazioneStore;
	}

	/**
	 * 
	 * @param nazione
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addNazione(Nazione nazione) throws Exception {
		if ((nazione == null)) {
			throw new IllegalParameterFault("The Parameter nazione must not be null");
		}
		if (nazione.getId() == null)
			nazione.setId(gen.generate().toString());
		return this.nazioneDAO.persist(nazione).getId();
	}

	/**
	 * 
	 * @param nazione
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean updateNazione(Nazione nazione) throws Exception {
		if ((nazione == null)) {
			throw new IllegalParameterFault("The Parameter nazione must not be null");
		}
		this.nazioneDAO.update(nazione);
		return Boolean.TRUE;
	}

	/**
	 * 
	 * @param nazioneID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean deleteNazione(String nazioneID) throws Exception {
		if ((nazioneID == null) || (nazioneID.isEmpty())) {
			throw new IllegalParameterFault("The Parameter nazioneID must not be null " + "or an Empty String.");
		}
		this.nazioneDAO.delete(nazioneID);
		return Boolean.TRUE;
	}

}
