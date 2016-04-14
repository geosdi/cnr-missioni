package it.cnr.missioni.dashboard.component.window;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.RecuperaPasswordAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.rest.api.response.user.UserStore;

public class RecuperaPasswordWindow extends IWindow.AbstractWindow<String, RecuperaPasswordWindow> {

	public static final String ID = "recuperapasswordwindow";
	/**
	 *
	 */
	private static final long serialVersionUID = -5199469615215392155L;

	private TextField emailField;

	protected RecuperaPasswordWindow() {
	}

	public static RecuperaPasswordWindow getRecuperaPasswordWindow() {
		return new RecuperaPasswordWindow();
	}

	public RecuperaPasswordWindow build() {
		this.setID(ID);
		buildWindow();
		detailsWrapper.addComponent(buildCredenzialiTab());
		content.addComponent(buildFooter());
		UI.getCurrent().addWindow(this);
		this.focus();
		return self();
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
		emailField = new TextField("Email");
		details.addComponent(emailField);
		emailField.addValidator(new Validator() {


			/**
			 * 
			 */
			private static final long serialVersionUID = -6976695909999446610L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				UserStore userStore = null;
				try {
					IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
							.withMail(emailField.getValue());
					userStore = ClientConnector.getUser(userSearchBuilder);
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				if (userStore.getTotale() <= 0)
					throw new InvalidValueException(Utility.getMessage("email_not_inserted"));
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
					emailField.validate();
					DashboardEventBus.post(new RecuperaPasswordAction(emailField.getValue()));
					close();
				} catch (InvalidValueException e) {
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

	public RecuperaPasswordWindow self() {
		return this;
	}

}
