package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
public class DatiGeneraliMissioneStep implements WizardStep {

	private TextField altroField;
	private CheckBox mezzoProprioField;
	private DateField fieldDataInserimentoField;
	private DateField fieldDataLastModifiedField;
	private TextField distanzaField;
	boolean veicoloPrincipaleSetted = false;
	private BeanFieldGroup<Missione> fieldGroup;
	private HorizontalLayout mainLayout;;

	private Missione missione;

	public String getCaption() {
		return "step 4";
	}

	public DatiGeneraliMissioneStep(Missione missione) {
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

		mezzoProprioField = (CheckBox) fieldGroup.buildAndBind("Mezzo Proprio", "mezzoProprio", CheckBox.class);
		distanzaField = (TextField) fieldGroup.buildAndBind("Distanza", "distanza");

		altroField = (TextField) fieldGroup.buildAndBind("Altro", "altro");

		fieldDataInserimentoField = (DateField) fieldGroup.buildAndBind("Data Inserimento", "dataInserimento");
		fieldDataInserimentoField.setReadOnly(true);
		fieldDataLastModifiedField = (DateField) fieldGroup.buildAndBind("Data ultima modifica", "dateLastModified");
		fieldDataLastModifiedField.setReadOnly(true);

		addvalidator();

	}

	private void addvalidator() {
		mezzoProprioField.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				Boolean v = (Boolean) value;

				User user = (User) VaadinSession.getCurrent().getAttribute(User.class);
				user.getMappaVeicolo().values().forEach(veicolo -> {
					if (veicolo.isVeicoloPrincipale())
						veicoloPrincipaleSetted = true;
				});
				// se non è presnte un veicolo settato come principale
				if (v && !veicoloPrincipaleSetted)
					throw new InvalidValueException(Utility.getMessage("no_veicolo"));
			}
		});
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
		details.addComponent(distanzaField);
		details.addComponent(mezzoProprioField);
		details.addComponent(altroField);
		details.addComponent(fieldDataInserimentoField);
		details.addComponent(fieldDataLastModifiedField);

		return mainLayout;
	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			fieldGroup.commit();

			BeanItem<Missione> beanItem = (BeanItem<Missione>) fieldGroup.getItemDataSource();
			missione = beanItem.getBean();

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
