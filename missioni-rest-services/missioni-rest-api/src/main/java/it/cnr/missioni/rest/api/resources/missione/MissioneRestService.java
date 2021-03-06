package it.cnr.missioni.rest.api.resources.missione;

import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.path.missione.MissioneServiceRSPathConfig;
import it.cnr.missioni.rest.api.request.NotificationMissionRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Path(value = MissioneServiceRSPathConfig.API_BASE_PATH)
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public interface MissioneRestService {

	/**
	 * 
	 * @param idMissione
	 * @param idUser
	 * @param stato
	 * @param numeroOrdineRimborso
	 * @param dataFromMissione
	 * @param dataToMissione
	 * @param dataFromRimborso
	 * @param dataToRimborso
	 * @param oggetto
	 * @param multiMatch
	 * @param fieldExist
	 * @param fieldNotExist
	 * @param rimborsoCompleted
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path(value = MissioneServiceRSPathConfig.GET_MISSIONE_BY_QUERY)
	Response getMissioneByQuery(@QueryParam(value = "idMissione") String idMissione,
			@QueryParam(value = "idUser") String idUser, @QueryParam(value = "stato") String stato,
			@QueryParam(value = "numeroOrdineRimborso") String numeroOrdineRimborso,
			@QueryParam(value = "dataFromMissione") Long dataFromMissione,
			@QueryParam(value = "dataToMissione") Long dataToMissione,
			@QueryParam(value = "dataFromRimborso") Long dataFromRimborso,
			@QueryParam(value = "dataToRimborso") Long dataToRimborso, @QueryParam(value = "oggetto") String oggetto,
			@QueryParam(value = "multiMatch") String multiMatch, @QueryParam(value = "fieldExist") String fieldExist,
			@QueryParam(value = "fieldNotExist") String fieldNotExist,@QueryParam(value = "rimborsoCompleted") boolean rimborsoCompleted, @QueryParam(value = "from") int from,
			@QueryParam(value = "size") int size) throws Exception;

	/**
	 * @param userID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@GET
	@Path(value = MissioneServiceRSPathConfig.GET_LAST_USER_MISSIONS_PATH)
	Response getLastUserMissions(@QueryParam(value = "userID") String userID) throws Exception;

	/**
	 * @param missione
	 * @return {@link Response}
	 * @throws Exception
	 */
	@POST
	@Path(value = MissioneServiceRSPathConfig.ADD_MISSIONE_PATH)
	Response addMissione(Missione missione) throws Exception;

	/**
	 * @param missione
	 * @return {@link Response}
	 * @throws Exception
	 */
	@PUT
	@Path(value = MissioneServiceRSPathConfig.UPDATE_MISSIONE_PATH)
	Response updateMissione(Missione missione) throws Exception;

	/**
	 * @param missioneID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@DELETE
	@Path(value = MissioneServiceRSPathConfig.DELETE_MISSIONE_PATH)
	Response deleteMissione(@QueryParam(value = "missioneID") String missioneID) throws Exception;

	/**
	 * @param request
	 * @return {@link Response}
	 * @throws Exception
	 */
	@POST
	@Path(value = MissioneServiceRSPathConfig.NOTIFY_MISSIONE_ADMINISTRATION_PATH)
	Response notifyMissionAdministration(NotificationMissionRequest request) throws Exception;

	/**
	 * @param missione
	 * @return {@link Response}
	 * @throws Exception
	 */
	@PUT
	@Path(value = MissioneServiceRSPathConfig.UPDATE_RIMBORSO)
	Response updateRimborso(Missione missione) throws Exception;

	/**
	 * @param request
	 * @return {@link Response}
	 * @throws Exception
	 */
	@POST
	@Path(value = MissioneServiceRSPathConfig.NOTIFY_RIMBORSO_MISSIONE_ADMINISTRATION_PATH)
	Response notifyRimborsoMissionAdministration(NotificationMissionRequest request) throws Exception;

	/**
	 * @param missionID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@GET
	@Path(value = MissioneServiceRSPathConfig.DOWNLOAD_MISSIONE_AS_PDF_PATH)
	@Produces(value = "application/pdf")
	Response downloadMissioneAsPdf(@QueryParam(value = "missionID") String missionID) throws Exception;

	/**
	 * @param missionID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@GET
	@Path(value = MissioneServiceRSPathConfig.DOWNLOAD_RIMBORSO_MISSIONE_AS_PDF_PATH)
	@Produces(value = "application/pdf")
	Response downloadRimborsoMissioneAsPdf(@QueryParam(value = "missionID") String missionID) throws Exception;

	/**
	 * @param missionID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@GET
	@Path(value = MissioneServiceRSPathConfig.DOWNLOAD_VEICOLO_MISSIONE_AS_PDF_PATH)
	@Produces(value = "application/pdf")
	Response downloadVeicoloMissioneAsPdf(@QueryParam(value = "missionID") String missionID) throws Exception;

	/**
	 * @param location
	 * @return {@link Response}
	 * @throws Exception
	 */
	@GET
	@Path(value = MissioneServiceRSPathConfig.GET_GEOCODER_STORE_FOR_MISSIONE_LOCATION_PATH)
	Response getGeocoderStoreForMissioneLocation(@QueryParam(value = "location") String location) throws Exception;

	/**
	 * @param start
	 * @param end
	 * @return {@link Response}
	 * @throws Exception
	 */
	@GET
	@Path(value = MissioneServiceRSPathConfig.GET_DISTANCE_FOR_MISSIONE_PATH)
	Response getDistanceForMissione(@QueryParam(value = "start") String start, @QueryParam(value = "end") String end)
			throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path(value = MissioneServiceRSPathConfig.GET_STATISTICHE)
	Response getStatistiche() throws Exception;

	/**
	 * 
	 * @param missione
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path(value = MissioneServiceRSPathConfig.UPDATE_MISSIONE_FOR_ANTICIPO_PATH)
	Response updateMissioneForAnticipo(Missione missione) throws Exception;

	/**
	 * @param missionID
	 * @return {@link Response}
	 * @throws Exception
	 */
	@GET
	@Path(value = MissioneServiceRSPathConfig.DOWNLOAD_ANTICIPO_PAGAMENTO_AS_PDF_PATH)
	@Produces(value = "application/pdf")
	Response downloadAnticipoPagamentoAsPdf(@QueryParam(value = "missionID") String missionID) throws Exception;
	
	/**
	 * @param start
	 * @param end
	 * @return {@link Response}
	 * @throws Exception
	 */
	@GET
	@Path(value = MissioneServiceRSPathConfig.GET_NEW_DISTANCE_FOR_MISSIONE_PATH)
	Response getNewDistanceForMissione(@QueryParam(value = "start") String start, @QueryParam(value = "end") String end)
			throws Exception;
}
