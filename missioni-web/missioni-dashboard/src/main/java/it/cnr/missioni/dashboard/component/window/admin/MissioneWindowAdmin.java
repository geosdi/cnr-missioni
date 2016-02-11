package it.cnr.missioni.dashboard.component.window.admin;

import org.elasticsearch.common.geo.GeoPoint;
import org.joda.time.DateTime;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.MissioneAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.rest.api.response.geocoder.GeocoderStore;
import it.cnr.missioni.rest.api.response.missione.distance.DistanceResponse;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;
import it.cnr.missioni.rest.api.response.user.UserStore;

public class MissioneWindowAdmin extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -233114930128321445L;

	/**
	 * 
	 */

	public static final String ID = "missioneadminwindow";

	private final BeanFieldGroup<Missione> fieldGroup;

	private TextField localitaField;
	private TextField oggettoField;
	private TextField fondoField;
	private TextField gaeField;
	private ComboBox statoField;
	private DateField inizioMissioneField;
	private DateField fineMissioneField;
	private DateField attraversamentoFrontieraAndataField;
	private DateField attraversamentoFrontieraRitornoField;
	private ComboBox trattamentoMissioneEsteraField;
	private CheckBox anticipazioniMonetarieField;
	private TextField numeroMandatoField;
	private TextField speseMissioniAnticipateField;
	private TextField importoDaTerziField;
	private CheckBox rimborsoDaTerziField;
	private TextField distanzaField;
	private OptionGroup optionGroupMezzo;
	private TextArea motivazioneMezzoProprio;
	private Label labelVeicoloProprio;
	boolean veicoloPrincipaleSetted = false;
	private ComboBox listaResponsabiliGruppoField;
	private ComboBox listaNazioneField;
	private ComboBox listaLocalitaField;
	private GeocoderStore geocoderStore;


	private User user;
	private Missione missione;

	private static final String VEICOLO_CNR = "Veicolo CNR";
	private static final String VEICOLO_PROPRIO = "Veicolo Proprio";
	private static final String ALTRO = "ALTRO";

	private MissioneWindowAdmin(final Missione missione) {

		super();
		this.missione = missione;
		getUser();
		fieldGroup = new BeanFieldGroup<Missione>(Missione.class);

		setId(ID);
		build();
		buildFieldGroup();
		buildTabs();
		if(!missione.isRimborsoSetted())
			content.addComponent(buildFooter());

	}

	private void getUser() {
		try {
			UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withId(missione.getIdUser());

			UserStore userStore = ClientConnector.getUser(userSearchBuilder);
			this.user = userStore.getUsers().get(0);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
	}

	private void buildFieldGroup() {
		fieldGroup.setItemDataSource(missione);
		fieldGroup.setBuffered(true);

		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);

	}

	private void buildTabs() {
		detailsWrapper.addComponent(buildGeneraleTab());
		detailsWrapper.addComponent(buildFondoGAETab());
		detailsWrapper.addComponent(buildVeicoloTab());
		detailsWrapper.addComponent(buildDatePeriodoMissione());
		if(missione.isMissioneEstera())
			detailsWrapper.addComponent(buildMissioneEstera());
		detailsWrapper.addComponent(buildAnticipazioniMonetarie());
		if(!missione.isRimborsoSetted()){
			fieldGroup.setReadOnly(true);
		}
		addValidator();
	}
	
	


	private Component buildGeneraleTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Generale");
		root.setIcon(FontAwesome.SUITCASE);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		String tipoMissione = missione.isMissioneEstera() ? "Estera" : "Italia";
		details.addComponent(new Label("Tipo Missione: " + tipoMissione));

		localitaField = new TextField("Localita");
		details.addComponent(localitaField);
		oggettoField = (TextField) fieldGroup.buildAndBind("Oggetto", "oggetto");
		details.addComponent(oggettoField);

		
		listaLocalitaField = new ComboBox("Seleziona località");
		fieldGroup.bind(listaLocalitaField, "localita");
		listaLocalitaField.setValidationVisible(false);
		listaLocalitaField.setImmediate(true);
		listaLocalitaField.addItem(missione.getGeoPoint().getLat() + "/" + missione.getGeoPoint().getLon());
		listaLocalitaField.setItemCaption(missione.getGeoPoint().getLat() + "/" + missione.getGeoPoint().getLon(),
				missione.getLocalita());
		listaLocalitaField.select(missione.getGeoPoint().getLat() + "/" + missione.getGeoPoint().getLon());
		details.addComponent(listaLocalitaField);
		
		distanzaField = (TextField) fieldGroup.buildAndBind("Distanza", "distanza");
		details.addComponent(distanzaField);
		
		listaNazioneField = new ComboBox("Nazione");
		listaNazioneField.setImmediate(true);
		listaNazioneField.setValidationVisible(false);
		fieldGroup.bind(listaNazioneField, "idNazione");
		try {
			NazioneStore nazioneStore = ClientConnector
					.getNazione(NazioneSearchBuilder.getNazioneSearchBuilder().withAll(true));
			if (nazioneStore != null) {
				nazioneStore.getNazione().forEach(c -> {
					listaNazioneField.addItem(c.getId());
					listaNazioneField.setItemCaption(c.getId(), c.getValue());
				});
			}
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		details.addComponent(listaNazioneField);

		statoField = (ComboBox) fieldGroup.buildAndBind("Stato", "stato", ComboBox.class);
		details.addComponent(statoField);
		details.addComponent(new Label("Data Inserimento: " + missione.getDataInserimento()));
		details.addComponent(new Label("Data Ultima Modifica: " + missione.getDateLastModified()));

		return root;
	}

	private void addValidator(){
		
		optionGroupMezzo.addValidator(new Validator() {

			// Verifica se è presenta alemno un veicolo proprio per l'utente
			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value.equals(VEICOLO_PROPRIO)) {
					User user = (User) VaadinSession.getCurrent().getAttribute(User.class);
					user.getMappaVeicolo().values().forEach(veicolo -> {
						if (veicolo.isVeicoloPrincipale())
							veicoloPrincipaleSetted = true;
					});

					// se non è presnte un veicolo settato come principale
					if (!veicoloPrincipaleSetted)
						throw new InvalidValueException(Utility.getMessage("no_veicolo"));

				}

			}
		});

		// Se veicolo è proprio motivazione è obbligatoria
		motivazioneMezzoProprio.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (motivazioneMezzoProprio.getValue() == null && optionGroupMezzo.getValue().equals(VEICOLO_PROPRIO))
					throw new InvalidValueException(Utility.getMessage("altre_disposizioni_error"));

			}
		});

		optionGroupMezzo.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (optionGroupMezzo.getValue().equals(VEICOLO_CNR) || optionGroupMezzo.getValue().equals(ALTRO)) {
					motivazioneMezzoProprio.setValue(null);
					motivazioneMezzoProprio.setReadOnly(true);
					motivazioneMezzoProprio.setValidationVisible(false);
					labelVeicoloProprio.setVisible(false);
				}
				if (optionGroupMezzo.getValue().equals(VEICOLO_PROPRIO)) {
					motivazioneMezzoProprio.setReadOnly(false);
					labelVeicoloProprio.setVisible(true);

				}
			}
		});
		
		listaLocalitaField.addBlurListener(new BlurListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2212681196076543399L;

			@Override
			public void blur(BlurEvent event) {
				try {
					listaLocalitaField.validate();
				} catch (Exception e) {
					listaLocalitaField.setValidationVisible(true);
				}
			}
		});

		listaNazioneField.addBlurListener(new BlurListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5929157918251479978L;

			@Override
			public void blur(BlurEvent event) {
				try {
					listaNazioneField.validate();
				} catch (Exception e) {
					listaNazioneField.setValidationVisible(true);
				}
			}
		});
		
		listaLocalitaField.addBlurListener(new BlurListener(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1110922024985265794L;

			@Override
			public void blur(BlurEvent event) {
				try {
										
				String localita = listaLocalitaField.getItemCaption(listaLocalitaField.getValue());
				
				if(localita != null){
				
					DistanceResponse.MissioneDistanceResponse distance = ClientConnector
							.getDistanceForMissione("Tito Scalo",localita);
					distanzaField.setValue(distance.getDistance());
				}
				else{
					distanzaField.setValue(null);
				}
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}				
			}
			
		});
		

		listaNazioneField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -3806636267951350192L;

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value == null && missione.isMissioneEstera())
					throw new InvalidValueException(Utility.getMessage("nazione_error"));

			}

		});

		localitaField.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5214527806486720528L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				listaLocalitaField.removeAllItems();

				if (!localitaField.getValue().trim().equals("")) {

					try {
						geocoderStore = ClientConnector.getGeocoderStoreForMissioneLocation(localitaField.getValue());

						if (geocoderStore.getGeocoderResponses() != null) {

							geocoderStore.getGeocoderResponses().forEach(c -> {
								listaLocalitaField.addItem(c.getLat() + "/" + c.getLon());
								listaLocalitaField.setItemCaption(c.getLat() + "/" + c.getLon(),
										c.getFormattedAddress());
							});
						} else {
							Utility.getNotification(Utility.getMessage("error_message"),
									Utility.getMessage("localita_error"), Type.ERROR_MESSAGE);
						}

					} catch (Exception e) {
						Utility.getNotification(Utility.getMessage("error_message"),
								Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
					}
				}

			}
		});
	}
	
	private Component buildFondoGAETab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Fondo\\GAE");
		root.setIcon(FontAwesome.SUITCASE);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		fondoField = (TextField) fieldGroup.buildAndBind("Fondo", "fondo");
		details.addComponent(fondoField);
		gaeField = (TextField) fieldGroup.buildAndBind("GAE", "GAE");
		details.addComponent(gaeField);

		listaResponsabiliGruppoField = new ComboBox("Responsabile Gruppo");

		try {
			UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withAll(true)
					.withResponsabileGruppo(true);

			UserStore userStore = ClientConnector.getUser(userSearchBuilder);

			if (userStore != null) {

				userStore.getUsers().forEach(u -> {
					listaResponsabiliGruppoField.addItem(u.getId());
					listaResponsabiliGruppoField.setItemCaption(u.getId(),
							u.getAnagrafica().getCognome() + " " + u.getAnagrafica().getNome());
				});
			}
			listaResponsabiliGruppoField.select(missione.getResponsabileGruppo());
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		listaResponsabiliGruppoField.setValidationVisible(false);
		listaResponsabiliGruppoField.addBlurListener(new BlurListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6359823173723675169L;

			@Override
			public void blur(BlurEvent event) {
				try {
					listaResponsabiliGruppoField.validate();
				} catch (Exception e) {
					listaResponsabiliGruppoField.setValidationVisible(true);
				}
			}

		});
		details.addComponent(listaResponsabiliGruppoField);

		return root;
	}

	private Component buildVeicoloTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Veicolo");
		root.setIcon(FontAwesome.CAR);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		optionGroupMezzo = new OptionGroup("Veicolo");
		optionGroupMezzo.addItems(VEICOLO_CNR, VEICOLO_PROPRIO, ALTRO);

		
		optionGroupMezzo.select(missione.isMezzoProprio() ? VEICOLO_PROPRIO : VEICOLO_CNR);
		details.addComponent(optionGroupMezzo);

		motivazioneMezzoProprio = (TextArea) fieldGroup.buildAndBind("Motivazione mezzo proprio",
				"motivazioniMezzoProprio", TextArea.class);

		motivazioneMezzoProprio.setReadOnly(true);

		// recupera il veicolo principale dell'user per visualizzarlo
		Veicolo v = this.user.getVeicoloPrincipale();
		if (v != null)
			labelVeicoloProprio = new Label("Veicolo: " + v.getTipo() + " Targa: " + v.getTarga());
		labelVeicoloProprio.setVisible(false);
		details.addComponent(labelVeicoloProprio);
		details.addComponent(motivazioneMezzoProprio);


		return root;
	}

	private Component buildDatePeriodoMissione() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Inizio\\Fine");
		root.setIcon(FontAwesome.CALENDAR);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		inizioMissioneField = (DateField) fieldGroup.buildAndBind("Inizio Missione",
				"datiPeriodoMissione.inizioMissione");
		details.addComponent(inizioMissioneField);
		fineMissioneField = (DateField) fieldGroup.buildAndBind("Fine Missione", "datiPeriodoMissione.fineMissione");
		details.addComponent(fineMissioneField);

		fineMissioneField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 338225108510178631L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				DateTime data = (DateTime) value;
				if (inizioMissioneField.getValue() != null && inizioMissioneField.getValue() != null
						&& data.isBefore(inizioMissioneField.getValue().getTime()))
					throw new InvalidValueException(Utility.getMessage("data_error"));
			}
		});

		return root;
	}

	private Component buildMissioneEstera() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Missione Estera");
		root.setIcon(FontAwesome.PLANE);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		trattamentoMissioneEsteraField = (ComboBox) fieldGroup.buildAndBind("Trattamento Rimborso",
				"datiMissioneEstera.trattamentoMissioneEsteraEnum", ComboBox.class);
		details.addComponent(trattamentoMissioneEsteraField);

		trattamentoMissioneEsteraField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6066841322332275332L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				TrattamentoMissioneEsteraEnum v = (TrattamentoMissioneEsteraEnum) value;
				if (missione.isMissioneEstera() && (v == null)) {
					throw new InvalidValueException(Utility.getMessage("checkbox_missione_error"));
				}

			}

		});

		attraversamentoFrontieraAndataField = (DateField) fieldGroup.buildAndBind("Attraversamento Frontiera Andata",
				"datiMissioneEstera.attraversamentoFrontieraAndata");
		details.addComponent(attraversamentoFrontieraAndataField);
		attraversamentoFrontieraRitornoField = (DateField) fieldGroup.buildAndBind("Attraversamento Frontiera Ritorno",
				"datiMissioneEstera.attraversamentoFrontieraRitorno");
		details.addComponent(attraversamentoFrontieraRitornoField);

		return root;
	}

	private Component buildAnticipazioniMonetarie() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Anticipazioni Monetarie");
		root.setIcon(FontAwesome.MONEY);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		anticipazioniMonetarieField = (CheckBox) fieldGroup.buildAndBind("Anticipazioni Monetarie",
				"datiAnticipoPagamenti.anticipazioniMonetarie", CheckBox.class);
		details.addComponent(anticipazioniMonetarieField);

		numeroMandatoField = (TextField) fieldGroup.buildAndBind("Numero Mandato CNR",
				"datiAnticipoPagamenti.mandatoCNR");
		details.addComponent(numeroMandatoField);

		numeroMandatoField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2589461952936884428L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				String v = (String) value;
				if ((value == null || v.equals("")) && anticipazioniMonetarieField.getValue()) {
					throw new InvalidValueException(Utility.getMessage("numero_mandato_missione_errore"));
				}

			}

		});

		speseMissioniAnticipateField = (TextField) fieldGroup.buildAndBind("Altre Spese di Missione Anticipate",
				"datiAnticipoPagamenti.speseMissioniAnticipate");
		details.addComponent(speseMissioniAnticipateField);

		rimborsoDaTerziField = (CheckBox) fieldGroup.buildAndBind("Rimborso da Terzi",
				"datiAnticipoPagamenti.rimborsoDaTerzi", CheckBox.class);
		details.addComponent(rimborsoDaTerziField);

		importoDaTerziField = (TextField) fieldGroup.buildAndBind("Importo da Terzi",
				"datiAnticipoPagamenti.importoDaTerzi");
		details.addComponent(importoDaTerziField);

		importoDaTerziField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -3396122710114688681L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				Double v = (Double) value;
				if ((v == null || v == 0) && rimborsoDaTerziField.getValue()) {
					throw new InvalidValueException(Utility.getMessage("importo_da_terzi_errore"));
				}

			}

		});

		return root;
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7886873565009642842L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {

					for (Field<?> f : fieldGroup.getFields()) {
						((AbstractField<?>) f).setValidationVisible(true);
					}
					fieldGroup.commit();

					BeanItem<Missione> beanItem = (BeanItem<Missione>) fieldGroup.getItemDataSource();
					Missione new_missione = beanItem.getBean();
					new_missione.setShortResponsabileGruppo(listaResponsabiliGruppoField.getItemCaption(listaResponsabiliGruppoField.getValue()));

					String[] latLng = ((String) listaLocalitaField.getValue()).split("/");
					GeoPoint geoPoint = new GeoPoint(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1]));
					new_missione.setGeoPoint(geoPoint);
					new_missione.setLocalita(listaLocalitaField.getItemCaption(listaLocalitaField.getValue()));
					DashboardEventBus.post(new MissioneAction(new_missione, true));
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

	public static void open(final Missione missione) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new MissioneWindowAdmin(missione);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
