package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.model.missione.DatiMissioneEstera;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class TipoMissioneStep implements WizardStep {

	private OptionGroup optionGroup;
	private HorizontalLayout mainLayout;;

	private Missione missione;

	public String getCaption() {
		return "Step 1";
	}

	public TipoMissioneStep(Missione missione ) {
		this.missione = missione;

	}

	public Component getContent() {
		return buildPanel();
	}

	public void bindFieldGroup() {


	}

	private Component buildPanel() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Tipo Missione");
		root.setIcon(FontAwesome.GEARS);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);
		
		
		optionGroup = new OptionGroup("Tipo Missione");
		optionGroup.addItems("Italia", "Estera");

		if (missione.isMissioneEstera())
			optionGroup.select("Estera");
		else
			optionGroup.select("Italia");
		details.addComponent(optionGroup);
		
		return root;
	}

	@Override
	public boolean onAdvance() {
		missione.setMissioneEstera(optionGroup.getValue().equals("Italia") ? false : true);
		
		//reset dei valori in caos in cui si scelga prima missione estera e poi si cambi
		if(!missione.isMissioneEstera()){
			missione.setIdNazione(null);
			missione.setShortDescriptionNazione(null);
			missione.setDatiMissioneEstera(new DatiMissioneEstera());
		}
		
		return true;
	}

	public boolean onBack() {
		return true;
	}

	public HorizontalLayout getMainLayout() {
		return mainLayout;
	}

}
