package it.cnr.missioni.dropwizard.delegate.base;

import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.response.base.MissioniStore;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface BaseDelegate {

    /**
     * @param missioneID
     * @return {@link Missione}
     * @throws Exception
     */
    Missione getMissioneByID(String missioneID) throws Exception;

    /**
     * @param userID
     * @return {@link it.cnr.missioni.rest.api.response.base.MissioniStore}
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
