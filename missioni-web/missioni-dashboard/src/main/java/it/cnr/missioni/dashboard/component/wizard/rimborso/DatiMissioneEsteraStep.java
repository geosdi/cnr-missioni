package it.cnr.missioni.dashboard.component.wizard.rimborso;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.component.form.rimborso.IDatiPeriodoEsteraMissioneForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiMissioneEstera;
import it.cnr.missioni.model.missione.Missione;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class DatiMissioneEsteraStep implements WizardStep {


    private Missione missione;
    private IDatiPeriodoEsteraMissioneForm datiPeriodoEsteraMissioneForm;

    public DatiMissioneEsteraStep(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
        this.missione = missione;
        this.datiPeriodoEsteraMissioneForm = IDatiPeriodoEsteraMissioneForm.DatiPeriodoEsteraMissioneForm.getDatiPeriodoEsteraMissioneForm()
                .withBean(missione.getDatiMissioneEstera()).withModifica(modifica).withEnabled(enabled).withIsAdmin(isAdmin).withMissione(missione).build();
    }

    public String getCaption() {
        return "Step 2";
    }

    public Component getContent() {
        this.datiPeriodoEsteraMissioneForm.addDateRange();
        return this.datiPeriodoEsteraMissioneForm;
    }

    public boolean onAdvance() {

        DatiMissioneEstera datiMissioneEstera;
        try {
            if (missione.isMissioneEstera()) {
                datiMissioneEstera = datiPeriodoEsteraMissioneForm.validate();
                missione.setDatiMissioneEstera(datiMissioneEstera);
            } else {
                missione.setDatiMissioneEstera(null);
            }
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
