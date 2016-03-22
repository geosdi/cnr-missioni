package it.cnr.missioni.dashboard.menu;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

import it.cnr.missioni.dashboard.view.PrenotazioneVeicoloCNRView;
import it.cnr.missioni.dashboard.view.HomeView;
import it.cnr.missioni.dashboard.view.admin.AdminHomeView;
import it.cnr.missioni.dashboard.view.admin.GestioneMassimaleView;
import it.cnr.missioni.dashboard.view.admin.GestioneMissioneAdminView;
import it.cnr.missioni.dashboard.view.admin.GestioneNazioneView;
import it.cnr.missioni.dashboard.view.admin.GestioneQualificaUserView;
import it.cnr.missioni.dashboard.view.admin.GestioneRimborsoAdminView;
import it.cnr.missioni.dashboard.view.admin.GestioneRimborsoKmView;
import it.cnr.missioni.dashboard.view.admin.GestioneTipologiaSpesaView;
import it.cnr.missioni.dashboard.view.admin.GestioneUserAdminView;
import it.cnr.missioni.dashboard.view.admin.GestioneVeicoloCNRView;
import it.cnr.missioni.dashboard.view.GestioneMissioneView;
import it.cnr.missioni.dashboard.view.GestioneRimborsoView;
import it.cnr.missioni.dashboard.view.GestioneVeicoloView;


public enum DashboardViewType {
    HOME_ADMIN("home admin",AdminHomeView.class, FontAwesome.HOME, true),

    HOME("home", HomeView.class, FontAwesome.HOME, true) ,
    COMPLETA_REGISTRAZIONE("completa registrazione",null, FontAwesome.USER, true),
    GESTIONE_MISSIONE("gestione missione",GestioneMissioneView.class, FontAwesome.SUITCASE, true),
    GESTIONE_RIMBORSO("gestione rimborso",GestioneRimborsoView.class, FontAwesome.EURO, true),
    GESTIONE_VEICOLO_PROPRIO("gestione veicolo proprio",GestioneVeicoloView.class, FontAwesome.CAR, true),
    CALENDARIO("prenotazione veicolo CNR", PrenotazioneVeicoloCNRView.class, FontAwesome.CALENDAR, true),
    GESTIONE_USER_ADMIN("gestione user",GestioneUserAdminView.class, FontAwesome.USERS, true)
    ,
    GESTIONE_MISSIONE_ADMIN("gestione missione admin",GestioneMissioneAdminView.class, FontAwesome.SUITCASE, true),
    GESTIONE_RIMBORSO_ADMIN("gestione rimborso admin",GestioneRimborsoAdminView.class, FontAwesome.EURO, true),
    GESTIONE_VEICOLO_CNR_ADMIN("gestione veicolo CNR",GestioneVeicoloCNRView.class, FontAwesome.CAR, true),
    GESTIONE_QUALIFICA_USER_ADMIN("gestione qualifica user",GestioneQualificaUserView.class, FontAwesome.GRADUATION_CAP, true),
    GESTIONE_NAZIONE_ADMIN("gestione nazione",GestioneNazioneView.class, FontAwesome.GLOBE, true),
    GESTIONE_RIMBORSO_KM_ADMIN("gestione rimborso km",GestioneRimborsoKmView.class, FontAwesome.EURO, true),
    GESTIONE_TIPOLOGIA_SPESA_ADMIN("gestione tipologia spesa",GestioneTipologiaSpesaView.class, FontAwesome.LIST, true),
    GESTIONE_MASSIMALE_ADMIN("gestione massimale",GestioneMassimaleView.class, FontAwesome.EURO, true),

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
