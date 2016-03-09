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

	public FatturaRimborsoStep(Missione missione) {
		this.missione = missione;
		this.fatturaRimborsoForm = new FatturaRimborsoForm(missione,false,true,false);

	}

	public Component getContent() {
		return fatturaRimborsoForm;
	}


	public boolean onAdvance() {
		return true;
	}

	public boolean onBack() {
		return true;
	}

}
