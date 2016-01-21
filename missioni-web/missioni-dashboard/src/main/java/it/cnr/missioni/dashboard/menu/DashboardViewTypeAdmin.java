package it.cnr.missioni.dashboard.menu;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

import it.cnr.missioni.dashboard.view.CalendarioView;
import it.cnr.missioni.dashboard.view.HomeView;
import it.cnr.missioni.dashboard.view.GestioneMissioneView;
import it.cnr.missioni.dashboard.view.GestioneRimborsoView;
import it.cnr.missioni.dashboard.view.GestioneVeicoloView;


public enum DashboardViewTypeAdmin implements IDashboardMenu{
    HOME("home", HomeView.class, FontAwesome.HOME, true),
    GESTIONE_MISSIONE_ADMIN("gestione missione",GestioneMissioneView.class, FontAwesome.SUITCASE, true),
    GESTIONE_RIMBORSO_ADMIN("gestione rimborso",GestioneRimborsoView.class, FontAwesome.EURO, true),
    ;

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewTypeAdmin(final String viewName,
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

    public static DashboardViewTypeAdmin getByViewName(final String viewName) {
        DashboardViewTypeAdmin result = null;
        for (DashboardViewTypeAdmin viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
