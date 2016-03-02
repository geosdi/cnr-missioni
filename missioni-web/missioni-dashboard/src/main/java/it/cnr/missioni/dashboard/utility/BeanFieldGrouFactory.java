package it.cnr.missioni.dashboard.utility;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;

import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.prenotazione.StatoVeicoloEnum;

/**
 * @author Salvia Vito
 */
public class BeanFieldGrouFactory extends DefaultFieldGroupFieldFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3176387859311468306L;

	@Override
	public <T extends Field> T createField(Class<?> type, Class<T> fieldType) {
		T field = null;
		if (type.isAssignableFrom(DateTime.class) || type.isAssignableFrom(Date.class)) {

			field = customDateField(field, type, fieldType);
		} else if (type.isAssignableFrom(String.class) || type.isAssignableFrom(Double.class)
				|| type.isAssignableFrom(Integer.class) || type.isAssignableFrom(Long.class)) {
			field = customFieldText(field, type, fieldType);

		} else if (type.isAssignableFrom(StatoEnum.class) && fieldType.isAssignableFrom(ComboBox.class)) {

			field = customStatoComboBox(field, type, fieldType);
		} else if (type.isAssignableFrom(StatoVeicoloEnum.class) && fieldType.isAssignableFrom(ComboBox.class)) {

			field = customStatoVeicoloComboBox(field, type, fieldType);
		}

		else if (type.isAssignableFrom(TrattamentoMissioneEsteraEnum.class)
				&& fieldType.isAssignableFrom(ComboBox.class)) {

			field = customTrattamentoMissioneEsteraVeicoloComboBox(field, type, fieldType);
		} 
		else if ( fieldType.isAssignableFrom(ComboBox.class)) {
			field = customComboBox(field, type, fieldType);
		}
		else if (type.isAssignableFrom(Boolean.class)) {
			field = customCheckBox(field, type, fieldType);

		} else {
			field = super.createField(type, fieldType);

		}

		return field;
	}
	
	private <T extends Field> T customComboBox(T field, Class<?> type, Class<T> fieldType) {
		field = super.createField(type, fieldType);
		BListener listener = new BListener(field);
		((ComboBox)field).addBlurListener(listener);
		((ComboBox)field).setValidationVisible(false);
		return field;
	}

	private <T extends Field> T customFieldText(T field, Class<?> type, Class<T> fieldType) {
		field = super.createField(type, fieldType);
		BListener listener = new BListener(field);
		((AbstractTextField) field).setValidationVisible(false);
		((AbstractTextField) field).setImmediate(true);
		((AbstractTextField) field).setNullRepresentation("");
		((AbstractTextField) field).addBlurListener(listener);
		return field;
	}

	private <T extends Field> T customStatoComboBox(T field, Class<?> type, Class<T> fieldType) {
		ComboBox comboBox = new ComboBox();
		comboBox.setImmediate(true);
		comboBox.setValidationVisible(false);
		StatoEnum[] lista = StatoEnum.values();
		BListener listener = new BListener(comboBox);
		comboBox.addBlurListener(listener);
		for (StatoEnum s : lista) {
			if(s != StatoEnum.PAGATA){
				comboBox.addItem(s);
				comboBox.setItemCaption(s, s.getStato());
			}
		}

		field = (T) comboBox;
		return field;
	}

	private <T extends Field> T customStatoVeicoloComboBox(T field, Class<?> type, Class<T> fieldType) {
		ComboBox comboBox = new ComboBox();
		comboBox.setImmediate(true);
		comboBox.setValidationVisible(false);
		StatoVeicoloEnum[] lista = StatoVeicoloEnum.values();
		BListener listener = new BListener(comboBox);
		comboBox.addBlurListener(listener);
		for (StatoVeicoloEnum s : lista) {
			comboBox.addItem(s);
			comboBox.setItemCaption(s, s.getStato());
		}

		field = (T) comboBox;
		return field;
	}

	private <T extends Field> T customTrattamentoMissioneEsteraVeicoloComboBox(T field, Class<?> type,
			Class<T> fieldType) {
		ComboBox comboBox = new ComboBox();
		comboBox.setImmediate(true);
		comboBox.setValidationVisible(false);
		BListener listener = new BListener(comboBox);
		comboBox.addBlurListener(listener);
		TrattamentoMissioneEsteraEnum[] lista = TrattamentoMissioneEsteraEnum.values();

		for (TrattamentoMissioneEsteraEnum s : lista) {
			comboBox.addItem(s);
			comboBox.setItemCaption(s, s.getStato());
		}

		field = (T) comboBox;
		return field;
	}

	private <T extends Field> T customDateField(T field, Class<?> type, Class<T> fieldType) {
		DateField dateField = new DateField();
		BListener listener = new BListener(dateField);
		dateField.addBlurListener(listener);
		dateField.setConverter(new DateTimeConverter());

		dateField.setValidationVisible(false);
		dateField.setImmediate(true);
		dateField.setDateFormat("dd/MM/yyyy HH:mm");
		dateField.setResolution(Resolution.MINUTE);
		dateField.setLocale(Locale.ITALY);
		field = (T) dateField;
		return field;
	}

	private <T extends Field> T customCheckBox(T field, Class<?> type, Class<T> fieldType) {
		field = super.createField(type, fieldType);
		BListener listener = new BListener(field);
		((CheckBox) field).setValidationVisible(false);
		((CheckBox) field).setImmediate(true);
		((CheckBox) field).addBlurListener(listener);
		return field;
	}

	/**
	 * 
	 * Converte JodaTime in Date e viceversa
	 * 
	 * @author Salvia Vito
	 *
	 */
	public class DateTimeConverter implements Converter<Date, DateTime> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6389154856677729962L;

		@Override
		public Class<DateTime> getModelType() {
			return DateTime.class;
		}

		@Override
		public Class<Date> getPresentationType() {
			return Date.class;
		}

		/**
		 * @param value
		 * @param targetType
		 * @param locale
		 * @return
		 * @throws ConversionException
		 */
		@Override
		public DateTime convertToModel(Date value, Class<? extends DateTime> targetType, Locale locale)
				throws com.vaadin.data.util.converter.Converter.ConversionException {
			if (value == null)
				return null;
			else
				return new DateTime(value);
			// return LocalDate.fromDateFields(value);
		}

		/**
		 * @param value
		 * @param targetType
		 * @param locale
		 * @return
		 * @throws ConversionException
		 */
		@Override
		public Date convertToPresentation(DateTime value, Class<? extends Date> targetType, Locale locale)
				throws com.vaadin.data.util.converter.Converter.ConversionException {
			if (value != null) {
				Date date = value.toDate();
				return date;
			}
			return null;
		}

	}

	/**
	 * 
	 * Creazione del BlurListener
	 * 
	 * @author Salvia Vito
	 *
	 */
	static class BListener implements BlurListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6722845037728924352L;
		Field<?> f;

		/**
		 * 
		 */
		public BListener(Field<?> f) {
			this.f = f;
		}

		/**
		 * @param event
		 */
		@Override
		public void blur(BlurEvent event) {
			try {
				f.validate();
			} catch (Exception e) {
				((AbstractField<?>) f).setValidationVisible(true);
			}

		}

	}

	private static class CustomeStringToDoubleConverter extends StringToDoubleConverter {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8378981781086601780L;

		@Override
		protected NumberFormat getFormat(Locale locale) {
			NumberFormat format = super.getFormat(locale);
			format.setGroupingUsed(false);
			format.setMaximumFractionDigits(2);
			format.setMinimumFractionDigits(2);
			return format;
		}
	}

	public static class ConverterFactory extends DefaultConverterFactory {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5604318645600543506L;

		@Override
		protected <PRESENTATION, MODEL> Converter<PRESENTATION, MODEL> findConverter(
				Class<PRESENTATION> presentationType, Class<MODEL> modelType) {
			// Handle String <-> Double
			if (presentationType == String.class && modelType == Double.class) {
				return (Converter<PRESENTATION, MODEL>) new CustomeStringToDoubleConverter();
			}
			// Let default factory handle the rest
			return super.findConverter(presentationType, modelType);
		}
	}

}
