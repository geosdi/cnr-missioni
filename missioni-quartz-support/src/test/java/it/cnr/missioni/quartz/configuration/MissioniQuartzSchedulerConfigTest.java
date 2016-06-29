package it.cnr.missioni.quartz.configuration;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */

import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.geosdi.geoplatform.support.quartz.configuration.trigger.GPCronTriggerConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextMissioni"
        + "-Quartz-Test.xml"})
public class MissioniQuartzSchedulerConfigTest {

    @GeoPlatformLog
    static Logger logger;
    //
    @Resource(name = "missioniImaaCronTriggerConfig")
    private GPCronTriggerConfiguration missioniImaaCronTriggerConfig;

    @Test
    public void missioniImaaQuartzCronTriggerTest() {
        logger.info("\n\n####################MISSIONI_IMAA_TRIGGER_CONFIG : "
                + "{}\n\n", missioniImaaCronTriggerConfig);
    }
}
