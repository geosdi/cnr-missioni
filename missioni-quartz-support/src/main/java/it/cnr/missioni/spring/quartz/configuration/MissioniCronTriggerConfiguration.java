package it.cnr.missioni.spring.quartz.configuration;

import net.jcip.annotations.Immutable;
import org.geosdi.geoplatform.support.quartz.configuration.trigger.GPCronTriggerConfiguration;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Immutable
class MissioniCronTriggerConfiguration implements GPCronTriggerConfiguration {

    private static final long serialVersionUID = -3232996888063356500L;
    //
    @Value("gpQuartzConfigurator{missioniCronTriggerExpression:@null}")
    private String cronTriggerExpression;

    @Override
    public String getCronTriggerExpression() {
        return this.cronTriggerExpression = (((this.cronTriggerExpression != null)
                && !(this.cronTriggerExpression.isEmpty()))
                ? this.cronTriggerExpression : "0 20 9 * * ?");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {"
                + "cronTriggerExpression = " + getCronTriggerExpression() + '}';
    }
}
