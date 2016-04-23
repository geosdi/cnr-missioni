package it.cnr.missioni.dashboard.menu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

import it.cnr.missioni.dashboard.view.GestioneMissioneView;
import it.cnr.missioni.dashboard.view.GestioneRimborsoView;
import it.cnr.missioni.dashboard.view.GestioneVeicoloView;
import it.cnr.missioni.dashboard.view.HomeView;
import it.cnr.missioni.dashboard.view.PrenotazioneVeicoloCNRView;
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


public enum DashboardViewType {
	//USER MENU
    HOME("home", HomeView.class, FontAwesome.HOME, true,false,true),
    COMPLETA_REGISTRAZIONE("completa registrazione", null, FontAwesome.USER, true,false,false),
    GESTIONE_MISSIONE("gestione missione", GestioneMissioneView.class, FontAwesome.SUITCASE, true,false,true),
    GESTIONE_RIMBORSO("gestione rimborso", GestioneRimborsoView.class, FontAwesome.EURO, true,false,true),
    GESTIONE_VEICOLO_PROPRIO("gestione veicolo proprio", GestioneVeicoloView.class, FontAwesome.CAR, true,false,true),
    CALENDARIO("prenotazione autovettura </br> di servizio", PrenotazioneVeicoloCNRView.class, FontAwesome.CALENDAR, true,false,true),
    //ADMIN MENU
    HOME_ADMIN("home admin", AdminHomeView.class, FontAwesome.HOME, true,true,true),
    GESTIONE_USER_ADMIN("gestione user", GestioneUserAdminView.class, FontAwesome.USERS, true,true,true),
    GESTIONE_MISSIONE_ADMIN("gestione missione admin", GestioneMissioneAdminView.class, FontAwesome.SUITCASE, true,true,true),
    GESTIONE_RIMBORSO_ADMIN("gestione rimborso admin", GestioneRimborsoAdminView.class, FontAwesome.EURO, true,true,true),
    GESTIONE_VEICOLO_CNR_ADMIN("gestione </br> autovettura di servizio", GestioneVeicoloCNRView.class, FontAwesome.CAR, true,true,true),
    GESTIONE_QUALIFICA_USER_ADMIN("gestione qualifica user", GestioneQualificaUserView.class, FontAwesome.GRADUATION_CAP, true,true,true),
    GESTIONE_NAZIONE_ADMIN("gestione nazione", GestioneNazioneView.class, FontAwesome.GLOBE, true,true,true),
    GESTIONE_RIMBORSO_KM_ADMIN("gestione rimborso km", GestioneRimborsoKmView.class, FontAwesome.EURO, true,true,true),
    GESTIONE_TIPOLOGIA_SPESA_ADMIN("gestione tipologia spesa", GestioneTipologiaSpesaView.class, FontAwesome.LIST, true,true,true),
    GESTIONE_MASSIMALE_ADMIN("gestione massimale", GestioneMassimaleView.class, FontAwesome.EURO, true,true,true);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;
    private final boolean isAdmin;
    private final boolean registrationComplete;

    private DashboardViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful,final boolean isAdmin,final boolean registrationComplete) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
        this.isAdmin = isAdmin;
        this.registrationComplete = registrationComplete;
    }

    public static List<DashboardViewType> getMenuUser() {
    	return Arrays.asList(DashboardViewType.values()).stream().filter(f -> !f.isAdmin).filter(f -> f.registrationComplete).collect(Collectors.toList());
    }
    
    public static List<DashboardViewType> getMenuUserRegistrationNotComplete() {
    	return Arrays.asList(DashboardViewType.values()).stream().filter(f -> !f.isAdmin).filter(f -> !f.registrationComplete).collect(Collectors.toList());
    }

    public static List<DashboardViewType> getMenuAdmin() {
    	return Arrays.asList(DashboardViewType.values()).stream().filter(f -> f.isAdmin).collect(Collectors.toList());
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

}
