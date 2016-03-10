package it.cnr.missioni.dashboard.component.form.rimborsokm;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.model.configuration.RimborsoKm;

/**
 * @author Salvia Vito
 */
public class RimborsoKmForm extends IForm.FormAbstract<RimborsoKm> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1341421621645150748L;

	private TextField valueField;

	public RimborsoKmForm(RimborsoKm rimborsoKm,boolean isAdmin,boolean enabled,boolean modifica) {
		super(rimborsoKm,isAdmin,enabled,modifica);
		setFieldGroup(new BeanFieldGroup<RimborsoKm>(RimborsoKm.class));

		buildFieldGroup();
		buildTab();

	}

	public void buildTab() {
		addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		valueField = (TextField) getFieldGroup().buildAndBind("Valore", "value");
		addComponent(valueField);

	}

	public RimborsoKm validate() throws CommitException,InvalidValueException{
		for (Field<?> f : getFieldGroup().getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}
		getFieldGroup().commit();

		BeanItem<RimborsoKm> beanItem = (BeanItem<RimborsoKm>) getFieldGroup().getItemDataSource();
		RimborsoKm rimborsoKm = beanItem.getBean();
		return rimborsoKm;
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
