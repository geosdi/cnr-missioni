package it.cnr.missioni.dashboard.component.window;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.IEventResetSelectedMissione;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;

/**
 * @author Salvia Vito
 */
@Theme("valo")
public class WizardSetupWindow extends Window {

    public static final String ID = "wizardWindow";
    /**
     *
     */
    private static final long serialVersionUID = 959422733901644184L;
    private VerticalLayout mainLayout;
    private IWizard wizard;

    private WizardSetupWindow() {
    }

    public static WizardSetupWindow getWizardSetup() {
        return new WizardSetupWindow();
    }

    public WizardSetupWindow withTipo(IWizard wizard) {
        this.wizard = wizard;
        return this;
    }

    public WizardSetupWindow withMissione(Missione missione) {
        wizard.setMissione(missione);
        return this;
    }

    public WizardSetupWindow withEvent(IEventResetSelectedMissione event) {
        wizard.setEvent(event);
        return this;
    }

    public WizardSetupWindow withUser(User user) {
        this.wizard.setUser(user);
        return this;
    }

    public WizardSetupWindow withModifica(boolean modifica) {
        this.wizard.setModifica(modifica);
        return this;
    }

    public WizardSetupWindow withIsAdmin(boolean isAdmin) {
        this.wizard.isAdmin(isAdmin);
        return this;
    }

    public WizardSetupWindow withEnabled(boolean enabled) {
        this.wizard.setEnabled(enabled);
        return this;
    }

//    public VerticalLayout getComponent() {
//        return mainLayout;
//    }

    public void build() {
        wizard.build();
        init();
        setContent(wizard.getWizard());
        addStyleName("profile-window");
        UI.getCurrent().addWindow(this);
    }

    private void init() {
        DashboardEventBus.post(new CloseOpenWindowsEvent());
//        mainLayout = new VerticalLayout();
//        mainLayout.setSizeFull();
//        mainLayout.setMargin(true);
//        mainLayout.addStyleName("wizard");
//        mainLayout.addComponent(wizard.getWizard());
//        mainLayout.setComponentAlignment(wizard.getWizard(), Alignment.TOP_CENTER);
//        mainLayout.setExpandRatio(wizard.getWizard(), 1f);
        setId(ID);
        setVisible(true);
        Responsive.makeResponsive(this);
        setModal(true);
        setResizable(false);
        setClosable(false);
        setHeight("60%");
//        setWidth("45%");
    }

}
