package it.cnr.missioni.rest.api.path.user;

import it.cnr.missioni.rest.api.path.ApplicationServiceRSPathConfig;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public final class UsersServiceRSPathConfig extends ApplicationServiceRSPathConfig {

    /**
     * USERS SERVICE PATH
     */
    public static final String USERS_PATH = "/users";
    public static final String AUTHORIZE_USER_PATH = USERS_PATH + "/authorize";
    public static final String GET_USER_BY_QUERY = USERS_PATH
            + "/getUserByQuery";
    public static final String ADD_USER_PATH = USERS_PATH + "/addUser";
    public static final String UPDATE_USER_PATH = USERS_PATH + "/updateUser";
    public static final String DELETE_USER_PATH = USERS_PATH + "/deleteUser";
    public static final String RECUPERA_PASSWORD_PATH = USERS_PATH + "/recuperaPassword";
    public static final String GET_USER_BY_USERNAME = USERS_PATH + "/getUserByUsername";



    private UsersServiceRSPathConfig() {
    }
}
