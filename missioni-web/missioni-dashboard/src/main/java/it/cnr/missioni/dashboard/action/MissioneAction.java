package it.cnr.missioni.dashboard.action;

import org.joda.time.DateTime;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.ElencoMissioniTable;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class MissioneAction implements IAction {

	private Missione missione;
	private boolean modifica;
	private ElencoMissioniTable elencoMissioniTable;
	
	public MissioneAction(Missione missione,boolean modifica,ElencoMissioniTable elencoMissioniTable ){
		this.missione =  missione;
		this.modifica = modifica;
		this.elencoMissioniTable = elencoMissioniTable;
	}


	public boolean doAction() {

		try {
			if(!modifica){
				User user = (User)VaadinSession.getCurrent().getAttribute(User.class);			
				missione.setIdUser(user.getId());
				missione.setDataInserimento(new DateTime());
				ClientConnector.addMissione(missione);
				Thread.sleep(1000);
			}else{
				missione.setDateLastModified(new DateTime());
				ClientConnector.updateMissione(missione);
				Thread.sleep(1000);
			}

			
			Utility.getNotification(Utility.getMessage("success_message"),null,
					Type.HUMANIZED_MESSAGE);

			DashboardEventBus.post(elencoMissioniTable);
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
