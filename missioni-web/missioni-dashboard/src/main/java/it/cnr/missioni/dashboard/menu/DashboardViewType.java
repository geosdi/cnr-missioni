package it.cnr.missioni.dashboard.menu;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

import it.cnr.missioni.dashboard.view.CalendarioView;
import it.cnr.missioni.dashboard.view.HomeView;
import it.cnr.missioni.dashboard.view.RicercaMissioneView;


public enum DashboardViewType {
    HOME("home", HomeView.class, FontAwesome.HOME, true)
    ,
    AGGIUNGI_MISSIONE("aggiungi missione", null, FontAwesome.SUITCASE, true),
    COMPLETA_REGISTRAZIONE("completa registrazione",null, FontAwesome.USER, true),
    AGGIUNGI_VEICOLO("aggiungi veicolo",null, FontAwesome.CAR, true),
    RICERCA_MISSIONE("ricerca missione",RicercaMissioneView.class, FontAwesome.SEARCH, true),
    CALENDARIO("calendario", CalendarioView.class, FontAwesome.CALENDAR, true)  
    ;

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
