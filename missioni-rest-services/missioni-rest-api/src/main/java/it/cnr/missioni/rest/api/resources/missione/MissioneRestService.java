package it.cnr.missioni.rest.api.resources.missione;

import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.path.missione.BaseServiceRSPathConfig;
import it.cnr.missioni.rest.api.request.NotificationMissionRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Path(value = BaseServiceRSPathConfig.API_BASE_PATH)
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public interface MissioneRestService {

    /**
     * @param idMissione
     * @return {@link Response}
     * @throws Exception
     */
    @GET
    @Path(value = BaseServiceRSPathConfig.GET_MISSIONE_BY_QUERY)
    Response getMissioneByQuery(@QueryParam(value = "idMissione") String idMissione, @QueryParam(value = "idUser") String idUser, @QueryParam(value = "stato") String stato, @QueryParam(value = "numeroOrdineRimborso") String numeroOrdineRimborso) throws Exception;

    /**
     * @param userID
     * @return {@link Response}
     * @throws Exception
     */
    @GET
    @Path(value = BaseServiceRSPathConfig.GET_LAST_USER_MISSIONS_PATH)
    Response getLastUserMissions(@QueryParam(value = "userID") String userID) throws Exception;

    /**
     * @param missione
     * @return {@link Response}
     * @throws Exception
     */
    @POST
    @Path(value = BaseServiceRSPathConfig.ADD_MISSIONE_PATH)
    Response addMissione(Missione missione) throws Exception;

    /**
     * @param missione
     * @return {@link Response}
     * @throws Exception
     */
    @PUT
    @Path(value = BaseServiceRSPathConfig.UPDATE_MISSIONE_PATH)
    Response updateMissione(Missione missione) throws Exception;

    /**
     * @param missioneID
     * @return {@link Response}
     * @throws Exception
     */
    @DELETE
    @Path(value = BaseServiceRSPathConfig.DELETE_MISSIONE_PATH)
    Response deleteMissione(@QueryParam(value = "missioneID") String missioneID) throws Exception;

    /**
     * @param request
     * @return {@link Response}
     * @throws Exception
     */
    @POST
    @Path(value = BaseServiceRSPathConfig.NOTIFY_MISSIONE_ADMINISTRATION_PATH)
    Response notifyMissionAdministration(NotificationMissionRequest request) throws Exception;
}
