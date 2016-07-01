package it.cnr.missioni.spring.quartz.task;

import org.geosdi.geoplatform.logger.support.annotation.GeoPlatformLog;
import org.geosdi.geoplatform.support.quartz.task.GPBaseJobTask;
import org.geosdi.geoplatform.support.quartz.task.exception.TaskException;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.cnr.missioni.el.dao.IMissioneDAO;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.notification.dispatcher.MissioniMailDispatcher;
import it.cnr.missioni.notification.message.factory.NotificationMessageFactory;
import it.cnr.missioni.notification.support.itext.user.UserMissionePDFBuilder;

import static it.cnr.missioni.spring.quartz.task.MissioniJobTaskKeyEnum.MISSIONI_KEY_TASK;

import java.util.List;

import javax.annotation.Resource;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Component(value = "missioniPresenceJobTask")
public class MissioniPresenceJobTask extends GPBaseJobTask {

    @GeoPlatformLog
    private Logger logger;
	@Resource(name = "missioneDAO")
	private IMissioneDAO missioneDAO;
	@Resource(name = "missioniMailDispatcher")
	private MissioniMailDispatcher missioniMailDispatcher;
	@Autowired
	private NotificationMessageFactory notificationMessageFactory;

    @Override
    public void run(JobExecutionContext jobExecutionContext) throws TaskException {
        try {
			List<Missione> lista = missioneDAO.getUsersInMissione();
			if(!lista.isEmpty()){
				missioniMailDispatcher.dispatchMessage(this.notificationMessageFactory.buildUsersInMissioneMessage(UserMissionePDFBuilder.newPDFBuilder().withMissioneList(lista)));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    }

    @Override
    protected JobTaskKey getJobTaskKey() {
        return MISSIONI_KEY_TASK.getValue();
    }
}
