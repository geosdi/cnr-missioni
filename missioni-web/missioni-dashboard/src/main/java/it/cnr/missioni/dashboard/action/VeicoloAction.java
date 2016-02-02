package it.cnr.missioni.dashboard.action;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification.Type;

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
	private String oldTarga;
	
	public VeicoloAction(Veicolo veicolo ,String oldTarga ){
		this.veicolo =  veicolo;
		this.oldTarga = oldTarga;
	}


	public boolean doAction() {

		try {
			
			User user =  (User)VaadinSession.getCurrent().getAttribute(User.class.getName());
			
			//se settato come veicolo principale aggiorno tutti i veicoli presenti a NON principale
			if(veicolo.isVeicoloPrincipale()){
				for(Veicolo v : user.getMappaVeicolo().values())
					if(!v.getTarga().equals(veicolo.getTarga()))
						v.setVeicoloPrincipale(false);
			}
			
			//se cambia la targa, anche la key nell' HashMap cambia. Elimino la vecchia key
			if(!veicolo.getTarga().equals(oldTarga)){
				user.getMappaVeicolo().remove(oldTarga);
			}
			
			//se inserisco un veicolo iniziale sarà di default principale
			if(user.getMappaVeicolo().isEmpty())
				veicolo.setVeicoloPrincipale(true);
			
			user.getMappaVeicolo().put(veicolo.getTarga().toUpperCase(), veicolo);
			ClientConnector.updateUser(user);
			VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
			Utility.getNotification(Utility.getMessage("success_message"),null,
					Type.HUMANIZED_MESSAGE);
			DashboardEventBus.post(new  DashboardEvent.TableVeicoliUpdatedEvent(new ArrayList<Veicolo>(user.getMappaVeicolo().values())) );
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
