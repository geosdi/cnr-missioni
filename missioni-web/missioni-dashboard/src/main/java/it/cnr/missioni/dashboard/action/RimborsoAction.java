package it.cnr.missioni.dashboard.action;

import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;

import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class RimborsoAction implements IAction {

    private Missione missione;
    private boolean admin;

    public RimborsoAction(Missione missione,boolean admin) {
        this.missione = missione;
        this.admin = admin;
    }

    public boolean doAction() {
        try {
            if (missione.getRimborso().getDataRimborso() == null)
                missione.getRimborso().setDataRimborso(new DateTime());
            if (missione.getRimborso().isPagata() && admin)
                missione.setStato(StatoEnum.PAGATA);
            missione.getRimborso().setDateLastModified(new DateTime());
            ClientConnector.updateMissione(missione);
            Thread.sleep(1000);
            DashboardEventBus.post(new DashboardEvent.TableRimborsiUpdatedEvent());
            //invia la mail nel caso in cui l'utente ha completato il rimborso e non sia ADMIN
            if(missione.isRimborsoCompleted() && !admin)
            	ClientConnector.sendRimborsoMail(missione.getId());
            Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
            return true;
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
            return false;
        }
    }
}
