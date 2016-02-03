package it.cnr.missioni.rest.api.path.massimale;

import it.cnr.missioni.rest.api.path.ApplicationServiceRSPathConfig;

/**
 * 
 * @author Salvia Vito
 *
 */
public final class MassimaleServiceRSPathConfig extends ApplicationServiceRSPathConfig {

    /**
     * MASSIMALE SERVICE PATH
     */
    public static final String MASSIMALE_PATH = "/massimale";
    public static final String GET_MASSIMALE_BY_QUERY = MASSIMALE_PATH
            + "/getMassimaleByQuery";
    public static final String ADD_MASSIMALE_PATH = MASSIMALE_PATH + "/addMassimale";
    public static final String UPDATE_MASSIMALE_PATH = MASSIMALE_PATH + "/updateMassimale";
    public static final String DELETE_MASSIMALE_PATH = MASSIMALE_PATH + "/deleteMassimale";

    private MassimaleServiceRSPathConfig() {
    }
}
