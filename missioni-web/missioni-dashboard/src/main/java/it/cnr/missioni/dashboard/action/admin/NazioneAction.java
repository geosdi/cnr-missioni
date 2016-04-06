package it.cnr.missioni.dashboard.action.admin;

import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.action.IAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.Nazione;

/**
 * @author Salvia Vito
 */
public class NazioneAction implements IAction {

    private Nazione nazione;
    private boolean modifica;

    public NazioneAction(Nazione nazione, boolean modifica) {
        this.nazione = nazione;
        this.modifica = modifica;
    }


    public boolean doAction() {
        try {
            if (modifica)
                ClientConnector.updateNazione(nazione);
            else
                ClientConnector.addNazione(nazione);
            Thread.sleep(1000);
            Utility.getNotification(Utility.getMessage("success_message"), null,
                    Type.HUMANIZED_MESSAGE);
            DashboardEventBus.post(new DashboardEvent.TableNazioneUpdatedEvent());
            return true;

        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
            return false;
        }
    }
}
