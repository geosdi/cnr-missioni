package it.cnr.missioni.dashboard.notification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.vaadin.server.VaadinSession;

import it.cnr.missioni.model.user.User;

/**
 * 
 * @author Salvia Vito
 *
 */
public class NotificationProvider implements INotificationProvider {

	private  final List<DashboardNotification> notifications = new ArrayList<DashboardNotification>();

	/**
	 * Initialize the data for this application.
	 */
	public NotificationProvider() {
//		chechRegistrationComplete();
	}
	
	public void check(){
		chechRegistrationComplete();
	}

	private void chechRegistrationComplete() {
		DashboardNotification n = null;
		if (!isUserRegistrationComplete()) {
			n = new DashboardNotification();
			n.setContent("E' necessario completare la registrazione");
			n.setTitle("Dati User non completi");
			n.setRead(false);
			notifications.add(n);
		}
		
	}

	private boolean isUserRegistrationComplete() {
		return ((User) VaadinSession.getCurrent().getAttribute(User.class)).isRegistrazioneCompletata();
	}

	@Override
	public int getUnreadNotificationsCount() {
		Predicate<DashboardNotification> unreadPredicate = new Predicate<DashboardNotification>() {
			@Override
			public boolean apply(DashboardNotification input) {
				return !input.isRead();
			}
		};
		return Collections2.filter(notifications, unreadPredicate).size();
	}

	@Override
	public Collection<DashboardNotification> getNotifications() {
		for (DashboardNotification notification : notifications) {
			notification.setRead(true);
		}
		return Collections.unmodifiableCollection(notifications);
	}

}