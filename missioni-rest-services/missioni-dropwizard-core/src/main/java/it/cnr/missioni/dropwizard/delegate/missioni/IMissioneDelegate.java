package it.cnr.missioni.dropwizard.delegate.missioni;

import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.request.NotificationMissionRequest;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

import javax.ws.rs.core.StreamingOutput;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface IMissioneDelegate {
    
    /**
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
     * @param from
     * @param size
     * @return {@link Missione}
     * @throws Exception
     */
    MissioniStore getMissioneByQuery(String idMissione, String idUser, String stato, Long numeroOrdineRimborso,
            Long dataFromMissione, Long dataToMissione, Long dataFromRimborso, Long dataToRimborso, String oggetto, String multiMatch, String fieldExist, int from, int size) throws Exception;

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
    StreamingOutput downloadRimborsoMissioneAsPdf(String missionID) throws Exception;
}
