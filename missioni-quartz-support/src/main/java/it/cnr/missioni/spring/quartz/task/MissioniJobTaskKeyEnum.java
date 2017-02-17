package it.cnr.missioni.spring.quartz.task;

import org.geosdi.geoplatform.support.quartz.task.JobTask;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public enum MissioniJobTaskKeyEnum {

    MISSIONI_KEY_TASK(new JobTask.JobTaskKey("MISSIONI_QUARTZ_TASK_PRESENCE"));

    private final JobTask.JobTaskKey value;

    MissioniJobTaskKeyEnum(JobTask.JobTaskKey theValue) {
        this.value = theValue;
    }

    /**
     * @return the value
     */
    public JobTask.JobTaskKey getValue() {
        return value;
    }
}
