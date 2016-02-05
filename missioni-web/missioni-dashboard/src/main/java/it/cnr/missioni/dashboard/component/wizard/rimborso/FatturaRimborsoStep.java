package it.cnr.missioni.dashboard.component.wizard.rimborso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.TipologiaSpesa.TipoSpesaEnum;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * @author Salvia Vito
 */
public class FatturaRimborsoStep implements WizardStep {

	private TextField numeroFatturaField;
	private ComboBox tipologiaSpesaField;
	private TextField importoField;
	private DateField dataField;
	private TextField valutaField;
	private TextField altroField;
	private Fattura fattura;
	private ElencoFattureTable elencoFattureTable;

	private BeanFieldGroup<Fattura> fieldGroup;
	private VerticalLayout mainLayout;
	private List<TipologiaSpesa> listaTipologiaSpesaItalia = new ArrayList<TipologiaSpesa>();
	private List<TipologiaSpesa> listaTipologiaSpesaEstera = new ArrayList<TipologiaSpesa>();

	private Missione missione;

	public String getCaption() {
		return "Step 2";
	}

	public FatturaRimborsoStep(Missione missione) {
		this.missione = missione;

	}

	public Component getContent() {

		return buildFatturaTab();
	}

	public void bindFieldGroup() {

		fattura = new Fattura();
		fieldGroup = new BeanFieldGroup<Fattura>(Fattura.class);
		fieldGroup.setItemDataSource(fattura);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);

		numeroFatturaField = (TextField) fieldGroup.buildAndBind("Numero Fattura", "numeroFattura");
		tipologiaSpesaField = new ComboBox("Tipologia Spesa");
		tipologiaSpesaField.setValidationVisible(false);
		tipologiaSpesaField.setImmediate(true);

		fieldGroup.bind(tipologiaSpesaField, "idTipologiaSpesa");

		importoField = (TextField) fieldGroup.buildAndBind("Importo", "importo");
		valutaField = (TextField) fieldGroup.buildAndBind("Valuta", "valuta");
		altroField = (TextField) fieldGroup.buildAndBind("Altro", "altro");

		dataField = new DateField("Data");
		dataField.setRangeStart(new DateTime().toDate());
		dataField.setDateOutOfRangeMessage("Data non possibile");
		dataField.setResolution(Resolution.MINUTE);
		dataField.setDateFormat("dd/MM/yyyy HH:mm");
		dataField.setValidationVisible(false);
		// Ogni fattura deve essere compresa tra le date di inizio e fine
		// missione
		dataField.setRangeStart(missione.getDatiPeriodoMissione().getInizioMissione().toDate());
		dataField.setRangeEnd(missione.getDatiPeriodoMissione().getFineMissione().toDate());

		getTipologiaSpesa(TipoSpesaEnum.ITALIA.name(), listaTipologiaSpesaItalia);

		// carica la combo con tutte le voce di spesa italiane
		if (!missione.isMissioneEstera()) {
			buildTipologiaCombo(listaTipologiaSpesaItalia);

		}
		// preleva la lista di spesa ESTERA e aggiunge il listener sul DATE
		// FIELD
		if (missione.isMissioneEstera()) {
			getTipologiaSpesa(TipoSpesaEnum.ESTERA.name(), listaTipologiaSpesaEstera);
			addListener();
		}

