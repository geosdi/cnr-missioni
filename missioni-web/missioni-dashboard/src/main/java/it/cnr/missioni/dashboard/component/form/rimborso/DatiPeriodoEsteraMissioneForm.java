package it.cnr.missioni.dashboard.component.form.rimborso;

import java.util.Date;

import org.joda.time.DateTime;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;

import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiMissioneEstera;
import it.cnr.missioni.model.missione.Missione;

/**
 * @author Salvia Vito
 */
public class DatiPeriodoEsteraMissioneForm extends IForm.FormAbstract<DatiMissioneEstera> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4295277244622616103L;
	private DateField attraversamentoFrontieraAndataField;
	private DateField attraversamentoFrontieraRitornoField;
	private Missione missione;

	public DatiPeriodoEsteraMissioneForm(DatiMissioneEstera datiMissioneEstera, boolean isAdmin, boolean enabled,
			boolean modifica, Missione missione) {
		super(datiMissioneEstera, isAdmin, enabled, modifica);
		this.modifica = modifica;
		this.setMissione(missione);
		setFieldGroup(new BeanFieldGroup<DatiMissioneEstera>(DatiMissioneEstera.class));

		buildFieldGroup();
		buildTab();

	}

	public void buildTab() {

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

		addComponent(attraversamentoFrontieraAndataField);
		addComponent(attraversamentoFrontieraRitornoField);

		if (modifica) {
			attraversamentoFrontieraAndataField.setValue(bean.getAttraversamentoFrontieraAndata().toDate());
			attraversamentoFrontieraRitornoField.setValue(bean.getAttraversamentoFrontieraRitorno().toDate());
			addComponent(Utility.buildLabel("GG all'estero: ", Integer.toString(Utility.getDaysEstero(missione))));
			addComponent(Utility.buildLabel("Tot. lordo TAM: ", missione.getRimborso().getTotaleTAM().toString()));
		}

		attraversamentoFrontieraRitornoField.setReadOnly(!enabled);
		attraversamentoFrontieraAndataField.setReadOnly(!enabled);

		addListener();
		addValidator();
	}

	public void addDateRange() {
		// Le date di attraversamento frontiera devono essere comprese tra la
		// data di inizio e fine missione
		attraversamentoFrontieraRitornoField
				.setRangeStart(getMissione().getDatiPeriodoMissione().getInizioMissione().toDate());
		attraversamentoFrontieraRitornoField
				.setRangeEnd(getMissione().getDatiPeriodoMissione().getFineMissione().toDate());
		attraversamentoFrontieraAndataField
				.setRangeStart(getMissione().getDatiPeriodoMissione().getInizioMissione().toDate());
		attraversamentoFrontieraAndataField
				.setRangeEnd(getMissione().getDatiPeriodoMissione().getFineMissione().toDate());
	}

	public DatiMissioneEstera validate() throws CommitException, InvalidValueException {

		DatiMissioneEstera datiMissioneEstera = null;

		attraversamentoFrontieraAndataField.setValidationVisible(true);
		attraversamentoFrontieraRitornoField.setValidationVisible(true);

		attraversamentoFrontieraAndataField.validate();
		attraversamentoFrontieraRitornoField.validate();

		if (missione.isMissioneEstera()) {
			BeanItem<DatiMissioneEstera> beanItem = (BeanItem<DatiMissioneEstera>) getFieldGroup().getItemDataSource();
			datiMissioneEstera = beanItem.getBean();
			datiMissioneEstera
					.setAttraversamentoFrontieraAndata(new DateTime(attraversamentoFrontieraAndataField.getValue()));
			datiMissioneEstera
					.setAttraversamentoFrontieraRitorno(new DateTime(attraversamentoFrontieraRitornoField.getValue()));

		}
		return datiMissioneEstera;

	}

	/**
	 * 
	 */
	@Override
	public void addValidator() {

		attraversamentoFrontieraAndataField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8390338466745533228L;

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (attraversamentoFrontieraAndataField.getValue() == null)
					throw new InvalidValueException(Utility.getMessage("field_required"));
				else {
					DateTime d = new DateTime(attraversamentoFrontieraAndataField.getValue());
					if (d.isBefore(missione.getDatiPeriodoMissione().getInizioMissione())
							|| d.isAfter(missione.getDatiPeriodoMissione().getFineMissione())) {
						throw new InvalidValueException(Utility.getMessage("date_range_start"));
					}
				}

			}
		});

		// la data di ritorno posteriore alla data di andata
		attraversamentoFrontieraRitornoField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 897702684811845371L;

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (attraversamentoFrontieraRitornoField.getValue() == null)
					throw new InvalidValueException(Utility.getMessage("field_required"));
				else {
					DateTime data = new DateTime((Date) value);
					if (missione.isMissioneEstera() && attraversamentoFrontieraAndataField.getValue() != null
							&& data.isBefore(attraversamentoFrontieraAndataField.getValue().getTime()))
						throw new InvalidValueException(Utility.getMessage("data_error"));
					else if (data.isBefore(missione.getDatiPeriodoMissione().getInizioMissione())
							|| data.isAfter(missione.getDatiPeriodoMissione().getFineMissione())) {
						throw new InvalidValueException(Utility.getMessage("date_range_start"));
					}

				}

			}
		});

	}

	/**
	 * 
	 */
	@Override
	public void addListener() {
		attraversamentoFrontieraAndataField.addBlurListener(new BlurListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 9131736226036796594L;

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

			/**
			 * 
			 */
			private static final long serialVersionUID = -6904717521046171088L;

			@Override
			public void blur(BlurEvent event) {
				try {
					attraversamentoFrontieraRitornoField.validate();
				} catch (Exception e) {
					attraversamentoFrontieraRitornoField.setValidationVisible(true);
				}

			}
		});

	}

	/**
	 * @return the attraversamentoFrontieraAndataField
	 */
	public DateField getAttraversamentoFrontieraAndataField() {
		return attraversamentoFrontieraAndataField;
	}

	/**
	 * @param attraversamentoFrontieraAndataField
	 */
	public void setAttraversamentoFrontieraAndataField(DateField attraversamentoFrontieraAndataField) {
		this.attraversamentoFrontieraAndataField = attraversamentoFrontieraAndataField;
	}

	/**
	 * @return the attraversamentoFrontieraRitornoField
	 */
	public DateField getAttraversamentoFrontieraRitornoField() {
		return attraversamentoFrontieraRitornoField;
	}

	/**
	 * @param attraversamentoFrontieraRitornoField
	 */
	public void setAttraversamentoFrontieraRitornoField(DateField attraversamentoFrontieraRitornoField) {
		this.attraversamentoFrontieraRitornoField = attraversamentoFrontieraRitornoField;
	}

	// /**
	// * @return the missioneEstera
	// */
	// public boolean isMissioneEstera() {
	// return missioneEstera;
	// }
	//
	// /**
	// * @param missioneEstera
	// */
	// public void setMissioneEstera(boolean missioneEstera) {
	// this.missioneEstera = missioneEstera;
	// }

	/**
	 * @return the missione
	 */
	public Missione getMissione() {
		return missione;
	}

	/**
	 * @param missione
	 */
	public void setMissione(Missione missione) {
		this.missione = missione;
	}

	// @Subscribe
	// public void aggiornaDati(AggiornaDatiMissioneEsteraEvent event){
	// if(!event.isStatus()){
	// missione.setDatiMissioneEstera(null);
	// attraversamentoFrontieraRitornoField.setValidationVisible(false);
	// attraversamentoFrontieraAndataField.setValidationVisible(false);
	// }
	// }

}
