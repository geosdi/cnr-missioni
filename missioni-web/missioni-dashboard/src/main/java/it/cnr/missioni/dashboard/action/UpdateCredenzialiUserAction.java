package it.cnr.missioni.dashboard.action;

import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.support.builder.generator.IMd5PasswordGenerator;

/**
 * @author Salvia Vito
 */
public class UpdateCredenzialiUserAction implements IAction {

    private User user;

    public UpdateCredenzialiUserAction(User user) {
        this.user = user;
    }

    public boolean doAction() {
        try {
        	user.getCredenziali().setPassword((IMd5PasswordGenerator.Md5PasswordGenerator.getMd5PasswordGenerator().withPassword(user.getCredenziali().getPassword()).build()));
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
