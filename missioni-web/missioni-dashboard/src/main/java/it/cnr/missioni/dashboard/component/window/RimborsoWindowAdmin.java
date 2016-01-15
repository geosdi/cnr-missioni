package it.cnr.missioni.dashboard.component.window;

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

import it.cnr.missioni.dashboard.action.RimborsoAction;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;

public class RimborsoWindowAdmin extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4269186437699906289L;

	/**
	 * 
	 */

	public static final String ID = "rimborsowindow";

	private final BeanFieldGroup<Rimborso> fieldGroupRimborso;
	private  BeanFieldGroup<Fattura> fieldGroupFattura;
	private FieldGroupFieldFactory fieldFactory ;

	//FIELD RIMBORSO
	private TextField numeroOrdineField;
	private TextField avvisoPagamentoField;
	private TextField anticipazionePagamentoField;
	
	//FIELD FATTURA
	private TextField numeroFatturaField;
	private DateField dataField;
	private TextField tipologiaSpesaField;
	private TextField importoField;
	private TextField valutaField;
	private TextField altroField;
	
	private TabSheet detailsWrapper;

	
	private boolean modifica;
	
	private Rimborso rimborso;
	private Missione missione;
	private Fattura fattura;

	private RimborsoWindowAdmin(final Rimborso rimborso,Missione missione,boolean modifica) {

		this.modifica = modifica;
		this.missione = missione;
		this.rimborso = rimborso;
		
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

		 detailsWrapper = new TabSheet();
		detailsWrapper.setSizeFull();
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		content.addComponent(detailsWrapper);
		content.setExpandRatio(detailsWrapper, 1f);

		fieldGroupRimborso = new BeanFieldGroup<Rimborso>(Rimborso.class);
		fieldGroupRimborso.setItemDataSource(rimborso);
		fieldGroupRimborso.setBuffered(true);

		 fieldFactory = new BeanFieldGrouFactory();
		fieldGroupRimborso.setFieldFactory(fieldFactory);
		detailsWrapper.addComponent(buildRimborsoTab());
		detailsWrapper.addComponent(buildFatturaTab());

		if(rimborso.getNumeroOrdine() == null)
			detailsWrapper.getTab(1).setEnabled(false);
		else
			detailsWrapper.getTab(1).setEnabled(true);
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
		fieldGroupFattura= new BeanFieldGroup<Fattura>(Fattura.class);
		fieldGroupFattura.setItemDataSource(fattura);
		fieldGroupFattura.setBuffered(true);

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
		tipologiaSpesaField = (TextField) fieldGroupFattura.buildAndBind("Tipologia Spesa", "tipologiaSpesa");
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

			@Override
			public void buttonClick(ClickEvent event) {
				fattura = new Fattura();
				aggiornaFatturaTab(fattura);
				
			}
			
		});
				
		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
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

	public static void open(final Rimborso rimborso,Missione missione,boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new RimborsoWindowAdmin(rimborso,missione,modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
