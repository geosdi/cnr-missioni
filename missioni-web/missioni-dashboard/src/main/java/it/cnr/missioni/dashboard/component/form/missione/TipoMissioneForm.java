package it.cnr.missioni.dashboard.component.form.missione;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.OptionGroup;

import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.event.DashboardEvent.AggiornaDatiMissioneEsteraEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.missione.DatiMissioneEstera;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class TipoMissioneForm extends IForm.FormAbstract<Missione> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2817082336391488467L;

	private OptionGroup optionGroup;

	public TipoMissioneForm(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
		super(missione, isAdmin, enabled, modifica);
		buildTab();

	}

	public void buildTab() {

		optionGroup = new OptionGroup("Tipo Missione");
		optionGroup.addItems("Italia", "Estera");

		if (bean.isMissioneEstera())
			optionGroup.select("Estera");
		else
			optionGroup.select("Italia");

		addComponent(optionGroup);
		optionGroup.setReadOnly(!enabled);
		if(modifica)
			addListener();

	}

	public Missione validate() throws CommitException, InvalidValueException {
		bean.setMissioneEstera(optionGroup.getValue().equals("Italia") ? false : true);

		// reset dei valori in caos in cui si scelga prima missione estera e poi
		// si cambi
		if (!bean.isMissioneEstera()) {
			bean.setIdNazione(null);
			bean.setShortDescriptionNazione(null);
			bean.setDatiMissioneEstera(new DatiMissioneEstera());
		}
		return bean;
	}

	/**
	 * 
	 */
	@Override
	public void addValidator() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	@Override
	public void addListener() {
		optionGroup.addValueChangeListener(new ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -5506401574945154781L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				bean.setMissioneEstera(optionGroup.getValue().equals("Estera") ? true : false);
				DashboardEventBus.post(new AggiornaDatiMissioneEsteraEvent(bean.isMissioneEstera()));	
					
			}
		});

}
}
