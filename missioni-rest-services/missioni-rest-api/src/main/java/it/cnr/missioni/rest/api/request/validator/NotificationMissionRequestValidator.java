package it.cnr.missioni.rest.api.request.validator;

import it.cnr.missioni.model.validator.ICNRMissionValidator;
import it.cnr.missioni.rest.api.request.NotificationMissionRequest;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class NotificationMissionRequestValidator extends ICNRMissionValidator.CNRMissionValidator<NotificationMissionRequest> {

    /**
     * @return {@link String}
     */
    @Override
    public String getValidatorName() {
        return "NOTIFICATION_MISSION_REQUEST_VALIDATOR";
    }
}
