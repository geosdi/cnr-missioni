package it.cnr.missioni.dashboard.action;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.support.builder.generator.IMd5PasswordGenerator;
import it.cnr.missioni.support.builder.generator.IRandomPasswordGenerator;

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
        	String new_password = IRandomPasswordGenerator.RandomPasswordGenerator.getRandomPasswordGenerator().withLenght(8).withSeed(DateTime.now(DateTimeZone.UTC).toString().getBytes()).build();
            user.getCredenziali().setPassword(IMd5PasswordGenerator.Md5PasswordGenerator.getMd5PasswordGenerator().withPassword(new_password).build());
            ClientConnector.updateUser(user);
            ClientConnector.recuperePasswordMail(user.getAnagrafica().getNome(),user.getAnagrafica().getCognome(),user.getDatiCNR().getMail(),new_password);
            Utility.getNotification(Utility.getMessage("restore_password"), null,
                    Type.HUMANIZED_MESSAGE);
            return true;
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), null,
                    Type.ERROR_MESSAGE);
            return false;
        }
    }
}
