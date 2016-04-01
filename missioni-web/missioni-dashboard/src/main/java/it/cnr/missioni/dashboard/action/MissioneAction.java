package it.cnr.missioni.dashboard.action;

import org.joda.time.DateTime;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
public class MissioneAction implements IAction {

	private Missione missione;
	private boolean modifica;

	public MissioneAction(Missione missione, boolean modifica) {
		this.missione = missione;
		this.modifica = modifica;
	}

	public boolean doAction() {

		try {
			missione.setDateLastModified(new DateTime());
			// se inseriamo una nuova missione
			if (!modifica) {
				missione.setDataInserimento(new DateTime());
				if(missione.isMezzoProprio()){
					Veicolo v = DashboardUI.getCurrentUser().getVeicoloPrincipale();
					missione.setIdVeicolo(v.getId());
					missione.setShortDescriptionVeicolo(v.getTipo());
				}
				missione.setIdUser(DashboardUI.getCurrentUser().getId());
				missione.setDataInserimento(new DateTime());
				missione.setStato(StatoEnum.INSERITA);
				missione.setShortUser(DashboardUI.getCurrentUser().getAnagrafica().getCognome()+ " "+DashboardUI.getCurrentUser().getAnagrafica().getNome());
				String id = ClientConnector.addMissione(missione);
				ClientConnector.sendMissioneMail(id);
			} else {
				ClientConnector.updateMissione(missione);
			}

			Thread.sleep(1000);
			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
			DashboardEventBus.post(new DashboardEvent.TableMissioniUpdateEvent());
			
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
