package it.cnr.missioni.dashboard.component.wizard.missione;

import java.util.Date;

import org.joda.time.DateTime;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.form.missione.DatiPeriodoMissioneForm;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class DatiPeriodoMissioneStep implements WizardStep {

	private DatiPeriodoMissione datiPeriodoMissione;
	private Missione missione;
	private DatiPeriodoMissioneForm datiPeriodoMissioneForm;

	public String getCaption() {
		return "Step 5";
	}

	public DatiPeriodoMissioneStep(DatiPeriodoMissione datiPeriodoMissione, Missione missione) {
		this.datiPeriodoMissione = datiPeriodoMissione;
		this.missione = missione;
		datiPeriodoMissioneForm = new DatiPeriodoMissioneForm(datiPeriodoMissione, false, true,false);

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
