package it.cnr.missioni.rest.api.resources.massimale;

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

import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.rest.api.path.massimale.MassimaleServiceRSPathConfig;

/**
 * 
 * @author Salvia Vito
 *
 */
@Path(value = MassimaleServiceRSPathConfig.API_BASE_PATH)
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public interface MassimaleRestService {

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path(value = MassimaleServiceRSPathConfig.GET_MASSIMALE_BY_QUERY)
	Response getMassimaleByQuery(@QueryParam(value = "from") int from,@QueryParam(value = "size") int size,@QueryParam(value = "livello") String livello,@QueryParam(value = "areaGeografica") String areaGeografica,@QueryParam(value = "notId") String notId) throws Exception;

	/**
	 * 
	 * @param massimale
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path(value = MassimaleServiceRSPathConfig.ADD_MASSIMALE_PATH)
	Response addMassimale(Massimale massimale) throws Exception;

	/**
	 * 
	 * @param massimale
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path(value = MassimaleServiceRSPathConfig.UPDATE_MASSIMALE_PATH)
	Response updateMassimale(Massimale massimale) throws Exception;

	/**
	 * 
	 * @param massimaleID
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path(value = MassimaleServiceRSPathConfig.DELETE_MASSIMALE_PATH)
	Response deleteMassimale(@QueryParam(value = "massimaleID") String massimaleID) throws Exception;
}
