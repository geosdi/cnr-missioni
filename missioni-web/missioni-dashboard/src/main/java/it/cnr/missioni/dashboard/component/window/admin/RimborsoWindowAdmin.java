package it.cnr.missioni.dashboard.component.window.admin;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.datefield.Resolution;
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
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.RimborsoAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.TipologiaSpesa.TipoSpesaEnum;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

public class RimborsoWindowAdmin extends IWindow.AbstractWindow {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1118586137070207948L;

	public static final String ID = "rimborsoadminwindow";

	private final  BeanFieldGroup<Rimborso> fieldGroupRimborso;
	private  BeanFieldGroup<Fattura> fieldGroupFattura;
	private FieldGroupFieldFactory fieldFactory ;

	//FIELD RIMBORSO
	private TextField numeroOrdineField;
	private TextField avvisoPagamentoField;
	private TextField anticipazionePagamentoField;
	
	//FIELD FATTURA
	private TextField numeroFatturaField;
	private DateField dataField;
	private ComboBox tipologiaSpesaField;
	private TextField importoField;
	private TextField valutaField;
	private TextField altroField;
	
	private List<TipologiaSpesa> listaTipologiaSpesaItalia = new ArrayList<TipologiaSpesa>();
	private List<TipologiaSpesa> listaTipologiaSpesaEstera = new ArrayList<TipologiaSpesa>();
	
	
	private Rimborso rimborso;
	private Missione missione;
	private Fattura fattura;

	private RimborsoWindowAdmin(final Missione missione) {
		super();
		this.missione = missione;
		this.rimborso = missione.getRimborso();
		fieldGroupRimborso = new BeanFieldGroup<Rimborso>(Rimborso.class);

		addStyleName("profile-window");
		setId(ID);
		Responsive.makeResponsive(this);

		build();
		buildFieldGroup();
		buildTabs();
//		fieldGroupRimborso = new BeanFieldGroup<Rimborso>(Rimborso.class);
//		fieldGroupRimborso.setItemDataSource(rimborso);
//		fieldGroupRimborso.setBuffered(true);
//
//		 fieldFactory = new BeanFieldGrouFactory();
//		fieldGroupRimborso.setFieldFactory(fieldFactory);
//		detailsWrapper.addComponent(buildRimborsoTab());
//		detailsWrapper.addComponent(buildFatturaTab());
//
//		if(rimborso.getNumeroOrdine() == null)
//			detailsWrapper.getTab(1).setEnabled(false);
//		else
//			detailsWrapper.getTab(1).setEnabled(true);
	}

	private void buildTabs(){
		detailsWrapper.addComponent(buildRimborsoTab());
		detailsWrapper.addComponent(buildFatturaTab());
	}
	
	private void buildFieldGroup() {
		fieldGroupRimborso.setItemDataSource(rimborso);
		fieldGroupRimborso.setBuffered(true);

		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroupRimborso.setFieldFactory(fieldFactory);

	}
	
	private Component buildRimborsoTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Rimborso");
		root.setIcon(FontAwesome.MONEY);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		numeroOrdineField = (TextField) fieldGroupRimborso.buildAndBind("Numero Ordine", "numeroOrdine");
		details.addComponent(numeroOrdineField);
		numeroOrdineField.setReadOnly(true);
		avvisoPagamentoField = (TextField) fieldGroupRimborso.buildAndBind("Avviso Pagamento", "avvisoPagamento");
		details.addComponent(avvisoPagamentoField);
		anticipazionePagamentoField = (TextField) fieldGroupRimborso.buildAndBind("Anticipazione Pagamento", "anticipazionePagamento");
		details.addComponent(anticipazionePagamentoField);

		DateField fieldDataRegistrazione = (DateField)fieldGroupRimborso.buildAndBind("Data Rimborso","dataRimborso");
		fieldDataRegistrazione.setReadOnly(true);
		fieldDataRegistrazione.setResolution(Resolution.MINUTE);
		fieldDataRegistrazione.setDateFormat("dd/MM/yyyy HH:mm");
		details.addComponent(fieldDataRegistrazione);
		
		
		DateField fieldDataLastModified = (DateField)fieldGroupRimborso.buildAndBind("Data ultima modifica","dateLastModified");
		fieldDataLastModified.setReadOnly(true);
		fieldDataLastModified.setResolution(Resolution.MINUTE);
		fieldDataLastModified.setDateFormat("dd/MM/yyyy HH:mm");
		details.addComponent(fieldDataLastModified);
		
		HorizontalLayout footer = new HorizontalLayout();
		root.addComponent(footer);
		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8007185581159515333L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					
					
					for (Field<?> f : fieldGroupRimborso.getFields()) {
						((AbstractField<?>) f).setValidationVisible(true);
					}
					fieldGroupRimborso.commit();

					BeanItem<Rimborso> beanItem = (BeanItem<Rimborso>) fieldGroupRimborso.getItemDataSource();
					Rimborso new_rimborso = beanItem.getBean();
					
