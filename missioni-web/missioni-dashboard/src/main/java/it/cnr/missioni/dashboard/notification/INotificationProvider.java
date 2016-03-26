package it.cnr.missioni.dashboard.notification;

import java.util.Collection;


/**
 * 
 * @author Salvia Vito
 *
 */
public interface INotificationProvider {


	void check();
	
    /**
     * @return The number of unread notifications for the current user.
     */
    int getUnreadNotificationsCount();
    
    /**
     * 
     * @param prenotazione
     */
    void addPrenotazione(String prenotazione);

    /**
     * @return Notifications for the current user.
     */
    Collection<DashboardNotification> getNotifications();



}