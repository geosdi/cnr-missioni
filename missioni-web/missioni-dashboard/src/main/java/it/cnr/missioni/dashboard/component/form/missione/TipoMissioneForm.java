package it.cnr.missioni.dashboard.component.form.missione;

import com.vaadin.data.Validator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.OptionGroup;

import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiMissioneEstera;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;

/**
 * @author Salvia Vito
 */
public class TipoMissioneForm extends IForm.FormAbstract<Missione> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2817082336391488467L;

	private OptionGroup optionGroup;
	private ComboBox trattamentoMissioneEsteraField;

	public TipoMissioneForm(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
		super(missione, isAdmin, enabled, modifica);
		setFieldGroup(new BeanFieldGroup<Missione>(Missione.class));
		buildFieldGroup();
		buildTab();

	}

	public void buildTab() {

		trattamentoMissioneEsteraField = (ComboBox) getFieldGroup().buildAndBind("Trattamento Rimborso",
				"datiMissioneEstera.trattamentoMissioneEsteraEnum", ComboBox.class);
		optionGroup = new OptionGroup("Tipo Missione");
		optionGroup.addItems("Italia", "Estera");

		if (bean.isMissioneEstera()){
			optionGroup.select("Estera");
			trattamentoMissioneEsteraField.setValue(bean.getDatiMissioneEstera().getTrattamentoMissioneEsteraEnum());
		}
		else
			optionGroup.select("Italia");

		addComponent(optionGroup);
		addComponent(trattamentoMissioneEsteraField);

		optionGroup.setReadOnly(!enabled);
		addListener();
		visible(bean.isMissioneEstera());
		addValidator();
	}

	public Missione validate() throws CommitException, InvalidValueException {
		bean.setMissioneEstera(optionGroup.getValue().equals("Italia") ? false : true);

		// reset dei valori in caos in cui si scelga prima missione estera e poi
		// si cambi
		if (!bean.isMissioneEstera()) {
			bean.setIdNazione(null);
			bean.setShortDescriptionNazione(null);
			bean.setDatiMissioneEstera(new DatiMissioneEstera());
		}else{
			trattamentoMissioneEsteraField.setValidationVisible(true);
			trattamentoMissioneEsteraField.validate();
			bean.getDatiMissioneEstera().setTrattamentoMissioneEsteraEnum(TrattamentoMissioneEsteraEnum
					.getStatoEnum(trattamentoMissioneEsteraField.getValue().toString()));
		}
		return bean;
	}

	/**
	 * 
	 */
	@Override
	public void addValidator() {
		trattamentoMissioneEsteraField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 45054264794648534L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				TrattamentoMissioneEsteraEnum v = (TrattamentoMissioneEsteraEnum) value;
				if ((v == null) && optionGroup.getValue().equals("Estera")) {
					throw new InvalidValueException(Utility.getMessage("checkbox_missione_error"));
				}

			}

		});
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
				visible(optionGroup.getValue().equals("Estera"));

			}
		});
		
		trattamentoMissioneEsteraField.addBlurListener(new BlurListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7537242999340446064L;

			@Override
			public void blur(BlurEvent event) {
				try {
					trattamentoMissioneEsteraField.validate();
				} catch (Exception e) {
					trattamentoMissioneEsteraField.setValidationVisible(true);
				}

			}
		});

	}
	
	
	private void visible(boolean visible){
		trattamentoMissioneEsteraField.setEnabled(visible);
	}
	
}
