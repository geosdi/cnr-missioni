package it.cnr.missioni.dashboard.component.window;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.action.UpdateUserAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.QualificaUserSearchBuilder;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;
import it.cnr.missioni.rest.api.response.qualificaUser.QualificaUserStore;
import it.cnr.missioni.rest.api.response.user.UserStore;

public class UserCompletedRegistrationWindow extends IWindow.AbstractWindow {

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
	private ComboBox listaUserField;

	private TextField numeroPatenteField;
	private TextField rilasciataDaField;

	private ComboBox livelloField;
	private ComboBox qualificaField;
	// private TextField datoreLavoroField;
	private TextField matricolaField;
	private TextField codiceTerzoField;
	private TextField mailField;
	private TextField ibanField;
	private User user;

	private UserCompletedRegistrationWindow(final User user, final boolean isAdmin) {
		super();
		this.user = user;
		fieldGroup = new BeanFieldGroup<User>(User.class);
		setId(ID);
		build();
		buildFieldGroup();
		buildTabs();
		if (!isAdmin)
			content.addComponent(buildFooter());
		fieldGroup.setReadOnly(isAdmin);
		addValidator();

	}

	private void buildTabs() {
		detailsWrapper.addComponent(buildAnagraficaTab());
		detailsWrapper.addComponent(buildResidenzaTab());
		detailsWrapper.addComponent(buildPatenteTab());
		detailsWrapper.addComponent(buildDatiCNRTab());
		detailsWrapper.addComponent(buildMassimaleTab());
	}

	private void buildFieldGroup() {
		fieldGroup.setItemDataSource(user);
		fieldGroup.setBuffered(true);

		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);
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

	private Component buildDatiCNRTab() {
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

		livelloField = (ComboBox) fieldGroup.buildAndBind("Livello", "datiCNR.livello", ComboBox.class);
		details.addComponent(livelloField);

		qualificaField = new ComboBox("Qualifica");
		listaUserField = new ComboBox("Datore Lavoro");
		try {

			QualificaUserSearchBuilder qualificaUserSearchBuilder = QualificaUserSearchBuilder
					.getQualificaUserSearchBuilder().withAll(true);
			QualificaUserStore qualificaStore = ClientConnector.getQualificaUser(qualificaUserSearchBuilder);
			qualificaStore.getQualificaUser().forEach(q -> {
				qualificaField.addItem(q.getId());
				qualificaField.setItemCaption(q.getId(), q.getValue());
			});

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

		fieldGroup.bind(qualificaField, "datiCNR.idQualifica");
		details.addComponent(qualificaField);

		listaUserField.setValidationVisible(false);
		fieldGroup.bind(listaUserField, "datiCNR.datoreLavoro");
		details.addComponent(listaUserField);

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

	private Component buildMassimaleTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Massimali");
		root.setIcon(FontAwesome.EURO);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		for (AreaGeograficaEnum a : AreaGeograficaEnum.values()) {
			try {
				
				double massimaleTAM = 0.0;
				double massimaleRimborsoDocumentato = 0.0;
				
				MassimaleStore massimaleStore = ClientConnector
						.getMassimale(MassimaleSearchBuilder.getMassimaleSearchBuilder()
								.withLivello(DashboardUI.getCurrentUser().getDatiCNR().getLivello().name())
								.withAreaGeografica(a.name())
								.withTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name()));
				if(massimaleStore != null)
					massimaleTAM = massimaleStore.getMassimale().get(0).getValue();
					
				 massimaleStore = ClientConnector
							.getMassimale(MassimaleSearchBuilder.getMassimaleSearchBuilder()
									.withLivello(DashboardUI.getCurrentUser().getDatiCNR().getLivello().name())
									.withAreaGeografica(a.name())
									.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name()));
					if(massimaleStore != null)
						massimaleRimborsoDocumentato = massimaleStore.getMassimale().get(0).getValue();
				
				if(massimaleStore != null)
				details.addComponent(new Label("<b>Area geografica: </b>" + a.name() + " <b>TAM:</b> "+Double.toString(massimaleTAM)+" <b>Rimborso documentato:</b> "+Double.toString(massimaleRimborsoDocumentato), ContentMode.HTML));

			} catch (Exception e) {
				Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
						Type.ERROR_MESSAGE);
			}
		}

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

					new_user.getDatiCNR()
							.setDescrizioneQualifica(qualificaField.getItemCaption(qualificaField.getValue()));
					new_user.getDatiCNR()
							.setShortDescriptionDatoreLavoro(listaUserField.getItemCaption(listaUserField.getValue()));

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

		codiceFiscaleField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {

				UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder()
						.withCodiceFiscale(codiceFiscaleField.getValue()).withNotId(user.getId());

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
							.withMatricola(matricolaField.getValue()).withNotId(user.getId());
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
							.withIban(ibanField.getValue()).withNotId(user.getId());
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
							.withMail(mailField.getValue()).withNotId(user.getId());

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
							.withNumeroPatente(numeroPatenteField.getValue()).withNotId(user.getId());
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

	public static void open(final User user, final boolean isAdmin) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new UserCompletedRegistrationWindow(user, isAdmin);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
