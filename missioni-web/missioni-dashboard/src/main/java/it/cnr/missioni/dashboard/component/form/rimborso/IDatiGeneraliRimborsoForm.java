package it.cnr.missioni.dashboard.component.form.rimborso;

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
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IRimborsoKmSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;
import org.joda.time.DateTime;

import java.text.NumberFormat;
import java.util.Date;

/**
 * @author Salvia Vito
 */
public interface IDatiGeneraliRimborsoForm extends IForm<Rimborso, IDatiGeneraliRimborsoForm> {

    /**
     * @param missione
     * @return {@link IDatiGeneraliRimborsoForm }
     */
    IDatiGeneraliRimborsoForm withMissione(Missione missione);

    public class DatiGeneraliRimborsoForm extends IForm.FormAbstract<Rimborso, IDatiGeneraliRimborsoForm>
            implements IDatiGeneraliRimborsoForm {

        /**
         *
         */
        private static final long serialVersionUID = 4676249391052429281L;
        private TextField importoDaTerziField;
        private TextField totKmField;
        private TextField mandatoPagamentoField;
        private CheckBox pagataField;
        private TextField totaleDovutoField;
        private TextField avvisoPagamentoField;
        private TextField siglaField;
        private DateField dataFineMissioneField;
        private DateField dataInizioMissioneField;
        private Label anticipoPagamentoLabel;
        private Label labelTotRimborsoKm = new Label("Tot. Rimborso km: ");
        private Missione missione;
        private boolean mezzoProprio;
        private double totRimborsoKm = 0.0;

        private DatiGeneraliRimborsoForm() {
        }

        public static IDatiGeneraliRimborsoForm getDatiGeneraliRimborsoForm() {
            return new DatiGeneraliRimborsoForm();
        }

        public IDatiGeneraliRimborsoForm build() {
            this.mezzoProprio = missione.isMezzoProprio();
            setFieldGroup(new BeanFieldGroup<Rimborso>(Rimborso.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        public IDatiGeneraliRimborsoForm withMissione(Missione missione) {
            this.missione = missione;
            return self();
        }

        public void buildTab() {
            importoDaTerziField = (TextField) getFieldGroup().buildAndBind("Importo da Terzi", "importoDaTerzi");
            avvisoPagamentoField = (TextField) getFieldGroup().buildAndBind("Avviso di Pagamento", "avvisoPagamento");
            siglaField = (TextField) getFieldGroup().buildAndBind("Sigla", "sigla");
            dataFineMissioneField = new DateField("Data fine missione");
            dataFineMissioneField.setDateOutOfRangeMessage("Data non possibile");
            dataFineMissioneField.setResolution(Resolution.MINUTE);
            dataFineMissioneField.setDateFormat("dd/MM/yyyy HH:mm");
            dataFineMissioneField.setValidationVisible(false);
            dataFineMissioneField.setRangeStart(missione.getDatiPeriodoMissione().getInizioMissione().toDate());
            dataFineMissioneField.setValue(missione.getDatiPeriodoMissione().getFineMissione().toDate());
            dataFineMissioneField.setReadOnly(!enabled);
            
            dataInizioMissioneField = new DateField("Data fine missione");
            dataInizioMissioneField.setDateOutOfRangeMessage("Data non possibile");
            dataInizioMissioneField.setResolution(Resolution.MINUTE);
            dataInizioMissioneField.setDateFormat("dd/MM/yyyy HH:mm");
            dataInizioMissioneField.setValidationVisible(false);
            dataInizioMissioneField.setValue(missione.getDatiPeriodoMissione().getInizioMissione().toDate());
            dataInizioMissioneField.setReadOnly(!enabled);
            
            totKmField = (TextField) getFieldGroup().buildAndBind("Km da rimborsare", "totKm");
            if (isAdmin || modifica) {
                mandatoPagamentoField = (TextField) getFieldGroup().buildAndBind("Mandato di Pagamento",
                        "mandatoPagamento");
                pagataField = (CheckBox) getFieldGroup().buildAndBind("Pagata", "pagata", CheckBox.class);
                totaleDovutoField = (TextField) getFieldGroup().buildAndBind("Tot.Dovuto", "totaleDovuto");
                addComponent(pagataField);
                addComponent(mandatoPagamentoField);
                addComponent(totaleDovutoField);
                addComponent(siglaField);
            }
            addComponent(importoDaTerziField);
            addComponent(avvisoPagamentoField);
            addComponent(dataInizioMissioneField);
            addComponent(dataFineMissioneField);
            if (mezzoProprio) {
                addComponent(totKmField);
                addComponent(labelTotRimborsoKm);
                setTotaleRimborsoKM();
            }
            if (missione.getDatiAnticipoPagamenti().isInserted()) {
                anticipoPagamentoLabel = new Label();
                anticipoPagamentoLabel.setValue(
                        "Anticipo di Pagamento: " + missione.getDatiAnticipoPagamenti().getSpeseMissioniAnticipate());
                addComponent(anticipoPagamentoLabel);
            }
            addListener();
            addValidator();
        }

        private void setTotaleRimborsoKM() {
            try {
                RimborsoKmStore rimborsoKmStore = ClientConnector
                        .getRimborsoKm(IRimborsoKmSearchBuilder.RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder());
                double rimborso = 0.0;
                if (rimborsoKmStore.getTotale() > 0) {
                    if (totKmField.getValue() != null) {
                        NumberFormat f = NumberFormat.getInstance();
                        double number = f.parse(totKmField.getValue()).doubleValue();
                        rimborso = number * rimborsoKmStore.getRimborsoKm().get(0).getValue();
                    }
                }
                totRimborsoKm = rimborso;
                setTotRimborsoKm(totRimborsoKm);
                labelTotRimborsoKm.setValue("Tot. Rimborso km: " + Utility.getDoubleDecimalFormat(totRimborsoKm));
            } catch (Exception e) {
                Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                        Type.ERROR_MESSAGE);
            }
        }

        public void addListener() {
            if (isAdmin)
                pagataField.addValueChangeListener(new ValueChangeListener() {

                    /**
                     *
                     */
                    private static final long serialVersionUID = 3533033215989257332L;

                    @Override
                    public void valueChange(ValueChangeEvent event) {
                        if (!pagataField.getValue()) {
                            mandatoPagamentoField.setValue(null);
                            totaleDovutoField.setValue(null);
                            mandatoPagamentoField.setValidationVisible(false);
                            totaleDovutoField.setValidationVisible(false);

                        }
                    }

                });

            dataFineMissioneField.addBlurListener(new BlurListener() {

                /**
                 *
                 */
                private static final long serialVersionUID = 9131736226036796594L;

                @Override
                public void blur(BlurEvent event) {
                    try {
                        dataFineMissioneField.validate();
                    } catch (Exception e) {
                        dataFineMissioneField.setValidationVisible(true);
                    }

                }
            });

            dataInizioMissioneField.addBlurListener(new BlurListener() {

                /**
                 *
                 */
                private static final long serialVersionUID = 9131736226036796594L;

                @Override
                public void blur(BlurEvent event) {
                    try {
                    	dataInizioMissioneField.validate();
                    } catch (Exception e) {
                    	dataInizioMissioneField.setValidationVisible(true);
                    }

                }
            });
            
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
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });
            dataFineMissioneField.setValidationVisible(true);
            dataInizioMissioneField.setValidationVisible(true);
            getFieldGroup().commit();
            dataFineMissioneField.validate();
            dataInizioMissioneField.validate();

            BeanItem<Rimborso> beanItem = (BeanItem<Rimborso>) getFieldGroup().getItemDataSource();
            bean = beanItem.getBean();
            bean.setRimborsoKm(getTotRimborsoKm());
            missione.getDatiPeriodoMissione().setFineMissione(new DateTime(dataFineMissioneField.getValue()));
            missione.getDatiPeriodoMissione().setInizioMissione(new DateTime(dataInizioMissioneField.getValue()));

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

            // la data di ritorno posteriore alla data di andata
            dataFineMissioneField.addValidator(new Validator() {

                /**
                 *
                 */
                private static final long serialVersionUID = -5030970150414733738L;

                @Override
                public void validate(Object value) throws InvalidValueException {

                    if (value == null)
                        throw new InvalidValueException(Utility.getMessage("field_required"));
                    else {
                        DateTime data = new DateTime((Date) value);
                        if (data.isBefore(missione.getDatiPeriodoMissione().getInizioMissione()))
                            throw new InvalidValueException(Utility.getMessage("data_error"));
                    }
                }
            });

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
        }

        /**
         * @return
         */
        @Override
        protected IDatiGeneraliRimborsoForm self() {
            return this;
        }
    }

}
