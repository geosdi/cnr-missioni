package it.cnr.missioni.dashboard.component.form.user;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.Anagrafica;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public interface IAnagraficaUserForm extends IForm<Anagrafica, IAnagraficaUserForm> {

    /**
     * @param user
     * @return {@link IAnagraficaUserForm}
     */
    IAnagraficaUserForm withUser(User user);

    class AnagraficaUserForm extends IForm.FormAbstract<Anagrafica, IAnagraficaUserForm>
            implements IAnagraficaUserForm {

        /**
         *
         */
        private static final long serialVersionUID = 4405905294960236094L;
        private TextField nomeField;
        private TextField cognomeField;
        private TextField codiceFiscaleField;
        private TextField luogoNascitaField;
        private DateField dataNascitaField;
        private ComboBox genereField;
        private User user;

        private AnagraficaUserForm() {
        }

        public static IAnagraficaUserForm getAnagraficaUserForm() {
            return new AnagraficaUserForm();
        }

        public IAnagraficaUserForm build() {
            setFieldGroup(new BeanFieldGroup<Anagrafica>(Anagrafica.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        /**
         * @param user
         * @return {@link IAnagraficaUserForm}
         */
        public IAnagraficaUserForm withUser(User user) {
            this.user = user;
            return self();
        }

        public void addValidator() {
            codiceFiscaleField.addValidator(new Validator() {
                /**
                 *
                 */
                private static final long serialVersionUID = -1337279258852931838L;

                @Override
                public void validate(Object value) throws InvalidValueException {
                    if (value != null) {
                        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder
                                .getUserSearchBuilder().withCodiceFiscale(codiceFiscaleField.getValue());
                        UserStore userStore = null;

                        if (modifica)
                            userSearchBuilder.withNotId(user.getId());

                        try {
                            userStore = ClientConnector.getUser(userSearchBuilder);
                        } catch (Exception e) {
                            Utility.getNotification(Utility.getMessage("error_message"),
                                    Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
                        }
                        if (userStore.getTotale() > 0)
                            throw new InvalidValueException(Utility.getMessage("codice_fiscale_present"));
                    }
                }
            });
        }

        public void buildTab() {
            nomeField = (TextField) getFieldGroup().buildAndBind("Nome", "nome");
            codiceFiscaleField = (TextField) getFieldGroup().buildAndBind("Codice Fiscale", "codiceFiscale");
            cognomeField = (TextField) getFieldGroup().buildAndBind("Cognome", "cognome");
            luogoNascitaField = (TextField) getFieldGroup().buildAndBind("Luogo Nascita", "luogoNascita");
            dataNascitaField = (DateField) getFieldGroup().buildAndBind("Data Nascita", "dataNascita");
            dataNascitaField.setResolution(Resolution.DAY);
            dataNascitaField.setDateFormat("dd/MM/yyyy");
            genereField = (ComboBox) getFieldGroup().buildAndBind("Genere", "genere", ComboBox.class);
            addValidator();
            addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
            addComponent(nomeField);
            addComponent(cognomeField);
            addComponent(codiceFiscaleField);
            addComponent(luogoNascitaField);
            addComponent(dataNascitaField);
            addComponent(genereField);
        }

        public Anagrafica validate() throws CommitException, InvalidValueException {
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });
            getFieldGroup().commit();
            BeanItem<Anagrafica> beanItem = (BeanItem<Anagrafica>) getFieldGroup().getItemDataSource();
            Anagrafica anagrafica = beanItem.getBean();
            return anagrafica;
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
        protected IAnagraficaUserForm self() {
            return this;
        }

    }

}
