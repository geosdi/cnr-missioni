package it.cnr.missioni.dashboard.view;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.LoginAction;
import it.cnr.missioni.dashboard.component.window.CredenzialiWindow;
import it.cnr.missioni.dashboard.component.window.RecuperaPasswordWindow;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.User;

import java.io.Serializable;

public class LoginView extends VerticalLayout implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5394668898619143116L;
    private BeanFieldGroup<User> fieldGroup;
    private TextField usernameField;
    private PasswordField passwordField;

    public LoginView() {
        setSizeFull();
        Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
        Notification notification = new Notification("Benvenuto nel modulo Missioni del CNR");
        notification.setDescription("<span>Per accedere è necessario essere registrati <span></span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(20000);
        notification.show(Page.getCurrent());
    }

    private Component buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");
        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields());
       // loginPanel.addComponent(new CheckBox("Remember me", true));
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        loginPanel.addComponent(buttonsLayout);
        Button buttonRegistrati = new Button("Registrati", new Button.ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 6725610315691344307L;

            @Override
            public void buttonClick(ClickEvent clickEvent) {
                CredenzialiWindow.getCredenzialiWindow().withBean(new User()).withIsAdmin(false).withEnabled(true).withModifica(false).build();
            }
        });
        buttonRegistrati.setStyleName(Reindeer.BUTTON_LINK);
        buttonsLayout.addComponent(buttonRegistrati);
        
        Button buttonRecuperaPassword = new Button("Recupera Password", new Button.ClickListener() {


            /**
			 * 
			 */
			private static final long serialVersionUID = 5787072526591206237L;

			@Override
            public void buttonClick(ClickEvent clickEvent) {
                RecuperaPasswordWindow.getRecuperaPasswordWindow().withBean(new User()).withIsAdmin(false).withEnabled(true).withModifica(false).build();
            }
        });
        buttonRecuperaPassword.setStyleName(Reindeer.BUTTON_LINK);
        buttonRegistrati.addStyleName("small-font");
        buttonRecuperaPassword.addStyleName("small-font");
        buttonsLayout.addComponent(buttonRecuperaPassword);
        
        return loginPanel;
    }

    private Component buildFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");
        User user = new User();
        fieldGroup = new BeanFieldGroup<User>(User.class);
        fieldGroup.setItemDataSource(user);
        fieldGroup.setBuffered(true);
        FieldGroupFieldFactory fieldFactory = new BeanFieldGrouFactory();
        fieldGroup.setFieldFactory(fieldFactory);
        usernameField = (TextField) fieldGroup.buildAndBind("Username", "credenziali.username");
        usernameField.setIcon(FontAwesome.USER);
        usernameField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        passwordField = (PasswordField) fieldGroup.buildAndBind("Password", "credenziali.password", PasswordField.class);
        passwordField.setIcon(FontAwesome.LOCK);
        passwordField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        final Button signin = new Button("Sign In");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(KeyCode.ENTER);
        signin.focus();
        fields.addComponents(usernameField, passwordField, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
        signin.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 5787072526591206237L;

            @Override
            public void buttonClick(final ClickEvent event) {
                try {
                    fieldGroup.getFields().stream().forEach(f -> {
                        ((AbstractField<?>) f).setValidationVisible(true);

                    });
                    fieldGroup.commit();
                    DashboardEventBus.post(new LoginAction(usernameField.getValue(), passwordField.getValue()));
                } catch (CommitException e) {

                    Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
                            Type.ERROR_MESSAGE);
                }
            }
        });
        return fields;
    }

    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");
        Label welcome = new Label("Benvenuto");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);
        Label title = new Label("Powered by <a href='http://www.geosdi.org/' target='_blank'>geoSDI</a>", ContentMode.HTML);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }

}
