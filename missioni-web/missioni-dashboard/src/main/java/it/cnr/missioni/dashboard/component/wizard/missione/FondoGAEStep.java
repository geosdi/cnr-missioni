package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.component.form.missione.FondoGAEMissioneForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class FondoGAEStep implements WizardStep {


	private Missione missione;
	private FondoGAEMissioneForm fondoGAEMissioneForm;

	public String getCaption() {
		return "Step 3";
	}

	public FondoGAEStep(Missione missione,boolean isAdmin,boolean enabled,boolean modifica) {
		this.missione = missione;
		this.fondoGAEMissioneForm = new FondoGAEMissioneForm(missione, isAdmin, enabled,modifica);

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
