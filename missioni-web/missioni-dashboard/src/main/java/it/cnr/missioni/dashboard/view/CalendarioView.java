package it.cnr.missioni.dashboard.view;

import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;

import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.addon.calendar.ui.Calendar.TimeFormat;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Panel;


@SuppressWarnings("serial")
public final class CalendarioView extends Panel implements View{


	public CalendarioView() {

		setSizeFull();
		setContent(buildCalendario());


	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	private Calendar buildCalendario() {
    	Calendar cal = new Calendar("Calendario");
//      cal.setHeight("80%");
  	cal.setHeight("80%");
      cal.setWeeklyCaptionFormat("dd/MM/yyyy");
      cal.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
      cal.setLocale(Locale.ITALY);
      cal.setTimeFormat(TimeFormat.Format24H);
      DateTime now = new DateTime();
      DateTime start = new DateTime(now.getYear(),now.getMonthOfYear(),1,0,0);
      DateTime end = new DateTime(now.getYear(),now.getMonthOfYear(),now.dayOfMonth().getMaximumValue(),0,0);
      cal.setStartDate(start.toDate());
      cal.setEndDate(end.toDate());
      return cal;
      
	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}

}
