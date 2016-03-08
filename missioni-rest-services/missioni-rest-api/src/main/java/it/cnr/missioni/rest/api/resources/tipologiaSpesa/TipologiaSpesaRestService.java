package it.cnr.missioni.rest.api.resources.tipologiaSpesa;

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

import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.rest.api.path.tipologiaSpesa.TipologiaSpesaServiceRSPathConfig;

/**
 * 
 * @author Salvia Vito
 *
 */
@Path(value = TipologiaSpesaServiceRSPathConfig.API_BASE_PATH)
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public interface TipologiaSpesaRestService {

	/**
	 * 
	 * @param id
	 * @param tipo
	 * @param tipologiaTrattamento
	 * @param from
	 * @param size
	 * @param all
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path(value = TipologiaSpesaServiceRSPathConfig.GET_TIPOLIGIA_SPESA_BY_QUERY)
	Response getTipologiaSpesaByQuery(@QueryParam(value = "id") String id, @QueryParam(value = "tipo") String tipo,
			@QueryParam(value = "tipologiaTrattamento") String tipologiaTrattamento,
			@QueryParam(value = "from") int from, @QueryParam(value = "size") int size,
			@QueryParam(value = "all") boolean all) throws Exception;

	/**
	 * 
	 * @param tipologiaSpesa
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path(value = TipologiaSpesaServiceRSPathConfig.ADD_TIPOLIGIA_SPESA_PATH)
	Response addTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception;

	/**
	 * 
	 * @param tipologiaSpesa
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path(value = TipologiaSpesaServiceRSPathConfig.UPDATE_TIPOLOGIA_SPESA_PATH)
	Response updateTipologiaSpesa(TipologiaSpesa tipologiaSpesa) throws Exception;

	/**
	 * 
	 * @param tipologiaSpesaID
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path(value = TipologiaSpesaServiceRSPathConfig.DELETE_TIPOLIOGIA_SPESA_PATH)
	Response deleteTipologiaSpesa(@QueryParam(value = "tipologiaSpesaID") String tipologiaSpesaID) throws Exception;
}
