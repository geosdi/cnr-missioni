package it.cnr.missioni.spring.quartz.jobs;

import org.geosdi.geoplatform.support.quartz.jobs.GPBaseJob;
import org.geosdi.geoplatform.support.quartz.task.exception.TaskException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static it.cnr.missioni.spring.quartz.task.MissioniJobTaskKeyEnum.MISSIONI_KEY_TASK;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class MissioniJobPresence extends GPBaseJob {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            super.getJobTask(MISSIONI_KEY_TASK.getValue()).run(jobExecutionContext);
        } catch (TaskException te) {
            logger.error("\n@@@@@@@@@@@@@@@@@@Task Exception : {} - "
                            + "for Trigger : {}\n", te.getMessage(),
                    jobExecutionContext.getTrigger().getKey());
        }
    }
}
