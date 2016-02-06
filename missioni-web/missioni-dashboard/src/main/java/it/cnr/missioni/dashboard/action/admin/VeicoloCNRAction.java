package it.cnr.missioni.dashboard.action.admin;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.action.IAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;

/**
 * @author Salvia Vito
 */
public class VeicoloCNRAction implements IAction {

	private VeicoloCNR veicoloCNR;
	private boolean modifica;
	
	public VeicoloCNRAction(VeicoloCNR veicoloCNR ,boolean modifica ){
		this.veicoloCNR =  veicoloCNR;
		this.modifica = modifica;
	}


	public boolean doAction() {

		try {
			
			if(modifica)
				ClientConnector.updateVeicoloCNR(veicoloCNR);
			else
				ClientConnector.addVeicoloCNR(veicoloCNR);
			Thread.sleep(1000);
			
			
			Utility.getNotification(Utility.getMessage("success_message"),null,
					Type.HUMANIZED_MESSAGE);
				
			DashboardEventBus.post(new  DashboardEvent.TableVeicoliCNRUpdatedEvent() );
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
