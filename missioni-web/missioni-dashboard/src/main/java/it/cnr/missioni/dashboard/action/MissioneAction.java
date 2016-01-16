package it.cnr.missioni.dashboard.action;

import org.joda.time.DateTime;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.user.User;
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
			User user = (User) VaadinSession.getCurrent().getAttribute(User.class);
			missione.setDateLastModified(new DateTime());
			// se inseriamo una nuova missione
			if (!modifica) {

				missione.setIdUser(user.getId());
				missione.setDataInserimento(new DateTime());
				missione.setStato(StatoEnum.INSERITA);
				ClientConnector.addMissione(missione);
			} else {
				ClientConnector.updateMissione(missione);
			}

			Thread.sleep(1000);
			Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);

			//ricarica tutte le missioni per aggiornare la table
			MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder().withIdUser(user.getId());
			MissioniStore missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
			DashboardEventBus.post(new DashboardEvent.TableMissioniUpdatedEvent(missioniStore));
			
			return true;

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
			return false;
		}

	}

}
