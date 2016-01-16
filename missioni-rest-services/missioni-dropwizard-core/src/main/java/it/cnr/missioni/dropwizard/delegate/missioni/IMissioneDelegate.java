package it.cnr.missioni.dropwizard.delegate.missioni;

import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface IMissioneDelegate {

    /**
     * @param missioneID
     * @return {@link Missione}
     * @throws Exception
     */
	MissioniStore getMissioneByQuery(String idMissione,String idUser,String stato, String numeroOrdineRimborso) throws Exception;

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
     * @param id
     * @throws Exception
     */
    Boolean deleteMissione(String missioneID) throws Exception;
}
