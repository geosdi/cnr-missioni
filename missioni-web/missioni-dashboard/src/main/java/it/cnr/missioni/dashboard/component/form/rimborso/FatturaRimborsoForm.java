package it.cnr.missioni.dashboard.component.form.rimborso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.TipologiaSpesa.VoceSpesaEnum;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.validator.IValidatorManager;
import it.cnr.missioni.model.validator.ValidatorFatturaPastoEstera;
import it.cnr.missioni.model.validator.ValidatorFatturaPastoItalia;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * @author Salvia Vito
 */
public class FatturaRimborsoForm extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3473679597608380736L;

	private ElencoFattureTable elencoFattureTable;

	private List<TipologiaSpesa> listaTipologiaSpesaItalia = new ArrayList<TipologiaSpesa>();
	private List<TipologiaSpesa> listaTipologiaSpesaEstera = new ArrayList<TipologiaSpesa>();
	private FormFattura formFattura;
	private Missione missione;
	private Button reset;
	private Button ok;

	public FatturaRimborsoForm(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
		this.formFattura = new FormFattura(missione, isAdmin, enabled, modifica);
		this.missione = missione;
		addComponent(formFattura);
		// if ((isAdmin || !modifica) && !missione.getRimborso().isPagata()) {
		if (enabled) {
			HorizontalLayout l = buildFatturaButton();
			addComponent(l);
			setComponentAlignment(l, Alignment.MIDDLE_RIGHT);

		}
		addComponent(elencoFattureTable);

	}

	// Ogni fattura deve essere compresa tra le date di inizio e fine
	// missione
	public void setRangeDate() {
		formFattura.getDataField().setRangeStart(missione.getDatiPeriodoMissione().getInizioMissione().toDate());
		formFattura.getDataField().setRangeEnd(missione.getDatiPeriodoMissione().getFineMissione().toDate());
	}

	private HorizontalLayout buildFatturaButton() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		reset = new Button("Reset");
		reset.addStyleName(ValoTheme.BUTTON_PRIMARY);

		reset.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6490162510371608133L;

			@Override
			public void buttonClick(ClickEvent event) {
				formFattura.getFieldGroup().discard();
				for (Field<?> f : formFattura.getFieldGroup().getFields()) {
					((AbstractField<?>) f).setValidationVisible(false);
				}
				formFattura.getDataField().setValidationVisible(false);
				formFattura.aggiornaFatturaTab(new Fattura());

			}

		});

		ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4170493805638852036L;

			@Override
			public void buttonClick(ClickEvent event) {

				boolean check = true;

				formFattura.getDataField().setValidationVisible(true);
				for (Field<?> f : formFattura.getFieldGroup().getFields()) {
					((AbstractField<?>) f).setValidationVisible(true);
				}

				try {

					formFattura.getFieldGroup().commit();

				} catch (InvalidValueException | CommitException e) {

					check = false;
				}

				try {
					formFattura.getDataField().validate();
				} catch (InvalidValueException e) {
					check = false;
				}

				if (check) {
					BeanItem<Fattura> beanItem = (BeanItem<Fattura>) formFattura.getFieldGroup().getItemDataSource();
					Fattura new_fattura = beanItem.getBean();
					new_fattura.setImportoSpettante(new_fattura.getImporto());
					// se la fattura è nuova creo un ID
					if (new_fattura.getId() == null)
						new_fattura.setId(UUID.randomUUID().toString());
					new_fattura.setData(new DateTime(formFattura.getDataField().getValue()));
					new_fattura.setShortDescriptionTipologiaSpesa(
							formFattura.getTipologiaSpesaField().getItemCaption(new_fattura.getIdTipologiaSpesa()));
					formFattura.getMissione().getRimborso().getMappaFattura().put(new_fattura.getId(), new_fattura);
					Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);

					// ripulisco la form
					for (Field<?> f : formFattura.getFieldGroup().getFields()) {
						((AbstractField<?>) f).setValidationVisible(false);
					}
					// aggiorno la tabella
					formFattura.aggiornaFatturaTab(new Fattura());
					elencoFattureTable.aggiornaTable(
							new ArrayList<Fattura>(formFattura.getMissione().getRimborso().getMappaFattura().values()));
					elencoFattureTable.aggiornaTotale(formFattura.getMissione().getRimborso().getTotale());

				} else {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();
		layout.addComponents(ok, reset);
		layout.setComponentAlignment(ok, Alignment.BOTTOM_RIGHT);
		layout.setComponentAlignment(reset, Alignment.BOTTOM_RIGHT);

		return layout;
	}

	class FormFattura extends IForm.FormAbstract<Fattura> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1838428837308262700L;
		private Missione missione;
		private TextField numeroFatturaField;
		private ComboBox tipologiaSpesaField;
		private TextField importoField;
		private DateField dataField;
		private TextField valutaField;
		private TextField altroField;

		public FormFattura(Missione missione, boolean isAdmin, boolean enabled, boolean modifica) {
			super(new Fattura(), isAdmin, enabled, modifica);
			this.bean = new Fattura();
			this.missione = missione;
			setFieldGroup(new BeanFieldGroup<Fattura>(Fattura.class));
			getFieldGroup().setEnabled(enabled);
			buildFieldGroup();
			buildTab();
		}

		/**
		 * @return
		 * @throws CommitException
		 * @throws InvalidValueException
		 */
		@Override
		public Fattura validate() throws CommitException, InvalidValueException {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * 
		 */
		@Override
		public void addValidator() {

			dataField.addValidator(new Validator() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1892203509953963434L;

				@Override
				public void validate(Object value) throws InvalidValueException {

					if (dataField.getValue() == null)
						throw new InvalidValueException(Utility.getMessage("field_required"));
					else {
						DateTime d = new DateTime(dataField.getValue());
						// Verifica che la fattura sia inserita nel range tra
						// inizio e fine missione
						if (d.isBefore(missione.getDatiPeriodoMissione().getInizioMissione())
								|| d.isAfter(missione.getDatiPeriodoMissione().getFineMissione()))
							throw new InvalidValueException(Utility.getMessage("date_range_start"));
					}

				}
			});

			tipologiaSpesaField.addValidator(new Validator() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -5034812581780310007L;

				@Override
				public void validate(Object value) throws InvalidValueException {
					try {
						checkOccorrenzeFattura();
					} catch (Exception e) {
						throw new InvalidValueException(Utility.getMessage("error_occorrenze"));

					}
				}

			});

		}

		private void checkOccorrenzeFattura() throws Exception {
			TipologiaSpesaStore t = null;
			DateTime dateFattura;
			int n = 0;
			if (formFattura.getTipologiaSpesaField().getValue() != null
					&& formFattura.getDataField().getValue() != null) {
				dateFattura = new DateTime(formFattura.getDataField().getValue());

				try {
					t = ClientConnector.getTipologiaSpesa(TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder()
							.withId(formFattura.getTipologiaSpesaField().getValue().toString()));
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				if (t.getTipologiaSpesa().get(0).getVoceSpesa() == VoceSpesaEnum.PASTO) {

					DateTime dateTo = new DateTime(dateFattura.getYear(), dateFattura.getMonthOfYear(),
							dateFattura.getDayOfMonth(), 0, 0);
					DateTime datFrom = new DateTime(dateFattura.getYear(), dateFattura.getMonthOfYear(),
							dateFattura.getDayOfMonth(), 23, 59);

					// numero di fatture inserite per quel giorno
					n = missione.getRimborso().getNumberOfFatturaInDay(dateTo, datFrom,
							formFattura.getTipologiaSpesaField().getValue().toString()).size();

					int maxOccorrenze = 0;
					IValidatorManager v;

					if (missione.isMissioneEstera()) {
						v = new ValidatorFatturaPastoEstera(new DateTime(formFattura.getDataField().getValue()),
								missione.getDatiPeriodoMissione().getInizioMissione(),
								missione.getDatiPeriodoMissione().getFineMissione(),
								missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata(),
								missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno());

					} else {
						v = new ValidatorFatturaPastoItalia(new DateTime(formFattura.getDataField().getValue()),
								missione.getDatiPeriodoMissione().getInizioMissione(),
								missione.getDatiPeriodoMissione().getFineMissione());
					}
					v.initialize();
					maxOccorrenze = v.getMaxOccorrenze();

					if (maxOccorrenze <= n && !missione.getRimborso().getMappaFattura().containsKey(
							((BeanItem<Fattura>) formFattura.getFieldGroup().getItemDataSource()).getBean().getId()))
						throw new InvalidValueException(Utility.getMessage("error_occorrenze"));

				}

			}
		}

		/**
		 * 
		 */
		@Override
		public void addListener() {

			dataField.addBlurListener(new BlurListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 568983456000966911L;

				@Override
				public void blur(BlurEvent event) {
					try {
						tipologiaSpesaField.setValidationVisible(false);
						dataField.validate();
					} catch (Exception e) {
						dataField.setValidationVisible(true);
					}

				}
			});

			tipologiaSpesaField.addBlurListener(new BlurListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -7387400551551046370L;

				@Override
				public void blur(BlurEvent event) {
					try {
						tipologiaSpesaField.validate();
					} catch (Exception e) {
						tipologiaSpesaField.setValidationVisible(true);
					}
				}
			});

			if (missione.isMissioneEstera()) {

				dataField.addBlurListener(new BlurListener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 3158019137599500668L;

					@Override
					public void blur(BlurEvent event) {

						if (dataField.getValue() != null) {

							DateTime d = new DateTime((dataField.getValue().getTime()));

							if (d.compareTo(missione.getDatiPeriodoMissione().getInizioMissione().toLocalDateTime()
									.toDateTime()) >= 0
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

		}

		@Override
		public void buildTab() {

			bean = new Fattura();

			numeroFatturaField = (TextField) getFieldGroup().buildAndBind("Numero Fattura", "numeroFattura");
			tipologiaSpesaField = new ComboBox("Tipologia Spesa");
			tipologiaSpesaField.setValidationVisible(false);
			tipologiaSpesaField.setImmediate(true);

			getFieldGroup().bind(tipologiaSpesaField, "idTipologiaSpesa");

			importoField = (TextField) getFieldGroup().buildAndBind("Importo", "importo");
			valutaField = (TextField) getFieldGroup().buildAndBind("Valuta", "valuta");
			altroField = (TextField) getFieldGroup().buildAndBind("Altro", "altro");

			dataField = new DateField("Data");
			dataField.setDateOutOfRangeMessage("Data non possibile");
			dataField.setResolution(Resolution.MINUTE);
			dataField.setDateFormat("dd/MM/yyyy HH:mm");
			dataField.setValidationVisible(false);


			// carica la combo con tutte le voce di spesa italiane
			if (!missione.isMissioneEstera()) {
				getTipologiaSpesa(listaTipologiaSpesaItalia, null);
				buildTipologiaCombo(listaTipologiaSpesaItalia);

			}
			// preleva la lista di spesa ESTERA e aggiunge il listener sul DATE
			// FIELD
			if (missione.isMissioneEstera()) {
				String tipoTrattamento = null;
				if (missione.getDatiMissioneEstera()
						.getTrattamentoMissioneEsteraEnum() == TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO)
					tipoTrattamento = TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name();
				getTipologiaSpesa(listaTipologiaSpesaEstera, tipoTrattamento);
				addListener();
			}

			elencoFattureTable = new ElencoFattureTable(getFieldGroup(), dataField, missione);
			elencoFattureTable.aggiornaTable(new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
			elencoFattureTable.aggiornaTotale(missione.getRimborso().getTotale());

			if (enabled) {

				addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

				addComponent(dataField);
				addComponent(numeroFatturaField);
				addComponent(tipologiaSpesaField);
				addComponent(importoField);

				addComponent(altroField);
				addComponent(valutaField);

			}

			addComponent(elencoFattureTable);
			setComponentAlignment(elencoFattureTable, Alignment.MIDDLE_LEFT);

			addValidator();
			addListener();

		}

		/**
		 * 
		 * Carica le tipologia spesa in base ad ITALIA o ESTERA
		 * 
		 * @param tipo
		 * @param lista
		 */
		private void getTipologiaSpesa(List<TipologiaSpesa> lista, String tipoTrattamento) {
			try {

				TipologiaSpesaSearchBuilder t = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder()
						.withEstera(true).withItalia(true).withAll(true);
				if (tipoTrattamento != null){
					t.withTipoTrattamento(tipoTrattamento);
				}
				TipologiaSpesaStore tipologiaStore = ClientConnector.getTipologiaSpesa(t);

				if (tipologiaStore.getTotale() > 0) {
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

		public void aggiornaFatturaTab(Fattura fattura) {
			formFattura.getDataField().setValue(null);
			formFattura.getDataField().setValidationVisible(false);
			getFieldGroup().setItemDataSource(fattura);
		}

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

		/**
		 * @return the numeroFatturaField
		 */
		public TextField getNumeroFatturaField() {
			return numeroFatturaField;
		}

		/**
		 * @param numeroFatturaField
		 */
		public void setNumeroFatturaField(TextField numeroFatturaField) {
			this.numeroFatturaField = numeroFatturaField;
		}

		/**
		 * @return the tipologiaSpesaField
		 */
		public ComboBox getTipologiaSpesaField() {
			return tipologiaSpesaField;
		}

		/**
		 * @param tipologiaSpesaField
		 */
		public void setTipologiaSpesaField(ComboBox tipologiaSpesaField) {
			this.tipologiaSpesaField = tipologiaSpesaField;
		}

		/**
		 * @return the importoField
		 */
		public TextField getImportoField() {
			return importoField;
		}

		/**
		 * @param importoField
		 */
		public void setImportoField(TextField importoField) {
			this.importoField = importoField;
		}

		/**
		 * @return the dataField
		 */
		public DateField getDataField() {
			return dataField;
		}

		/**
		 * @param dataField
		 */
		public void setDataField(DateField dataField) {
			this.dataField = dataField;
		}

		/**
		 * @return the valutaField
		 */
		public TextField getValutaField() {
			return valutaField;
		}

		/**
		 * @param valutaField
		 */
		public void setValutaField(TextField valutaField) {
			this.valutaField = valutaField;
		}

		/**
		 * @return the altroField
		 */
		public TextField getAltroField() {
			return altroField;
		}

		/**
		 * @param altroField
		 */
		public void setAltroField(TextField altroField) {
			this.altroField = altroField;
		}

	}

}
