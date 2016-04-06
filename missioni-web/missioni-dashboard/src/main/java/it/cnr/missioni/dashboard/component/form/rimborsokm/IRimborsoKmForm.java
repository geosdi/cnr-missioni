package it.cnr.missioni.dashboard.component.form.rimborsokm;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.model.configuration.RimborsoKm;

/**
 * @author Salvia Vito
 */
public interface IRimborsoKmForm extends IForm<RimborsoKm, IRimborsoKmForm> {
    class RimborsoKmForm extends IForm.FormAbstract<RimborsoKm, IRimborsoKmForm> implements IRimborsoKmForm {

        /**
         *
         */
        private static final long serialVersionUID = -1341421621645150748L;

        private TextField valueField;

        private RimborsoKmForm() {
        }

        public static IRimborsoKmForm getRimborsoKmForm() {
            return new RimborsoKmForm();
        }

        public IRimborsoKmForm build() {
            setFieldGroup(new BeanFieldGroup<RimborsoKm>(RimborsoKm.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        public void buildTab() {
            addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
            valueField = (TextField) getFieldGroup().buildAndBind("Valore", "value");
            addComponent(valueField);
        }

        public RimborsoKm validate() throws CommitException, InvalidValueException {
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });
            getFieldGroup().commit();
            BeanItem<RimborsoKm> beanItem = (BeanItem<RimborsoKm>) getFieldGroup().getItemDataSource();
            RimborsoKm rimborsoKm = beanItem.getBean();
            return rimborsoKm;
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
        protected IRimborsoKmForm self() {
            return this;
        }
    }

}