package it.cnr.missioni.rest.api.resources.users;

import it.cnr.missioni.model.utente.Utente;
import it.cnr.missioni.rest.api.path.users.UsersServiceRSPathConfig;

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
    Response authorize(String userName, String password) throws Exception;

    /**
     * @param userID
     * @return {@link Response}
     * @throws Exception
     */
    @GET
    @Path(value = UsersServiceRSPathConfig.GET_USER_BY_ID_PATH)
    Response getUserByID(String userID) throws Exception;

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
    @Path(value = UsersServiceRSPathConfig.GET_USER_BY_USERNAME_PATH)
    Response getUserByUserName(String userName) throws Exception;

    /**
     * @param user
     * @return {@link Response}
     * @throws Exception
     */
    @POST
    @Path(value = UsersServiceRSPathConfig.ADD_USER_PATH)
    Response addUser(Utente user) throws Exception;

    /**
     * @param user
     * @return {@link Response}
     * @throws Exception
     */
    @PUT
    @Path(value = UsersServiceRSPathConfig.UPDATE_USER_PATH)
    Response updateUser(Utente user) throws Exception;

    /**
     * @param userID
     * @return {@link Response}
     * @throws Exception
     */
    @DELETE
    @Path(value = UsersServiceRSPathConfig.DELETE_USER_PATH)
    Response deleteUser(String userID) throws Exception;
}
