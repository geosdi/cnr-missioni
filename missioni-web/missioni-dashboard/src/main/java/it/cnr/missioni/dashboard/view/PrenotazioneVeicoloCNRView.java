package it.cnr.missioni.dashboard.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.component.calendar.CalendarPrenotazioni;
import it.cnr.missioni.dashboard.event.DashboardEventBus;


public final class PrenotazioneVeicoloCNRView extends VerticalLayout implements View {


    /**
     *
     */
    private static final long serialVersionUID = -3779182500846329663L;


    // private CssLayout panel = new CssLayout();

    public PrenotazioneVeicoloCNRView() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        addStyleName("missione-view");
        setSizeFull();
        DashboardEventBus.register(this);
        setHeight("96%");
        setWidth("98%");
        addStyleName(ValoTheme.LAYOUT_CARD);
        addStyleName("panel-view");
        Responsive.makeResponsive(this);
        CalendarPrenotazioni calendar = new CalendarPrenotazioni(null, null);
        calendar.initContent();
        addComponent(calendar.getLayoutCalendar());
    }

    /**
     * @param event
     */
    @Override
    public void enter(ViewChangeEvent event) {
    }

}
