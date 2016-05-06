package it.cnr.missioni.dashboard.component.calendar;

import com.google.common.eventbus.Subscribe;
import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.event.CalendarEventProvider;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.*;
import com.vaadin.addon.calendar.ui.handler.BasicDateClickHandler;
import com.vaadin.annotations.Push;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.window.IPrenotazioneWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CalendarUpdateEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IPrenotazioneSearchBuilder;
import it.cnr.missioni.model.user.RuoloUserEnum;
import it.cnr.missioni.rest.api.response.prenotazione.PrenotazioniStore;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Push
public class CalendarPrenotazioni implements Serializable {

    private static final long serialVersionUID = -5436777475398410597L;
    private final Label captionLabel = new Label("");
    HorizontalLayout layoutButtons;
    private GregorianCalendar calendar;
    private Calendar calendarComponent;
    private Date currentMonthsFirstDate;
    private Button monthButton;
    private Button weekButton;
    private Button dayButton;
    private Button nextButton;
    private Button prevButton;
    private Mode viewMode = Mode.MONTH;
    private boolean testBench = false;
    private Integer firstHour;
    private Integer lastHour;
    private Integer firstDay;
    private Integer lastDay;
    private VerticalLayout layoutCalendar;
    private String width;
    private String height;
    private static List<CalendarEvent> events = new ArrayList<CalendarEvent>();

    public CalendarPrenotazioni(String width, String height) {
        this.width = width;
        this.height = height;
    }

    private static Date getEndOfDay(java.util.Calendar calendar, Date date) {
        java.util.Calendar calendarClone = (java.util.Calendar) calendar.clone();
        calendarClone.setTime(date);
        calendarClone.set(java.util.Calendar.MILLISECOND,
                calendarClone.getActualMaximum(java.util.Calendar.MILLISECOND));
        calendarClone.set(java.util.Calendar.SECOND, calendarClone.getActualMaximum(java.util.Calendar.SECOND));
        calendarClone.set(java.util.Calendar.MINUTE, calendarClone.getActualMaximum(java.util.Calendar.MINUTE));
        calendarClone.set(java.util.Calendar.HOUR, calendarClone.getActualMaximum(java.util.Calendar.HOUR));
        calendarClone.set(java.util.Calendar.HOUR_OF_DAY,
                calendarClone.getActualMaximum(java.util.Calendar.HOUR_OF_DAY));
        return calendarClone.getTime();
    }

//    private static Date getStartOfDay(java.util.Calendar calendar, Date date) {
//        java.util.Calendar calendarClone = (java.util.Calendar) calendar.clone();
//        calendarClone.setTime(date);
//        calendarClone.set(java.util.Calendar.MILLISECOND, 0);
//        calendarClone.set(java.util.Calendar.SECOND, 0);
//        calendarClone.set(java.util.Calendar.MINUTE, 0);
//        calendarClone.set(java.util.Calendar.HOUR, 0);
//        calendarClone.set(java.util.Calendar.HOUR_OF_DAY, 0);
//        return calendarClone.getTime();
//    }

    public void initContent() {
        initCalendar();
        initLayoutContent();
    }

    private void initLayoutContent() {
        DashboardEventBus.register(this);
        initNavigationButtons();
        VerticalLayout hl = new VerticalLayout();
        hl.setSpacing(true);
        hl.addComponent(captionLabel);
        CssLayout group = new CssLayout();
        group.addStyleName("v-component-group");
        hl.addComponent(layoutButtons);
        hl.setComponentAlignment(layoutButtons, Alignment.MIDDLE_CENTER);
        hl.setComponentAlignment(captionLabel, Alignment.MIDDLE_CENTER);
        captionLabel.addStyleName(ValoTheme.LABEL_LIGHT);
        captionLabel.addStyleName("label-calendar");
        HorizontalLayout controlPanel = new HorizontalLayout();
        controlPanel.setSpacing(true);
        controlPanel.setWidth("100%");
        controlPanel.setMargin(new MarginInfo(false, false, true, false));
        layoutCalendar = new VerticalLayout();
        layoutCalendar.addComponent(controlPanel);
        layoutCalendar.addComponent(hl);
        layoutCalendar.addComponent(getCalendarComponent());
        addListener();
    }

