package it.cnr.missioni.el.configurator;

import org.geosdi.geoplatform.experimental.el.configurator.GPBaseIndexConfigurator;
import org.geosdi.geoplatform.experimental.el.configurator.GPIndexConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Salvia Vito
 */
@Configuration
public class MissioniIndexConfiguratorConfig {

    @Bean(name = "missioniIndexConfigurator", initMethod = "configure")
    public static GPIndexConfigurator missioniIndexConfigurator() {
        return new GPBaseIndexConfigurator();
    }

}
