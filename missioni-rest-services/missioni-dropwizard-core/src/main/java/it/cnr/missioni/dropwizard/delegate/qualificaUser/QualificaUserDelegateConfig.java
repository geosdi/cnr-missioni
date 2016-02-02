package it.cnr.missioni.dropwizard.delegate.qualificaUser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Salvia Vito
 *
 */
@Configuration
class QualificaUserDelegateConfig {

    @Bean
    public IQualificaUserDelegate qualificaUserDelegate() {
        return new QualificaUserDelegate();
    }
}
