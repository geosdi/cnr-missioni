package it.cnr.missioni.dashboard.component.wizard.missione;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.component.form.missione.IDatiPeriodoMissioneForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class DatiPeriodoMissioneStep implements WizardStep {

    private DatiPeriodoMissione datiPeriodoMissione;
    private Missione missione;
    private IDatiPeriodoMissioneForm datiPeriodoMissioneForm;

    public DatiPeriodoMissioneStep(DatiPeriodoMissione datiPeriodoMissione, Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
        this.datiPeriodoMissione = datiPeriodoMissione;
        this.missione = missione;
//		datiPeriodoMissioneForm = new DatiPeriodoMissioneForm(datiPeriodoMissione, isAdmin, enabled,modifica);
        datiPeriodoMissioneForm = IDatiPeriodoMissioneForm.DatiPeriodoMissioneForm.getDatiPeriodoMissioneForm().withBean(datiPeriodoMissione).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();

    }

    public String getCaption() {
        return "Step 5";
    }

    public Component getContent() {
        return datiPeriodoMissioneForm;
    }

    public boolean onAdvance() {
        DatiPeriodoMissione periodoMissione;
        try {
            periodoMissione = datiPeriodoMissioneForm.validate();
            missione.setDatiPeriodoMissione(periodoMissione);
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
