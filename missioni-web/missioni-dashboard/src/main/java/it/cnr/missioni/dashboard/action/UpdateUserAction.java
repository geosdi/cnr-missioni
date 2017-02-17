package it.cnr.missioni.dashboard.action;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.NotificationsCountUpdatedEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.notification.DashboardNotification;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.User;
import org.joda.time.DateTime;

import java.util.Collection;

/**
 * @author Salvia Vito
 */
public class UpdateUserAction implements IAction {

    private User user;

    public UpdateUserAction(User user) {
        this.user = user;
    }

    public boolean doAction() {
        try {
            boolean value = user.isRegistrazioneCompletata();
            user.setRegistrazioneCompletata(true);
            user.setDateLastModified(new DateTime());
            user.getCredenziali().setPassword(user.getCredenziali().getPassword());
            ClientConnector.updateUser(user);
            VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
            DashboardEventBus.post(new DashboardEvent.ProfileUpdatedEvent());
            if (!value) {
                DashboardEventBus.post(new DashboardEvent.MenuUpdateEvent());
                DashboardEventBus.post(new NotificationsCountUpdatedEvent());
            }
            Utility.getNotification(Utility.getMessage("success_message"), null,
                    Type.HUMANIZED_MESSAGE);
            return true;
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), null,
                    Type.ERROR_MESSAGE);
            return false;
        }
    }
}
