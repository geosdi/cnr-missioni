package it.cnr.missioni.dashboard.action;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification.Type;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.UserSearchBuilder;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */
public class LoginAction implements IAction {

	private String username;
	private String password;
	private User user;

	public LoginAction(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public boolean doAction() {
			try {
				UserSearchBuilder userSearchBuilder = UserSearchBuilder.getUserSearchBuilder().withUsername(username);
				UserStore userStore = ClientConnector.getUser(userSearchBuilder);
				if (userStore == null) {
					Utility.getNotification(Utility.getMessage("error_message"),
							Utility.getMessage("username_not_present"), Type.ERROR_MESSAGE);
					return false;
				} else if (! (user = userStore.getUsers().get(0)).getCredenziali().getPassword().equals(user.getCredenziali().md5hash(password))) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("password_error"),
							Type.ERROR_MESSAGE);

					return false;
				} else {
					VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
					Utility.getNotification(Utility.getMessage("success_message"), null,
							Type.HUMANIZED_MESSAGE);
					

					
					return true;
				}

			} catch (Exception e) {
				Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
						Type.ERROR_MESSAGE);
				return false;
			}
		
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username 
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password 
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user 
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
