package it.cnr.missioni.dashboard.component.form.rimborso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.RimborsoKmSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;

/**
 * @author Salvia Vito
 */
public class DatiGeneraliRimborsoForm extends IForm.FormAbstract<Rimborso> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4676249391052429281L;
	private CheckBox rimborsoDaTerziField;
	private TextField importoDaTerziField;
	private TextField totKmField;
	private TextField mandatoPagamentoField;
	private CheckBox pagataField;
	private TextField totaleDovutoField;
	private TextField avvisoPagamentoField;
	private Label labelTotRimborsoKm = new Label("Tot. Rimborso km: ");

	// TO DO inserire il rimborso da Terzi
	private Missione missione;
	private final int days;
	private final boolean mezzoProprio;
	private double totRimborsoKm = 0.0;

	public DatiGeneraliRimborsoForm(Missione missione, int days, boolean isAdmin, boolean enabled, boolean modifica) {
		super(missione.getRimborso(), isAdmin, enabled, modifica);
		this.bean = missione.getRimborso();
		this.missione = missione;
		this.days = days;
		this.mezzoProprio = missione.isMezzoProprio();
		setFieldGroup(new BeanFieldGroup<Rimborso>(Rimborso.class));
		getFieldGroup().setEnabled(enabled);
		buildFieldGroup();
		buildTab();
	}

	public void buildTab() {

		addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

		rimborsoDaTerziField = (CheckBox) getFieldGroup().buildAndBind("Rimborso da Terzi", "rimborsoDaTerzi",
				CheckBox.class);
		importoDaTerziField = (TextField) getFieldGroup().buildAndBind("Importo da Terzi", "importoDaTerzi");
		avvisoPagamentoField = (TextField) getFieldGroup().buildAndBind("Avviso di Pagamento", "avvisoPagamento");

		totKmField = (TextField) getFieldGroup().buildAndBind("Km da rimborsare", "totKm");

		if (isAdmin || modifica) {
			mandatoPagamentoField = (TextField) getFieldGroup().buildAndBind("Mandato di Pagamento",
					"mandatoPagamento");
			pagataField = (CheckBox) getFieldGroup().buildAndBind("Pagata", "pagata", CheckBox.class);
			totaleDovutoField = (TextField) getFieldGroup().buildAndBind("Tot.Dovuto", "totaleDovuto");

			addComponent(pagataField);
			addComponent(mandatoPagamentoField);
			addComponent(totaleDovutoField);

		}
		addComponent(rimborsoDaTerziField);
		addComponent(importoDaTerziField);
		addComponent(avvisoPagamentoField);

		if (mezzoProprio) {
			addComponent(totKmField);
			addComponent(labelTotRimborsoKm);
			if (modifica) {
				setTotaleRimborsoKM();
			}
		}

		if (modifica && missione.isMissioneEstera()) {
			Label l = new Label("<b>GG all'estero:</b> " + this.days + "\t<b>Tot. lordo TAM:</b> "
					+ (bean.getTotaleTAM() != null ? bean.getTotaleTAM() : 0), ContentMode.HTML);
			l.addStyleName(ValoTheme.LABEL_H3);
			l.addStyleName(ValoTheme.LABEL_LIGHT);

			addComponent(l);
		}
		addListener();
		addValidator();
	}

	private void setTotaleRimborsoKM() {
		try {
			RimborsoKmStore rimborsoKmStore = ClientConnector
					.getRimborsoKm(RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder());
			if (rimborsoKmStore.getTotale() > 0) {

				NumberFormat f = NumberFormat.getInstance();
				double number = f.parse(totKmField.getValue()).doubleValue();

				setTotRimborsoKm(number * rimborsoKmStore.getRimborsoKm().get(0).getValue());

			}
			labelTotRimborsoKm.setValue("Tot. Rimborso km: " + getTotRimborsoKm());
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
	}

	public void addListener() {

		totKmField.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4941530987925161416L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				setTotaleRimborsoKM();
			}

		});

	}

	public Rimborso validate() throws CommitException, InvalidValueException {
		for (Field<?> f : getFieldGroup().getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}
		getFieldGroup().commit();

		BeanItem<Rimborso> beanItem = (BeanItem<Rimborso>) getFieldGroup().getItemDataSource();
		bean = beanItem.getBean();
		bean.setRimborsoKm(getTotRimborsoKm());
		return bean;
	}

	/**
	 * @return the totRimborsoKm
	 */
	public double getTotRimborsoKm() {
		return totRimborsoKm;
	}

	/**
	 * @param totRimborsoKm
	 */
	public void setTotRimborsoKm(double totRimborsoKm) {
		this.totRimborsoKm = totRimborsoKm;
	}

	/**
	 * 
	 */
	@Override
	public void addValidator() {
		if (isAdmin) {
			mandatoPagamentoField.addValidator(new Validator() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 419080222173379095L;

				@Override
				public void validate(Object value) throws InvalidValueException {
					if ((value == null || ((String) value).isEmpty()) && pagataField.getValue())
						throw new InvalidValueException(Utility.getMessage("mandato_pagamento_error"));

				}

			});

			totaleDovutoField.addValidator(new Validator() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 419080222173379095L;

				@Override
				public void validate(Object value) throws InvalidValueException {
					if ((value == null) && pagataField.getValue())
						throw new InvalidValueException(Utility.getMessage("totale_dovuto_error"));

				}

			});

			pagataField.addValidator(new Validator() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -5030970150414733738L;

				@Override
				public void validate(Object value) throws InvalidValueException {
					boolean v = (Boolean) value;
					if (!v && mandatoPagamentoField.getValue() != null)
						throw new InvalidValueException(Utility.getMessage("pagata_error"));

				}

			});

		}
		
		
		rimborsoDaTerziField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8647870626324583181L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				
				double number = 0;
				NumberFormat format =  NumberFormat.getInstance(Locale.ITALY);
				format.setGroupingUsed(false);
				format.setMaximumFractionDigits(2);
				format.setMinimumFractionDigits(2);
				
				
				try {
					 number = format.parse(importoDaTerziField.getValue()).doubleValue();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				if (!rimborsoDaTerziField.getValue() && number > 0 )
					throw new InvalidValueException(Utility.getMessage("rimborso_terzi_error"));

			}

		});

		importoDaTerziField.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8647870626324583181L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				
				double number = 0;
				NumberFormat format =  NumberFormat.getInstance(Locale.ITALY);
				format.setGroupingUsed(false);
				format.setMaximumFractionDigits(2);
				format.setMinimumFractionDigits(2);
				
				
				try {
					 number = format.parse(importoDaTerziField.getValue()).doubleValue();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (rimborsoDaTerziField.getValue() && (importoDaTerziField.getValue() == null || number <= 0 ))
					throw new InvalidValueException(Utility.getMessage("importo_terzi_error"));
				
				else if (!rimborsoDaTerziField.getValue() && number > 0 )
					throw new InvalidValueException(Utility.getMessage("importo_terzi_error"));

			}

		});

	}

}
