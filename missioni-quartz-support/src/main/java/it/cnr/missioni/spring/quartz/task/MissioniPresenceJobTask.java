package it.cnr.missioni.spring.quartz.task;

import static it.cnr.missioni.spring.quartz.task.MissioniJobTaskKeyEnum.MISSIONI_KEY_TASK;

import org.apache.log4j.Logger;
import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.geosdi.geoplatform.support.quartz.task.GPBaseJobTask;
import org.geosdi.geoplatform.support.quartz.task.exception.TaskException;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Component(value = "missioniPresenceJobTask")
public class MissioniPresenceJobTask extends GPBaseJobTask {
	
    @GeoPlatformLog
    private Logger logger;

    @Override
    public void run(JobExecutionContext jobExecutionContext) throws TaskException {
    	logger.info("::::::::::::JOB");
    }

    @Override
    protected JobTaskKey getJobTaskKey() {
        return MISSIONI_KEY_TASK.getValue();
    }
}
