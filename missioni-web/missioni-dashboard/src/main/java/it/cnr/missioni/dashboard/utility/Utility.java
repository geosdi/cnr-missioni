package it.cnr.missioni.dashboard.utility;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.joda.time.Days;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

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
	 * Recupera il server su cui sono stati deployati i rest services
	 * 
	 * @param property
	 * @return
	 */
	public static String getRestServiccesURL(String property){
		Properties prop = new Properties();
		InputStream input = null;

		try {
	        InputStream in = Utility.class.getResourceAsStream("/config.properties");

			prop.load(in);

			return prop.getProperty(property);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Calcola il numero di giorni trascorsi all'estero e calcola il TAM
	 * 
	 * @param missione
	 * @return
	 */
	public static int getDaysEstero(Missione missione){
		int days = 0;
		try {
			Nazione nazione = ClientConnector
					.getNazione(NazioneSearchBuilder.getNazioneSearchBuilder().withId(missione.getIdNazione()))
					.getNazione().get(0);
			MassimaleStore massimaleStore = ClientConnector
					.getMassimale(MassimaleSearchBuilder.getMassimaleSearchBuilder()
							.withLivello(DashboardUI.getCurrentUser().getDatiCNR().getLivello().name())
							.withAreaGeografica(nazione.getAreaGeografica().name())
							.withTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name()));

			if (massimaleStore.getTotale() > 0) {
				missione.getRimborso().calcolaTotaleTAM(massimaleStore.getMassimale().get(0),
						missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata(),
						missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno());
			}
			days = Days.daysBetween(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata(),
					missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno()).getDays();
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		return days;

	}
	
	/**
	 * 
	 * Costruisce la label
	 * 
	 * @param caption
	 * @param value
	 * @return
	 */
	public static Label buildLabel(String caption, String value) {
		Label labelValue = new Label("<b>" + caption + "</b>" + value, ContentMode.HTML);
		labelValue.setStyleName(ValoTheme.LABEL_LIGHT);

		labelValue.setWidth("50%");
		return labelValue;
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
