package it.cnr.missioni.dashboard.component.wizard.missione;

import java.util.Date;

import org.joda.time.DateTime;
import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class DatiPeriodoMissioneStep implements WizardStep {

	private DateField inizioMissioneField;
	private DateField fineMissioneField;
	private BeanFieldGroup<DatiPeriodoMissione> fieldGroup;
	private HorizontalLayout mainLayout;;

	private DatiPeriodoMissione datiPeriodoMissione;
	private Missione missione;

	public String getCaption() {
		return "Step 5";
	}

	public DatiPeriodoMissioneStep(DatiPeriodoMissione datiPeriodoMissione, Missione missione) {
		this.datiPeriodoMissione = datiPeriodoMissione;
		this.missione = missione;

	}

	public Component getContent() {
		return buildPanel();
	}

	public BeanFieldGroup<DatiPeriodoMissione> bindFieldGroup() {

		fieldGroup = new BeanFieldGroup<DatiPeriodoMissione>(DatiPeriodoMissione.class);
		fieldGroup.setItemDataSource(datiPeriodoMissione);
		fieldGroup.setBuffered(true);
		FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
		fieldGroup.setFieldFactory(fieldFactory);

		inizioMissioneField = new DateField("Data Inizio");
		inizioMissioneField.setRangeStart(new DateTime().toDate());
		inizioMissioneField.setDateOutOfRangeMessage("Data non possibile");
		inizioMissioneField.setResolution(Resolution.MINUTE);
		inizioMissioneField.setDateFormat("dd/MM/yyyy HH:mm");
		inizioMissioneField.setValidationVisible(false);

		fineMissioneField = new DateField("Data Fine");
		fineMissioneField.setRangeStart(new DateTime().toDate());
		fineMissioneField.setDateOutOfRangeMessage("Data non possibile");
		fineMissioneField.setResolution(Resolution.MINUTE);
		fineMissioneField.setDateFormat("dd/MM/yyyy HH:mm");
		fineMissioneField.setValidationVisible(false);

		addValidator();

		return fieldGroup;

	}

	private void addValidator() {

		inizioMissioneField.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				try {
					inizioMissioneField.validate();
				} catch (Exception e) {
					inizioMissioneField.setValidationVisible(true);
				}

			}
		});

		fineMissioneField.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				try {
					fineMissioneField.validate();
				} catch (Exception e) {
					fineMissioneField.setValidationVisible(true);
				}

			}
		});

		fineMissioneField.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (fineMissioneField.getValue() == null)
					throw new InvalidValueException(Utility.getMessage("field_required"));

				else {
					DateTime data = new DateTime((Date) value);
					if (data.isBefore(new DateTime(inizioMissioneField.getValue().getTime())))
						throw new InvalidValueException(Utility.getMessage("data_error"));
				}

			}
		});

		inizioMissioneField.addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (inizioMissioneField.getValue() == null)
					throw new InvalidValueException(Utility.getMessage("field_required"));
			}
		});
	}

	private Component buildPanel() {
		mainLayout = new HorizontalLayout();
		mainLayout.setCaption("Inizio\\Fine");
		mainLayout.setIcon(FontAwesome.CALENDAR);
		mainLayout.setWidth(100.0f, Unit.PERCENTAGE);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		mainLayout.addComponent(details);
		mainLayout.setExpandRatio(details, 1);

		details.addComponent(inizioMissioneField);
		details.addComponent(fineMissioneField);

		return mainLayout;
	}

	public boolean onAdvance() {

		boolean check = true;

		inizioMissioneField.setValidationVisible(true);
		fineMissioneField.setValidationVisible(true);

		try {
			inizioMissioneField.validate();

		} catch (InvalidValueException e) {
			check = false;
		}

		try {
			fineMissioneField.validate();

		} catch (InvalidValueException e) {

			check = false;
		}

		if (check) {
			DatiPeriodoMissione periodoMissione = new DatiPeriodoMissione();
			periodoMissione.setFineMissione(new DateTime(fineMissioneField.getValue()));
			periodoMissione.setInizioMissione(new DateTime(inizioMissioneField.getValue()));

			missione.setDatiPeriodoMissione(periodoMissione);
		} else {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
					Type.ERROR_MESSAGE);
		}
		return check;

	}

	public boolean onBack() {
		return true;
	}

	public HorizontalLayout getMainLayout() {
		return mainLayout;
	}

}
