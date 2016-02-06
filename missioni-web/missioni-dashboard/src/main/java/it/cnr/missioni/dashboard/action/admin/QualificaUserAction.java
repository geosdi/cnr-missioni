package it.cnr.missioni.dashboard.action.admin;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.action.IAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.QualificaUser;

/**
 * @author Salvia Vito
 */
public class QualificaUserAction implements IAction {

	private QualificaUser qualificaUser;
	private boolean modifica;
	
	public QualificaUserAction(QualificaUser qualificaUser ,boolean modifica ){
		this.qualificaUser =  qualificaUser;
		this.modifica = modifica;
	}


	public boolean doAction() {

		try {
			
			if(modifica)
				ClientConnector.updateQualificaUser(qualificaUser);
			else
				ClientConnector.addQualificaUser(qualificaUser);
			Thread.sleep(1000);
			
			
			Utility.getNotification(Utility.getMessage("success_message"),null,
					Type.HUMANIZED_MESSAGE);
				
			DashboardEventBus.post(new  DashboardEvent.TableQualificaUserUpdatedEvent() );
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
