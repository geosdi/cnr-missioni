package it.cnr.missioni.rest.api.path.base;

import it.cnr.missioni.rest.api.path.ApplicationServiceRSPathConfig;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public final class BaseServiceRSPathConfig extends ApplicationServiceRSPathConfig {

    /**
     * Missioni Service Path
     */
    public static final String MISSIONI_PATH = "/missioni";
    public static final String GET_MISSIONE_BY_ID_PATH = MISSIONI_PATH + "/getMissioneByID";
    public static final String GET_LAST_USER_MISSIONS_PATH = MISSIONI_PATH + "/getLastUserMissions";
    public static final String ADD_MISSIONE_PATH = MISSIONI_PATH + "/addMissione";
    public static final String UPDATE_MISSIONE_PATH = MISSIONI_PATH + "/updateMissione";
    public static final String DELETE_MISSIONE_PATH = MISSIONI_PATH + "/deleteMissione";

    private BaseServiceRSPathConfig() {
    }
}
