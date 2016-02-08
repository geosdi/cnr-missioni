package it.cnr.missioni.dashboard.menu;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

import it.cnr.missioni.dashboard.view.PrenotazioneVeicoloCNRView;
import it.cnr.missioni.dashboard.view.HomeView;
import it.cnr.missioni.dashboard.view.admin.AdminHomeView;
import it.cnr.missioni.dashboard.view.admin.GestioneMassimaleView;
import it.cnr.missioni.dashboard.view.admin.GestioneNazioneView;
import it.cnr.missioni.dashboard.view.admin.GestioneQualificaUserView;
import it.cnr.missioni.dashboard.view.admin.GestioneRimborsoKmView;
import it.cnr.missioni.dashboard.view.admin.GestioneTipologiaSpesaView;
import it.cnr.missioni.dashboard.view.admin.GestioneUserAdminView;
import it.cnr.missioni.dashboard.view.admin.GestioneVeicoloCNRView;
import it.cnr.missioni.dashboard.view.GestioneMissioneView;
import it.cnr.missioni.dashboard.view.GestioneRimborsoView;
import it.cnr.missioni.dashboard.view.GestioneVeicoloView;


public enum DashboardViewType {
    HOME_ADMIN("home admin",AdminHomeView.class, FontAwesome.USER_MD, true),

    HOME("home", HomeView.class, FontAwesome.HOME, true) ,
    COMPLETA_REGISTRAZIONE("completa registrazione",null, FontAwesome.USER, true),
    GESTIONE_MISSIONE("gestione missione",GestioneMissioneView.class, FontAwesome.SUITCASE, false),
    GESTIONE_RIMBORSO("gestione rimborso",GestioneRimborsoView.class, FontAwesome.EURO, false),
    GESTIONE_VEICOLO_PROPRIO("gestione veicolo proprio",GestioneVeicoloView.class, FontAwesome.CAR, false),
    CALENDARIO("prenotazione veicolo CNR", PrenotazioneVeicoloCNRView.class, FontAwesome.CALENDAR, false),
    GESTIONE_USER_ADMIN("gestione user",GestioneUserAdminView.class, FontAwesome.USER, false)
    ,
    GESTIONE_VEICOLO_CNR_ADMIN("gestione veicolo CNR",GestioneVeicoloCNRView.class, FontAwesome.CAR, false),
    GESTIONE_QUALIFICA_USER_ADMIN("gestione qualifica user",GestioneQualificaUserView.class, FontAwesome.USERS, false),
    GESTIONE_NAZIONE_ADMIN("gestione nazione",GestioneNazioneView.class, FontAwesome.GLOBE, false),
    GESTIONE_RIMBORSO_KM_ADMIN("gestione rimborso km",GestioneRimborsoKmView.class, FontAwesome.EURO, false),
    GESTIONE_TIPOLOGIA_SPESA_ADMIN("gestione tipologia spesa",GestioneTipologiaSpesaView.class, FontAwesome.LIST, false),
    GESTIONE_MASSIMALE_ADMIN("gestione massimale",GestioneMassimaleView.class, FontAwesome.EURO, false),

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
