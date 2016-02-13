package it.cnr.missioni.dashboard.component.wizard.rimborso;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.ui.Component;

import it.cnr.missioni.dashboard.component.tab.rimborso.FatturaTabLayout;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class FatturaRimborsoStep implements WizardStep {


	private Missione missione;

	public String getCaption() {
		return "Step 2";
	}

	public FatturaRimborsoStep(Missione missione) {
		this.missione = missione;

	}

	public Component getContent() {
		return new FatturaTabLayout(missione,true);
	}


	public boolean onAdvance() {
		return true;
	}

	public boolean onBack() {
		return true;
	}

}
