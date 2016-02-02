package it.cnr.missioni.rest.api.path.nazione;

import it.cnr.missioni.rest.api.path.ApplicationServiceRSPathConfig;

/**
 * 
 * @author Salvia Vito
 *
 */
public final class NazioneServiceRSPathConfig extends ApplicationServiceRSPathConfig {

    /**
     * NAZIONE SERVICE PATH
     */
    public static final String NAZIONE_PATH = "/nazione";
    public static final String GET_NAZIONE_BY_QUERY = NAZIONE_PATH
            + "/getNazioneByQuery";
    public static final String ADD_NAZIONE_PATH = NAZIONE_PATH + "/addNazione";
    public static final String UPDATE_NAZIONE_PATH = NAZIONE_PATH + "/updateNazione";
    public static final String DELETE_NAZIONE_PATH = NAZIONE_PATH + "/deleteNazione";

    private NazioneServiceRSPathConfig() {
    }
}
