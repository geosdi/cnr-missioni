package it.cnr.missioni.rest.api.resources.user;

import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.path.user.UsersServiceRSPathConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Path(value = UsersServiceRSPathConfig.API_BASE_PATH)
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public interface UsersRestService {

    /**
     * @param userName
     * @param password
     * @return {@link Response}
     * @throws Exception
     */
    @GET
    @Path(value = UsersServiceRSPathConfig.AUTHORIZE_USER_PATH)
    Response authorize(@QueryParam(value = "username")String userName, @QueryParam(value = "password")String password) throws Exception;


    /**
     * @return {@link Response}
     * @throws Exception
     */
    @GET
    @Path(value = UsersServiceRSPathConfig.GET_ALL_USERS_PATH)
    Response getLastUsers() throws Exception;

    /**
     * @param userName
     * @return {@link Response}
     * @throws Exception
     */
    @GET
    @Path(value = UsersServiceRSPathConfig.GET_USER_BY_QUERY)
    Response getUserByQuery(@QueryParam(value="nome") String nome,@QueryParam(value="cognome") String cognome,@QueryParam(value="codiceFiscale") String codiceFiscale,@QueryParam(value="matricola") String matricola,@QueryParam(value="username") String username) throws Exception;

    /**
     * @param user
     * @return {@link Response}
     * @throws Exception
     */
    @POST
    @Path(value = UsersServiceRSPathConfig.ADD_USER_PATH)
    Response addUser(User user) throws Exception;

    /**
     * @param user
     * @return {@link Response}
     * @throws Exception
     */
    @PUT
    @Path(value = UsersServiceRSPathConfig.UPDATE_USER_PATH)
    Response updateUser(User user) throws Exception;

    /**
     * @param userID
     * @return {@link Response}
     * @throws Exception
     */
    @DELETE
    @Path(value = UsersServiceRSPathConfig.DELETE_USER_PATH)
    Response deleteUser(@QueryParam(value = "userID")String userID) throws Exception;
}
