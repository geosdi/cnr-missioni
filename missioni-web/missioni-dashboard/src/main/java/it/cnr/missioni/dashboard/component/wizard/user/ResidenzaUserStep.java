package it.cnr.missioni.dashboard.component.wizard.user;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.Residenza;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class ResidenzaUserStep implements WizardStep {

	private TextField comuneField;
	private TextField indirizzoField;
	private TextField domicilioFiscaleField;



	private BeanFieldGroup<Residenza> fieldGroup;
	private HorizontalLayout mainLayout;;

	private Residenza residenza = new Residenza();
	private User user;

	public String getCaption() {
		return "Residenza";
	}

	public ResidenzaUserStep(User user) {
		this.user = user;

	}

	public Component getContent() {
		return buildGeneraleTab();
	}

	public void bindFieldGroup() {
		fieldGroup = new BeanFieldGroup<Residenza>(Residenza.class);
		fieldGroup.setItemDataSource(residenza);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);

		comuneField = (TextField) fieldGroup.buildAndBind("Comune", "comune");
		indirizzoField = (TextField) fieldGroup.buildAndBind("Indirizzo", "indirizzo");
		domicilioFiscaleField = (TextField) fieldGroup.buildAndBind("Domicilio Fiscale", "domicilioFiscale");



	}

	private Component buildGeneraleTab() {

		mainLayout = new HorizontalLayout();
		mainLayout.setCaption("Dati Generali");
		mainLayout.setIcon(FontAwesome.SUITCASE);
		mainLayout.setWidth(100.0f, Unit.PERCENTAGE);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		mainLayout.addComponent(details);
		mainLayout.setExpandRatio(details, 1);

		details.addComponent(comuneField);
		details.addComponent(indirizzoField);
		details.addComponent(domicilioFiscaleField);



		return mainLayout;
	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			fieldGroup.commit();

			BeanItem<Residenza> beanItem = (BeanItem<Residenza>) fieldGroup.getItemDataSource();
			residenza = beanItem.getBean();
			user.setResidenza(residenza);
			return true;
		} catch (InvalidValueException | CommitException e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
					Type.ERROR_MESSAGE);
			return false;
		}
	}

	public boolean onBack() {
		return true;
	}

	public HorizontalLayout getMainLayout() {
		return mainLayout;
	}

}
