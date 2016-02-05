package it.cnr.missioni.dashboard.utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * @author Salvia Vito
 */
public class Utility {

	private final static ResourceBundle bundle;
	private static NumberFormat formatter = new DecimalFormat("0.##", new DecimalFormatSymbols(new Locale("tr_TR")));

	static {
		bundle = ResourceBundle.getBundle("message");
	}

	public static String getStringDecimalFormat(double d) {
		return formatter.format(d);
	}

	/**
	 * 
	 * Ritorna un double formattato
	 * 
	 * @param d
	 * @return double
	 */
	public static double getDoubleDecimalFormat(double d) {
		return Double.parseDouble(formatter.format(d));
	}

	/**
	 * 
	 * Costruisce i popup per le notifiche
	 * 
	 * @param body
	 * @param description
	 * @param t
	 * @return Notification
	 */
	public static Notification getNotification(String body, String description, Type t) {

		Notification notification = new Notification(body, t);
		notification.setDescription(description);
		notification.setHtmlContentAllowed(true);
		notification.setStyleName(EnumStyleNotification.getStyle(t));
		notification.setPosition(Position.BOTTOM_CENTER);
		notification.setDelayMsec(2000);
		notification.show(Page.getCurrent());
		return notification;

	}

	private enum EnumStyleNotification {
		ERROR_MESSAGE("bar error small"), HUMANIZED_MESSAGE("bar success small"), WARNING_MESSAGE(
				"bar warning small"), TRAY_NOTIFICATION("tray dark small closable");

		private String style;

		EnumStyleNotification(String style) {
			this.setStyle(style);
		}

		public static String getStyle(Type t) {
			for (EnumStyleNotification e : EnumStyleNotification.values()) {
				if (e.name() == t.name())
					return e.getStyle();
			}
			return null;
		}

		/**
		 * @return the style
		 */
		public String getStyle() {
			return style;
		}

		/**
		 * @param style
		 */
		public void setStyle(String style) {
			this.style = style;
		}

	}

	public static String getMessage(String key) {
		return bundle.getString(key);
	}


}
