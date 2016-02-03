package it.cnr.missioni.dropwizard.delegate.tipologiaSpesa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Salvia Vito
 *
 */
@Configuration
class TipologiaSpesaDelegateConfig {

    @Bean
    public ITipologiaSpesaDelegate tipologiaSpesaDelegate() {
        return new TipologiaSpesaDelegate();
    }
}
