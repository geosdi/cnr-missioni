package it.cnr.missioni.dashboard.component;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.domain.User_old;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.request.RegistrationRequest;

@SuppressWarnings("serial")
public class RegistrationWindow extends Window {

    public static final String ID = "registrationwindow";

    private final BeanFieldGroup<User_old> fieldGroup;
    /*
     * Fields for editing the User object are defined here as class members.
     * They are later bound to a FieldGroup by calling
     * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
     * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
     * the fields with the user object.
     */
    @PropertyId("username")
    private TextField usernameField;
    @PropertyId("password")
    private PasswordField passwordField;
    @PropertyId("passwordRepeat")
    private PasswordField passwordRepeatField;

    private RegistrationWindow() {
        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
//        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(true);
        setHeight(90.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildProfileTab());
//        detailsWrapper.addComponent(buildPreferencesTab());


        content.addComponent(buildFooter());

        fieldGroup = new BeanFieldGroup<User_old>(User_old.class);
        fieldGroup.bindMemberFields(this);
    }

//    private Component buildPreferencesTab() {
//        VerticalLayout root = new VerticalLayout();
//        root.setCaption("Preferences");
//        root.setIcon(FontAwesome.COGS);
//        root.setSpacing(true);
//        root.setMargin(true);
//        root.setSizeFull();
//
//        Label message = new Label("Not implemented in this demo");
//        message.setSizeUndefined();
//        message.addStyleName(ValoTheme.LABEL_LIGHT);
//        root.addComponent(message);
//        root.setComponentAlignment(message, Alignment.MIDDLE_CENTER);
//
//        return root;
//    }

    private Component buildProfileTab() {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Registrazione");
        root.setIcon(FontAwesome.USER);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
//        root.addStyleName("profile-form");

//        VerticalLayout pic = new VerticalLayout();
//        pic.setSizeUndefined();
//        pic.setSpacing(true);
//        Image profilePic = new Image(null, new ThemeResource(
//                "img/profile-pic-300px.jpg"));
//        profilePic.setWidth(100.0f, Unit.PIXELS);
//        pic.addComponent(profilePic);

//        Button upload = new Button("Change…", new ClickListener() {
//            @Override
//            public void buttonClick(ClickEvent event) {
//                Notification.show("Not implemented in this demo");
//            }
//        });
//        upload.addStyleName(ValoTheme.BUTTON_TINY);
//        pic.addComponent(upload);
//
//        root.addComponent(pic);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);

        usernameField = new TextField("Username");
        details.addComponent(usernameField);
        passwordField = new PasswordField("Password");
        details.addComponent(passwordField);
        passwordRepeatField = new PasswordField("Ripeti Password");
        details.addComponent(passwordRepeatField);

//        titleField = new ComboBox("Title");
//        titleField.setInputPrompt("Please specify");
//        titleField.addItem("Mr.");
//        titleField.addItem("Mrs.");
//        titleField.addItem("Ms.");
//        titleField.setNewItemsAllowed(true);
//        details.addComponent(titleField);

//        sexField = new OptionGroup("Sex");
//        sexField.addItem(Boolean.FALSE);
//        sexField.setItemCaption(Boolean.FALSE, "Female");
//        sexField.addItem(Boolean.TRUE);
//        sexField.setItemCaption(Boolean.TRUE, "Male");
//        sexField.addStyleName("horizontal");
//        details.addComponent(sexField);

//        Label section = new Label("Contact Info");
//        section.addStyleName(ValoTheme.LABEL_H4);
//        section.addStyleName(ValoTheme.LABEL_COLORED);
//        details.addComponent(section);

//        emailField = new TextField("Email");
//        emailField.setWidth("100%");
//        emailField.setRequired(true);
//        emailField.setNullRepresentation("");
//        details.addComponent(emailField);

//        locationField = new TextField("Location");
//        locationField.setWidth("100%");
//        locationField.setNullRepresentation("");
//        locationField.setComponentError(new UserError(
//                "This address doesn't exist"));
//        details.addComponent(locationField);

//        phoneField = new TextField("Phone");
//        phoneField.setWidth("100%");
//        phoneField.setNullRepresentation("");
//        details.addComponent(phoneField);

//        newsletterField = new OptionalSelect<Integer>();
//        newsletterField.addOption(0, "Daily");
//        newsletterField.addOption(1, "Weekly");
//        newsletterField.addOption(2, "Monthly");
//        details.addComponent(newsletterField);

//        section = new Label("Additional Info");
//        section.addStyleName(ValoTheme.LABEL_H4);
//        section.addStyleName(ValoTheme.LABEL_COLORED);
//        details.addComponent(section);

//        websiteField = new TextField("Website");
//        websiteField.setInputPrompt("http://");
//        websiteField.setWidth("100%");
//        websiteField.setNullRepresentation("");
//        details.addComponent(websiteField);

//        bioField = new TextArea("Bio");
//        bioField.setWidth("100%");
//        bioField.setRows(4);
//        bioField.setNullRepresentation("");
//        details.addComponent(bioField);

        return root;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button("OK");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
//                try {
//                    fieldGroup.commit();
                    
                    String username = usernameField.getValue();
                    String password = passwordField.getValue();
                    String passwordRepeat = passwordRepeatField.getValue();            
                    DashboardEventBus.post(new RegistrationRequest(username, password,passwordRepeat));
//                    close();
//                } catch (CommitException e) {
//                    Notification.show("Error while updating profile",
//                            Type.ERROR_MESSAGE);
//                }

            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }

    public static void open() {
        DashboardEventBus.post(new CloseOpenWindowsEvent());
        Window w = new RegistrationWindow();
        UI.getCurrent().addWindow(w);
        w.focus();
    }
}
