package it.cnr.missioni.dashboard.component.form.nazione;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.model.configuration.Nazione;

/**
 * @author Salvia Vito
 */
public class NazioneForm extends IForm.FormAbstract<Nazione> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5674917270638827694L;

	private TextField valueField;
	private ComboBox areagGeograficaField;


	public NazioneForm(Nazione nazione,boolean isAdmin,boolean enabled,boolean modifica) {
		super(nazione,isAdmin,enabled,modifica);
		setFieldGroup(new BeanFieldGroup<Nazione>(Nazione.class));
		buildFieldGroup();
		buildTab();

	}


	public void  buildTab() {
		valueField = (TextField) getFieldGroup().buildAndBind("Nazione", "value");
		addComponent(valueField);
		areagGeograficaField = (ComboBox) getFieldGroup().buildAndBind("Area Geografica", "areaGeografica", ComboBox.class);
		addComponent(areagGeograficaField);
	}

	public Nazione validate() throws CommitException,InvalidValueException {
		for (Field<?> f : getFieldGroup().getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}
		getFieldGroup().commit();

		BeanItem<Nazione> beanItem = (BeanItem<Nazione>) getFieldGroup().getItemDataSource();
		Nazione nazione = beanItem.getBean();
		return nazione;
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
		// TODO Auto-generated method stub
		
	}

}
