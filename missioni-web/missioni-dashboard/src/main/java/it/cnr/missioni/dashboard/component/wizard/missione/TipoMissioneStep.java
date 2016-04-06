package it.cnr.missioni.dashboard.component.wizard.missione;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.component.form.missione.ITipoMissioneForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import org.vaadin.teemu.wizards.WizardStep;

/**
 * @author Salvia Vito
 */
public class TipoMissioneStep implements WizardStep {

    private HorizontalLayout mainLayout;
    private ITipoMissioneForm tipoMissioneForm;
    private Missione missione;

    public TipoMissioneStep(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
        this.missione = missione;
//		this.tipoMissioneForm = new TipoMissioneForm(missione, isAdmin,enabled,modifica);
        this.tipoMissioneForm = ITipoMissioneForm.TipoMissioneForm.getTipoMissioneForm().withBean(missione).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
    }

    public String getCaption() {
        return "Step 1";
    }

    public Component getContent() {
        return this.tipoMissioneForm;
    }

    @Override
    public boolean onAdvance() {

        try {
            missione = this.tipoMissioneForm.validate();
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

    public HorizontalLayout getMainLayout() {
        return mainLayout;
    }

}
