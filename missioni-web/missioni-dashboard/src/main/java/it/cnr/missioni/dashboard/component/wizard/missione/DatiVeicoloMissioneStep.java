package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.component.form.missione.VeicoloMissioneForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class DatiVeicoloMissioneStep implements WizardStep {



	private Missione missione;
	private VeicoloMissioneForm veicoloMissioneForm;

	public String getCaption() {
		return "step 4";
	}

	public DatiVeicoloMissioneStep(Missione missione) {
		this.missione = missione;
		this.veicoloMissioneForm = new VeicoloMissioneForm(missione, false, true,false);

	}

	public Component getContent() {
		return veicoloMissioneForm;
	}






	public boolean onAdvance() {
		try{
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
