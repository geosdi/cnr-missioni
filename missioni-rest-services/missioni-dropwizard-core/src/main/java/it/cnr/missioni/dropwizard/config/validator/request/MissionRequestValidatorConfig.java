package it.cnr.missioni.dropwizard.config.validator.request;

import it.cnr.missioni.model.validator.ICNRMissionValidator;
import it.cnr.missioni.rest.api.request.NotificationMissionRequest;
import it.cnr.missioni.rest.api.request.validator.NotificationMissionRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Configuration
class MissionRequestValidatorConfig {

    @Bean
    public ICNRMissionValidator<NotificationMissionRequest, String> notificationMissionRequestValidator() {
        return new NotificationMissionRequestValidator();
    }
}
