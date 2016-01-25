package it.cnr.missioni.dropwizard.resources.prenotazione;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import it.cnr.missioni.dropwizard.delegate.prenotazione.IPrenotazioneDelegate;
import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.rest.api.resources.prenotazione.PrenotazioneRestService;

/**
 * 
 * @author Salvia Vito
 *
 */
@Component(value = "prenotazioneRestServiceResource")
public class PrenotazioneRestServiceResource implements PrenotazioneRestService {

	@GeoPlatformLog
	static Logger logger;
	//
	@Resource(name = "prenotazioneDelegate")
	private IPrenotazioneDelegate prenotazioneDelegate;



	/**
	 * @param dataFrom
	 * @param dataTo
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response getPrenotazioneByQuery(Long dataFrom, Long dataTo) throws Exception {
		return Response.ok(this.prenotazioneDelegate.getPrenotazioneByQuery(dataFrom, dataTo)).build();
	}

	/**
	 * @param Prenotazione
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response addPrenotazione(Prenotazione prenotazione) throws Exception {
		return Response.ok(this.prenotazioneDelegate.addPrenotazione(prenotazione)).build();
	}

	/**
	 * @param Prenotazione
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response updatePrenotazione(Prenotazione prenotazione) throws Exception {
		return Response.ok(this.prenotazioneDelegate.updatePrenotazione(prenotazione)).build();
	}

	/**
	 * @param prenotazioneID
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response deletePrenotazione(String prenotazioneID) throws Exception {
		return Response.ok(this.prenotazioneDelegate.deletePrenotazione(prenotazioneID)).build();

	}

}
