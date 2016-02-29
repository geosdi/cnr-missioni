package it.cnr.missioni.dashboard.component.form.user;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.model.user.Residenza;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class ResidenzaUserForm extends IForm.FormAbstract<Residenza> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6816875017679269273L;
	private TextField comuneField;
	private TextField indirizzoField;
	private TextField domicilioFiscaleField;


	public ResidenzaUserForm(User user,boolean isAdmin, boolean enabled,boolean modifica) {
		super(user.getResidenza(),isAdmin,enabled,modifica);
		this.bean = user.getResidenza();
		setFieldGroup(new BeanFieldGroup<Residenza>(Residenza.class));

		buildFieldGroup();
		buildTab();

	}

	public void buildTab() {

		comuneField = (TextField) getFieldGroup().buildAndBind("Comune", "comune");
		indirizzoField = (TextField) getFieldGroup().buildAndBind("Indirizzo", "indirizzo");
		domicilioFiscaleField = (TextField) getFieldGroup().buildAndBind("Domicilio Fiscale", "domicilioFiscale");

		addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

		addComponent(comuneField);
		addComponent(indirizzoField);
		addComponent(domicilioFiscaleField);

	}

	public Residenza validate() throws CommitException,InvalidValueException {
		for (Field<?> f : getFieldGroup().getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}
		getFieldGroup().commit();

		BeanItem<Residenza> beanItem = (BeanItem<Residenza>) getFieldGroup().getItemDataSource();
		Residenza residenza = beanItem.getBean();
		return residenza;

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
