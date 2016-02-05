package it.cnr.missioni.dashboard.action.admin;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.action.IAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.RimborsoKmSearchBuilder;
import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;

/**
 * @author Salvia Vito
 */
public class RimborsoKmAction implements IAction {

	private RimborsoKm rimborsoKm;
	private boolean modifica;
	
	public RimborsoKmAction(RimborsoKm rimborsoKm ,boolean modifica ){
		this.rimborsoKm =  rimborsoKm;
		this.modifica = modifica;
	}


	public boolean doAction() {

		try {
			
			if(modifica)
				ClientConnector.updateRimborsoKm(rimborsoKm);
			else
				ClientConnector.addRimborsoKm(rimborsoKm);
			Thread.sleep(1000);
			
			
			Utility.getNotification(Utility.getMessage("success_message"),null,
					Type.HUMANIZED_MESSAGE);
				
			//ricarica i rimborsi km
			RimborsoKmStore rimborsoKmStore = ClientConnector.getRimborsoKm(RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder());
			DashboardEventBus.post(new  DashboardEvent.TableRimborsoKmUpdatedEvent(rimborsoKmStore));
			DashboardEventBus.post(new  DashboardEvent.DisableButtonNewEvent());

			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
