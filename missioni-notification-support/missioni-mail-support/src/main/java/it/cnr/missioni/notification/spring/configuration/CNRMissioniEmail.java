package it.cnr.missioni.notification.spring.configuration;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface CNRMissioniEmail extends InitializingBean {

    /**
     * @return {@link String}
     */
    String getEmail();

    default String emailInfo() {
        return getClass().getSimpleName() + " {" +
                "email='" + getEmail() + '\'' +
                '}';
    }
}
