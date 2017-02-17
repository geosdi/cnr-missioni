package it.cnr.missioni.spring.quartz.configuration;

import org.geosdi.geoplatform.support.quartz.configuration.trigger.GPCronTriggerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Configuration
@ComponentScan(value = {"org.geosdi.geoplatform.support"
        + ".quartz.spring.placeholder"})
class MissioniCronTriggerConfig {

    @Bean
    public static GPCronTriggerConfiguration missioniImaaCronTriggerConfig() {
        return new MissioniCronTriggerConfiguration();
    }
}
