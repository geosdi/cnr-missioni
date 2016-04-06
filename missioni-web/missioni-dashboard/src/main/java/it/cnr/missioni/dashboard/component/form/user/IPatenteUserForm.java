package it.cnr.missioni.dashboard.component.form.user;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.Patente;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public interface IPatenteUserForm extends IForm<Patente, IPatenteUserForm> {

    IPatenteUserForm withUser(User user);

    class PatenteUserForm extends IForm.FormAbstract<Patente, IPatenteUserForm> implements IPatenteUserForm {

        /**
         *
         */
        private static final long serialVersionUID = -2190283068413520001L;
        private TextField numeroPatenteField;
        private TextField rilasciataDaField;
        private DateField dataRilascioField;
        private DateField dataValiditaField;
        private User user;

        private PatenteUserForm() {
        }

        public static IPatenteUserForm getPatenteUserForm() {
            return new PatenteUserForm();
        }

        public IPatenteUserForm build() {
            setFieldGroup(new BeanFieldGroup<Patente>(Patente.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        public IPatenteUserForm withUser(User user) {
            this.user = user;
            return self();
        }

        public void addValidator() {
            numeroPatenteField.addValidator(new Validator() {
                /**
                 *
                 */
                private static final long serialVersionUID = 4140589215849335096L;

                @Override
                public void validate(Object value) throws InvalidValueException {
                    if (value != null) {
                        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder
                                .getUserSearchBuilder().withNumeroPatente(numeroPatenteField.getValue());

                        if (modifica)
                            userSearchBuilder.withNotId(user.getId());

                        UserStore userStore = null;
                        try {
                            userStore = ClientConnector.getUser(userSearchBuilder);
                        } catch (Exception e) {
                            Utility.getNotification(Utility.getMessage("error_message"),
                                    Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
                        }
                        if (userStore.getTotale() > 0)
                            throw new InvalidValueException(Utility.getMessage("patente_present"));
                    }
                }
            });
        }

        public void buildTab() {
            numeroPatenteField = (TextField) getFieldGroup().buildAndBind("Numero Patente", "numeroPatente");
            rilasciataDaField = (TextField) getFieldGroup().buildAndBind("Rilasciata Da", "rilasciataDa");
            dataRilascioField = (DateField) getFieldGroup().buildAndBind("Data Rilascio", "dataRilascio");
            dataValiditaField = (DateField) getFieldGroup().buildAndBind("Valida Fino Al", "validaFinoAl");
            dataRilascioField.setResolution(Resolution.DAY);
            dataRilascioField.setDateFormat("dd/MM/yyyy");
            dataValiditaField.setResolution(Resolution.DAY);
            dataValiditaField.setDateFormat("dd/MM/yyyy");
            addValidator();
            addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
            addComponent(numeroPatenteField);
            addComponent(rilasciataDaField);
            addComponent(dataRilascioField);
            addComponent(dataValiditaField);
        }

        public Patente validate() throws CommitException, InvalidValueException {
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });
            getFieldGroup().commit();
            BeanItem<Patente> beanItem = (BeanItem<Patente>) getFieldGroup().getItemDataSource();
            Patente patente = beanItem.getBean();
            return patente;
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
        protected IPatenteUserForm self() {
            return this;
        }

    }

}
