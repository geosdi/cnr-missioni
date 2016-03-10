package it.cnr.missioni.dashboard.component.wizard.rimborso;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.checkmassimale.CheckMassimale;
import it.cnr.missioni.dashboard.component.form.rimborso.FatturaRimborsoForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;

/**
 * @author Salvia Vito
 */
public class FatturaRimborsoStep implements WizardStep {

	private Missione missione;
	private FatturaRimborsoForm fatturaRimborsoForm;

	public String getCaption() {
		return "Step 3";
	}

	/*
	 * Per ogni fattura verifica il massimale
	 */
	private void checkMassimale(Rimborso rimborso) {

		Map<String, Fattura> mappa = new HashMap<String, Fattura>();

		rimborso.getMappaFattura().values().forEach(f -> {
			String id = f.getId();
			if (!mappa.containsKey(id)) {
				try {
					CheckMassimale checkMassimale = new CheckMassimale(missione, f, missione.getRimborso(), mappa);
					checkMassimale.initialize();

				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}

			} else
				mappa.put(f.getId(), f);
		});

	}

	public FatturaRimborsoStep(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
		this.missione = missione;
		this.fatturaRimborsoForm = new FatturaRimborsoForm(missione, isAdmin, enabled, modifica);
	}

	public Component getContent() {
		this.fatturaRimborsoForm.setRangeDate();
		return fatturaRimborsoForm;
	}

	public boolean onAdvance() {
		checkMassimale(missione.getRimborso());
		return true;
	}

	public boolean onBack() {
		return true;
	}

}
