package it.cnr.missioni.dashboard.component.form.qualificaUser;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.model.configuration.QualificaUser;

/**
 * @author Salvia Vito
 */
public class QualificaUserForm extends IForm.FormAbstract<QualificaUser> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2059728090692910599L;

	private TextField valueField;

	public QualificaUserForm(QualificaUser qualificaUser,boolean isAdmin,boolean enabled,boolean modifica) {
		super(qualificaUser,isAdmin,enabled,modifica);
		setFieldGroup(new BeanFieldGroup<QualificaUser>(QualificaUser.class));

		buildFieldGroup();
		buildTab();

	}

	public void buildTab() {

		addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		valueField = (TextField) getFieldGroup().buildAndBind("Qualifica", "value");
		addComponent(valueField);
	}

	public QualificaUser validate() throws CommitException,InvalidValueException {
		for (Field<?> f : getFieldGroup().getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}
		getFieldGroup().commit();

		BeanItem<QualificaUser> beanItem = (BeanItem<QualificaUser>) getFieldGroup().getItemDataSource();
		QualificaUser qualificaUser = beanItem.getBean();
		return qualificaUser;
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
