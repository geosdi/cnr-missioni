package it.cnr.missioni.dashboard.component.wizard.rimborso;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.component.form.rimborso.DatiGeneraliRimborsoForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class DatiGeneraliRimborsoStep implements WizardStep {

	private final int days;
	private DatiGeneraliRimborsoForm datiGenerali;
	private Missione missione;

	public String getCaption() {
		return "Step 1";
	}

	public DatiGeneraliRimborsoStep(Missione missione, int days, boolean isAdmin,boolean enabled,boolean modifica) {
		this.missione = missione;
		this.days = days;
		datiGenerali = new DatiGeneraliRimborsoForm(missione, days, isAdmin,enabled,modifica);

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
