package it.cnr.missioni.dashboard.action.admin;

import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.action.IAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.User;
import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class AdminUpdateUserAction implements IAction {

    private User user;

    public AdminUpdateUserAction(User user) {
        this.user = user;
    }

    public boolean doAction() {

        try {
            user.setDateLastModified(new DateTime());
            ClientConnector.updateUser(user);
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
