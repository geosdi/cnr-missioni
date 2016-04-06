package it.cnr.missioni.dashboard.component.form.anticipopagamenti;

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
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;

/**
 * @author Salvia Vito
 */
public interface IDatiAnticipoPagamentoForm extends IForm<DatiAnticipoPagamenti, IDatiAnticipoPagamentoForm> {

    /**
     * @param trattamentoMissioneEstera
     * @return {@link IDatiAnticipoPagamentoForm }
     */
    IDatiAnticipoPagamentoForm withTrattamentoMissioneEstera(TrattamentoMissioneEsteraEnum trattamentoMissioneEstera);

    class DatiAnticipoPagamentoForm extends IForm.FormAbstract<DatiAnticipoPagamenti, IDatiAnticipoPagamentoForm>
            implements IDatiAnticipoPagamentoForm {

        /**
         *
         */
        private static final long serialVersionUID = -5674917270638827694L;

        private CheckBox speseAlberghiereField;
        private CheckBox speseViaggioDocumentatoField;
        private CheckBox speseViaggioTamField;
        private CheckBox prospettoField;
        private TrattamentoMissioneEsteraEnum trattamentoMissioneEstera;
        private TextField speseMissioniAnticipateField;
        private TextField mandatoCNRField;

        private DatiAnticipoPagamentoForm() {
        }

        public static IDatiAnticipoPagamentoForm getDatiAnticipoPagamentoForm() {
            return new DatiAnticipoPagamentoForm();
        }

        public IDatiAnticipoPagamentoForm build() {
            setFieldGroup(new BeanFieldGroup<DatiAnticipoPagamenti>(DatiAnticipoPagamenti.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        /**
         * @param trattamentoMissioneEstera
         * @return {@link IDatiAnticipoPagamentoForm}
         */
        public IDatiAnticipoPagamentoForm withTrattamentoMissioneEstera(
                TrattamentoMissioneEsteraEnum trattamentoMissioneEstera) {
            this.trattamentoMissioneEstera = trattamentoMissioneEstera;
            return self();
        }

        public void buildTab() {
            speseAlberghiereField = (CheckBox) getFieldGroup().buildAndBind(
                    "Spese alberghiere/alloggio preventivate\n (Trattamento di missione con rimborso documentato);",
                    "speseAlberghiere", CheckBox.class);
            speseViaggioDocumentatoField = (CheckBox) getFieldGroup().buildAndBind(
                    "Spese di viaggio(Trattamento di missione con rimborso documentato);", "speseViaggioDocumentato",
                    CheckBox.class);
            speseViaggioTamField = (CheckBox) getFieldGroup().buildAndBind(
                    "Spese di viaggio(Trattamento alternativo di missione);", "speseViaggioTam", CheckBox.class);
            prospettoField = (CheckBox) getFieldGroup().buildAndBind(
                    "Prospetto calcolo anticipo(Trattamento alternativo di missione);", "prospetto", CheckBox.class);

            // Crea checkbox in base al tipo di trattamento
            if (trattamentoMissioneEstera == TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO) {
                addComponent(speseAlberghiereField);
                addComponent(speseViaggioDocumentatoField);
            } else {
                addComponent(speseViaggioTamField);
                addComponent(prospettoField);
            }

            if (isAdmin)
                setIsAdmin();
        }

        /*
         * Nel caso in cui si accede come Admin
         *
         */
        private void setIsAdmin() {
            speseMissioniAnticipateField = (TextField) getFieldGroup().buildAndBind("Anticipo",
                    "speseMissioniAnticipate");
            mandatoCNRField = (TextField) getFieldGroup().buildAndBind("Mandato CNR", "mandatoCNR");
            addComponent(speseMissioniAnticipateField);
            addComponent(mandatoCNRField);
        }

        public DatiAnticipoPagamenti validate() throws CommitException, InvalidValueException {
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });

            if (!speseAlberghiereField.getValue() && !speseViaggioDocumentatoField.getValue()
                    && !speseViaggioTamField.getValue() && !prospettoField.getValue()) {
                speseAlberghiereField.setValidationVisible(true);
                throw new InvalidValueException(Utility.getMessage("no_anticipo_pagamenti"));
            }

            getFieldGroup().commit();
            BeanItem<DatiAnticipoPagamenti> beanItem = (BeanItem<DatiAnticipoPagamenti>) getFieldGroup()
                    .getItemDataSource();
            DatiAnticipoPagamenti datiAnticipoPagamenti = beanItem.getBean();
            return datiAnticipoPagamenti;
        }

        /**
         *
         */
        @Override
        public void addValidator() {
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
        protected IDatiAnticipoPagamentoForm self() {
            return this;
        }

    }

}
