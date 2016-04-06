package it.cnr.missioni.dashboard.component.form.missione;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiAnticipoPagamenti;

/**
 * @author Salvia Vito
 */
public interface IAnticipazionePagamentoMissioneForm
        extends IForm<DatiAnticipoPagamenti, IAnticipazionePagamentoMissioneForm> {

    class AnticipazionePagamentoMissioneForm
            extends IForm.FormAbstract<DatiAnticipoPagamenti, IAnticipazionePagamentoMissioneForm>
            implements IAnticipazionePagamentoMissioneForm {

        /**
         *
         */
        private static final long serialVersionUID = -3064260423936491077L;
        private TextField importoDaTerziField;
        private TextField speseMissioniAnticipateField;
        private TextField numeroMandatoField;
        private CheckBox anticipazioniMonetarieField;
        private CheckBox rimborsoDaTerziField;

        private AnticipazionePagamentoMissioneForm() {
        }

        public static IAnticipazionePagamentoMissioneForm getAnticipazionePagamentoMissioneForm() {
            return new AnticipazionePagamentoMissioneForm();
        }

        public IAnticipazionePagamentoMissioneForm build() {
            setFieldGroup(new BeanFieldGroup<DatiAnticipoPagamenti>(DatiAnticipoPagamenti.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        public void buildTab() {
            anticipazioniMonetarieField = (CheckBox) getFieldGroup().buildAndBind("Anticipazioni Monetarie",
                    "anticipazioniMonetarie", CheckBox.class);
            numeroMandatoField = (TextField) getFieldGroup().buildAndBind("Numero Mandato CNR", "mandatoCNR");
            speseMissioniAnticipateField = (TextField) getFieldGroup()
                    .buildAndBind("Altre Spese di Missione Anticipate", "speseMissioniAnticipate");
            rimborsoDaTerziField = (CheckBox) getFieldGroup().buildAndBind("Rimborso da Terzi", "rimborsoDaTerzi",
                    CheckBox.class);
            importoDaTerziField = (TextField) getFieldGroup().buildAndBind("Importo da Terzi", "importoDaTerzi");
            addComponent(anticipazioniMonetarieField);
            addComponent(numeroMandatoField);
            addComponent(speseMissioniAnticipateField);
            addComponent(rimborsoDaTerziField);
            addComponent(importoDaTerziField);
            addValidator();
        }

        public DatiAnticipoPagamenti validate() throws CommitException, InvalidValueException {
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });
            getFieldGroup().commit();
            BeanItem<DatiAnticipoPagamenti> beanItem = (BeanItem<DatiAnticipoPagamenti>) getFieldGroup()
                    .getItemDataSource();
            DatiAnticipoPagamenti new_datiAnticipoPagamenti = beanItem.getBean();
            return new_datiAnticipoPagamenti;
        }

        /**
         *
         */
        @Override
        public void addValidator() {
            numeroMandatoField.addValidator(new Validator() {

                /**
                 *
                 */
                private static final long serialVersionUID = 7710611747082572703L;

                @Override
                public void validate(Object value) throws InvalidValueException {
                    String v = (String) value;
                    if ((value == null || v.equals("")) && anticipazioniMonetarieField.getValue()) {
                        throw new InvalidValueException(Utility.getMessage("numero_mandato_missione_errore"));
                    }
                }
            });

            importoDaTerziField.addValidator(new Validator() {

                /**
                 *
                 */
                private static final long serialVersionUID = -5658908188744784855L;

                @Override
                public void validate(Object value) throws InvalidValueException {
                    Double v = (Double) value;
                    if ((v == null || v == 0) && rimborsoDaTerziField.getValue()) {
                        throw new InvalidValueException(Utility.getMessage("importo_da_terzi_errore"));
                    }
                }
            });

        }

        /**
         *
         */
        @Override
        public void addListener() {
        }

        /**
         * @return
         */
        @Override
        protected IAnticipazionePagamentoMissioneForm self() {
            return this;
        }
    }
}
