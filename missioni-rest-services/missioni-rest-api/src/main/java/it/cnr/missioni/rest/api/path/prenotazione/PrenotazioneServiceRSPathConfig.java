package it.cnr.missioni.rest.api.path.prenotazione;

import it.cnr.missioni.rest.api.path.ApplicationServiceRSPathConfig;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public final class PrenotazioneServiceRSPathConfig extends ApplicationServiceRSPathConfig {

    /**
     * PRENOTAZIONI SERVICE PATH
     */
    public static final String PRENOTAZIONI_PATH = "/prenotazioni";
    public static final String GET_PRENOTAZIONI_BY_QUERY = PRENOTAZIONI_PATH
            + "/getPrenotazioniByQuery";
    public static final String ADD_PRENOTAZIONE_PATH = PRENOTAZIONI_PATH + "/addPrenotazione";
    public static final String UPDATE_PRENOTAZIONE_PATH = PRENOTAZIONI_PATH + "/updatePrenotazione";
    public static final String DELETE_PRENOTAZIONE_PATH = PRENOTAZIONI_PATH + "/deletePrenotazione";

    private PrenotazioneServiceRSPathConfig() {
    }
}
