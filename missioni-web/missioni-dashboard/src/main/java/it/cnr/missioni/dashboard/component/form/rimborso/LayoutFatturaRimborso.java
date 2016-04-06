package it.cnr.missioni.dashboard.component.form.rimborso;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.event.DashboardEvent.ComboBoxListaFatturaUpdatedEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.ITipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.model.configuration.TipologiaSpesa.VoceSpesaEnum;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.validator.IValidatorGenericFattura;
import it.cnr.missioni.model.validator.IValidatorPastoFattura;
import it.cnr.missioni.model.validator.IValidatorPastoFattura.ValidatorPastoFattura;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Salvia Vito
 */
public class LayoutFatturaRimborso extends VerticalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 3473679597608380736L;

    private static ElencoFattureTable elencoFattureTable;

    private static List<TipologiaSpesa> listaTipologiaSpesaItalia = new ArrayList<TipologiaSpesa>();
    private static List<TipologiaSpesa> listaTipologiaSpesaEstera = new ArrayList<TipologiaSpesa>();
    private IFormFattura formFattura;
    private Missione missione;
    private Button reset;
    private Button ok;
    private boolean isAdmin;
    private boolean enabled;
    private boolean modifica;
    private static TableTypeEnum tableTypeEnum = TableTypeEnum.LIGHT;
    
    public enum TableTypeEnum{
    	FULL,LIGHT;
    }

    private LayoutFatturaRimborso(){}
    
    public static LayoutFatturaRimborso getFatturaRimborsoForm(){
    	return new LayoutFatturaRimborso();
    }
    
    public LayoutFatturaRimborso withMissione(Missione missione){
    	this.missione = missione;
    	return self();
    }
    
    public LayoutFatturaRimborso withIsAdmin(boolean isAdmin){
    	this.isAdmin = isAdmin;
    	return self();
    }
    
    public LayoutFatturaRimborso withEnabled(boolean enabled){
    	this.enabled = enabled;
    	return self();
    }
    
    public LayoutFatturaRimborso withModifica(boolean modifica){
    	this.modifica = modifica;
    	return self();
    }
    
    public LayoutFatturaRimborso withTableType(TableTypeEnum tableTypeEnum){
    	this.tableTypeEnum = tableTypeEnum;
    	return self();
    }
    
    public LayoutFatturaRimborso build(){
        this.formFattura = IFormFattura.FormFattura.getFormFattura().withModifica(modifica).withEnabled(enabled)
                .withIsAdmin(isAdmin).withMissione(missione).build();
        addComponent(formFattura);
        if (enabled) {
            HorizontalLayout l = buildFatturaButton();
            addComponent(l);
            setComponentAlignment(l, Alignment.MIDDLE_RIGHT);
        }
        addComponent(elencoFattureTable);
        return self();
    }
    
    public LayoutFatturaRimborso self(){
    	return this;
    }

    // Ogni fattura deve essere compresa tra le date di inizio e fine
    // missione
    public void setRangeDate() {
        // formFattura.getDataField().setRangeStart(missione.getDatiPeriodoMissione().getInizioMissione().toDate());
        formFattura.getDataField().setRangeEnd(missione.getDatiPeriodoMissione().getFineMissione().toDate());
    }

    public void discardChange() {
        formFattura.getFieldGroup().discard();
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
                    // Se estera ricancello la combobox
                    if (missione.isMissioneEstera())
                        formFattura.getTipologiaSpesaField().removeAllItems();
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
    


    public interface IFormFattura extends IForm<Fattura, IFormFattura> {

        IFormFattura withMissione(Missione missione);

        /**
         * @return {@link Missione }
         */
        public Missione getMissione();

        /**
         * @param missione
         */
        public void setMissione(Missione missione);

        /**
         * @return {@link TextField }
         */
        public TextField getNumeroFatturaField();

        /**
         * @param numeroFatturaField
         */
        public void setNumeroFatturaField(TextField numeroFatturaField);

        /**
         * @return {@link ComboBox }
         */
        public ComboBox getTipologiaSpesaField();

        /**
         * @param tipologiaSpesaField
         */
        public void setTipologiaSpesaField(ComboBox tipologiaSpesaField);

        /**
         * @return {@link TextField }
         */
        public TextField getImportoField();

        /**
         * @param importoField
         */
        public void setImportoField(TextField importoField);

        /**
         * @return {@link DateField }
         */
        public DateField getDataField();

        /**
         * @param dataField
         */
        public void setDataField(DateField dataField);

        /**
         * @return {@link TextField }
         */
        public TextField getValutaField();

        /**
         * @param valutaField
         */
        public void setValutaField(TextField valutaField);

        /**
         * @return {@link TextField }
         */
        public TextField getAltroField();

        /**
         * @param altroField
         */
        public void setAltroField(TextField altroField);

        /**
         * @param fattura
         */
        void aggiornaFatturaTab(Fattura fattura);

        class FormFattura extends IForm.FormAbstract<Fattura, IFormFattura> implements IFormFattura {

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

            private FormFattura() {
            }

            public static IFormFattura getFormFattura() {
                return new FormFattura();
            }

            public IFormFattura build() {
                this.bean = new Fattura();
                setFieldGroup(new BeanFieldGroup<Fattura>(Fattura.class));
                getFieldGroup().setEnabled(enabled);
                buildFieldGroup();
                buildTab();
                return self();
            }

            public IFormFattura withMissione(Missione missione) {
                this.missione = missione;
                return self();
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
                dataField.setResolution(Resolution.HOUR);
                dataField.setDateFormat("dd/MM/yyyy");
                dataField.setValidationVisible(false);
                dataField.setImmediate(true);
                getTipologiaSpesa(listaTipologiaSpesaItalia, false, false);
                if (missione.isMissioneEstera())
                    getTipologiaSpesa(listaTipologiaSpesaEstera,
                            missione.getDatiMissioneEstera()
                                    .getTrattamentoMissioneEsteraEnum() == TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO,
                            true);
                else
                    buildTipologiaCombo(listaTipologiaSpesaItalia);
                elencoFattureTable = new ElencoFattureTable(getFieldGroup(), dataField, missione);
                buildTable(tableTypeEnum);
//                elencoFattureTable
//                        .aggiornaTable(new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
                elencoFattureTable.aggiornaTotale(missione.getRimborso().getTotale());
                if (enabled) {
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
            
            private void buildTable(TableTypeEnum tableTypeEnum){
            	switch(tableTypeEnum){
            	case FULL:
                    elencoFattureTable.aggiornaTableRiepilogo(
                            new ArrayList<Fattura>(getMissione().getRimborso().getMappaFattura().values()));
                    break;
            	case LIGHT:
                    elencoFattureTable.aggiornaTable(
                            new ArrayList<Fattura>(getMissione().getRimborso().getMappaFattura().values()));
                    break;
                    default :
                    	break;
            	}
            }

            /**
             * @return
             * @throws CommitException
             * @throws InvalidValueException
             */
            @Override
            public Fattura validate() throws CommitException, InvalidValueException {
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
                    private static final long serialVersionUID = -2216627168202742197L;

                    @Override
                    public void validate(Object value) throws InvalidValueException {
                        if (dataField.getValue() != null) {
                            DateTime d = new DateTime(dataField.getValue());
                            if (
                                // d.isBefore(missione.getDatiPeriodoMissione().getInizioMissione())
                                // ||
                                    d.isAfter(missione.getDatiPeriodoMissione().getFineMissione()))
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
                            checkFattura();
                        } catch (Exception e) {
                            throw new InvalidValueException(e.getMessage());
                        }
                    }

                });
            }

            private void checkFattura() throws Exception {
                TipologiaSpesaStore t = null;
                DateTime dateFattura;
                if (getTipologiaSpesaField().getValue() != null && getDataField().getValue() != null) {
                    dateFattura = new DateTime(getDataField().getValue(), DateTimeZone.UTC)
                            .withHourOfDay(missione.getDatiPeriodoMissione().getInizioMissione().getHourOfDay())
                            .withMinuteOfHour(missione.getDatiPeriodoMissione().getInizioMissione().getMinuteOfHour())
                            .plusMinutes(1);

                    try {
                        t = ClientConnector.getTipologiaSpesa(ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                                .getTipologiaSpesaSearchBuilder()
                                .withId(getTipologiaSpesaField().getValue().toString()));
                    } catch (Exception e) {
                        Utility.getNotification(Utility.getMessage("error_message"),
                                Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
                    }
                    TipologiaSpesa tipologiaSpesa = t.getTipologiaSpesa().get(0);
                    checkTipologiaSpesa(tipologiaSpesa.getVoceSpesa(), tipologiaSpesa, dateFattura);
                }
            }

            /**
             * Controllo per la tipologia di spesa se i dati sono stati inseriti
             * correttamente
             *
             * @param voceSpesa
             * @param tipologiaSpesa
             * @param dateFattura
             * @throws Exception
             */
            private void checkTipologiaSpesa(VoceSpesaEnum voceSpesa, TipologiaSpesa tipologiaSpesa,
                    DateTime dateFattura) throws Exception {
                boolean check = true;
                String message = null;

                switch (voceSpesa) {
                    case PASTO:
                        IValidatorPastoFattura validatorPasto = ValidatorPastoFattura.getValidatorPastoFattura()
                                .withTipologiaSpesa(tipologiaSpesa)
                                .withFattura(((BeanItem<Fattura>) getFieldGroup().getItemDataSource()).getBean())
                                .withMissione(missione).withDataFattura(dateFattura);
                        check = validatorPasto.build();
                        message = validatorPasto.getMessage();
                        break;
                    case TRASPORTO:
                    case ALLOGGIO:
                    case RIMBORSO_KM:
                    case ALTRO:
                        IValidatorGenericFattura genericValidator = IValidatorGenericFattura.GenericValidatorFattura
                                .getGenericValidatorFattura().withMissione(missione).withTipologiaSpesa(tipologiaSpesa)
                                .withDataFattura(dateFattura)
                                .withFattura(((BeanItem<Fattura>) getFieldGroup().getItemDataSource()).getBean());
                        check = genericValidator.build();
                        message = genericValidator.getMessage();
                        break;
                    default:
                        break;
                }
                if (!check)
                    throw new InvalidValueException(Utility.getMessage(message));
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

                    dataField.addValueChangeListener(new ValueChangeListener() {

                        /**
                         *
                         */
                        private static final long serialVersionUID = 3158019137599500668L;

                        @Override
                        public void valueChange(ValueChangeEvent event) {
                            if (dataField.getValue() != null) {

                                DateTime d = new DateTime((dataField.getValue().getTime()));

                                caricaListaFattura(d);
                            }
                        }

                    });
                }

            }

            // Carica la combobox in base alal data selezionata per la missione
            // estera
            public void caricaListaFattura(DateTime d) {
                if (d.isAfter(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata())
                        && d.isBefore(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno())) {
                    buildTipologiaCombo(listaTipologiaSpesaEstera);
                } else {
                    buildTipologiaCombo(listaTipologiaSpesaItalia);

                }
            }

            // Quando viene selezionato una fattura carico la lista corretta in
            // base
            // alla data della fattura, in caso di missione estera
            @Subscribe
            public void aggiornaListaFattura(final ComboBoxListaFatturaUpdatedEvent event) {
                caricaListaFattura(event.getFattura().getData());
                tipologiaSpesaField.select(event.getFattura().getIdTipologiaSpesa());
            }

            /**
             * Carica le tipologia spesa in base ad ITALIA o ESTERA
             *
             * @param lista
             * @param tam
             * @param estera
             */
            private void getTipologiaSpesa(List<TipologiaSpesa> lista, boolean tam, boolean estera) {
                try {

                    ITipologiaSpesaSearchBuilder t = ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                            .getTipologiaSpesaSearchBuilder().withEstera(estera).withItalia(!estera).withAll(true);
                    if (tam) {
                        t.withTipoTrattamento(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name());
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
                getDataField().setValue(null);
                getDataField().setValidationVisible(false);
                getFieldGroup().setItemDataSource(fattura);
            }

            /**
             * @return {@link Missione }
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
             * @return {@link TextField }
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
             * @return {@link ComboBox }
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
             * @return {@link TextField }
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
             * @return {@link DateField }
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
             * @return {@link TextField }
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
             * @return {@link TextField }
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

            /**
             * @return {@link IFormFattura }
             */
            @Override
            protected IFormFattura self() {
                return this;
            }

        }
    }

}