					DashboardEventBus.post(new RimborsoAction(missione));
					detailsWrapper.getTab(1).setEnabled(true);

				} catch (InvalidValueException | CommitException e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();
		footer.addComponent(ok);
		root.setComponentAlignment(footer, Alignment.BOTTOM_RIGHT);
		return root;
	}
	
	public void aggiornaFatturaTab(Fattura fattura){
		fieldGroupFattura.setItemDataSource(fattura);
	}
	
	private Component buildFatturaTab() {
		
		fattura = new Fattura();
		fieldGroupFattura = new BeanFieldGroup<Fattura>(Fattura.class);
		fieldGroupFattura.setItemDataSource(fattura);
		fieldGroupFattura.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroupFattura.setFieldFactory(fieldFactory);

		fieldGroupFattura.setFieldFactory(fieldFactory);
		
		VerticalLayout root = new VerticalLayout();
		
		root.setCaption("Fattura");
		root.setIcon(FontAwesome.BARCODE);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		HorizontalLayout footer = new HorizontalLayout();
		root.addComponent(footer);
		root.addComponent(new ElencoFattureTable(fieldGroupFattura,missione));
//		fatturaLayout.setExpandRatio(details, 1);
		

		numeroFatturaField = (TextField) fieldGroupFattura.buildAndBind("Numero Fattura", "numeroFattura");
		details.addComponent(numeroFatturaField);
		dataField = (DateField)fieldGroupFattura.buildAndBind("Data","data");
		details.addComponent(dataField);
		
		tipologiaSpesaField = new ComboBox("Tipologia Spesa");
		tipologiaSpesaField.setValidationVisible(false);
		tipologiaSpesaField.setImmediate(true);
		
		getTipologiaSpesa(TipoSpesaEnum.ITALIA.name(), listaTipologiaSpesaItalia);

		// carica la combo con tutte le voce di spesa italiane
		if (!missione.isMissioneEstera()) {
			buildTipologiaCombo(listaTipologiaSpesaItalia);

		}
		// preleva la lista di spesa ESTERA e aggiunge il listener sul DATE
		// FIELD
		if (missione.isMissioneEstera()) {
			getTipologiaSpesa(TipoSpesaEnum.ESTERA.name(), listaTipologiaSpesaEstera);
		}

		fieldGroupFattura.bind(tipologiaSpesaField, "idTipologiaSpesa");
		
		details.addComponent(tipologiaSpesaField);
		importoField = (TextField) fieldGroupFattura.buildAndBind("Importo", "importo");
		details.addComponent(importoField);
		valutaField = (TextField) fieldGroupFattura.buildAndBind("Valuta", "valuta");
		details.addComponent(valutaField);
		altroField = (TextField) fieldGroupFattura.buildAndBind("Altro", "altro");
		details.addComponent(altroField);


		
		Button reset = new Button("Reset");
		reset.addStyleName(ValoTheme.BUTTON_PRIMARY);
		reset.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3448301358809646724L;

			@Override
			public void buttonClick(ClickEvent event) {
				fattura = new Fattura();
				aggiornaFatturaTab(fattura);
				
			}
			
		});
				
		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1516426501992135874L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					
					
					for (Field<?> f : fieldGroupRimborso.getFields()) {
						((AbstractField<?>) f).setValidationVisible(true);
					}
					fieldGroupRimborso.commit();

					BeanItem<Rimborso> beanItem = (BeanItem<Rimborso>) fieldGroupRimborso.getItemDataSource();
					Rimborso new_rimborso = beanItem.getBean();
					
					DashboardEventBus.post(new RimborsoAction(missione));
					close();

				} catch (InvalidValueException | CommitException e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();
		footer.addComponent(ok);
		footer.addComponent(reset);
		root.setComponentAlignment(footer, Alignment.BOTTOM_RIGHT);
		
		return root;
	}
	
	
	/**
	 * 
	 * Costruisce la COMBO BOX
	 * 
	 * @param lista
	 */
	private void buildTipologiaCombo(List<TipologiaSpesa> lista) {

		tipologiaSpesaField.removeAllItems();

		lista.forEach(s -> {
			tipologiaSpesaField.addItem(s.getId());
			tipologiaSpesaField.setItemCaption(s.getId(), s.getValue());
		});
	}
	
	/**
	 * 
	 * Carica le tipologia spesa in base ad ITALIA o ESTERA
	 * 
	 * @param tipo
	 * @param lista
	 */
	private void getTipologiaSpesa(String tipo, List<TipologiaSpesa> lista) {
		try {
			TipologiaSpesaStore tipologiaStore = ClientConnector
					.getTipologiaSpesa(TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder().withTipo(tipo));

			if (tipologiaStore != null) {
				lista.addAll(tipologiaStore.getTipologiaSpesa());
			}
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
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
			private static final long serialVersionUID = 127413665855903412L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					
					
					for (Field<?> f : fieldGroupRimborso.getFields()) {
						((AbstractField<?>) f).setValidationVisible(true);
					}
					fieldGroupRimborso.commit();

					BeanItem<Rimborso> beanItem = (BeanItem<Rimborso>) fieldGroupRimborso.getItemDataSource();
					Rimborso new_rimborso = beanItem.getBean();
					
					DashboardEventBus.post(new RimborsoAction(missione));
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
		Window w = new RimborsoWindowAdmin(missione);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
