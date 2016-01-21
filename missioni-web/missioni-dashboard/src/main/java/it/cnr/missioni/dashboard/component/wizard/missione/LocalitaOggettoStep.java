package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
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
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class LocalitaOggettoStep implements WizardStep {

	private TextField localitaField;
	private TextField oggettoField;


	private BeanFieldGroup<Missione> fieldGroup;
	private HorizontalLayout mainLayout;;

	private Missione missione;

	public String getCaption() {
		return "Step 2";
	}

	public LocalitaOggettoStep(Missione missione) {
		this.missione = missione;

	}

	public Component getContent() {
		return buildGeneraleTab();
	}

	public void bindFieldGroup() {
		fieldGroup = new BeanFieldGroup<Missione>(Missione.class);
		fieldGroup.setItemDataSource(missione);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);
		localitaField = (TextField) fieldGroup.buildAndBind("Località", "localita");
		oggettoField = (TextField) fieldGroup.buildAndBind("Oggetto", "oggetto");


	}

	private Component buildGeneraleTab() {

		mainLayout = new HorizontalLayout();
		mainLayout.setCaption("Località\\Oggetto");
		mainLayout.setIcon(FontAwesome.SUITCASE);
		mainLayout.setWidth(100.0f, Unit.PERCENTAGE);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		mainLayout.addComponent(details);
		mainLayout.setExpandRatio(details, 1);

		details.addComponent(localitaField);
		details.addComponent(oggettoField);

		return mainLayout;
	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			localitaField.validate();
			oggettoField.validate();
			
			BeanItem<Missione> beanItem = (BeanItem<Missione>)fieldGroup.getItemDataSource();
			missione = beanItem.getBean();
			missione.setOggetto(oggettoField.getValue());
			missione.setLocalita(localitaField.getValue());
			
			return true;
		} catch (InvalidValueException e) {
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
