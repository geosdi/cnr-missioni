package it.cnr.missioni.dashboard.component.form.missione;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public interface IFondoGAEMissioneForm extends IForm<Missione, IFondoGAEMissioneForm> {
    class FondoGAEMissioneForm extends IForm.FormAbstract<Missione, IFondoGAEMissioneForm>
            implements IFondoGAEMissioneForm {

        /**
         *
         */
        private static final long serialVersionUID = -7757093067983938824L;
        private TextField fondoField;
        private TextField GAEField;
        private ComboBox listaResponsabiliGruppoField;
        private ComboBox listaSeguito;

        private FondoGAEMissioneForm() {
        }

        public static IFondoGAEMissioneForm getFondoGAEMissioneForm() {
            return new FondoGAEMissioneForm();
        }

        public IFondoGAEMissioneForm build() {
            setFieldGroup(new BeanFieldGroup<Missione>(Missione.class));
            buildFieldGroup();
            buildTab();
            return self();
        }

        public void buildTab() {

            buildFields();
            fondoField = (TextField) getFieldGroup().buildAndBind("Fondo", "fondo");
            GAEField = (TextField) getFieldGroup().buildAndBind("GAE", "GAE");
            getFieldGroup().bind(listaResponsabiliGruppoField, "responsabileGruppo");
            getFieldGroup().bind(listaSeguito, "idUserSeguito");
            addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
            addComponent(fondoField);
            addComponent(GAEField);
            addComponent(listaResponsabiliGruppoField);
            addComponent(listaSeguito);
            addListener();
        }

        private void buildFields() {
            listaResponsabiliGruppoField = new ComboBox("Responsabile Fondo");

            try {
                IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                        .withAll(true).withResponsabileGruppo(true);

                UserStore userStore = ClientConnector.getUser(userSearchBuilder);
                userStore.getUsers().forEach(u -> {
                    listaResponsabiliGruppoField.addItem(u.getId());
                    listaResponsabiliGruppoField.setItemCaption(u.getId(),
                            u.getAnagrafica().getCognome() + " " + u.getAnagrafica().getNome());
                });
            } catch (Exception e) {
                Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                        Type.ERROR_MESSAGE);
            }
            listaResponsabiliGruppoField.setValidationVisible(false);
            listaSeguito = new ComboBox("A seguito");
            try {
                IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                        .withAll(true);
                UserStore userStore = ClientConnector.getUser(userSearchBuilder);
                userStore.getUsers().forEach(u -> {
                    listaSeguito.addItem(u.getId());
                    listaSeguito.setItemCaption(u.getId(),
                            u.getAnagrafica().getCognome() + " " + u.getAnagrafica().getNome());
                });
            } catch (Exception e) {
                Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                        Type.ERROR_MESSAGE);
            }
        }

        public Missione validate() throws CommitException, InvalidValueException {
            getFieldGroup().getFields().stream().forEach(f -> {
                ((AbstractField<?>) f).setValidationVisible(true);
            });
            fondoField.validate();
            GAEField.validate();
            listaResponsabiliGruppoField.validate();
            listaSeguito.validate();
            bean.setGAE(GAEField.getValue());
            bean.setFondo(fondoField.getValue());
            bean.setResponsabileGruppo(listaResponsabiliGruppoField.getValue().toString());
            bean.setShortResponsabileGruppo(
                    listaResponsabiliGruppoField.getItemCaption(listaResponsabiliGruppoField.getValue()));
            // Selezionato user a seguito
            if (listaSeguito.getValue() != null) {
                bean.setIdUserSeguito(listaSeguito.getValue().toString());
                bean.setShortUserSeguito(listaSeguito.getItemCaption(listaSeguito.getValue()));
            } else {
                bean.setIdUserSeguito(null);
                bean.setShortUserSeguito(null);
            }
            return bean;
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
            listaResponsabiliGruppoField.addBlurListener(new BlurListener() {

                /**
                 *
                 */
                private static final long serialVersionUID = 5963823205819332648L;

                @Override
                public void blur(BlurEvent event) {
                    try {
                        listaResponsabiliGruppoField.validate();
                    } catch (Exception e) {
                        listaResponsabiliGruppoField.setValidationVisible(true);
                    }
                }
            });
        }

        /**
         * @return
         */
        @Override
        protected IFondoGAEMissioneForm self() {
            return this;
        }

    }

}
