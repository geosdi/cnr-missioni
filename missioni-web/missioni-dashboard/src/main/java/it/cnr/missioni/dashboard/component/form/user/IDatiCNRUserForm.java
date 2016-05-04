package it.cnr.missioni.dashboard.component.form.user;

import java.util.Comparator;
import java.util.List;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.IComparator;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IQualificaUserSearchBuilder;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.model.user.DatiCNR;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.qualificaUser.QualificaUserStore;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public interface IDatiCNRUserForm extends IForm<DatiCNR, IDatiCNRUserForm> {

    /**
     * @param user
     * @return {@link IDatiCNRUserForm}
     */
    IDatiCNRUserForm withUser(User user);

    class DatiCNRUserForm extends IForm.FormAbstract<DatiCNR, IDatiCNRUserForm> implements IDatiCNRUserForm {

        /**
         *
         */
        private static final long serialVersionUID = -5098566174960351037L;
        private ComboBox livelloField;
        private ComboBox qualificaField;
        private TextField matricolaField;
        private TextField codiceTerzoField;
        private TextField mailField;
        private TextField ibanField;
        private User user;

        private DatiCNRUserForm() {
        }

        public static IDatiCNRUserForm getDatiCNRUserForm() {
            return new DatiCNRUserForm();
        }

        public IDatiCNRUserForm build() {
            setFieldGroup(new BeanFieldGroup<DatiCNR>(DatiCNR.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        /**
         * @param user
         * @return {@link IDatiCNRUserForm}
         */
        public IDatiCNRUserForm withUser(User user) {
            this.user = user;
            return self();
        }

        public void addValidator() {

            matricolaField.addValidator(new Validator() {
                /**
                 *
                 */
                private static final long serialVersionUID = -7182213817460538048L;

                @Override
                public void validate(Object value) throws InvalidValueException {
                    if (value != null) {
                        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder
                                .getUserSearchBuilder().withSearchType("exact")
                                .withMatricola(matricolaField.getValue());

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
                            throw new InvalidValueException(Utility.getMessage("matricola_present"));
                    }
                }
            });

            mailField.addValidator(new Validator() {
                /**
                 *
                 */
                private static final long serialVersionUID = 2497182427630850798L;

                @Override
                public void validate(Object value) throws InvalidValueException {
                    if (value != null) {
                        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder
                                .getUserSearchBuilder().withMail(mailField.getValue());
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
                            throw new InvalidValueException(Utility.getMessage("mail_present"));
                    }
                }
            });

            ibanField.addValidator(new Validator() {
                /**
                 *
                 */
                private static final long serialVersionUID = -2821415643000513181L;

                @Override
                public void validate(Object value) throws InvalidValueException {
                    if (value != null) {
                        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder
                                .getUserSearchBuilder().withIban(ibanField.getValue());
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
                            throw new InvalidValueException(Utility.getMessage("iban_present"));
                    }
                }
            });

            qualificaField.addValidator(new Validator() {

                /**
                 *
                 */
                private static final long serialVersionUID = -4282669073317590147L;

                @Override
                public void validate(Object value) throws InvalidValueException {

                    if (qualificaField.getValue() == null)
                        throw new InvalidValueException(Utility.getMessage("field_required"));
                }
            });
        }

        public void buildTab() {

            try {
                livelloField = (ComboBox) getFieldGroup().buildAndBind("Livello", "livello", ComboBox.class);
                qualificaField = new ComboBox("Qualifica");
                qualificaField.setImmediate(true);
                qualificaField.setValidationVisible(false);
                getFieldGroup().bind(qualificaField, "idQualifica");

                IQualificaUserSearchBuilder qualificaUserSearchBuilder = IQualificaUserSearchBuilder.QualificaUserSearchBuilder
                        .getQualificaUserSearchBuilder().withAll(true);
                QualificaUserStore qualificaStore = ClientConnector.getQualificaUser(qualificaUserSearchBuilder);

                qualificaStore.getQualificaUser().stream().sorted(new IComparator.QualificaComparator()).forEach(q -> {
                    qualificaField.addItem(q.getId());
                    qualificaField.setItemCaption(q.getId(), q.getValue());
                });

                matricolaField = (TextField) getFieldGroup().buildAndBind("Matricola", "matricola");
                codiceTerzoField = (TextField) getFieldGroup().buildAndBind("Codice Terzo", "codiceTerzo");
                mailField = (TextField) getFieldGroup().buildAndBind("Mail", "mail");
                ibanField = (TextField) getFieldGroup().buildAndBind("Iban", "iban");

            } catch (Exception e) {
                Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                        Type.ERROR_MESSAGE);
            }
            addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
            addComponent(livelloField);
            addComponent(qualificaField);
            // addComponent(listaUserField);
            addComponent(matricolaField);
            addComponent(codiceTerzoField);
            addComponent(mailField);
            addComponent(ibanField);
            addValidator();
            addListener();
        }

        public DatiCNR validate() throws CommitException, InvalidValueException {
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });
            getFieldGroup().commit();
            qualificaField.validate();
            qualificaField.setValidationVisible(true);
            BeanItem<DatiCNR> beanItem = (BeanItem<DatiCNR>) getFieldGroup().getItemDataSource();
            DatiCNR datiCNR = beanItem.getBean();
            datiCNR.setDescrizioneQualifica(getQualificaItemCaption());
            // datiCNR.setShortDescriptionDatoreLavoro(getShortDescriptionDatoreLavoro());
            return datiCNR;
        }

        private String getQualificaItemCaption() {
            return qualificaField.getItemCaption(qualificaField.getValue());
        }

        /**
         *
         */
        @Override
        public void addListener() {
            qualificaField.addBlurListener(new BlurListener() {

                /**
                 *
                 */
                private static final long serialVersionUID = -2604643711261342530L;

                @Override
                public void blur(BlurEvent event) {
                    try {
                        qualificaField.validate();
                    } catch (Exception e) {
                        qualificaField.setValidationVisible(true);
                    }
                }
            });
        }

        /**
         * @return
         */
        @Override
        protected IDatiCNRUserForm self() {
            return this;
        }

    }

}
