package it.cnr.missioni.rest.api.path.rimborsoKm;

import it.cnr.missioni.rest.api.path.ApplicationServiceRSPathConfig;

/**
 * 
 * @author Salvia Vito
 *
 */
public final class RimborsoKmServiceRSPathConfig extends ApplicationServiceRSPathConfig {

    /**
     * RIMBORSO KM SERVICE PATH
     */
    public static final String RIMBORSO_KM_PATH = "/rimborsoKm";
    public static final String GET_RIMBORSO_KM_BY_QUERY = RIMBORSO_KM_PATH
            + "/getRimborsoKmByQuery";
    public static final String ADD_RIMBORSO_KM_PATH = RIMBORSO_KM_PATH + "/addRimborsoKm";
    public static final String UPDATE_RIMBORSO_KM_PATH = RIMBORSO_KM_PATH + "/updateRimborsoKm";
    public static final String DELETE_RIMBORSO_KM_PATH = RIMBORSO_KM_PATH + "/deleteRimborsoKm";

    private RimborsoKmServiceRSPathConfig() {
    }
}
