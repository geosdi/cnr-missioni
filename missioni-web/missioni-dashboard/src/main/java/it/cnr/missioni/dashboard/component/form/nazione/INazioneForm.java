package it.cnr.missioni.dashboard.component.form.nazione;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.model.configuration.Nazione;

/**
 * @author Salvia Vito
 */
public interface INazioneForm extends IForm<Nazione, INazioneForm> {
    class NazioneForm extends IForm.FormAbstract<Nazione, INazioneForm> implements INazioneForm {

        /**
         *
         */
        private static final long serialVersionUID = -5674917270638827694L;

        private TextField valueField;
        private ComboBox areagGeograficaField;

        private NazioneForm() {
        }

        public static INazioneForm getNazioneForm() {
            return new NazioneForm();
        }

        public INazioneForm build() {
            setFieldGroup(new BeanFieldGroup<Nazione>(Nazione.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        public void buildTab() {
            valueField = (TextField) getFieldGroup().buildAndBind("Nazione", "value");
            addComponent(valueField);
            areagGeograficaField = (ComboBox) getFieldGroup().buildAndBind("Area Geografica", "areaGeografica",
                    ComboBox.class);
            addComponent(areagGeograficaField);
        }

        public Nazione validate() throws CommitException, InvalidValueException {
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });
            getFieldGroup().commit();
            BeanItem<Nazione> beanItem = (BeanItem<Nazione>) getFieldGroup().getItemDataSource();
            Nazione nazione = beanItem.getBean();
            return nazione;
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
        protected INazioneForm self() {
            return this;
        }

    }

}
