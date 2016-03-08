package it.cnr.missioni.dashboard.component.form.tipologiaSpesa;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
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
	private ComboBox tipoField;
	private CheckBox checkMassimaleField;
	private TextField occorrenzaGiornalieraField;



	public TipologiaSpesaForm(TipologiaSpesa tipologiaSpesa,boolean isAdmin,boolean enabled,boolean modifica) {
		super(tipologiaSpesa,isAdmin,enabled,modifica);
		setFieldGroup(new BeanFieldGroup<TipologiaSpesa>(TipologiaSpesa.class));

		buildFieldGroup();
		buildTab();

	}


	public void buildTab() {

		addStyleName(ValoTheme.FORMLAYOUT_LIGHT);


		valueField = (TextField) getFieldGroup().buildAndBind("Tipologia Spesa", "value");
		tipoField = (ComboBox)getFieldGroup().buildAndBind("Tipo","tipo",ComboBox.class);
		checkMassimaleField = (CheckBox)getFieldGroup().buildAndBind("Massimale","checkMassimale",CheckBox.class);
		occorrenzaGiornalieraField = (TextField) getFieldGroup().buildAndBind("Occorrenza Giornaliera", "occorrenzaGiornaliera");

		addComponent(valueField);
		addComponent(tipoField);
		addComponent(checkMassimaleField);
		addComponent(occorrenzaGiornalieraField);

	}

	public TipologiaSpesa validate() throws CommitException,InvalidValueException{
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
		occorrenzaGiornalieraField.addValidator(new Validator(){

			@Override
			public void validate(Object value) throws InvalidValueException {
				int v = Integer.parseInt(value.toString());
				if(checkMassimaleField.getValue() && v <= 0)
					throw new InvalidValueException(Utility.getMessage("occorrenza_giornaliera_error"));

			}
			
		});		
	}

	/**
	 * 
	 */
	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		
	}

}
