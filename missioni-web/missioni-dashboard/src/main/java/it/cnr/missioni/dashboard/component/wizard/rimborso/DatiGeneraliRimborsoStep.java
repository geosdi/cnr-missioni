package it.cnr.missioni.dashboard.component.wizard.rimborso;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.component.form.rimborso.IDatiGeneraliRimborsoForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class DatiGeneraliRimborsoStep implements WizardStep {

    private IDatiGeneraliRimborsoForm datiGenerali;
    private Missione missione;

    public DatiGeneraliRimborsoStep(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
        this.missione = missione;
        datiGenerali = IDatiGeneraliRimborsoForm.DatiGeneraliRimborsoForm.getDatiGeneraliRimborsoForm().withBean(missione.getRimborso()).withMissione(missione).withIsAdmin(isAdmin).
                withEnabled(enabled).withModifica(modifica).build();
    }

    public String getCaption() {
        return "Step 1";
    }

    public Component getContent() {
        return datiGenerali;
    }

    public boolean onAdvance() {
        try {
            missione.setRimborso(datiGenerali.validate());
        } catch (InvalidValueException | CommitException e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
                    Type.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean onBack() {
        return true;
    }

}
