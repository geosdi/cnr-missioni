package it.cnr.missioni.spring.quartz.trigger;

import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.geosdi.geoplatform.support.quartz.configuration.trigger.GPCronTriggerConfiguration;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static it.cnr.missioni.spring.quartz.jobs.MissioniGroupJobType.MISSIONI_PRESENCE_GROUP;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Configuration
class MissioniPresenceTriggerSchedulerConfig {

    @GeoPlatformLog
    static Logger logger;

    @Bean
    public Trigger missioniPresenceTriggerScheduler(
            @Qualifier(value = "missioniPresenceJobDetailScheduler") JobDetail missioniPresenceJobDetailScheduler,
            @Qualifier(value = "missioniImaaCronTriggerConfig") GPCronTriggerConfiguration missioniImaaCronTriggerConfig) {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@CONFIGURING "
                        + "MISSIONI_PRESENCE_TRIGGER_SCHEDULER with {}\n\n",
                missioniImaaCronTriggerConfig);

        return newTrigger().withIdentity(new TriggerKey(
                "MissioniPresenceQuartzScheduler", MISSIONI_PRESENCE_GROUP.toString()))
                .withDescription("Run Missioni Scheduler Job with Cron Expression : "
                        + missioniImaaCronTriggerConfig.getCronTriggerExpression())
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(missioniImaaCronTriggerConfig.getCronTriggerExpression()))
                .forJob(missioniPresenceJobDetailScheduler)
                .build();
    }
}
