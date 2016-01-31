package it.cnr.missioni.dashboard.action;

import org.joda.time.DateTime;

import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

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

			// se inseriamo una nuova missione
			if (!modifica) {
				missione.setDataInserimento(new DateTime());
				if(missione.isMezzoProprio()){
					Veicolo v = DashboardUI.getCurrentUser().getVeicoloPrincipale();
					missione.setIdVeicolo(v.getTarga());
					missione.setShortDescriptionVeicolo(v.getTipo());
				}
				missione.setIdUser(DashboardUI.getCurrentUser().getId());
				missione.setDataInserimento(new DateTime());
				missione.setStato(StatoEnum.INSERITA);
				String id = ClientConnector.addMissione(missione);
				Thread.sleep(1000);
				ClientConnector.sendMissioneMail(id);
			} else {
				missione.setDateLastModified(new DateTime());
				ClientConnector.updateMissione(missione);
			}


			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);

			//ricarica tutte le missioni per aggiornare la table
			MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser(DashboardUI.getCurrentUser().getId());
			MissioniStore missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
			DashboardEventBus.post(new DashboardEvent.TableMissioniUpdateUpdatedEvent(missioniStore));
			
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
