package it.cnr.missioni.rest.api.path.veicoloCNR;

import it.cnr.missioni.rest.api.path.ApplicationServiceRSPathConfig;

/**
 * 
 * @author Salvia Vito
 *
 */
public final class VeicoloCNRServiceRSPathConfig extends ApplicationServiceRSPathConfig {

    /**
     * VEICOLO CNR SERVICE PATH
     */
    public static final String VEICOLO_CNR_PATH = "/veicoloCNR";
    public static final String GET_VEICOLO_CNR_BY_QUERY = VEICOLO_CNR_PATH
            + "/getVeicoloCNRByQuery";
    public static final String ADD_VEICOLO_CNR_PATH = VEICOLO_CNR_PATH + "/addVeicoloCNR";
    public static final String UPDATE_VEICOLO_CNR_PATH = VEICOLO_CNR_PATH + "/updateVeicoloCNR";
    public static final String DELETE_VEICOLO_CNR_PATH = VEICOLO_CNR_PATH + "/deleteVeicoloCNR";

    private VeicoloCNRServiceRSPathConfig() {
    }
}
