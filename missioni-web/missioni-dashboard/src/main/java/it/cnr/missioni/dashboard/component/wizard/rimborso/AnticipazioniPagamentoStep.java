package it.cnr.missioni.dashboard.component.wizard.rimborso;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.component.form.missione.IAnticipazionePagamentoMissioneForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiAnticipoPagamenti;
import it.cnr.missioni.model.missione.Missione;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class AnticipazioniPagamentoStep implements WizardStep {

    private DatiAnticipoPagamenti datiAnticipoPagamenti;
    private Missione missione;
    private IAnticipazionePagamentoMissioneForm anticipazionePagamentoMissioneForm;

    public AnticipazioniPagamentoStep(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
        this.datiAnticipoPagamenti = missione.getDatiAnticipoPagamenti();
        this.missione = missione;
//		this.anticipazionePagamentoMissioneForm = new AnticipazionePagamentoMissioneForm(datiAnticipoPagamenti, isAdmin,
//				enabled,modifica);
        this.anticipazionePagamentoMissioneForm = IAnticipazionePagamentoMissioneForm.AnticipazionePagamentoMissioneForm.getAnticipazionePagamentoMissioneForm().withBean(datiAnticipoPagamenti).withIsAdmin(isAdmin)
                .withEnabled(enabled).withModifica(modifica).build();
    }

    public String getCaption() {
        return "Step 7";
    }

    public Component getContent() {
        return this.anticipazionePagamentoMissioneForm;
    }

    public boolean onAdvance() {
        try {
            DatiAnticipoPagamenti new_datiAnticipoPagamenti = anticipazionePagamentoMissioneForm.validate();
            missione.setDatiAnticipoPagamenti(new_datiAnticipoPagamenti);
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
