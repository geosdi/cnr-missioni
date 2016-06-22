package it.cnr.missioni.dropwizard.delegate.missioni;

import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.request.NotificationMissionRequest;
import it.cnr.missioni.rest.api.response.geocoder.GeocoderStore;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import it.cnr.missioni.rest.api.response.missione.distance.DistanceResponse;

import javax.ws.rs.core.StreamingOutput;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface IMissioneDelegate {

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
	MissioniStore getMissioneByQuery(String idMissione, String idUser, String stato, Long numeroOrdineRimborso,
			Long dataFromMissione, Long dataToMissione, Long dataFromRimborso, Long dataToRimborso, String oggetto,
			String multiMatch, String fieldExist, String fieldNotExist,boolean rimborsoCompleted, int from, int size) throws Exception;

	/**
	 * @param userID
	 * @return {@link it.cnr.missioni.rest.api.response.missione.MissioniStore}
	 * @throws Exception
	 */
	MissioniStore getLastUserMissions(String userID) throws Exception;

	/**
	 * @param missione
	 * @return {@link String} ID Missione
	 * @throws Exception
	 */
	String addMissione(Missione missione) throws Exception;

	/**
	 * @param missione
	 * @throws Exception
	 */
	Boolean updateMissione(Missione missione) throws Exception;

	/**
	 * @param missioneID
	 * @throws Exception
	 */
	Boolean deleteMissione(String missioneID) throws Exception;

	/**
	 * @param request
	 * @return {@link Boolean}
	 * @throws Exception
	 */
	Boolean notifyMissionAdministration(NotificationMissionRequest request) throws Exception;

	/**
	 * @param request
	 * @return {@link Boolean}
	 * @throws Exception
	 */
	Boolean notifyRimborsoMissionAdministration(NotificationMissionRequest request) throws Exception;

	/**
	 * @param missione
	 * @return {@link Boolean}
	 * @throws Exception
	 */
	Boolean updateRimborso(Missione missione) throws Exception;

	/**
	 * @param missionID
	 * @return {@link StreamingOutput}
	 * @throws Exception
	 */
	StreamingOutput downloadMissioneAsPdf(String missionID) throws Exception;

	/**
	 * @param missionID
	 * @return {@link StreamingOutput}
	 * @throws Exception
	 */
	StreamingOutput downloadVeicoloMissioneAsPdf(String missionID) throws Exception;

	/**
	 * @param missionID
	 * @return {@link StreamingOutput}
	 * @throws Exception
	 */
	StreamingOutput downloadRimborsoMissioneAsPdf(String missionID) throws Exception;

	/**
	 * @param location
	 * @return {@link GeocoderStore}
	 * @throws Exception
	 */
	GeocoderStore getGeocoderStoreForMissioneLocation(String location) throws Exception;

	/**
	 * @param start
	 * @param end
	 * @return {@link DistanceResponse}
	 * @throws Exception
	 */
	DistanceResponse getDistanceForMissione(String start, String end) throws Exception;

	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	StatisticheMissioni getStatistiche() throws Exception;

	/**
	 * 
	 * @param missione
	 * @param modifica
	 * @return
	 * @throws Exception
	 */
	Boolean updateMissioneForAnticipo(Missione missione) throws Exception;

	/**
	 * @param missionID
	 * @return {@link StreamingOutput}
	 * @throws Exception
	 */
	StreamingOutput downloadAnticipoPagamentoAsPdf(String missionID) throws Exception;
}