    // Aggiunge i listener
    private void addListener() {
        getCalendarComponent().setHandler(new BasicDateClickHandler() {

            /**
             *
             */
            private static final long serialVersionUID = 1038308797415286263L;

            @Override
            public void dateClick(DateClickEvent event) {
                // let BasicDateClickHandler handle calendar dates, and update
                // only the other parts of UI here
                super.dateClick(event);
                switchToDayView();
                monthButton.setVisible(true);
            }
        });

        getCalendarComponent().setHandler(new EventClickHandler() {

            /**
             *
             */
            private static final long serialVersionUID = 1884486780975341603L;

            public void eventClick(EventClick event) {

                //La prenotazione è modificabile solamente dall'user proprietario o dall'admin
                boolean enabled = DashboardUI.getCurrentUser().getCredenziali().getRuoloUtente() == RuoloUserEnum.UTENTE_ADMIN
                        || ((PrenotazioneEvent) event.getCalendarEvent()).getIdUser().equals(DashboardUI.getCurrentUser().getId());


//					PrenotazioneWindow.open(getCalendarComponent(), (PrenotazioneEvent) event.getCalendarEvent(), false,enabled,true);
                IPrenotazioneWindow.PrenotazioneWindow.getPrenotazioneWindow().withBean((PrenotazioneEvent) event.getCalendarEvent()).withCalendarComponent(getCalendarComponent())
                        .withIsAdmin(false).withEnabled(enabled).withModifica(true).build();
            }
        });

        getCalendarComponent().setHandler(new RangeSelectHandler() {

            /**
             *
             */
            private static final long serialVersionUID = 4617125157294854765L;

            public void rangeSelect(RangeSelectEvent event) {
                handleRangeSelect(event);
            }
        });

        getCalendarComponent().setHandler(new BasicDateClickHandler() {

            /**
             *
             */
            private static final long serialVersionUID = -4442209950706045534L;

            @Override
            public void dateClick(DateClickEvent event) {
                super.dateClick(event);
                switchToDayView();
            }
        });

    }

    private void initNavigationButtons() {
        this.layoutButtons = new HorizontalLayout();
        this.layoutButtons.setSpacing(true);
        monthButton = new Button("Mese", new ClickListener() {


            /**
             *
             */
            private static final long serialVersionUID = -4681033617026135027L;

            @Override
            public void buttonClick(ClickEvent event) {
                switchToMonthView();
            }
        });

        weekButton = new Button("Settimana", new ClickListener() {


            /**
             *
             */
            private static final long serialVersionUID = -2946257329173001870L;

            @Override
            public void buttonClick(ClickEvent event) {
                // simulate week click
                WeekClickHandler handler = (WeekClickHandler) getCalendarComponent().getHandler(WeekClick.EVENT_ID);
                handler.weekClick(new WeekClick(getCalendarComponent(), calendar.get(GregorianCalendar.WEEK_OF_YEAR),
                        calendar.get(GregorianCalendar.YEAR)));
            }
        });

        dayButton = new Button("Giorno", new ClickListener() {


            /**
             *
             */
            private static final long serialVersionUID = -4869635114067013308L;

            @Override
            public void buttonClick(ClickEvent event) {
                // simulate day click
                BasicDateClickHandler handler = (BasicDateClickHandler) getCalendarComponent()
                        .getHandler(DateClickEvent.EVENT_ID);
                handler.dateClick(new DateClickEvent(getCalendarComponent(), calendar.getTime()));
            }
        });

        nextButton = new Button("", new Button.ClickListener() {

            /**
             *
             */
            private static final long serialVersionUID = -6795124780982940969L;

            @Override
            public void buttonClick(ClickEvent event) {
                handleNextButtonClick();
            }
        });

        prevButton = new Button("", new Button.ClickListener() {

            /**
             *
             */
            private static final long serialVersionUID = 1918853730365477105L;

            @Override
            public void buttonClick(ClickEvent event) {
                handlePreviousButtonClick();
            }
        });

        monthButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        monthButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        weekButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        weekButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        dayButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        dayButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        prevButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        prevButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        nextButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        nextButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        nextButton.setIcon(FontAwesome.FORWARD);
        prevButton.setIcon(FontAwesome.BACKWARD);
        this.layoutButtons.addComponents(prevButton, dayButton, weekButton, monthButton, nextButton);
    }

