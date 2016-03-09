package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.component.form.missione.LocalitaOggettoMissioneForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class LocalitaOggettoStep implements WizardStep {

	private Missione missione;
	private LocalitaOggettoMissioneForm localitaOggettoMissioneForm;

	public String getCaption() {
		return "Step 2";
	}

	public LocalitaOggettoStep(Missione missione,boolean isAdmin,boolean enabled,boolean modifica) {
		this.missione = missione;
		this.localitaOggettoMissioneForm = new LocalitaOggettoMissioneForm(missione, isAdmin, enabled,modifica);

	}

	public Component getContent() {
		this.localitaOggettoMissioneForm.setVisibleField(missione.isMissioneEstera());
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
