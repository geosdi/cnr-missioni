package it.cnr.missioni.dashboard.component.wizard.user;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.Patente;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public class PatenteUserStep implements WizardStep {

	private TextField numeroPatenteField;
	private TextField rilasciataDaField;
	private DateField dataRilascioField;
	private DateField dataValiditaField;

	private BeanFieldGroup<Patente> fieldGroup;
	private HorizontalLayout mainLayout;;

	private Patente patente = new Patente();
	private User user;

	public String getCaption() {
		return "Step 3";
	}

	public PatenteUserStep(User user) {
		this.user = user;

	}

	public Component getContent() {
		return buildGeneraleTab();
	}

	public void bindFieldGroup() {
		fieldGroup = new BeanFieldGroup<Patente>(Patente.class);
		fieldGroup.setItemDataSource(patente);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);

		numeroPatenteField = (TextField) fieldGroup.buildAndBind("Numero Patente", "numeroPatente");
		rilasciataDaField = (TextField) fieldGroup.buildAndBind("Rilasciata Da", "rilasciataDa");
		dataRilascioField = (DateField) fieldGroup.buildAndBind("Data Rilascio", "dataRilascio");
		dataValiditaField = (DateField) fieldGroup.buildAndBind("Valida Fino Al", "validaFinoAl");
		dataRilascioField.setResolution(Resolution.DAY);
		dataRilascioField.setDateFormat("dd/MM/yyyy");
		dataValiditaField.setResolution(Resolution.DAY);
		dataValiditaField.setDateFormat("dd/MM/yyyy");

		addValidator();

	}

	private void addValidator() {
		numeroPatenteField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {
					UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
							.withNumeroPatente(numeroPatenteField.getValue());
					UserStore userStore = null;
					try {
						userStore = ClientConnector.getUser(userSearchBuilder);
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					if (userStore != null)
						throw new InvalidValueException(Utility.getMessage("patente_present"));
				}
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

		details.addComponent(numeroPatenteField);
		details.addComponent(rilasciataDaField);
		details.addComponent(dataRilascioField);
		details.addComponent(dataValiditaField);

		return mainLayout;
	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			fieldGroup.commit();

			BeanItem<Patente> beanItem = (BeanItem<Patente>) fieldGroup.getItemDataSource();
			patente = beanItem.getBean();
			user.setPatente(patente);
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
