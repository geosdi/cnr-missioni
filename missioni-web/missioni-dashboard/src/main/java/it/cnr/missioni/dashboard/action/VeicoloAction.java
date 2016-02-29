package it.cnr.missioni.dashboard.action;

import java.util.UUID;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
public class VeicoloAction implements IAction {

	private Veicolo veicolo;
	private boolean modifica;
	
	public VeicoloAction(Veicolo veicolo,boolean modifica ){
		this.veicolo =  veicolo;
		this.modifica = modifica;
	}


	public boolean doAction() {

		try {
			
			if(!modifica)
				veicolo.setId(UUID.randomUUID().toString());
			
			User user =  DashboardUI.getCurrentUser();
			
			//se settato come veicolo principale aggiorno tutti i veicoli presenti a NON principale
			if(veicolo.isVeicoloPrincipale()){
				for(Veicolo v : user.getMappaVeicolo().values())
					if(!v.getTarga().equals(veicolo.getTarga()))
						v.setVeicoloPrincipale(false);
			}

			
			//se inserisco un veicolo iniziale sarà di default principale
			if(user.getMappaVeicolo().isEmpty())
				veicolo.setVeicoloPrincipale(true);
			
			user.getMappaVeicolo().put(veicolo.getId(), veicolo);
			ClientConnector.updateUser(user);
			VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
			Utility.getNotification(Utility.getMessage("success_message"),null,
					Type.HUMANIZED_MESSAGE);
			DashboardEventBus.post(new  DashboardEvent.TableVeicoliUpdatedEvent() );
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
