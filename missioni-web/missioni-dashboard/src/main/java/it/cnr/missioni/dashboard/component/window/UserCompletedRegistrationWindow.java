package it.cnr.missioni.dashboard.component.window;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.UpdateUserAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

public class UserCompletedRegistrationWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9064172202629030178L;

	public static final String ID = "profilepreferenceswindow";

	private final BeanFieldGroup<User> fieldGroup;

	private TextField comuneField;
	private TextField indirizzoField;
	private TextField domicilioFiscaleField;
	private TextField nomeField;
	private TextField cognomeField;
	private TextField codiceFiscaleField;
	private TextField luogoNascitaField;
	private DateField dataNascitaField;

	private TextField numeroPatenteField;
	private TextField rilasciataDaField;

	private TextField livelloField;
	private TextField qualificaField;
	private TextField datoreLavoroField;
	private TextField matricolaField;
	private TextField codiceTerzoField;
	private TextField mailField;
	private TextField ibanField;
	private User user;

	private UserCompletedRegistrationWindow(final User user) {

		this.user = user;
		addStyleName("profile-window");
		setId(ID);
		Responsive.makeResponsive(this);

		setModal(true);
		setCloseShortcut(KeyCode.ESCAPE, null);
		setResizable(false);
		setClosable(true);
		setHeight(90.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(new MarginInfo(true, false, false, false));
		setContent(content);

		TabSheet detailsWrapper = new TabSheet();
		detailsWrapper.setSizeFull();
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		content.addComponent(detailsWrapper);
		content.setExpandRatio(detailsWrapper, 1f);

		fieldGroup = new BeanFieldGroup<User>(User.class);
		fieldGroup.setItemDataSource(user);
		fieldGroup.setBuffered(true);

		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);
		detailsWrapper.addComponent(buildAnagraficaTab());
		detailsWrapper.addComponent(buildResidenzaTab());
		detailsWrapper.addComponent(buildPatenteTab());
		detailsWrapper.addComponent(buildDatiCNR());

		content.addComponent(buildFooter());

	}

	private Component buildAnagraficaTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Anagrafica");
		root.setIcon(FontAwesome.USER);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		nomeField = (TextField) fieldGroup.buildAndBind("Nome", "anagrafica.nome");
		details.addComponent(nomeField);
		cognomeField = (TextField) fieldGroup.buildAndBind("Cognome", "anagrafica.cognome");
		details.addComponent(cognomeField);
		codiceFiscaleField = (TextField) fieldGroup.buildAndBind("Codice Fiscale", "anagrafica.codiceFiscale");
		details.addComponent(codiceFiscaleField);
		luogoNascitaField = (TextField) fieldGroup.buildAndBind("Luogo Nascita", "anagrafica.luogoNascita");
		details.addComponent(luogoNascitaField);

		dataNascitaField = (DateField) fieldGroup.buildAndBind("Data Nascita", "anagrafica.dataNascita",
				DateField.class);
		dataNascitaField.setResolution(Resolution.DAY);
		dataNascitaField.setDateFormat("dd/MM/yyyy");
		details.addComponent(dataNascitaField);

		DateField fieldDataRegistrazione = (DateField) fieldGroup.buildAndBind("Data Registrazione",
				"dataRegistrazione");
		fieldDataRegistrazione.setReadOnly(true);
		fieldDataRegistrazione.setResolution(Resolution.MINUTE);

		fieldDataRegistrazione.setDateFormat("dd/MM/yyyy HH:mm");
		details.addComponent(fieldDataRegistrazione);

		DateField fieldDataLastModified = (DateField) fieldGroup.buildAndBind("Data ultima modifica",
				"dateLastModified");
		fieldDataLastModified.setReadOnly(true);
		fieldDataLastModified.setResolution(Resolution.MINUTE);
		fieldDataLastModified.setDateFormat("dd/MM/yyyy HH:mm");
		details.addComponent(fieldDataLastModified);

		return root;
	}

	private void addValidator() {
		codiceFiscaleField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {

				UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
						.withCodiceFiscale(codiceFiscaleField.getValue()).withId(user.getId());

				UserStore userStore = null;
				try {
					userStore = ClientConnector.getUser(userSearchBuilder);
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				if (userStore != null)
					throw new InvalidValueException(Utility.getMessage("codice_fiscale_present"));

			}

		});

		matricolaField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value != null) {
					UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
							.withMatricola(matricolaField.getValue()).withId(user.getId());
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

		ibanField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {

					UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
							.withIban(ibanField.getValue()).withId(user.getId());
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

		mailField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value != null) {

					UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
							.withMail(mailField.getValue()).withId(user.getId());

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

		numeroPatenteField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (value != null) {
					UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
							.withNumeroPatente(numeroPatenteField.getValue()).withId(user.getId());
					UserStore userStore = null;
					try {
						userStore = ClientConnector.getUser(userSearchBuilder);
					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
					if (userStore != null)
						throw new InvalidValueException(Utility.getMessage("numero_patente_present"));
				}
			}

		});

	}

	private Component buildPatenteTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Patente");
		root.setIcon(FontAwesome.CAR);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		numeroPatenteField = (TextField) fieldGroup.buildAndBind("Numero Patente", "patente.numeroPatente");
		details.addComponent(numeroPatenteField);
		rilasciataDaField = (TextField) fieldGroup.buildAndBind("Rilasciata da", "patente.rilasciataDa");
		details.addComponent(rilasciataDaField);
		DateField dataRilascio = (DateField) fieldGroup.buildAndBind("Data Rilascio", "patente.dataRilascio");

		details.addComponent(dataRilascio);
		DateField dataValidita = (DateField) fieldGroup.buildAndBind("Valida fino al", "patente.validaFinoAl");
		details.addComponent(dataValidita);

		return root;
	}

	private Component buildDatiCNR() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Dati CNR");
		root.setIcon(FontAwesome.INSTITUTION);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		livelloField = (TextField) fieldGroup.buildAndBind("Livello", "datiCNR.livello");
		details.addComponent(livelloField);
		qualificaField = (TextField) fieldGroup.buildAndBind("Qualifica", "datiCNR.qualifica");
		details.addComponent(qualificaField);
		datoreLavoroField = (TextField) fieldGroup.buildAndBind("Datore Lavoro", "datiCNR.datoreLavoro");
		details.addComponent(datoreLavoroField);
		matricolaField = (TextField) fieldGroup.buildAndBind("Matricola", "datiCNR.matricola");
		details.addComponent(matricolaField);
		codiceTerzoField = (TextField) fieldGroup.buildAndBind("Codice Terzi", "datiCNR.codiceTerzo");
		details.addComponent(codiceTerzoField);
		mailField = (TextField) fieldGroup.buildAndBind("Mail", "datiCNR.mail");
		details.addComponent(mailField);
		ibanField = (TextField) fieldGroup.buildAndBind("Iban", "datiCNR.iban");
		details.addComponent(ibanField);

		return root;
	}

	private Component buildResidenzaTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Residenza");
		root.setIcon(FontAwesome.HOME);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		comuneField = (TextField) fieldGroup.buildAndBind("Comune", "residenza.comune");
		details.addComponent(comuneField);
		indirizzoField = (TextField) fieldGroup.buildAndBind("Indirizzo", "residenza.indirizzo");
		details.addComponent(indirizzoField);
		domicilioFiscaleField = (TextField) fieldGroup.buildAndBind("Domicilio Fiscale", "residenza.domicilioFiscale");
		details.addComponent(domicilioFiscaleField);

		return root;
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				try {
					for (Field<?> f : fieldGroup.getFields()) {
						((AbstractField<?>) f).setValidationVisible(true);
					}
					fieldGroup.commit();

					BeanItem<User> beanItem = (BeanItem<User>) fieldGroup.getItemDataSource();
					User new_user = beanItem.getBean();

					DashboardEventBus.post(new UpdateUserAction(new_user));
					close();

				} catch (InvalidValueException | CommitException e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();
		footer.addComponent(ok);
		footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
		return footer;
	}

	public static void open(final User user) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new UserCompletedRegistrationWindow(user);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
