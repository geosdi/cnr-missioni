package it.cnr.missioni.dashboard.component.wizard.user;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.action.UpdateUserAction;
import it.cnr.missioni.dashboard.component.form.user.IDatiCNRUserForm;
import it.cnr.missioni.dashboard.component.form.user.IDatiCNRUserForm.DatiCNRUserForm;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.DatiCNR;
import it.cnr.missioni.model.user.User;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class DatiCNRUserStep implements WizardStep {

    private User user;
    private IDatiCNRUserForm datiCNRTabLayout;

    public DatiCNRUserStep(User user) {
        this.user = user;
        this.datiCNRTabLayout = DatiCNRUserForm.getDatiCNRUserForm().withBean(user.getDatiCNR())
                .withUser(user).withIsAdmin(false).withEnabled(true).withModifica(false).build();
    }

    public String getCaption() {
        return "Step 4";
    }

    public Component getContent() {
        return this.datiCNRTabLayout;
    }

    public boolean onAdvance() {
        try {
            DatiCNR datiCNR = datiCNRTabLayout.validate();
            if (datiCNR != null)
                user.setDatiCNR(datiCNR);
            DashboardEventBus.post(new UpdateUserAction(user));
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
