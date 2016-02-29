package it.cnr.missioni.dashboard.component.wizard.user;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.component.form.user.ResidenzaUserForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.Anagrafica;
import it.cnr.missioni.model.user.Residenza;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class ResidenzaUserStep implements WizardStep {

	private User user;
	private ResidenzaUserForm residenzaTabLayout;

	public String getCaption() {
		return "Step 2";
	}

	public ResidenzaUserStep(User user) {
		this.user = user;
		this.residenzaTabLayout = new ResidenzaUserForm(user,false, true,false);

	}

	public Component getContent() {
		return this.residenzaTabLayout;
	}

	public boolean onAdvance() {
		try {
			Residenza residenza = residenzaTabLayout.validate();
			if (residenza != null)
				user.setResidenza(residenza);
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
