package it.cnr.missioni.dashboard.component.form.veicolo;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public interface IVeicoloForm extends IForm<Veicolo, IVeicoloForm> {
    class VeicoloForm extends IForm.FormAbstract<Veicolo, IVeicoloForm> implements IVeicoloForm {

        /**
         *
         */
        private static final long serialVersionUID = -23331040271400029L;
        private TextField tipoField;
        private TextField targaField;
        private TextField cartaCircolazioneField;
        private TextField polizzaAssicurativaField;
        private CheckBox veicoloPrincipaleField;
        private User user;
        private Veicolo veicolo;

        private VeicoloForm() {
        }

        public static IVeicoloForm getVeicoloForm() {
            return new VeicoloForm();
        }

        public IVeicoloForm build() {
            setFieldGroup(new BeanFieldGroup<Veicolo>(Veicolo.class));
            buildFieldGroup();
            buildTab();
            this.user = DashboardUI.getCurrentUser();
            return self();
        }

        public void buildTab() {
            tipoField = (TextField) getFieldGroup().buildAndBind("Tipo", "tipo");
            addComponent(tipoField);
            targaField = (TextField) getFieldGroup().buildAndBind("Targa", "targa");
            addComponent(targaField);

            cartaCircolazioneField = (TextField) getFieldGroup().buildAndBind("Carta Circolazione",
                    "cartaCircolazione");
            addComponent(cartaCircolazioneField);
            polizzaAssicurativaField = (TextField) getFieldGroup().buildAndBind("Polizza Assicurativa",
                    "polizzaAssicurativa");
            addComponent(polizzaAssicurativaField);

            veicoloPrincipaleField = (CheckBox) getFieldGroup().buildAndBind("Veicolo Principale", "veicoloPrincipale",
                    CheckBox.class);
            addComponent(veicoloPrincipaleField);
            addValidator();
        }

        public void addValidator() {

            targaField.addValidator(new Validator() {
                /**
                 *
                 */
                private static final long serialVersionUID = -7711516415924870879L;

                @Override
                public void validate(Object value) throws InvalidValueException {
                    if (value != null) {
                        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder
                                .getUserSearchBuilder().withTarga(targaField.getValue());
                        if (modifica)
                            userSearchBuilder.withNotId(user.getId());
                        UserStore userStore = null;
                        try {
                            userStore = ClientConnector.getUser(userSearchBuilder);
                        } catch (Exception e) {
                            Utility.getNotification(Utility.getMessage("error_message"),
                                    Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
                        }
                        if (userStore.getTotale() > 0 || user.getVeicoloWithTarga(targaField.getValue(),
                                (modifica ? veicolo.getId() : null)) != null)
                            throw new InvalidValueException(Utility.getMessage("targa_present"));
                    }
                }
            });

            polizzaAssicurativaField.addValidator(new Validator() {
                /**
                 *
                 */
                private static final long serialVersionUID = 5107603646411959871L;

                @Override
                public void validate(Object value) throws InvalidValueException {
                    if (value != null) {
                        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder
                                .getUserSearchBuilder().withPolizzaAssicurativa(polizzaAssicurativaField.getValue());
                        if (modifica)
                            userSearchBuilder.withNotId(user.getId());
                        UserStore userStore = null;
                        try {
                            userStore = ClientConnector.getUser(userSearchBuilder);
                        } catch (Exception e) {
                            Utility.getNotification(Utility.getMessage("error_message"),
                                    Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
                        }
                        if (userStore.getTotale() > 0
                                || user.getVeicoloWithPolizzaAssicurativa(polizzaAssicurativaField.getValue(),
                                (modifica ? veicolo.getId() : null)) != null)
                            throw new InvalidValueException(Utility.getMessage("polizza_present"));
                    }
                }
            });

            cartaCircolazioneField.addValidator(new Validator() {
                /**
                 *
                 */
                private static final long serialVersionUID = 5319453085222360011L;

                @Override
                public void validate(Object value) throws InvalidValueException {
                    if (value != null) {
                        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder
                                .getUserSearchBuilder().withCartaCircolazione(cartaCircolazioneField.getValue());
                        if (modifica)
                            userSearchBuilder.withNotId(user.getId());
                        UserStore userStore = null;
                        try {
                            userStore = ClientConnector.getUser(userSearchBuilder);
                        } catch (Exception e) {
                            Utility.getNotification(Utility.getMessage("error_message"),
                                    Utility.getMessage("request_error"), Type.ERROR_MESSAGE);
                        }
                        if (userStore.getTotale() > 0
                                || user.getVeicoloWithCartaCircolazione(cartaCircolazioneField.getValue(),
                                (modifica ? veicolo.getId() : null)) != null)
                            throw new InvalidValueException(Utility.getMessage("carta_circolazione_present"));
                    }
                }
            });

        }

        public Veicolo validate() throws CommitException, InvalidValueException {
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });
            getFieldGroup().commit();
            BeanItem<Veicolo> beanItem = (BeanItem<Veicolo>) getFieldGroup().getItemDataSource();
            Veicolo veicolo = beanItem.getBean();
            return veicolo;
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
        protected IVeicoloForm self() {
            return this;
        }

    }

}
