package it.cnr.missioni.dropwizard.config;

import java.io.Serializable;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class CNRMissioniConfigApp implements Serializable {

    private static final long serialVersionUID = -5911971948201200568L;
    //
    private String applicationName;

    public CNRMissioniConfigApp() {
    }

    /**
     * @return {@link String}
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * @param applicationName
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                " applicationName = '" + applicationName + '\'' +
                '}';
    }
}
