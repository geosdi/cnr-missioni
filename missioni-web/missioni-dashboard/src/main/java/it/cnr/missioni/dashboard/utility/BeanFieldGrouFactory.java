package it.cnr.missioni.dashboard.utility;

import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;

import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;

/**
 * @author Salvia Vito
 */
public class BeanFieldGrouFactory extends DefaultFieldGroupFieldFactory {
	@Override
	public <T extends Field> T createField(Class<?> type, Class<T> fieldType) {
		T field;
		if (type.isAssignableFrom(DateTime.class)) {

			DateField dateField = new DateField();
			BListener listener = new BListener(dateField);
			dateField.addBlurListener(listener);
			dateField.setConverter(new DateTimeConverter());

			dateField.setValidationVisible(false);
			dateField.setImmediate(true);
			dateField.setDateFormat("dd/MM/yyyy");
			field = (T) dateField;
		} else if (type.isAssignableFrom(String.class) || type.isAssignableFrom(Double.class)) {

			field = super.createField(type, fieldType);
			BListener listener = new BListener(field);
			((AbstractTextField) field).setValidationVisible(false);
			((AbstractTextField) field).setImmediate(true);
			((AbstractTextField) field).setNullRepresentation("");
			((AbstractTextField) field).addBlurListener(listener);
		} else if (type.isAssignableFrom(StatoEnum.class) && fieldType.isAssignableFrom(ComboBox.class)) {

			ComboBox comboBox = new ComboBox();
			comboBox.setImmediate(true);
			comboBox.setValidationVisible(false);
			StatoEnum[] lista = StatoEnum.values();
			BListener listener = new BListener(comboBox);
			comboBox.addBlurListener(listener);
			for (StatoEnum s : lista) {
				comboBox.addItem(s);
				comboBox.setItemCaption(s, s.getStato());
			}

			field = (T) comboBox;
		} else if (type.isAssignableFrom(TrattamentoMissioneEsteraEnum.class)
				&& fieldType.isAssignableFrom(ComboBox.class)) {

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
		} else if (type.isAssignableFrom(Boolean.class)) {
			field = super.createField(type, fieldType);
			BListener listener = new BListener(field);
			((CheckBox) field).setValidationVisible(false);
			((CheckBox) field).setImmediate(true);
			((CheckBox) field).addBlurListener(listener);
		}

		else {
			field = super.createField(type, fieldType);

		}

		return field;
	}

	public class DateTimeConverter implements Converter<Date, DateTime> {
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

}
