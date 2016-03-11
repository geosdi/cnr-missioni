package it.cnr.missioni.dashboard.component.form.missione;

import java.util.Date;

import org.joda.time.DateTime;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;

import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiPeriodoMissione;

/**
 * @author Salvia Vito
 */
public class DatiPeriodoMissioneForm extends IForm.FormAbstract<DatiPeriodoMissione> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 809374535670336094L;
	private DateField inizioMissioneField;
	private DateField fineMissioneField;

	public DatiPeriodoMissioneForm(DatiPeriodoMissione datiPeriodoMissione, boolean isAdmin, boolean enabled,
			boolean modifica) {
		super(datiPeriodoMissione, isAdmin, enabled, modifica);
		setFieldGroup(new BeanFieldGroup<DatiPeriodoMissione>(DatiPeriodoMissione.class));
		buildFieldGroup();
		buildTab();
	}

	public void buildTab() {

		inizioMissioneField = new DateField("Data Inizio");
		fineMissioneField = new DateField("Data Presunta Fine");
		fineMissioneField.setResolution(Resolution.MINUTE);
		fineMissioneField.setDateFormat("dd/MM/yyyy HH:mm");
		inizioMissioneField.setResolution(Resolution.MINUTE);
		inizioMissioneField.setDateFormat("dd/MM/yyyy HH:mm");
		inizioMissioneField.setDateOutOfRangeMessage("Data non possibile");
		inizioMissioneField.setValidationVisible(false);
		fineMissioneField.setDateOutOfRangeMessage("Data non possibile");
		fineMissioneField.setValidationVisible(false);

		if (modifica) {
			inizioMissioneField.setValue(bean.getInizioMissione().toDate());
			fineMissioneField.setValue(bean.getFineMissione().toDate());
		}
		if(!isAdmin){
			inizioMissioneField.setRangeStart(new DateTime().toDate());
			fineMissioneField.setRangeStart(new DateTime().toDate());

		}
		inizioMissioneField.setReadOnly(!enabled);
		fineMissioneField.setReadOnly(!enabled);

		addComponent(inizioMissioneField);
		addComponent(fineMissioneField);
		addListener();
		addValidator();
	}

	public DatiPeriodoMissione validate() throws CommitException, InvalidValueException {

		DatiPeriodoMissione periodoMissione = null;

		inizioMissioneField.setValidationVisible(true);
		fineMissioneField.setValidationVisible(true);
		inizioMissioneField.validate();
		fineMissioneField.validate();

		periodoMissione = new DatiPeriodoMissione();
		periodoMissione.setFineMissione(new DateTime(fineMissioneField.getValue()));
		periodoMissione.setInizioMissione(new DateTime(inizioMissioneField.getValue()));
		return periodoMissione;

	}

	/**
	 * 
	 */
	@Override
	public void addValidator() {

		fineMissioneField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2243927443594597429L;

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
			/**
			 * 
			 */
			private static final long serialVersionUID = 416154042136580163L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				if (inizioMissioneField.getValue() == null)
					throw new InvalidValueException(Utility.getMessage("field_required"));
			}
		});
	}

	/**
	 * 
	 */
	@Override
	public void addListener() {
		inizioMissioneField.addBlurListener(new BlurListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2593817487211396503L;

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

			/**
			 * 
			 */
			private static final long serialVersionUID = 4341920929829378087L;

			@Override
			public void blur(BlurEvent event) {
				try {
					fineMissioneField.validate();
				} catch (Exception e) {
					fineMissioneField.setValidationVisible(true);
				}

			}
		});
	}

}
