package it.cnr.missioni.dashboard.component.form.tipologiaspesa;

import com.vaadin.data.Validator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.TipologiaSpesa;

/**
 * @author Salvia Vito
 */
public class TipologiaSpesaForm extends IForm.FormAbstract<TipologiaSpesa> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1486887627905963349L;

	private TextField valueField;
	private ComboBox voceSpesaField;
	private CheckBox esteraField;
	private CheckBox italiaField;
	private CheckBox checkDataField;


	public TipologiaSpesaForm(TipologiaSpesa tipologiaSpesa, boolean isAdmin, boolean enabled, boolean modifica) {
		super(tipologiaSpesa, isAdmin, enabled, modifica);
		setFieldGroup(new BeanFieldGroup<TipologiaSpesa>(TipologiaSpesa.class));

		buildFieldGroup();
		buildTab();

	}

	public void buildTab() {

		addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

		valueField = (TextField) getFieldGroup().buildAndBind("Tipologia Spesa", "value");
		esteraField = (CheckBox) getFieldGroup().buildAndBind("Estera", "estera", CheckBox.class);
		italiaField = (CheckBox) getFieldGroup().buildAndBind("Italia", "italia", CheckBox.class);
		voceSpesaField = (ComboBox) getFieldGroup().buildAndBind("Voce Spesa", "voceSpesa", ComboBox.class);
		checkDataField = (CheckBox) getFieldGroup().buildAndBind("Data Antecedente", "checkData", CheckBox.class);

		addComponent(valueField);
		addComponent(voceSpesaField);
		addComponent(esteraField);
		addComponent(italiaField);
		addComponent(checkDataField);

		addValidator();
		addListener();

	}

	public TipologiaSpesa validate() throws CommitException, InvalidValueException {
		for (Field<?> f : getFieldGroup().getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}

		getFieldGroup().commit();
		BeanItem<TipologiaSpesa> beanItem = (BeanItem<TipologiaSpesa>) getFieldGroup().getItemDataSource();
		TipologiaSpesa tipologiaSpesa = beanItem.getBean();
		return tipologiaSpesa;
	}

	/**
	 * 
	 */
	@Override
	public void addValidator() {

		italiaField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6962580320805030378L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (!esteraField.getValue() && !italiaField.getValue())
					throw new InvalidValueException(Utility.getMessage("estera_italia_error"));

			}

		});

		esteraField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6962580320805030378L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (!esteraField.getValue() && !italiaField.getValue())
					throw new InvalidValueException(Utility.getMessage("estera_italia_error"));

			}

		});

	}

	/**
	 * 
	 */
	@Override
	public void addListener() {
		
		
		italiaField.addValueChangeListener(new ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 8492201471237752790L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				if(italiaField.getValue()){
					italiaField.setValidationVisible(false);
					esteraField.setValidationVisible(false);
				}
				else{
					italiaField.setValidationVisible(true);
					esteraField.setValidationVisible(true);
				}

			}
		});
		
		esteraField.addValueChangeListener(new ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -9169555307266198718L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				if(esteraField.getValue()){
					italiaField.setValidationVisible(false);
					esteraField.setValidationVisible(false);
				}
				else{
					italiaField.setValidationVisible(true);
					esteraField.setValidationVisible(true);
				}

			}
		});
		
		
		italiaField.addBlurListener(new BlurListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8492201471237752790L;

			@Override
			public void blur(BlurEvent event) {
				try {
					italiaField.validate();
				} catch (Exception e) {
					italiaField.setValidationVisible(true);
				}

			}
		});

		esteraField.addBlurListener(new BlurListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -9169555307266198718L;

			@Override
			public void blur(BlurEvent event) {
				try {
					esteraField.validate();
				} catch (Exception e) {
					esteraField.setValidationVisible(true);
				}

			}
		});
	}

}
