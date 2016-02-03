package it.cnr.missioni.rest.api.resources.nazione;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.rest.api.path.nazione.NazioneServiceRSPathConfig;

/**
 * 
 * @author Salvia Vito
 *
 */
@Path(value = NazioneServiceRSPathConfig.API_BASE_PATH)
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public interface NazioneRestService {

	/**
	 * 
	 * @param id
	 * @param from
	 * @param size
	 * @param all
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path(value = NazioneServiceRSPathConfig.GET_NAZIONE_BY_QUERY)
	Response getNazioneByQuery(@QueryParam(value = "id") String id, @QueryParam(value = "from") int from,
			@QueryParam(value = "size") int size, @QueryParam(value = "all") boolean all) throws Exception;

	/**
	 * 
	 * @param nazione
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path(value = NazioneServiceRSPathConfig.ADD_NAZIONE_PATH)
	Response addNazione(Nazione nazione) throws Exception;

	/**
	 * 
	 * @param nazione
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path(value = NazioneServiceRSPathConfig.UPDATE_NAZIONE_PATH)
	Response updateNazione(Nazione nazione) throws Exception;

	/**
	 * 
	 * @param nazioneID
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path(value = NazioneServiceRSPathConfig.DELETE_NAZIONE_PATH)
	Response deleteNazione(@QueryParam(value = "nazioneID") String nazioneID) throws Exception;
}
