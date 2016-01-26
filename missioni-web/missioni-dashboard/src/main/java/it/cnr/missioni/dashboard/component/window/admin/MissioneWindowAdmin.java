package it.cnr.missioni.dashboard.component.window.admin;

import org.joda.time.DateTime;

import com.vaadin.data.Validator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
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
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.MissioneAction;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.User;

public class MissioneWindowAdmin extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -233114930128321445L;

	/**
	 * 
	 */

	public static final String ID = "missionewindow";

	private final BeanFieldGroup<Missione> fieldGroup;


	private TextField localitaField;
	private TextField oggettoField;
	private TextField altroField;
	private TextField fondoField;
	private TextField gaeField;
	private ComboBox statoField;
	private DateField inizioMissioneField;
	private DateField fineMissioneField;
	private CheckBox missioneEsteraField;
	private DateField attraversamentoFrontieraAndataField;
	private DateField attraversamentoFrontieraRitornoField;
	private ComboBox trattamentoMissioneEsteraField;
	private CheckBox anticipazioniMonetarieField;
	private TextField numeroMandatoField;
	private TextField speseMissioniAnticipateField;
	private TextField importoDaTerziField;
	private CheckBox rimborsoDaTerziField;
	private CheckBox mezzoProprioField;
	private TextField distanzaField;
	
	private ElencoMissioniTable elencoMissioniTable;
	private boolean modifica;

	private MissioneWindowAdmin(final Missione missione,boolean modifica,ElencoMissioniTable elencoMissioniTable) {
		
		this.elencoMissioniTable = elencoMissioniTable;
		this.modifica = modifica;
		
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

		fieldGroup = new BeanFieldGroup<Missione>(Missione.class);
		fieldGroup.setItemDataSource(missione);
		fieldGroup.setBuffered(true);

		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);
//			detailsWrapper.addComponent(buildGeneraleTab());
//			detailsWrapper.addComponent(buildDatePeriodoMissione());
//			detailsWrapper.addComponent(buildMissioneEstera());
//			detailsWrapper.addComponent(buildAnticipazioniMonetarie());




		content.addComponent(buildFooter());

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
		
		localitaField = (TextField)fieldGroup.buildAndBind("Località","localita");
		details.addComponent(localitaField);
		oggettoField = (TextField)fieldGroup.buildAndBind("Oggetto","oggetto");
		details.addComponent(oggettoField);
		fondoField = (TextField) fieldGroup.buildAndBind("Fondo", "fondo");
		details.addComponent(fondoField);
		gaeField = (TextField) fieldGroup.buildAndBind("GAE", "GAE");
		details.addComponent(gaeField);
		distanzaField = (TextField) fieldGroup.buildAndBind("Distanza", "distanza");
		details.addComponent(distanzaField);
//		statoField =(ComboBox)fieldGroup.buildAndBind("Stato","stato",ComboBox.class);
//		details.addComponent(statoField);
		mezzoProprioField =(CheckBox)fieldGroup.buildAndBind("Mezzo Proprio","mezzoProprio",CheckBox.class);
		details.addComponent(mezzoProprioField);
		
		
		mezzoProprioField.addValidator(new Validator() {
			
			@Override
			public void validate(Object value) throws InvalidValueException {
				Boolean v = (Boolean)value;
				if(v && ((User)VaadinSession.getCurrent().getAttribute(User.class)).getMappaVeicolo().isEmpty())
					throw new InvalidValueException(Utility.getMessage("no_veicolo"));
			}
		});
		
		missioneEsteraField = (CheckBox)fieldGroup.buildAndBind("Missione Estera","missioneEstera");
		details.addComponent(missioneEsteraField);	
		altroField = (TextField)fieldGroup.buildAndBind("Altro","altro");
		details.addComponent(altroField);
		

		DateField fieldDataInserimento = (DateField)fieldGroup.buildAndBind("Data Inserimento","dataInserimento");
		fieldDataInserimento.setReadOnly(true);
//		fieldDataInserimento.setResolution(Resolution.MINUTE);
//		fieldDataInserimento.setDateFormat("dd/MM/yyyy HH:mm");
		details.addComponent(fieldDataInserimento);
		
		
		DateField fieldDataLastModified = (DateField)fieldGroup.buildAndBind("Data ultima modifica","dateLastModified");
		fieldDataLastModified.setReadOnly(true);
