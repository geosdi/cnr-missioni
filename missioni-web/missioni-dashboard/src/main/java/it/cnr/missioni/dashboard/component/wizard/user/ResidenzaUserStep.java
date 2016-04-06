package it.cnr.missioni.dashboard.component.wizard.user;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.component.form.user.IResidenzaUserForm;
import it.cnr.missioni.dashboard.component.form.user.IResidenzaUserForm.ResidenzaUserForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.Residenza;
import it.cnr.missioni.model.user.User;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class ResidenzaUserStep implements WizardStep {

    private User user;
    private IResidenzaUserForm residenzaTabLayout;

    public ResidenzaUserStep(User user) {
        this.user = user;
//		this.residenzaTabLayout = new ResidenzaUserForm(user,false, true,false);
        this.residenzaTabLayout = ResidenzaUserForm.getResidenzaUserForm()
                .withBean(user.getResidenza())
                .withIsAdmin(false)
                .withEnabled(true)
                .withModifica(false).build();
    }

    public String getCaption() {
        return "Step 2";
    }

    public Component getContent() {
        return this.residenzaTabLayout;
    }

    public boolean onAdvance() {
        try {
            Residenza residenza = residenzaTabLayout.validate();
            if (residenza != null)
                user.setResidenza(residenza);
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
