package it.cnr.missioni.dashboard.component.wizard.missione;

import java.util.Date;

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
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiMissioneEstera;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;

/**
 * @author Salvia Vito
 */
public class DatiMissioneEsteraStep implements WizardStep {

	private ComboBox trattamentoMissioneEsteraField;
	private DateField attraversamentoFrontieraAndataField;
	private DateField attraversamentoFrontieraRitornoField;

	private BeanFieldGroup<DatiMissioneEstera> fieldGroup;
	private HorizontalLayout mainLayout;;

	private DatiMissioneEstera datiMissioneEstera;
	private Missione missione;

	public String getCaption() {
		return "Step 6";
	}

	public DatiMissioneEsteraStep(DatiMissioneEstera datiMissioneEstera, Missione missione) {
		this.datiMissioneEstera = datiMissioneEstera;
		this.missione = missione;

	}

	public Component getContent() {
		return buildPanel();
	}

	public void bindFieldGroup() {

		fieldGroup = new BeanFieldGroup<DatiMissioneEstera>(DatiMissioneEstera.class);
		fieldGroup.setItemDataSource(datiMissioneEstera);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);

		trattamentoMissioneEsteraField = (ComboBox) fieldGroup.buildAndBind("Trattamento Rimborso",
				"trattamentoMissioneEsteraEnum", ComboBox.class);

		attraversamentoFrontieraAndataField = new DateField("Attraversamento Frontiera andata");
		attraversamentoFrontieraAndataField.setDateOutOfRangeMessage("Data non possibile");
		attraversamentoFrontieraAndataField.setResolution(Resolution.MINUTE);
		attraversamentoFrontieraAndataField.setDateFormat("dd/MM/yyyy HH:mm");
		attraversamentoFrontieraAndataField.setValidationVisible(false);

		attraversamentoFrontieraRitornoField = new DateField("Attraversamento Frontiera andata");
		attraversamentoFrontieraRitornoField.setDateOutOfRangeMessage("Data non possibile");
		attraversamentoFrontieraRitornoField.setResolution(Resolution.MINUTE);
		attraversamentoFrontieraRitornoField.setDateFormat("dd/MM/yyyy HH:mm");
		attraversamentoFrontieraRitornoField.setValidationVisible(false);

		addValidator();

	}

	public void addDateRange() {
		// Le date di attraversamento frontiera devono essere comprese tra la
		// data di inizio e fine missione
		attraversamentoFrontieraRitornoField
				.setRangeStart(missione.getDatiPeriodoMissione().getInizioMissione().toDate());
		attraversamentoFrontieraRitornoField.setRangeEnd(missione.getDatiPeriodoMissione().getFineMissione().toDate());
		attraversamentoFrontieraAndataField
				.setRangeStart(missione.getDatiPeriodoMissione().getInizioMissione().toDate());
		attraversamentoFrontieraAndataField.setRangeEnd(missione.getDatiPeriodoMissione().getFineMissione().toDate());
	}

	public void addValidator() {

		// se la missione è di tipo estera
		if (missione.isMissioneEstera()) {

			attraversamentoFrontieraAndataField.addBlurListener(new BlurListener() {

				@Override
				public void blur(BlurEvent event) {
					try {
						attraversamentoFrontieraAndataField.validate();
					} catch (Exception e) {
						attraversamentoFrontieraAndataField.setValidationVisible(true);
					}

				}
			});

			attraversamentoFrontieraRitornoField.addBlurListener(new BlurListener() {

				@Override
				public void blur(BlurEvent event) {
					try {
						attraversamentoFrontieraAndataField.validate();
					} catch (Exception e) {
						attraversamentoFrontieraAndataField.setValidationVisible(true);
					}

				}
			});

			trattamentoMissioneEsteraField.addValidator(new Validator() {

				@Override
				public void validate(Object value) throws InvalidValueException {
					TrattamentoMissioneEsteraEnum v = (TrattamentoMissioneEsteraEnum) value;
					if ((v == null)) {
						throw new InvalidValueException(Utility.getMessage("checkbox_missione_error"));
					}

				}

			});

			attraversamentoFrontieraAndataField.addValidator(new Validator() {

				@Override
				public void validate(Object value) throws InvalidValueException {

					if (attraversamentoFrontieraAndataField.getValue() == null)
						throw new InvalidValueException(Utility.getMessage("field_required"));

				}
			});

			// la data di ritorno posteriore alla data di andata
			attraversamentoFrontieraRitornoField.addValidator(new Validator() {

				@Override
				public void validate(Object value) throws InvalidValueException {

					if (attraversamentoFrontieraRitornoField.getValue() == null)
						throw new InvalidValueException(Utility.getMessage("field_required"));
					else {
						DateTime data = new DateTime((Date) value);
						if (attraversamentoFrontieraAndataField.getValue() != null
								&& data.isBefore(attraversamentoFrontieraAndataField.getValue().getTime()))
							throw new InvalidValueException(Utility.getMessage("data_error"));
					}

				}
			});
		}

	}

	private Component buildPanel() {
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

		if (!missione.isMissioneEstera()) {
			trattamentoMissioneEsteraField.setReadOnly(true);
			attraversamentoFrontieraAndataField.setReadOnly(true);
			attraversamentoFrontieraRitornoField.setReadOnly(true);
		} else {
			trattamentoMissioneEsteraField.setReadOnly(false);
			attraversamentoFrontieraAndataField.setReadOnly(false);
			attraversamentoFrontieraRitornoField.setReadOnly(false);
		}

		details.addComponent(trattamentoMissioneEsteraField);

		details.addComponent(attraversamentoFrontieraAndataField);
		details.addComponent(attraversamentoFrontieraRitornoField);

		return root;
	}

	public boolean onAdvance() {

		if (!missione.isMissioneEstera()) {
			return true;
		}else{
		
		boolean check = true;

		for (Field<?> f : fieldGroup.getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}
		attraversamentoFrontieraAndataField.setValidationVisible(true);
		attraversamentoFrontieraRitornoField.setValidationVisible(true);
		try {
			fieldGroup.commit();
		} catch (InvalidValueException | CommitException e) {
			check = false;
		}
		try {
			attraversamentoFrontieraAndataField.validate();
		} catch (InvalidValueException e) {
			check = false;
		}
		try {
			attraversamentoFrontieraRitornoField.validate();
		} catch (InvalidValueException e) {
			check = false;
		}

		if (check && missione.isMissioneEstera()) {
			BeanItem<DatiMissioneEstera> beanItem = (BeanItem<DatiMissioneEstera>) fieldGroup.getItemDataSource();
			DatiMissioneEstera new_datiMissioneEstera = beanItem.getBean();
			missione.setDatiMissioneEstera(new_datiMissioneEstera);
			missione.getDatiMissioneEstera()
					.setAttraversamentoFrontieraAndata(new DateTime(attraversamentoFrontieraAndataField.getValue()));
			missione.getDatiMissioneEstera()
					.setAttraversamentoFrontieraRitorno(new DateTime(attraversamentoFrontieraRitornoField.getValue()));
		} else {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
					Type.ERROR_MESSAGE);
		}
		return check;
		}

	}

	public boolean onBack() {
		return true;
	}

	public HorizontalLayout getMainLayout() {
		return mainLayout;
	}

}
