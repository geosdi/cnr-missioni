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
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.RegistrationUserAction;
import it.cnr.missioni.dashboard.action.UpdateUserAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

public class CredenzialiWindow extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5199469615215392155L;
	public static final String ID = "credenzialiwindow";

	private final BeanFieldGroup<User> fieldGroup;

	private TextField usernameField;
	private PasswordField passwordField;
	private PasswordField passwordRepeatField;
	private User user;

	private boolean registration;

	private CredenzialiWindow(final User user, boolean registration) {
		super();
		this.user = user;
		this.registration = registration;
		this.setID(ID);
		fieldGroup = new BeanFieldGroup<User>(User.class);
		build();
		buildFieldGroup();
		detailsWrapper.addComponent(buildCredenzialiTab());
		content.addComponent(buildFooter());

	}

	private void buildFieldGroup() {
		fieldGroup.setItemDataSource(user);
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

//		BeanItem<User> beanItem = (BeanItem<User>) fieldGroup.getItemDataSource();
//		User user = beanItem.getBean();

		usernameField = (TextField) fieldGroup.buildAndBind("Username", "credenziali.username");
		
		usernameField.addValidator(new Validator(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -9137656929603215054L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				UserStore userStore = null;
				try{
				UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withUsername(usernameField.getValue());
				
				 userStore = ClientConnector.getUser(userSearchBuilder);
			
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				
				
				if (userStore.getTotale() > 0)
					throw new InvalidValueException(Utility.getMessage("user_already_inserted"));	
			}
			
		});
		
		if (!registration)
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
					for (Field<?> f : fieldGroup.getFields()) {
						((AbstractField<?>) f).setValidationVisible(true);
					}
					passwordRepeatField.setValidationVisible(true);
					passwordRepeatField.validate();

					fieldGroup.commit();

					BeanItem<User> beanItem = (BeanItem<User>) fieldGroup.getItemDataSource();
					User user = beanItem.getBean();

					if (registration)
						DashboardEventBus.post(new RegistrationUserAction(user));
					else
						DashboardEventBus.post(new UpdateUserAction(user));
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

	public static void open(final User user, boolean registration) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new CredenzialiWindow(user, registration);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
