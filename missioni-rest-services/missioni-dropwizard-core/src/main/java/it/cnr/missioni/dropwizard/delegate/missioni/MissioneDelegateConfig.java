package it.cnr.missioni.dropwizard.delegate.missioni;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Configuration
class MissioneDelegateConfig {

    @Bean
    public IMissioneDelegate missioneDelegate() {
        return new MissioneDelegate();
    }
}