    private void initCalendar() {
        setCalendarComponent((new Calendar(new MyEventProvider())));
        getCalendarComponent().setWidth(this.width);
        getCalendarComponent().setHeight(this.height);

        if (width != null || height != null) {
            if (height != null) {
                getCalendarComponent().setHeight(height);
            }
            if (width != null) {
                getCalendarComponent().setWidth(width);
            }
        } else {
            getCalendarComponent().setSizeFull();
        }

        if (firstHour != null && lastHour != null) {
            getCalendarComponent().setFirstVisibleHourOfDay(firstHour);
            getCalendarComponent().setLastVisibleHourOfDay(lastHour);
        }

        if (firstDay != null && lastDay != null) {
            getCalendarComponent().setFirstVisibleDayOfWeek(firstDay);
            getCalendarComponent().setLastVisibleDayOfWeek(lastDay);
        }
        Date today = getToday();
        calendar = new GregorianCalendar();
        calendar.setTime(today);
        getCalendarComponent().getInternalCalendar().setTime(today);
        currentMonthsFirstDate = calendar.getTime();
        int rollAmount = calendar.get(GregorianCalendar.DAY_OF_MONTH) - 1;
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -rollAmount);
        currentMonthsFirstDate = calendar.getTime();
        getCalendarComponent().setStartDate(currentMonthsFirstDate);
        calendar.add(GregorianCalendar.MONTH, 1);
        calendar.add(GregorianCalendar.DATE, -1);
        getCalendarComponent().setEndDate(calendar.getTime());
        updateCaptionLabel();
    }

    private Date getToday() {
        if (testBench) {
            GregorianCalendar testDate = new GregorianCalendar();
            testDate.set(GregorianCalendar.YEAR, 2000);
            testDate.set(GregorianCalendar.MONTH, 0);
            testDate.set(GregorianCalendar.DATE, 10);
            testDate.set(GregorianCalendar.HOUR_OF_DAY, 0);
            testDate.set(GregorianCalendar.MINUTE, 0);
            testDate.set(GregorianCalendar.SECOND, 0);
            testDate.set(GregorianCalendar.MILLISECOND, 0);
            return testDate.getTime();
        }
        return new Date();
    }

    private void handleNextButtonClick() {
        switch (viewMode) {
            case MONTH:
                nextMonth();
                break;
            case WEEK:
                nextWeek();
                break;
            case DAY:
                nextDay();
                break;
        }
    }

    private void handlePreviousButtonClick() {
        switch (viewMode) {
            case MONTH:
                previousMonth();
                break;
            case WEEK:
                previousWeek();
                break;
            case DAY:
                previousDay();
                break;
        }
    }

    private void handleRangeSelect(RangeSelectEvent event) {
        Date start = event.getStart();
        Date end = event.getEnd();
        if (event.isMonthlyMode()) {
            end = getEndOfDay(calendar, end);
        }
        PrenotazioneEvent p = new PrenotazioneEvent();
        p.setStart(start);
        p.setEnd(end);
//		PrenotazioneWindow.open(getCalendarComponent(), p,false,true, false);
        IPrenotazioneWindow.PrenotazioneWindow.getPrenotazioneWindow().withBean(p).withCalendarComponent(getCalendarComponent())
                .withIsAdmin(false).withEnabled(true).withModifica(false).withListPrenotazione(events).build();
    }

    private void nextMonth() {
        rollMonth(1);
    }

    private void previousMonth() {
        rollMonth(-1);
    }

    private void nextWeek() {
        rollWeek(1);
    }

    private void previousWeek() {
        rollWeek(-1);
    }

    private void nextDay() {
        rollDate(1);
    }

    private void previousDay() {
        rollDate(-1);
    }

    private void rollMonth(int direction) {
        calendar.setTime(currentMonthsFirstDate);
        calendar.add(GregorianCalendar.MONTH, direction);
        resetTime(false);
        currentMonthsFirstDate = calendar.getTime();
        getCalendarComponent().setStartDate(currentMonthsFirstDate);
        updateCaptionLabel();
        calendar.add(GregorianCalendar.MONTH, 1);
        calendar.add(GregorianCalendar.DATE, -1);
        resetCalendarTime(true);
    }

    private void rollWeek(int direction) {
        calendar.add(GregorianCalendar.WEEK_OF_YEAR, direction);
        calendar.set(GregorianCalendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        resetCalendarTime(false);
        resetTime(true);
        calendar.add(GregorianCalendar.DATE, 6);
        getCalendarComponent().setEndDate(calendar.getTime());
    }

    private void rollDate(int direction) {
        calendar.add(GregorianCalendar.DATE, direction);
        resetCalendarTime(false);
        resetCalendarTime(true);
    }

    private void updateCaptionLabel() {
        DateFormatSymbols s = new DateFormatSymbols();
        String month = s.getMonths()[calendar.get(GregorianCalendar.MONTH)];
        captionLabel.setValue(month + " " + calendar.get(GregorianCalendar.YEAR));
    }

//    private PrenotazioneEvent getNewEvent(String caption, Date start, Date end) {
//        PrenotazioneEvent event = new PrenotazioneEvent();
//        event.setCaption(caption);
//        event.setStart(start);
//        event.setEnd(end);
//        return event;
//    }

    /*
     * Switch the view to week view.
     */
    public void switchToWeekView() {
        viewMode = Mode.WEEK;
    }

    /*
     * Switch the Calendar component's start and end date range to the target
     * month only. (sample range: 01.01.2010 00:00.000 - 31.01.2010 23:59.999)
     */
    public void switchToMonthView() {
        viewMode = Mode.MONTH;
        int rollAmount = calendar.get(GregorianCalendar.DAY_OF_MONTH) - 1;
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -rollAmount);
        getCalendarComponent().setStartDate(calendar.getTime());
        updateCaptionLabel();
        calendar.add(GregorianCalendar.MONTH, 1);
        calendar.add(GregorianCalendar.DATE, -1);
        getCalendarComponent().setEndDate(calendar.getTime());
        calendar.setTime(getToday());
        // resetCalendarTime(true);
    }

    /*
     * Switch to day view (week view with a single day visible).
     */
    public void switchToDayView() {
        viewMode = Mode.DAY;
    }

    private void resetCalendarTime(boolean resetEndTime) {
        resetTime(resetEndTime);
        if (resetEndTime) {
            getCalendarComponent().setEndDate(calendar.getTime());
        } else {
            getCalendarComponent().setStartDate(calendar.getTime());
            updateCaptionLabel();
        }
    }

    /*
     * Resets the calendar time (hour, minute second and millisecond) either to
     * zero or maximum value.
     */
    private void resetTime(boolean max) {
        if (max) {
            calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getMaximum(GregorianCalendar.HOUR_OF_DAY));
            calendar.set(GregorianCalendar.MINUTE, calendar.getMaximum(GregorianCalendar.MINUTE));
            calendar.set(GregorianCalendar.SECOND, calendar.getMaximum(GregorianCalendar.SECOND));
            calendar.set(GregorianCalendar.MILLISECOND, calendar.getMaximum(GregorianCalendar.MILLISECOND));
        } else {
            calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
            calendar.set(GregorianCalendar.MINUTE, 0);
            calendar.set(GregorianCalendar.SECOND, 0);
            calendar.set(GregorianCalendar.MILLISECOND, 0);
        }
    }

    /**
     * @return the layoutCalendar
     */
    public VerticalLayout getLayoutCalendar() {
        return layoutCalendar;
    }

    /**
     * @param layoutCalendar
     */
    public void setLayoutCalendar(VerticalLayout layoutCalendar) {
        this.layoutCalendar = layoutCalendar;
    }

    /**
     * @return the calendarComponent
     */
    public Calendar getCalendarComponent() {
        return calendarComponent;
    }

    /**
     * @param calendarComponent
     */
    public void setCalendarComponent(Calendar calendarComponent) {
        this.calendarComponent = calendarComponent;
    }

    @Subscribe
    public void updateCalendar(CalendarUpdateEvent calendarUpdateEvent) {
        getCalendarComponent().markAsDirty();
    }

    private enum Mode {
        MONTH, WEEK, DAY;
    }

    public static class MyEventProvider implements CalendarEventProvider {

        private static final long serialVersionUID = -3655982234130426761L;



        public MyEventProvider() {

        }

        public void addEvent(CalendarEvent BasicEvent) {
            events.add(BasicEvent);
        }

        @Override
        public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
            events = new ArrayList<CalendarEvent>();
            try {
                IPrenotazioneSearchBuilder prenotazioneSearchBuilder = IPrenotazioneSearchBuilder.PrenotazioneSearchBuilder
                        .getPrenotazioneSearchBuilder()
                        .withRangeData(new DateTime(startDate.getTime()), new DateTime(endDate.getTime()));
                PrenotazioniStore prenotazioniStore = ClientConnector.getPrenotazione(prenotazioneSearchBuilder);

                if (prenotazioniStore != null) {
                    prenotazioniStore.getPrenotazioni().forEach(p -> {
                        PrenotazioneEvent prenotazioneEvent = new PrenotazioneEvent();
                        prenotazioneEvent.setAllDay(p.isAllDay());
                        prenotazioneEvent.setId(p.getId());
                        prenotazioneEvent.setIdUser(p.getIdUser());
                        prenotazioneEvent.setStart(p.getDataFrom().toDate());
                        prenotazioneEvent.setEnd(p.getDataTo().toDate());
                        prenotazioneEvent.setVeicolo(p.getIdVeicoloCNR());
                        prenotazioneEvent.setVeicoloDescription(p.getDescriptionVeicoloCNR());
                        prenotazioneEvent.setCaption(p.getLocalita() + " - " + p.getDescrizione() + " - " + p.getDescriptionVeicoloCNR());
                        prenotazioneEvent.setLocalita(p.getLocalita());
                        prenotazioneEvent.setDescrizione(p.getDescrizione());
                        events.add(prenotazioneEvent);
                    });
                }
                // lista.addAll(prenotazioniStore.getPrenotazioni());
            } catch (Exception e) {
                Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                        Type.ERROR_MESSAGE);
            }
            return events;
        }
    }

}