		addValidator();

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
	 * Se la data è compresa tra ATTRAVERSAMENTO FRONTIERA ANDATA e RITORNO
	 * carico lista ESTERA, altrimenti lista ITALIA
	 * 
	 */
	private void addListener() {

		dataField.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {

				if (dataField.getValue() != null) {

					DateTime d = new DateTime((dataField.getValue().getTime()));

					if (d.compareTo(
							missione.getDatiPeriodoMissione().getInizioMissione().toLocalDateTime().toDateTime()) >= 0
							&& d.compareTo(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata()
									.toLocalDateTime().toDateTime()) < 0)
						buildTipologiaCombo(listaTipologiaSpesaItalia);
					else if (d
							.compareTo(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno()
									.toLocalDateTime().toDateTime()) > 0
							&& d.compareTo(missione.getDatiPeriodoMissione().getFineMissione().toLocalDateTime()
									.toDateTime()) <= 0)
						buildTipologiaCombo(listaTipologiaSpesaItalia);
					else if (d
							.compareTo(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata()
									.toLocalDateTime().toDateTime()) >= 0
							&& d.compareTo(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno()
									.toLocalDateTime().toDateTime()) <= 0)
						buildTipologiaCombo(listaTipologiaSpesaEstera);
				}

			}

		});

	}

	private void addValidator() {

		dataField.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				try {
					dataField.validate();
				} catch (Exception e) {
					dataField.setValidationVisible(true);
				}

			}
		});

		dataField.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (dataField.getValue() == null)
					throw new InvalidValueException(Utility.getMessage("field_required"));

			}
		});

		
		importoField.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				checkMassimale(tipologiaSpesaField.getValue().toString());

			}
		});

		

		
		// tipologiaSpesaField.addValidator(new Validator() {
		//
		// @Override
		// public void validate(Object value) throws InvalidValueException {
		// if (value == null )
		// throw new
		// InvalidValueException(Utility.getMessage("field_required"));
		//
		// }
		//
		// });

		tipologiaSpesaField.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				try {
					tipologiaSpesaField.validate();
				} catch (Exception e) {
					tipologiaSpesaField.setValidationVisible(true);
				}
			}
		});

	}

	private Component buildFatturaTab() {

		mainLayout = new VerticalLayout();
		mainLayout.setWidth(100.0f, Unit.PERCENTAGE);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		mainLayout.addComponent(details);
		mainLayout.setExpandRatio(details, 1);

		HorizontalLayout footer = new HorizontalLayout();
		mainLayout.addComponent(footer);
		elencoFattureTable = new ElencoFattureTable(fieldGroup, missione);
		// elencoFattureTable.setStyleName("elencoFatture");
		elencoFattureTable.aggiornaTotale(missione.getRimborso().getTotale());
		mainLayout.addComponent(elencoFattureTable);

		// mainLayout.setComponentAlignment(elencoFattureTable,
		// Alignment.MIDDLE_CENTER);
		details.addComponent(dataField);
		details.addComponent(numeroFatturaField);
		details.addComponent(tipologiaSpesaField);
		details.addComponent(importoField);

		details.addComponent(altroField);
		details.addComponent(valutaField);

		Button reset = new Button("Reset");
		reset.addStyleName(ValoTheme.BUTTON_PRIMARY);

		reset.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				fieldGroup.discard();
				aggiornaFatturaTab(new Fattura());

			}

		});

		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				boolean check = true;

				dataField.setValidationVisible(true);
				for (Field<?> f : fieldGroup.getFields()) {
					((AbstractField<?>) f).setValidationVisible(true);
				}

				try {

					fieldGroup.commit();
				} catch (InvalidValueException | CommitException e) {

					check = false;
				}

				try {
					dataField.validate();
				} catch (InvalidValueException e) {
					check = false;
				}

				if (check) {
					
//					checkMassimale(tipologiaSpesaField.getValue().toString());
					
					BeanItem<Fattura> beanItem = (BeanItem<Fattura>) fieldGroup.getItemDataSource();
					Fattura new_fattura = beanItem.getBean();

					// se la fattura è nuova creo un ID
					if (new_fattura.getId() == null)
						new_fattura.setId(UUID.randomUUID().toString());
					new_fattura.setData(new DateTime(dataField.getValue()));
					new_fattura.setShortDescriptionTipologiaSpesa(
							tipologiaSpesaField.getItemCaption(new_fattura.getIdTipologiaSpesa()));
					missione.getRimborso().getMappaFattura().put(new_fattura.getId(), new_fattura);
					Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);

					// ripulisco la form
					for (Field<?> f : fieldGroup.getFields()) {
						((AbstractField<?>) f).setValidationVisible(false);
					}
					// aggiorno la tabella
					aggiornaFatturaTab(new Fattura());
					elencoFattureTable
							.aggiornaTable(new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
					elencoFattureTable.aggiornaTotale(missione.getRimborso().getTotale());
				} else {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();
		footer.addComponent(ok);
		footer.addComponent(reset);
		mainLayout.setComponentAlignment(footer, Alignment.BOTTOM_RIGHT);

		return mainLayout;
	}

	private void checkMassimale(String id) {

		try {
			TipologiaSpesaStore tipologiaStore = ClientConnector
					.getTipologiaSpesa(TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder().withId(id));

			TipologiaSpesa tipologiaSpesa = tipologiaStore.getTipologiaSpesa().get(0);
			if (tipologiaSpesa.isCheckMassimale()) {

				String areaGeografica;
				if (missione.isMissioneEstera()) {
					Nazione nazione = ClientConnector
							.getNazione(NazioneSearchBuilder.getNazioneSearchBuilder().withId(missione.getIdNazione()))
							.getNazione().get(0);
					areaGeografica = nazione.getAreaGeografica().name();
				}else{
					areaGeografica = AreaGeograficaEnum.ITALIA.name();
				}
				MassimaleStore massimaleStore = ClientConnector
						.getMassimale(MassimaleSearchBuilder.getMassimaleSearchBuilder()
								.withLivello(DashboardUI.getCurrentUser().getDatiCNR().getLivello().name())
								.withAreaGeografica(areaGeografica)
								.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name()));
				
				if(massimaleStore != null){
					Massimale massimale = massimaleStore.getMassimale().get(0);
					NumberFormat f = NumberFormat.getInstance(); 
					double number = f.parse(importoField.getValue()).doubleValue();
					
					if(number > massimale.getValue())
						importoField.setValue(massimale.getValue().toString());
				}

			}

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}

	public void aggiornaFatturaTab(Fattura fattura) {
		fieldGroup.setItemDataSource(fattura);
	}

	public boolean onAdvance() {
		return true;
	}

	public boolean onBack() {
		return true;
	}

	public VerticalLayout getMainLayout() {
		return mainLayout;
	}

}
