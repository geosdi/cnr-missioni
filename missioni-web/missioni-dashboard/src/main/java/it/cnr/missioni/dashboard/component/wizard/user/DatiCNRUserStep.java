package it.cnr.missioni.dashboard.component.wizard.user;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.action.UpdateUserAction;
import it.cnr.missioni.dashboard.component.form.user.DatiCNRUserForm;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.DatiCNR;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class DatiCNRUserStep implements WizardStep {

	private User user;
	private DatiCNRUserForm datiCNRTabLayout;

	public String getCaption() {
		return "Step 4";
	}

	public DatiCNRUserStep(User user) {
		this.user = user;
		this.datiCNRTabLayout = new DatiCNRUserForm(user, false,true,false);

	}

	public Component getContent() {
		return this.datiCNRTabLayout;
	}

	public boolean onAdvance() {
		try {
			DatiCNR datiCNR = datiCNRTabLayout.validate();
			if (datiCNR != null)
				user.setDatiCNR(datiCNR);
			DashboardEventBus.post(new UpdateUserAction(user));
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
