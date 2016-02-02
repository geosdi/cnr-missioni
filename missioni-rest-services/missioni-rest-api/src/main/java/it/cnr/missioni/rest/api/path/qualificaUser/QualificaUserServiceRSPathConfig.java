package it.cnr.missioni.rest.api.path.qualificaUser;

import it.cnr.missioni.rest.api.path.ApplicationServiceRSPathConfig;

/**
 * 
 * @author Salvia Vito
 *
 */
public final class QualificaUserServiceRSPathConfig extends ApplicationServiceRSPathConfig {

    /**
     * QUALIFICA USER SERVICE PATH
     */
    public static final String QUALIFICA_USER_PATH = "/qualificaUser";
    public static final String GET_QUALIFICA_USER_BY_QUERY = QUALIFICA_USER_PATH
            + "/getQualificaUserByQuery";
    public static final String ADD_QUALIFICA_USER_PATH = QUALIFICA_USER_PATH + "/addQualificaUser";
    public static final String UPDATE_QUALIFICA_USER_PATH = QUALIFICA_USER_PATH + "/updateQualificaUser";
    public static final String DELETE_QUALIFICA_PATH = QUALIFICA_USER_PATH + "/deleteQualificaUser";

    private QualificaUserServiceRSPathConfig() {
    }
}
