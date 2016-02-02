package it.cnr.missioni.dashboard.component.wizard.user;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.UpdateUserAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.DatiCNR;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public class DatiCNRUserStep implements WizardStep {

	private ComboBox livelloField;
	private ComboBox qualificaField;
	private TextField matricolaField;
	private TextField codiceTerzoField;
	private TextField mailField;
	private TextField ibanField;
	private ComboBox listaUserField;

	private BeanFieldGroup<DatiCNR> fieldGroup;
	private HorizontalLayout mainLayout;;

	private DatiCNR datiCNR = new DatiCNR();
	private User user;

	public String getCaption() {
		return "Step 4";
	}

	public DatiCNRUserStep(User user) {
		this.user = user;

	}

	public Component getContent() {
		return buildGeneraleTab();
	}

	public void bindFieldGroup() {
		try {
			fieldGroup = new BeanFieldGroup<DatiCNR>(DatiCNR.class);
			fieldGroup.setItemDataSource(datiCNR);
			fieldGroup.setBuffered(true);
			FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
			fieldGroup.setFieldFactory(fieldFactory);

			livelloField = (ComboBox) fieldGroup.buildAndBind("Livello", "livello", ComboBox.class);
			qualificaField = new ComboBox("Qualifica");
			fieldGroup.bind(qualificaField, "idQualifica");

			matricolaField = (TextField) fieldGroup.buildAndBind("Matricola", "matricola");
			codiceTerzoField = (TextField) fieldGroup.buildAndBind("Codice Terzo", "codiceTerzo");
			mailField = (TextField) fieldGroup.buildAndBind("Mail", "mail");
			ibanField = (TextField) fieldGroup.buildAndBind("Iban", "iban");

			listaUserField = new ComboBox("Datore Lavoro");

			UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withAll(true);

			UserStore userStore = ClientConnector.getUser(userSearchBuilder);

			userStore.getUsers().forEach(u -> {
				listaUserField.addItem(u.getId());
				listaUserField.setItemCaption(u.getId(),
						u.getAnagrafica().getCognome() + " " + u.getAnagrafica().getNome());
			});

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		listaUserField.setValidationVisible(false);
		fieldGroup.bind(listaUserField, "datiCNR.datoreLavoro");

		addValidator();
	}

	private void addValidator() {

		listaUserField.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				try {
					listaUserField.validate();
				} catch (Exception e) {
					listaUserField.setValidationVisible(true);
				}
			}

		});

		matricolaField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {
					UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
							.withMatricola(matricolaField.getValue());

					UserStore userStore = null;
					try {
						userStore = ClientConnector.getUser(userSearchBuilder);
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					if (userStore != null)
						throw new InvalidValueException(Utility.getMessage("matricola_present"));
				}
			}

		});

		mailField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {
					UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
							.withMail(mailField.getValue());
					UserStore userStore = null;
					try {
						userStore = ClientConnector.getUser(userSearchBuilder);
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					if (userStore != null)
						throw new InvalidValueException(Utility.getMessage("mail_present"));
				}
			}

		});

		ibanField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {
					UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
							.withIban(ibanField.getValue());
					UserStore userStore = null;
					try {
						userStore = ClientConnector.getUser(userSearchBuilder);
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					if (userStore != null)
						throw new InvalidValueException(Utility.getMessage("iban_present"));
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

		details.addComponent(livelloField);
		details.addComponent(qualificaField);
		details.addComponent(listaUserField);
		details.addComponent(matricolaField);
		details.addComponent(codiceTerzoField);
		details.addComponent(mailField);
		details.addComponent(ibanField);

		return mainLayout;
	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			fieldGroup.commit();

			BeanItem<DatiCNR> beanItem = (BeanItem<DatiCNR>) fieldGroup.getItemDataSource();
			datiCNR = beanItem.getBean();
			user.setDatiCNR(datiCNR);
			DashboardEventBus.post(new UpdateUserAction(user));
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
