package it.cnr.missioni.dashboard.component.wizard.user;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.component.form.user.AnagraficaUserForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.Anagrafica;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class AnagraficaUserStep implements WizardStep {

	private User user;
	private AnagraficaUserForm anagraficaForm;

	public String getCaption() {
		return "Step 1";
	}

	public AnagraficaUserStep(User user) {
		this.user = user;
		this.anagraficaForm = new AnagraficaUserForm(user, false,true,false);

	}

	public Component getContent() {
		return this.anagraficaForm;
	}

	public boolean onAdvance() {
		Anagrafica anagrafica;
		try {
			anagrafica = anagraficaForm.validate();
			if (anagrafica != null)
				user.setAnagrafica(anagrafica);
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

}
