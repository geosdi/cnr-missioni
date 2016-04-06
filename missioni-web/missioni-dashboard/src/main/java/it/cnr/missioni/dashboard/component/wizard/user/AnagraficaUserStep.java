package it.cnr.missioni.dashboard.component.wizard.user;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.component.form.user.IAnagraficaUserForm;
import it.cnr.missioni.dashboard.component.form.user.IAnagraficaUserForm.AnagraficaUserForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.Anagrafica;
import it.cnr.missioni.model.user.User;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class AnagraficaUserStep implements WizardStep {

    private User user;
    private IAnagraficaUserForm anagraficaForm;

    public AnagraficaUserStep(User user) {
        this.user = user;
//		this.anagraficaForm = AnagraficaUserForm(user, false,true,false);
        this.anagraficaForm = AnagraficaUserForm.getAnagraficaUserForm().withBean(user.getAnagrafica()).withUser(user).withIsAdmin(false).withEnabled(true).withModifica(false).build();
    }

    public String getCaption() {
        return "Step 1";
    }

    public Component getContent() {
        return this.anagraficaForm;
    }

    public boolean onAdvance() {
        Anagrafica anagrafica;
        try {
            anagrafica = anagraficaForm.validate();
            if (anagrafica != null)
                user.setAnagrafica(anagrafica);
            return true;
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean onBack() {
        return true;
    }

}
