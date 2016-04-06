package it.cnr.missioni.dashboard.component.form.qualificaUser;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.model.configuration.QualificaUser;

/**
 * @author Salvia Vito
 */
public interface IQualificaUserForm extends IForm<QualificaUser, IQualificaUserForm> {
    class QualificaUserForm extends IForm.FormAbstract<QualificaUser, IQualificaUserForm>
            implements IQualificaUserForm {

        /**
         *
         */
        private static final long serialVersionUID = 2059728090692910599L;

        private TextField valueField;

        private QualificaUserForm() {
        }

        public static IQualificaUserForm getQualificaUserForm() {
            return new QualificaUserForm();
        }

        public IQualificaUserForm build() {
            setFieldGroup(new BeanFieldGroup<QualificaUser>(QualificaUser.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        public void buildTab() {
            valueField = (TextField) getFieldGroup().buildAndBind("Qualifica", "value");
            addComponent(valueField);
        }

        public QualificaUser validate() throws CommitException, InvalidValueException {
            for (Field<?> f : getFieldGroup().getFields()) {
                ((AbstractField<?>) f).setValidationVisible(true);
            }
            getFieldGroup().commit();
            BeanItem<QualificaUser> beanItem = (BeanItem<QualificaUser>) getFieldGroup().getItemDataSource();
            QualificaUser qualificaUser = beanItem.getBean();
            return qualificaUser;
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
        protected IQualificaUserForm self() {
            return this;
        }

    }

}
