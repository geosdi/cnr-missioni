package it.cnr.missioni.dashboard.component.wizard.user;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.component.form.user.IPatenteUserForm;
import it.cnr.missioni.dashboard.component.form.user.IPatenteUserForm.PatenteUserForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.Patente;
import it.cnr.missioni.model.user.User;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class PatenteUserStep implements WizardStep {

    private User user;
    private IPatenteUserForm patenteTabLayout;

    public PatenteUserStep(User user) {
        this.user = user;
//		this.patenteTabLayout = new PatenteUserForm(user, false,true,false);
        this.patenteTabLayout = PatenteUserForm.getPatenteUserForm()
                .withUser(user)
                .withBean(user.getPatente())
                .withIsAdmin(false)
                .withEnabled(true)
                .withModifica(false).build();
    }

    public String getCaption() {
        return "Step 3";
    }

    public Component getContent() {
        return this.patenteTabLayout;
    }

    public boolean onAdvance() {
        try {
            Patente patente = patenteTabLayout.validate();
            if (patente != null)
                user.setPatente(patente);
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
