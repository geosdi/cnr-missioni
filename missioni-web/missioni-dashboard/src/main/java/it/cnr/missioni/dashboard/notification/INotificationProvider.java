package it.cnr.missioni.dashboard.notification;

import java.util.Collection;


/**
 * 
 * @author Salvia Vito
 *
 */
public interface INotificationProvider {


    /**
     * @return The number of unread notifications for the current user.
     */
    int getUnreadNotificationsCount();

    /**
     * @return Notifications for the current user.
     */
    Collection<DashboardNotification> getNotifications();



}