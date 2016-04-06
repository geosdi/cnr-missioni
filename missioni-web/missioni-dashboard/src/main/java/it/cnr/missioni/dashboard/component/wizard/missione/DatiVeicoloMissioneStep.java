package it.cnr.missioni.dashboard.component.wizard.missione;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.component.form.missione.IVeicoloMissioneForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class DatiVeicoloMissioneStep implements WizardStep {

    private Missione missione;
    private IVeicoloMissioneForm veicoloMissioneForm;

    public DatiVeicoloMissioneStep(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
        this.missione = missione;
//		this.veicoloMissioneForm = new VeicoloMissioneForm(missione, isAdmin, enabled,modifica);
        this.veicoloMissioneForm = IVeicoloMissioneForm.VeicoloMissioneForm.getVeicoloMissioneForm().withBean(missione).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
    }

    public String getCaption() {
        return "step 4";
    }

    public Component getContent() {
        return veicoloMissioneForm;
    }

    public boolean onAdvance() {
        try {
            missione = veicoloMissioneForm.validate();

            return true;
        } catch (InvalidValueException | CommitException e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
                    Type.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean onBack() {
        return true;
    }

}
