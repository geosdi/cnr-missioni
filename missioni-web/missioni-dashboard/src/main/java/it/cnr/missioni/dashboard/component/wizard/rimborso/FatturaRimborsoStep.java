package it.cnr.missioni.dashboard.component.wizard.rimborso;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.ui.Component;

import it.cnr.missioni.dashboard.component.form.rimborso.FatturaRimborsoForm;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class FatturaRimborsoStep implements WizardStep {


	private Missione missione;
	private FatturaRimborsoForm fatturaRimborsoForm;

	public String getCaption() {
		return "Step 3";
	}

	public FatturaRimborsoStep(Missione missione,boolean isAdmin,boolean enabled,boolean modifica) {
		this.missione = missione;
		this.fatturaRimborsoForm = new FatturaRimborsoForm(missione,isAdmin,enabled,modifica);
	}

	public Component getContent() {
		this.fatturaRimborsoForm .setRangeDate();
		return fatturaRimborsoForm;
	}


	public boolean onAdvance() {
		return true;
	}

	public boolean onBack() {
		return true;
	}

}
