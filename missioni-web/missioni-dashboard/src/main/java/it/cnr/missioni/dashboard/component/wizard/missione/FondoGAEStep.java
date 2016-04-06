package it.cnr.missioni.dashboard.component.wizard.missione;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.component.form.missione.IFondoGAEMissioneForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class FondoGAEStep implements WizardStep {

    private Missione missione;
    private IFondoGAEMissioneForm fondoGAEMissioneForm;

    public FondoGAEStep(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
        this.missione = missione;
//		this.fondoGAEMissioneForm = new IFondoGAEMissioneForm(missione, isAdmin, enabled,modifica);
        this.fondoGAEMissioneForm = IFondoGAEMissioneForm.FondoGAEMissioneForm.getFondoGAEMissioneForm().withBean(missione).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();

    }

    public String getCaption() {
        return "Step 3";
    }

    public Component getContent() {
        return this.fondoGAEMissioneForm;
    }

    public boolean onAdvance() {
        try {

            missione = fondoGAEMissioneForm.validate();
            return true;
        } catch (CommitException | InvalidValueException e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
                    Type.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean onBack() {
        return true;
    }

}
