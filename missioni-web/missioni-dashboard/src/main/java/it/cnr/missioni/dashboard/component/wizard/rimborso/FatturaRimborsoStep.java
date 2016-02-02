package it.cnr.missioni.dashboard.component.wizard.rimborso;

import java.util.ArrayList;
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
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.RimborsoAction;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;

/**
 * @author Salvia Vito
 */
public class FatturaRimborsoStep implements WizardStep {

	private TextField numeroFatturaField;
	private TextField tipologiaSpesaField;
	private TextField importoField;
	private DateField dataField;
	private TextField valutaField;
	private TextField altroField;
	private Fattura fattura;
	private ElencoFattureTable elencoFattureTable;

	private BeanFieldGroup<Fattura> fieldGroup;
	private VerticalLayout mainLayout;;

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
		tipologiaSpesaField = (TextField) fieldGroup.buildAndBind("Tipologia Spesa", "tipologiaSpesa");
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

		addValidator();

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

		details.addComponent(numeroFatturaField);
		details.addComponent(tipologiaSpesaField);
		details.addComponent(importoField);
		details.addComponent(dataField);
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
					BeanItem<Fattura> beanItem = (BeanItem<Fattura>) fieldGroup.getItemDataSource();
					Fattura new_fattura = beanItem.getBean();

					// se la fattura è nuova creo un ID
					if (new_fattura.getId() == null)
						new_fattura.setId(UUID.randomUUID().toString());
					new_fattura.setData(new DateTime(dataField.getValue()));
					missione.getRimborso().getMappaFattura().put(new_fattura.getId(), new_fattura);
					Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);

					// ripulisco la form
					for (Field<?> f : fieldGroup.getFields()) {
						((AbstractField<?>) f).setValidationVisible(false);
					}
					// aggiorno la tabella
					aggiornaFatturaTab(new Fattura());
					elencoFattureTable.aggiornaTable(new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
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
