package it.cnr.missioni.rest.api.resources.qualificaUser;

import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.rest.api.path.qualificaUser.QualificaUserServiceRSPathConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Salvia Vito
 *
 */
@Path(value = QualificaUserServiceRSPathConfig.API_BASE_PATH)
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public interface QualificaUserRestService {

	/**
	 * @param id
	 * @param from
	 * @param size
	 * @param all
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path(value = QualificaUserServiceRSPathConfig.GET_QUALIFICA_USER_BY_QUERY)
	Response getQualificaByQuery(@QueryParam(value = "id") String id,@QueryParam(value = "from") int from, @QueryParam(value = "size") int size,
			@QueryParam(value = "all") boolean all) throws Exception;

	/**
	 * 
	 * @param qualificaUser
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path(value = QualificaUserServiceRSPathConfig.ADD_QUALIFICA_USER_PATH)
	Response addQualificaUser(QualificaUser qualificaUser) throws Exception;

	/**
	 * 
	 * @param qualificaUser
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path(value = QualificaUserServiceRSPathConfig.UPDATE_QUALIFICA_USER_PATH)
	Response updateQualificaUser(QualificaUser qualificaUser) throws Exception;

	/**
	 * 
	 * @param qualificaUserID
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Path(value = QualificaUserServiceRSPathConfig.DELETE_QUALIFICA_PATH)
	Response deleteQualificaUser(@QueryParam(value = "qualificaUserID") String qualificaUserID) throws Exception;
}
