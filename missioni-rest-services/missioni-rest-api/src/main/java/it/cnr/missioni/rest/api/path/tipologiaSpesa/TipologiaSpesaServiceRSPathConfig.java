package it.cnr.missioni.rest.api.path.tipologiaSpesa;

import it.cnr.missioni.rest.api.path.ApplicationServiceRSPathConfig;

/**
 * 
 * @author Salvia Vito
 *
 */
public final class TipologiaSpesaServiceRSPathConfig extends ApplicationServiceRSPathConfig {

    /**
     * TIPOLOGIA SPESA SERVICE PATH
     */
    public static final String TIPOLOGIA_SPESA_PATH = "/tipologiaSpesa";
    public static final String GET_TIPOLIGIA_SPESA_BY_QUERY = TIPOLOGIA_SPESA_PATH
            + "/getTipologiaSpesaByQuery";
    public static final String ADD_TIPOLIGIA_SPESA_PATH = TIPOLOGIA_SPESA_PATH + "/addTipologiaSpesa";
    public static final String UPDATE_TIPOLOGIA_SPESA_PATH = TIPOLOGIA_SPESA_PATH + "/updateTipologiaSpesa";
    public static final String DELETE_TIPOLIOGIA_SPESA_PATH = TIPOLOGIA_SPESA_PATH + "/deleteTipologiaSpesa";

    private TipologiaSpesaServiceRSPathConfig() {
    }
}
