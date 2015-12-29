package it.cnr.missioni.dropwizard.delegate.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Salvia Vito
 *
 */
@Configuration
class UserDelegateConfig {

    @Bean
    public IUserDelegate userDelegate() {
        return new UserDelegate();
    }
}
