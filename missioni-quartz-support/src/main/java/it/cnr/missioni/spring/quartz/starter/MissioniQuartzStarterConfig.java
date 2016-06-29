package it.cnr.missioni.spring.quartz.starter;

import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Configuration
class MissioniQuartzStarterConfig {

    @GeoPlatformLog
    static Logger logger;

    @Bean(initMethod = "scheduleJob")
    public MissioniQuartzStarter missioniPresenceQuartzStarter(
            @Qualifier(value = "missioniImaaQuartzScheduler") Scheduler scheduler,
            @Qualifier(value = "missioniPresenceTriggerScheduler") Trigger missioniPresenceTriggerScheduler) {
        return () -> {

            try {
                scheduler.scheduleJob(missioniPresenceTriggerScheduler);
            } catch (SchedulerException se) {
                logger.error("################ERROR START MISSIONI_PRESENCE_QUARTZ SCHEDULER : {}\n\n",
                        se.getMessage());
                se.printStackTrace();
            }
        };
    }

    interface MissioniQuartzStarter {

        void scheduleJob();
    }
}
