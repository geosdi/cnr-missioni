package it.cnr.missioni.rest.api.resources.qualificaUser;

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

import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.rest.api.path.qualificaUser.QualificaUserServiceRSPathConfig;

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
	 * 
	 * @param from
	 * @param size
	 * @param all
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path(value = QualificaUserServiceRSPathConfig.GET_QUALIFICA_USER_BY_QUERY)
	Response getQualificaByQuery(@QueryParam(value = "from") int from, @QueryParam(value = "size") int size,
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
