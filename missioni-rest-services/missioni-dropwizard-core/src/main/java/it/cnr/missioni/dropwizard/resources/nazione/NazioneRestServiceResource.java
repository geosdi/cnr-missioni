package it.cnr.missioni.dropwizard.resources.nazione;

import it.cnr.missioni.dropwizard.delegate.nazione.INazioneDelegate;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.rest.api.resources.nazione.NazioneRestService;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Salvia Vito
 *
 */
@Component(value = "nazioneRestServiceResource")
public class NazioneRestServiceResource implements NazioneRestService {

	@GeoPlatformLog
	static Logger logger;
	//
	@Resource(name = "nazioneDelegate")
	private INazioneDelegate nazioneDelegate;

	/**
	 * 
	 * @param id
	 * @param from
	 * @param size
	 * @param all
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response getNazioneByQuery(String id, int from, int size, boolean all) throws Exception {
		return Response.ok(this.nazioneDelegate.getNazioneByQuery(id, from, size, all)).build();
	}

	/**
	 * 
	 * @param nazione
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response addNazione(Nazione nazione) throws Exception {
		return Response.ok(this.nazioneDelegate.addNazione(nazione)).build();
	}

	/**
	 * 
	 * @param nazione
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response updateNazione(Nazione nazione) throws Exception {
		return Response.ok(this.nazioneDelegate.updateNazione(nazione)).build();
	}

	/**
	 * 
	 * @param nazioneID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response deleteNazione(String nazioneID) throws Exception {
		return Response.ok(this.nazioneDelegate.deleteNazione(nazioneID)).build();
	}

}
