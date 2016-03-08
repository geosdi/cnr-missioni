package it.cnr.missioni.dashboard.component.form.rimborso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.TipologiaSpesa.TipoSpesaEnum;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * @author Salvia Vito
 */
public class FatturaRimborsoForm extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3473679597608380736L;

	private ElencoFattureTable elencoFattureTable;


	private List<TipologiaSpesa> listaTipologiaSpesaItalia = new ArrayList<TipologiaSpesa>();
	private List<TipologiaSpesa> listaTipologiaSpesaEstera = new ArrayList<TipologiaSpesa>();
	private FormFattura formFattura;
	private Button reset;
	private Button ok;

	public FatturaRimborsoForm(Missione missione, boolean isAdmin,boolean enabled,boolean modifica) {
		this.formFattura = new FormFattura(missione, isAdmin, enabled, modifica);
		addComponent(formFattura);
		if((isAdmin || !modifica) && !missione.getRimborso().isPagata()){
			HorizontalLayout l = buildFatturaButton();
			addComponent(l);
			setComponentAlignment(l, Alignment.MIDDLE_RIGHT);

		}
		addComponent(elencoFattureTable);

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

					// checkMassimale(tipologiaSpesaField.getValue().toString());

					BeanItem<Fattura> beanItem = (BeanItem<Fattura>) formFattura.getFieldGroup().getItemDataSource();
					Fattura new_fattura = beanItem.getBean();

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
					elencoFattureTable
							.aggiornaTable(new ArrayList<Fattura>(formFattura.getMissione().getRimborso().getMappaFattura().values()));
					elencoFattureTable.aggiornaTotale(formFattura.getMissione().getRimborso().getTotale());
				} else {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();
		layout.addComponents(ok,reset);
		layout.setComponentAlignment(ok,Alignment.BOTTOM_RIGHT);
		layout.setComponentAlignment(reset,Alignment.BOTTOM_RIGHT);

		return layout;
	}




	class FormFattura extends IForm.FormAbstract<Fattura>{

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


		
		public FormFattura(Missione missione, boolean isAdmin,boolean enabled,boolean modifica) {
			super(new Fattura(),isAdmin,enabled,modifica);
			this.bean = new Fattura();
			this.missione = missione;
			setFieldGroup(new BeanFieldGroup<Fattura>(Fattura.class));
			getFieldGroup().setEnabled(enabled);
			buildFieldGroup();
			buildTab();		}
		
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

			dataField.addBlurListener(new BlurListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 568983456000966911L;

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

				/**
				 * 
				 */
				private static final long serialVersionUID = 1892203509953963434L;

				@Override
				public void validate(Object value) throws InvalidValueException {

					if (dataField.getValue() == null)
						throw new InvalidValueException(Utility.getMessage("field_required"));

				}
			});

//			importoField.addBlurListener(new BlurListener() {
//
//				/**
//				 * 
//				 */
//				private static final long serialVersionUID = 496541445715331048L;
//
//				@Override
//				public void blur(BlurEvent event) {
//
//					if (tipologiaSpesaField.getValue() != null)
//						checkMassimale(tipologiaSpesaField.getValue().toString());
//
//				}
//			});

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
				private static final long serialVersionUID = 3158019137599500668L;

				@Override
				public void blur(BlurEvent event) {

					if (dataField.getValue() != null) {

						DateTime d = new DateTime((dataField.getValue().getTime()));

						if (d.compareTo(
								missione.getDatiPeriodoMissione().getInizioMissione().toLocalDateTime().toDateTime()) >= 0
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
			dataField.setRangeStart(new DateTime().toDate());
			dataField.setDateOutOfRangeMessage("Data non possibile");
			dataField.setResolution(Resolution.MINUTE);
			dataField.setDateFormat("dd/MM/yyyy HH:mm");
			dataField.setValidationVisible(false);
			// Ogni fattura deve essere compresa tra le date di inizio e fine
			// missione
			dataField.setRangeStart(missione.getDatiPeriodoMissione().getInizioMissione().toDate());
			dataField.setRangeEnd(missione.getDatiPeriodoMissione().getFineMissione().toDate());

			getTipologiaSpesa(TipoSpesaEnum.ITALIA.name(), listaTipologiaSpesaItalia);

			// carica la combo con tutte le voce di spesa italiane
			if (!missione.isMissioneEstera()) {
				buildTipologiaCombo(listaTipologiaSpesaItalia);

			}
			// preleva la lista di spesa ESTERA e aggiunge il listener sul DATE
			// FIELD
			if (missione.isMissioneEstera()) {
				getTipologiaSpesa(TipoSpesaEnum.ESTERA.name(), listaTipologiaSpesaEstera);
				addListener();
			}

			addValidator();
			

			elencoFattureTable = new ElencoFattureTable(getFieldGroup(),dataField, missione);
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

		}
		
		private void checkMassimale(String id,String livello) {

			try {
				TipologiaSpesaStore tipologiaStore = ClientConnector
						.getTipologiaSpesa(TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder().withId(id));


					TipologiaSpesa tipologiaSpesa = tipologiaStore.getTipologiaSpesa().get(0);
					if (tipologiaSpesa.isCheckMassimale()) {

						String areaGeografica;
						if (missione.isMissioneEstera()) {
							Nazione nazione = ClientConnector
									.getNazione(
											NazioneSearchBuilder.getNazioneSearchBuilder().withId(missione.getIdNazione()))
									.getNazione().get(0);
							areaGeografica = nazione.getAreaGeografica().name();
						} else {
							areaGeografica = AreaGeograficaEnum.ITALIA.name();
						}
						MassimaleStore massimaleStore = ClientConnector
								.getMassimale(MassimaleSearchBuilder.getMassimaleSearchBuilder()
										.withLivello(livello)
										.withAreaGeografica(areaGeografica)
										.withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name()));

						if (massimaleStore.getTotale() > 0) {
							Massimale massimale = massimaleStore.getMassimale().get(0);
							
							
							
							NumberFormat format =  NumberFormat.getInstance(Locale.ITALY);
							format.setGroupingUsed(false);
							format.setMaximumFractionDigits(2);
							format.setMinimumFractionDigits(2);
							
							
							double number = format.parse(importoField.getValue()).doubleValue();

							if (number > massimale.getValue())
								importoField.setValue(format.format(massimale.getValue()));
						}

					}

			} catch (Exception e) {
				Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
						Type.ERROR_MESSAGE);
			}

		}
		
		
		/**
		 * 
		 * Carica le tipologia spesa in base ad ITALIA o ESTERA
		 * 
		 * @param tipo
		 * @param lista
		 */
		private void getTipologiaSpesa(String tipo, List<TipologiaSpesa> lista) {
			try {
				TipologiaSpesaStore tipologiaStore = ClientConnector
						.getTipologiaSpesa(TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder().withTipo(tipo));

				if (tipologiaStore != null) {
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
	


//	/**
//	 * @return
//	 * @throws CommitException
//	 */
//	@Override
//	public Fattura validate() throws CommitException,InvalidValueException {
//		// TODO Auto-generated method stub
//		return null;
//	}

}

