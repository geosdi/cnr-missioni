package it.cnr.missioni.dropwizard.delegate.massimale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Salvia Vito
 *
 */
@Configuration
class MassimaleDelegateConfig {

    @Bean
    public IMassimaleDelegate massimaleDelegate() {
        return new MassimaleDelegate();
    }
}
