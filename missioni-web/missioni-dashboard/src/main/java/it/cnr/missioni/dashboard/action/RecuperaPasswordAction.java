package it.cnr.missioni.dashboard.action;

import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class RecuperaPasswordAction implements IAction {

    private String email;

    public RecuperaPasswordAction(String email) {
        this.email = email;
    }

    public boolean doAction() {
        try {
        	User user = ClientConnector.getUser(IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withMail(email)).getUsers().get(0);
            user.getCredenziali().setPassword(user.getCredenziali().md5hash(user.getCredenziali().getPassword()));
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
