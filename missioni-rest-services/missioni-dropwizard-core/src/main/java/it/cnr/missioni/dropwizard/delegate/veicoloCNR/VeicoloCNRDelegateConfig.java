package it.cnr.missioni.dropwizard.delegate.veicoloCNR;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Salvia Vito
 *
 */
@Configuration
class VeicoloCNRDelegateConfig {

    @Bean
    public IVeicoloCNRDelegate veicoloCNRDelegate() {
        return new VeicoloCNRDelegate();
    }
}
