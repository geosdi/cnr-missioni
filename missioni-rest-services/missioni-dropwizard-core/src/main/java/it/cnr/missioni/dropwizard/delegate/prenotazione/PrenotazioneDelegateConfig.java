package it.cnr.missioni.dropwizard.delegate.prenotazione;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Salvia Vito
 *
 */
@Configuration
class PrenotazioneDelegateConfig {

    @Bean
    public IPrenotazioneDelegate prenotazioneDelegate() {
        return new PrenotazioneDelegate();
    }
}
