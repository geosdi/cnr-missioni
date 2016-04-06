package it.cnr.missioni.dashboard.action;

import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.RuoloUserEnum;
import it.cnr.missioni.model.user.User;
import org.joda.time.DateTime;

/**
 * @author Salvia Vito
 */
public class UserRegistrationAction implements IAction {

    private User user;

    public UserRegistrationAction(User user) {
        this.user = user;
    }

    public boolean doAction() {
        try {
            user.setRegistrazioneCompletata(false);
            user.setDataRegistrazione(new DateTime());
            user.setDateLastModified(new DateTime());
            user.getCredenziali().setPassword(user.getCredenziali().md5hash(user.getCredenziali().getPassword()));
            user.getCredenziali().setRuoloUtente(RuoloUserEnum.UTENTE_SEMPLICE);
            ClientConnector.addUser(user);
            Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
            return true;
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
            return false;
        }
    }
}
