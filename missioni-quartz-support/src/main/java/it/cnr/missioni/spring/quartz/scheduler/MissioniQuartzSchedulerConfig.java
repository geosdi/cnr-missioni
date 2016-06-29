package it.cnr.missioni.spring.quartz.scheduler;

import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.geosdi.geoplatform.support.quartz.scheduler.GPQuartzSchedulerConfig;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Configuration
class MissioniQuartzSchedulerConfig implements GPQuartzSchedulerConfig {

    @GeoPlatformLog
    static Logger logger;

    @Bean(name = "missioniImaaQuartzScheduler", initMethod = "start",
            destroyMethod = "shutdown")
    @Override
    public Scheduler configureQuartzScheduler() throws SchedulerException {
        logger.debug("\n@@@@@@@@@@@@@@@@@@@INITIALIZING GP_QUARTZ_SCHEDULER "
                + "for {} Class\n", getClass().getSimpleName());

        return new StdSchedulerFactory().getScheduler();
    }
}
