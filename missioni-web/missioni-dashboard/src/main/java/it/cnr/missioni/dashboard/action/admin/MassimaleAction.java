package it.cnr.missioni.dashboard.action.admin;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.action.IAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

/**
 * @author Salvia Vito
 */
public class MassimaleAction implements IAction {

	private Massimale massimale;
	private boolean modifica;
	
	public MassimaleAction(Massimale massimale ,boolean modifica ){
		this.massimale =  massimale;
		this.modifica = modifica;
	}


	public boolean doAction() {

		try {
			
			if(modifica)
				ClientConnector.updateMassimale(massimale);
			else
				ClientConnector.addMassimale(massimale);
			Thread.sleep(1000);
			
			
			Utility.getNotification(Utility.getMessage("success_message"),null,
					Type.HUMANIZED_MESSAGE);
				
			//ricarico  i massimale
			MassimaleStore massimaleStore = ClientConnector.getMassimale(MassimaleSearchBuilder.getMassimaleSearchBuilder());
			DashboardEventBus.post(new  DashboardEvent.TableMassimaleUpdatedEvent(massimaleStore) );
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
