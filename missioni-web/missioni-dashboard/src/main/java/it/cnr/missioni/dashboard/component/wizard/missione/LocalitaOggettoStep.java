package it.cnr.missioni.dashboard.component.wizard.missione;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.component.form.missione.ILocalitaOggettoMissioneForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class LocalitaOggettoStep implements WizardStep {

    private Missione missione;
    private ILocalitaOggettoMissioneForm localitaOggettoMissioneForm;

    public LocalitaOggettoStep(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
        this.missione = missione;
//		this.localitaOggettoMissioneForm = new LocalitaOggettoMissioneForm(missione, isAdmin, enabled,modifica);
        this.localitaOggettoMissioneForm = ILocalitaOggettoMissioneForm.LocalitaOggettoMissioneForm.getDatiPeriodoMissioneForm().withBean(missione).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
    }

    public String getCaption() {
        return "Step 2";
    }

    public Component getContent() {
        this.localitaOggettoMissioneForm.setVisibleListaNazione(missione.isMissioneEstera());
        return this.localitaOggettoMissioneForm;
    }

    public boolean onAdvance() {
        try {
            missione = localitaOggettoMissioneForm.validate();
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
