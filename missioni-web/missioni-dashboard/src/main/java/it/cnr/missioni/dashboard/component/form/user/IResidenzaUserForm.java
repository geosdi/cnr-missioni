package it.cnr.missioni.dashboard.component.form.user;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.TextField;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.model.user.Residenza;

/**
 * @author Salvia Vito
 */
public interface IResidenzaUserForm extends IForm<Residenza, IResidenzaUserForm> {

    class ResidenzaUserForm extends IForm.FormAbstract<Residenza, IResidenzaUserForm> implements IResidenzaUserForm {

        /**
         *
         */
        private static final long serialVersionUID = 6816875017679269273L;
        private TextField comuneField;
        private TextField indirizzoField;
        private TextField domicilioFiscaleField;

        private ResidenzaUserForm() {
        }

        public static IResidenzaUserForm getResidenzaUserForm() {
            return new ResidenzaUserForm();
        }

        public IResidenzaUserForm build() {
            setFieldGroup(new BeanFieldGroup<Residenza>(Residenza.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        public void buildTab() {
            comuneField = (TextField) getFieldGroup().buildAndBind("Comune", "comune");
            indirizzoField = (TextField) getFieldGroup().buildAndBind("Indirizzo", "indirizzo");
            domicilioFiscaleField = (TextField) getFieldGroup().buildAndBind("Domicilio Fiscale", "domicilioFiscale");
            addComponent(comuneField);
            addComponent(indirizzoField);
            addComponent(domicilioFiscaleField);
        }

        public Residenza validate() throws CommitException, InvalidValueException {
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });
            getFieldGroup().commit();
            BeanItem<Residenza> beanItem = (BeanItem<Residenza>) getFieldGroup().getItemDataSource();
            Residenza residenza = beanItem.getBean();
            return residenza;

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
        protected IResidenzaUserForm self() {
            return this;
        }
    }

}
