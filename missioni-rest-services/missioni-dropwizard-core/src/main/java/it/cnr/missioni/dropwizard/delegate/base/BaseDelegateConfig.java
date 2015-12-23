package it.cnr.missioni.dropwizard.delegate.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Configuration
class BaseDelegateConfig {

    @Bean
    public BaseDelegate cnrBaseBaseDelegate() {
        return new CNRBaseDelegate();
    }
}
