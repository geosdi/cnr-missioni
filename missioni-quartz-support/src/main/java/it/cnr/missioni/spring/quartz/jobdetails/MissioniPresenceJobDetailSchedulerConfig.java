package it.cnr.missioni.spring.quartz.jobdetails;

import it.cnr.missioni.spring.quartz.jobs.MissioniJobPresence;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static it.cnr.missioni.spring.quartz.jobs.MissioniGroupJobType.MISSIONI_PRESENCE_GROUP;
import static org.quartz.JobBuilder.newJob;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Configuration
class MissioniPresenceJobDetailSchedulerConfig {

    @Bean
    public JobDetail missioniPresenceJobDetailScheduler(@Qualifier(value = "missioniImaaQuartzScheduler") Scheduler scheduler)
            throws SchedulerException {
        JobDetail missioniPresenceJobDetailScheduler = newJob(MissioniJobPresence.class)
                .withIdentity(MissioniJobPresence.class.getSimpleName(),
                        MISSIONI_PRESENCE_GROUP.toString())
                .withDescription("Try to fit people is in mission.")
                .storeDurably()
                .requestRecovery()
                .build();
        scheduler.addJob(missioniPresenceJobDetailScheduler, Boolean.FALSE);
        return missioniPresenceJobDetailScheduler;
    }
}
