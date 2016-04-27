package it.cnr.missioni.dashboard.component.window;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.UpdateCredenzialiUserAction;
import it.cnr.missioni.dashboard.action.UserRegistrationAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

public class CredenzialiWindow extends IWindow.AbstractWindow<User, CredenzialiWindow> {

    public static final String ID = "credenzialiwindow";
    /**
     *
     */
    private static final long serialVersionUID = -5199469615215392155L;
    private BeanFieldGroup<User> fieldGroup;

    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField passwordRepeatField;

    protected CredenzialiWindow() {
    }

    public static CredenzialiWindow getCredenzialiWindow() {
        return new CredenzialiWindow();
    }

    public CredenzialiWindow build() {
        this.setID(ID);
        fieldGroup = new BeanFieldGroup<User>(User.class);
        buildWindow();
        buildFieldGroup();
        detailsWrapper.addComponent(buildCredenzialiTab());
        content.addComponent(buildFooter());
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }

    private void buildFieldGroup() {
        fieldGroup.setItemDataSource(bean);
        fieldGroup.setBuffered(true);
        FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
        fieldGroup.setFieldFactory(fieldFactory);
    }

    private Component buildCredenzialiTab() {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Credenziali");
        root.setIcon(FontAwesome.LOCK);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);
        usernameField = (TextField) fieldGroup.buildAndBind("Username", "credenziali.username");

        if (!modifica)
            usernameField.addValidator(new Validator() {

                /**
                 *
                 */
                private static final long serialVersionUID = -9137656929603215054L;

                @Override
                public void validate(Object value) throws InvalidValueException {
                	User user = null;
                    try {
//                        IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder().withUsername(usernameField.getValue());
                         user = ClientConnector.getUserByUsername(usernameField.getValue());
//                        userStore = ClientConnector.getUser(userSearchBuilder);
                    } catch (Exception e) {
                        Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                                Type.ERROR_MESSAGE);
                    }
                    if (user != null)
                        throw new InvalidValueException(Utility.getMessage("user_already_inserted"));
                }
            });
        if (modifica)
            usernameField.setReadOnly(true);
        passwordField = (PasswordField) fieldGroup.buildAndBind("Password", "credenziali.password",
                PasswordField.class);
        passwordField.setValue("");
        passwordRepeatField = new PasswordField("Ripeti Password");
        passwordRepeatField.setValue(passwordField.getValue());
        passwordRepeatField.setImmediate(true);
        details.addComponent(usernameField);
        details.addComponent(passwordField);
        details.addComponent(passwordRepeatField);
        passwordRepeatField.setValidationVisible(false);
        passwordRepeatField.addValidator(new Validator() {
            /**
             *
             */
            private static final long serialVersionUID = 1231043176244348048L;

            @Override
            public void validate(Object value) throws InvalidValueException {
                String password = (String) value;
                if (!password.equals(passwordField.getValue())) {
                    throw new InvalidValueException(Utility.getMessage("password_not_equals"));
                }
            }
        });

        passwordRepeatField.addBlurListener(new BlurListener() {

            /**
             *
             */
            private static final long serialVersionUID = 3773767384576999765L;

            @Override
            public void blur(BlurEvent event) {
                try {
                    passwordRepeatField.validate();
                } catch (Exception e) {
                    passwordRepeatField.setValidationVisible(true);
                }
            }
        });

        return root;
    }


    protected Component buildFooter() {

        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -3753680701483343820L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    fieldGroup.getFields().stream().forEach(f -> {
                        ((AbstractField<?>) f).setValidationVisible(true);
                    });
                    passwordRepeatField.setValidationVisible(true);
                    passwordRepeatField.validate();
                    fieldGroup.commit();
                    BeanItem<User> beanItem = (BeanItem<User>) fieldGroup.getItemDataSource();
                    User user = beanItem.getBean();
                    if (!modifica)
                        DashboardEventBus.post(new UserRegistrationAction(user));
                    else
                        DashboardEventBus.post(new UpdateCredenzialiUserAction(user));
                    close();
                } catch (InvalidValueException | CommitException e) {
                    Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
                            Type.ERROR_MESSAGE);
                }
            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }

    public CredenzialiWindow self() {
        return this;
    }

}
