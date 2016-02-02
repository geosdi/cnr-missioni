package it.cnr.missioni.dropwizard.delegate.rimborsoKm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Salvia Vito
 *
 */
@Configuration
class RimborsoKmDelegateConfig {

    @Bean
    public IRimborsoKmDelegate rimborsoKmDelegate() {
        return new RimborsoKmDelegate();
    }
}
