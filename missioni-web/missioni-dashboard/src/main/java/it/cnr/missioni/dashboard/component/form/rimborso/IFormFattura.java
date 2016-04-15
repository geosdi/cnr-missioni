package it.cnr.missioni.dashboard.component.form.rimborso;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

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
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
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

/**
 * @author Salvia Vito
 */
public interface IFormFattura extends IForm<Fattura, IFormFattura> {

    IFormFattura withMissione(Missione missione);

    /**
     * @return {@link Missione }
     */
    public Missione getMissione();

    /**
     * @return {@link ComboBox }
     */
    public ComboBox getTipologiaSpesaField();

    /**
     * @return {@link DateField }
     */
    public DateField getDataField();

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
        private List<TipologiaSpesa> listaTipologiaSpesaItalia = new ArrayList<TipologiaSpesa>();
        private List<TipologiaSpesa> listaTipologiaSpesaEstera = new ArrayList<TipologiaSpesa>();

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
            numeroFatturaField = (TextField) getFieldGroup().buildAndBind("Numero Giustificativo", "numeroFattura");
            tipologiaSpesaField = new ComboBox("Tipologia Spesa");
            tipologiaSpesaField.setValidationVisible(false);
            tipologiaSpesaField.setImmediate(true);
            getFieldGroup().bind(tipologiaSpesaField, "idTipologiaSpesa");
            importoField = (TextField) getFieldGroup().buildAndBind("Importo", "importo");
            valutaField = (TextField) getFieldGroup().buildAndBind("Valuta", "valuta");
            altroField = (TextField) getFieldGroup().buildAndBind("Altro\\Descrizione", "altro");
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
            if (enabled) {
                addComponent(dataField);
                addComponent(numeroFatturaField);
                addComponent(tipologiaSpesaField);
                addComponent(importoField);
                addComponent(altroField);
                addComponent(valutaField);
            }
            addValidator();
            addListener();
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
            if (tipologiaSpesaField.getValue() != null && dataField.getValue() != null) {
                dateFattura = new DateTime(dataField.getValue(), DateTimeZone.UTC)
                        .withHourOfDay(missione.getDatiPeriodoMissione().getInizioMissione().getHourOfDay())
                        .withMinuteOfHour(missione.getDatiPeriodoMissione().getInizioMissione().getMinuteOfHour())
                        .plusMinutes(1);
                try {
                    t = ClientConnector.getTipologiaSpesa(ITipologiaSpesaSearchBuilder.TipologiaSpesaSearchBuilder
                            .getTipologiaSpesaSearchBuilder()
                            .withId(tipologiaSpesaField.getValue().toString()));
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
            dataField.setValue(null);
            dataField.setValidationVisible(false);
            getFieldGroup().setItemDataSource(fattura);
        }

        /**
         * @return {@link Missione }
         */
        public Missione getMissione() {
            return missione;
        }

        /**
         * @return {@link ComboBox }
         */
        public ComboBox getTipologiaSpesaField() {
            return tipologiaSpesaField;
        }

        /**
         * @return {@link DateField }
         */
        public DateField getDataField() {
            return dataField;
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
