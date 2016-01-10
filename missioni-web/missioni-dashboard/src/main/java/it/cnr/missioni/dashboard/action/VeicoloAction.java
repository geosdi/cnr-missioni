package it.cnr.missioni.dashboard.action;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.ElencoVeicoliTable;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
public class VeicoloAction implements IAction {

	private Veicolo veicolo;
	private boolean modifica;
	
	public VeicoloAction(Veicolo veicolo, boolean modifica ){
		this.veicolo =  veicolo;
		this.modifica = modifica;
	}


	public boolean doAction() {

		try {
			
			User user =  (User)VaadinSession.getCurrent().getAttribute(User.class.getName());
			
			user.getMappaVeicolo().put(veicolo.getTarga(), veicolo);
			ClientConnector.updateUser(user);
			VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
			Utility.getNotification(Utility.getMessage("success_message"), (!modifica) ? Utility.getMessage("insert_veicolo_success"):Utility.getMessage("modifica_veicolo_success"),
					Type.HUMANIZED_MESSAGE);

			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
