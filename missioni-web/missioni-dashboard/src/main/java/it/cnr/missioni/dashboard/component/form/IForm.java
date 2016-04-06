package it.cnr.missioni.dashboard.component.form;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;

/**
 * @author Salvia Vito
 */
public interface IForm<T extends Object, B> extends Layout {

    T validate() throws CommitException, InvalidValueException;

    /**
     * @param modifica
     * @return {@link IForm<T> }
     */
    B withModifica(boolean modifica);

    /**
     * @param enabled
     * @return {@link IForm<T> }
     */
    B withEnabled(boolean enabled);

    /**
     * @param isAdmin
     * @return {@link IForm<T> }
     */
    B withIsAdmin(boolean isAdmin);

    /**
     * @param bean
     * @return {@link IForm<T> }
     */
    B withBean(T bean);

    /**
     * @return {@link IForm<T> }
     */
    B build();

    /**
     * @return {@link BeanFieldGroup<T> }
     */
    BeanFieldGroup<T> getFieldGroup();


    void buildFieldGroup();

    void buildTab();

    void addValidator();

    void addListener();

    public abstract class FormAbstract<T, B> extends FormLayout implements IForm<T, B> {

        /**
         *
         */
        private static final long serialVersionUID = -7214832545147375718L;
        protected T bean;
        protected boolean enabled;
        protected boolean modifica;
        protected boolean isAdmin;
        private BeanFieldGroup<T> fieldGroup;

        protected FormAbstract() {
            DashboardEventBus.register(this);
            addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        }

        /**
         * @param modifica
         * @return {@link IForm<T> }
         */
        public B withModifica(boolean modifica) {
            this.modifica = modifica;
            return self();
        }

        /**
         * @param enabled
         * @return {@link IForm<T> }
         */
        public B withEnabled(boolean enabled) {
            this.enabled = enabled;
            return self();
        }

        /**
         * @param isAdmin
         * @return {@link IForm<T> }
         */
        public B withIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return self();
        }

        /**
         * @param bean
         * @return {@link IForm<T> }
         */
        public B withBean(T bean) {
            this.bean = bean;
            return self();
        }

        public void buildFieldGroup() {
            getFieldGroup().setItemDataSource(bean);
            getFieldGroup().setBuffered(true);
            getFieldGroup().setReadOnly(!enabled);
            FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
            getFieldGroup().setFieldFactory(fieldFactory);
        }


        public abstract void addValidator();

        public abstract void addListener();

        /**
         * @return the fieldGroup
         */
        public BeanFieldGroup<T> getFieldGroup() {
            return fieldGroup;
        }

        /**
         * @param fieldGroup
         */
        public void setFieldGroup(BeanFieldGroup<T> fieldGroup) {
            this.fieldGroup = fieldGroup;
        }

        protected abstract B self();

    }


}
