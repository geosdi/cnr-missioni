package it.cnr.missioni.rest.api.resources.veicoloCNR;

import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.rest.api.path.veicoloCNR.VeicoloCNRServiceRSPathConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Path(value = VeicoloCNRServiceRSPathConfig.API_BASE_PATH)
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public interface VeicoloCNRRestService {

	/**
	 * @param id
	 * @param stato
	 * @param targa
	 * @param cartaCircolazione
	 * @param polizzaAssicurativa
	 * @param notId
	 * @param from
	 * @param size
	 * @param all
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path(value = VeicoloCNRServiceRSPathConfig.GET_VEICOLO_CNR_BY_QUERY)
	Response getVeicoloCNRByQuery(@QueryParam(value = "id") String id,@QueryParam(value = "stato") String stato, @QueryParam(value = "targa") String targa,
			@QueryParam(value = "cartaCircolazione") String cartaCircolazione,
			@QueryParam(value = "polizzaAssicurativa") String polizzaAssicurativa,
			@QueryParam(value = "notId") String notId, @QueryParam(value = "from") int from,
			@QueryParam(value = "size") int size, @QueryParam(value = "all") boolean all) throws Exception;

	/**
	 * 
	 * @param veicoloCNR
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path(value = VeicoloCNRServiceRSPathConfig.ADD_VEICOLO_CNR_PATH)
	Response addVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception;

	/**
	 * 
	 * @param veicoloCNR
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path(value = VeicoloCNRServiceRSPathConfig.UPDATE_VEICOLO_CNR_PATH)
	Response updateVeicoloCNR(VeicoloCNR veicoloCNR) throws Exception;

	/**
	 * 
	 * @param veicoloCNRID
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path(value = VeicoloCNRServiceRSPathConfig.DELETE_VEICOLO_CNR_PATH)
	Response deleteVeicoloCNR(@QueryParam(value = "veicoloCNRID") String veicoloCNRID) throws Exception;
}
