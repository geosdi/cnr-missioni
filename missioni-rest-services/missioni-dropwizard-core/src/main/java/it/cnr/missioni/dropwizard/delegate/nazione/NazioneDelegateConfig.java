package it.cnr.missioni.dropwizard.delegate.nazione;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Salvia Vito
 *
 */
@Configuration
class NazioneDelegateConfig {

    @Bean
    public INazioneDelegate nazioneDelegate() {
        return new NazioneDelegate();
    }
}
