package it.cnr.missioni.dashboard.component.wizard.missione;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.MissioneAction;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiAnticipoPagamenti;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class AnticipazioniPagamentoStep implements WizardStep {

	private TextField importoDaTerziField;
	private TextField speseMissioniAnticipateField;
	private TextField numeroMandatoField;
	private CheckBox anticipazioniMonetarieField;
	private CheckBox rimborsoDaTerziField;

	private BeanFieldGroup<DatiAnticipoPagamenti> fieldGroup;
	private HorizontalLayout mainLayout;;

	private DatiAnticipoPagamenti datiAnticipoPagamenti;
	private Missione missione;
	private boolean modifica;

	public String getCaption() {
		return "Step 7";
	}

	public AnticipazioniPagamentoStep(DatiAnticipoPagamenti datiAnticipoPagamenti, Missione missione, boolean modifica
			 ) {
		this.datiAnticipoPagamenti = datiAnticipoPagamenti;
		this.missione = missione;
		this.modifica = modifica;

	}

	public Component getContent() {
		return buildPanel();
	}

	public void bindFieldGroup() {
		fieldGroup = new BeanFieldGroup<DatiAnticipoPagamenti>(DatiAnticipoPagamenti.class);
		fieldGroup.setItemDataSource(datiAnticipoPagamenti);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);

		anticipazioniMonetarieField = (CheckBox) fieldGroup.buildAndBind("Anticipazioni Monetarie",
				"anticipazioniMonetarie");
		numeroMandatoField = (TextField) fieldGroup.buildAndBind("Numero Mandato CNR", "mandatoCNR");
		speseMissioniAnticipateField = (TextField) fieldGroup.buildAndBind("Altre Spese di Missione Anticipate",
				"speseMissioniAnticipate");
		rimborsoDaTerziField = (CheckBox) fieldGroup.buildAndBind("Rimborso da Terzi", "rimborsoDaTerzi");
		importoDaTerziField = (TextField) fieldGroup.buildAndBind("Importo da Terzi", "importoDaTerzi");

		numeroMandatoField.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				String v = (String) value;
				if ((value == null || v.equals("")) && anticipazioniMonetarieField.getValue()) {
					throw new InvalidValueException(Utility.getMessage("numero_mandato_missione_errore"));
				}

			}

		});

		importoDaTerziField.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				Double v = (Double) value;
				if ((v == null || v == 0) && rimborsoDaTerziField.getValue()) {
					throw new InvalidValueException(Utility.getMessage("importo_da_terzi_errore"));
				}

			}

		});

	}

	private Component buildPanel() {
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

		details.addComponent(anticipazioniMonetarieField);

		details.addComponent(numeroMandatoField);

		details.addComponent(speseMissioniAnticipateField);
		details.addComponent(rimborsoDaTerziField);
		details.addComponent(importoDaTerziField);

		return root;
	}

	public boolean onAdvance() {
		try {
			for (Field<?> f : fieldGroup.getFields()) {
				((AbstractField<?>) f).setValidationVisible(true);
			}
			fieldGroup.commit();
			BeanItem<DatiAnticipoPagamenti> beanItem = (BeanItem<DatiAnticipoPagamenti>) fieldGroup.getItemDataSource();
			DatiAnticipoPagamenti new_datiAnticipoPagamenti = beanItem.getBean();
			missione.setDatiAnticipoPagamenti(new_datiAnticipoPagamenti);

			DashboardEventBus.post(new MissioneAction(missione, modifica));

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
