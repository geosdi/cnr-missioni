package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.component.form.missione.AnticipazionePagamentoMissioneForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiAnticipoPagamenti;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class AnticipazioniPagamentoStep implements WizardStep {

	private DatiAnticipoPagamenti datiAnticipoPagamenti;
	private Missione missione;
	private AnticipazionePagamentoMissioneForm anticipazionePagamentoMissioneForm;

	public String getCaption() {
		return "Step 7";
	}

	public AnticipazioniPagamentoStep(Missione missione) {
		this.datiAnticipoPagamenti = missione.getDatiAnticipoPagamenti();
		this.missione = missione;
		this.anticipazionePagamentoMissioneForm = new AnticipazionePagamentoMissioneForm(datiAnticipoPagamenti, false,
				true,false);
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