//		fieldDataLastModified.setResolution(Resolution.MINUTE);
//		fieldDataLastModified.setDateFormat("dd/MM/yyyy HH:mm");
		details.addComponent(fieldDataLastModified);

		
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

		
		inizioMissioneField = (DateField)fieldGroup.buildAndBind("Inizio Missione","datiPeriodoMissione.inizioMissione");
		details.addComponent(inizioMissioneField);
		fineMissioneField = (DateField)fieldGroup.buildAndBind("Fine Missione","datiPeriodoMissione.fineMissione");
		details.addComponent(fineMissioneField);

		
		
		fineMissioneField.addValidator(new Validator() {
			
			@Override
			public void validate(Object value) throws InvalidValueException {
				DateTime data = (DateTime)value;
				if(inizioMissioneField.getValue() != null && inizioMissioneField.getValue()!=null && data.isBefore(inizioMissioneField.getValue().getTime()))
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
		
		

		
		trattamentoMissioneEsteraField =(ComboBox)fieldGroup.buildAndBind("Trattamento Rimborso","datiMissioneEstera.trattamentoMissioneEsteraEnum",ComboBox.class);
		details.addComponent(trattamentoMissioneEsteraField);
		
		
		trattamentoMissioneEsteraField.addValidator(new Validator(){

			@Override
			public void validate(Object value) throws InvalidValueException {
				TrattamentoMissioneEsteraEnum v= (TrattamentoMissioneEsteraEnum)value;
				if(missioneEsteraField.getValue() && (v == null)){
					throw new InvalidValueException(Utility.getMessage("checkbox_missione_error"));
				}
		
				
			}
		
			
		});
		
		attraversamentoFrontieraAndataField = (DateField)fieldGroup.buildAndBind("Attraversamento Frontiera Andata","datiMissioneEstera.attraversamentoFrontieraAndata");
		details.addComponent(attraversamentoFrontieraAndataField);
		attraversamentoFrontieraRitornoField = (DateField)fieldGroup.buildAndBind("Attraversamento Frontiera Ritorno","datiMissioneEstera.attraversamentoFrontieraRitorno");
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
		
		
		anticipazioniMonetarieField = (CheckBox)fieldGroup.buildAndBind("Anticipazioni Monetarie","datiAnticipoPagamenti.anticipazioniMonetarie");
		details.addComponent(anticipazioniMonetarieField);	
		
		numeroMandatoField =(TextField)fieldGroup.buildAndBind("Numero Mandato CNR","datiAnticipoPagamenti.mandatoCNR");
		details.addComponent(numeroMandatoField);
		
		
		numeroMandatoField.addValidator(new Validator(){

			@Override
			public void validate(Object value) throws InvalidValueException {
				String v= (String)value;
				if((value == null || v.equals("")) && anticipazioniMonetarieField.getValue()){
					throw new InvalidValueException(Utility.getMessage("numero_mandato_missione_errore"));
				}
		
				
			}
		
			
		});
		
		speseMissioniAnticipateField =(TextField)fieldGroup.buildAndBind("Altre Spese di Missione Anticipate","datiAnticipoPagamenti.speseMissioniAnticipate");
		details.addComponent(speseMissioniAnticipateField);

		rimborsoDaTerziField = (CheckBox)fieldGroup.buildAndBind("Rimborso da Terzi","datiAnticipoPagamenti.rimborsoDaTerzi");
		details.addComponent(rimborsoDaTerziField);	
		
		importoDaTerziField =(TextField)fieldGroup.buildAndBind("Importo da Terzi","datiAnticipoPagamenti.importoDaTerzi");
		details.addComponent(importoDaTerziField);
		
		importoDaTerziField.addValidator(new Validator(){

			@Override
			public void validate(Object value) throws InvalidValueException {
				Double v= (Double)value;
				if((v == null || v == 0) && rimborsoDaTerziField.getValue()){
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
			@Override
			public void buttonClick(ClickEvent event) {

				try {
					
					for (Field<?> f : fieldGroup.getFields()) {
						((AbstractField<?>) f).setValidationVisible(true);
					}
//					missioneEsteraField.validate();
//					fineMissioneField.validate();
//					trattamentoMissioneEsteraField.validate();
					fieldGroup.commit();
					
					BeanItem<Missione> beanItem = (BeanItem<Missione>)fieldGroup.getItemDataSource();
					Missione new_missione = beanItem.getBean();

						DashboardEventBus.post(new MissioneAction(new_missione,modifica));
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
	


	public static void open(final Missione missione,boolean modifica,ElencoMissioniTable elencoMissioniTable) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new MissioneWindowAdmin(missione,modifica,elencoMissioniTable);
		UI.getCurrent().addWindow(w);
		w.focus();
	}





}
