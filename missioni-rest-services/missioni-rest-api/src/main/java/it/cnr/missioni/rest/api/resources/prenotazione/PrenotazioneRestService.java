package it.cnr.missioni.rest.api.resources.prenotazione;

import it.cnr.missioni.model.prenotazione.Prenotazione;
import it.cnr.missioni.rest.api.path.prenotazione.PrenotazioneServiceRSPathConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Salvia Vito
 *
 */
@Path(value = PrenotazioneServiceRSPathConfig.API_BASE_PATH)
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public interface PrenotazioneRestService {

	/**
	 * 
	 * @param dataFrom
	 * @param dataTo
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path(value = PrenotazioneServiceRSPathConfig.GET_PRENOTAZIONI_BY_QUERY)
	Response getPrenotazioneByQuery(@QueryParam(value = "dataFrom") Long dataFrom,
			@QueryParam(value = "dataTo") Long dataTo) throws Exception;

	/**
	 * 
	 * @param prenotazione
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path(value = PrenotazioneServiceRSPathConfig.ADD_PRENOTAZIONE_PATH)
	Response addPrenotazione(Prenotazione prenotazione) throws Exception;

	/**
	 * 
	 * @param prenotazione
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path(value = PrenotazioneServiceRSPathConfig.UPDATE_PRENOTAZIONE_PATH)
	Response updatePrenotazione(Prenotazione prenotazione) throws Exception;

	/**
	 * 
	 * @param prenotazioneID
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path(value = PrenotazioneServiceRSPathConfig.DELETE_PRENOTAZIONE_PATH)
	Response deletePrenotazione(@QueryParam(value = "prenotazioneID") String prenotazioneID) throws Exception;

}
