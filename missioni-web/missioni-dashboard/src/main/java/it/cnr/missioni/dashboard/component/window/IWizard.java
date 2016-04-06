package it.cnr.missioni.dashboard.component.window;

import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.IEventResetSelectedMissione;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.event.WizardProgressListener;

/**
 * @author Salvia Vito
 */
public interface IWizard {

    /**
     *
     */
    void build();

    /**
     * @return {@link Wizard}
     */
    Wizard getWizard();

    /**
     * @param missione
     */
    void setMissione(Missione missione);

    /**
     * @param user
     */
    void setUser(User user);

    /**
     * @param modifica
     */
    void setModifica(boolean modifica);

    /**
     * @param enabled
     */
    void setEnabled(boolean enabled);

    /**
     * @param enambled
     */
    void isAdmin(boolean enambled);

    /***
     * @param event
     */
    void setEvent(IEventResetSelectedMissione event);


    public abstract class AbstractWizard implements WizardProgressListener, IWizard {

        protected boolean enabled;
        protected boolean modifica;
        protected boolean isAdmin;
        private Wizard wizard;

        protected void endWizard() {
            getWizard().setVisible(false);
            DashboardEventBus.post(new CloseOpenWindowsEvent());
        }

        protected void buildWizard() {
            setWizard(new Wizard());
            getWizard().setUriFragmentEnabled(true);
            getWizard().setVisible(true);
            getWizard().addListener(this);
            getWizard().getBackButton().setCaption("Indietro");
            getWizard().getCancelButton().setCaption("Cancella");
            getWizard().getNextButton().setCaption("Avanti");
            getWizard().getFinishButton().setCaption("Concludi");
            getWizard().setWidth("100%");
            getWizard().setHeight("95%");
        }

        /**
         * @return the wizard
         */
        public Wizard getWizard() {
            return wizard;
        }

        /**
         * @param wizard
         */
        public void setWizard(Wizard wizard) {
            this.wizard = wizard;
        }

        public abstract void setMissione(Missione missione);

        public abstract void setUser(User user);

        public abstract void setEnabled(boolean enabled);

        public abstract void isAdmin(boolean isAdmin);

        public abstract void setEvent(IEventResetSelectedMissione event);

        public abstract void setModifica(boolean modifica);


    }


}
