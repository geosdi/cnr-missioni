package it.cnr.missioni.dashboard.component.window;

import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.DeletePrenotazioneAction;
import it.cnr.missioni.dashboard.action.PrenotazioneAction;
import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.component.form.prenotazione.IPrenotazioneForm;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;

public interface IPrenotazioneWindow extends IWindow<PrenotazioneEvent, IPrenotazioneWindow> {

    /**
     * @param calendarComponent
     * @return {@link IPrenotazioneWindow}
     */
    IPrenotazioneWindow withCalendarComponent(Calendar calendarComponent);

    class PrenotazioneWindow extends IWindow.AbstractWindow<PrenotazioneEvent, IPrenotazioneWindow>
            implements IPrenotazioneWindow {

        public static final String ID = "prenotazionewindow";
        /**
         *
         */
        private static final long serialVersionUID = 7733724385057534169L;
        // FIELD PRENOTAZIONE
        private IPrenotazioneForm prenotazioneForm;
        private Calendar calendarComponent;

        protected PrenotazioneWindow() {
        }

        public static IPrenotazioneWindow getPrenotazioneWindow() {
            return new PrenotazioneWindow();
        }

        public IPrenotazioneWindow build() {
            buildWindow();
            setId(ID);
//			this.prenotazioneForm = PrenotazioneForm(calendarComponent, prenotazione, isAdmin, enabled, modifica);
            this.prenotazioneForm = IPrenotazioneForm.PrenotazioneForm.getPrenotazioneForm().withBean(bean).withCalendarComponents(calendarComponent).withIsAdmin(isAdmin).withModifica(modifica).withEnabled(enabled).build();
            detailsWrapper.addComponent(buildTab("Prenotazione", FontAwesome.CALENDAR_O, this.prenotazioneForm));
            if (enabled)
                content.addComponent(buildFooter());
            UI.getCurrent().addWindow(this);
            this.focus();
            return self();
        }

        /**
         * @param calendarComponent
         * @return {@link IPrenotazioneWindow}
         */
        public IPrenotazioneWindow withCalendarComponent(Calendar calendarComponent) {
            this.calendarComponent = calendarComponent;
            return self();
        }

        protected Component buildFooter() {
            ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
            ok.addClickListener(new ClickListener() {
                /**
                 *
                 */
                private static final long serialVersionUID = -8098718471864221266L;

                @Override
                public void buttonClick(ClickEvent event) {
                    try {
                        PrenotazioneEvent prenotazioneEvent = prenotazioneForm.validate();
                        if (prenotazioneEvent != null) {
                            DashboardEventBus.post(new PrenotazioneAction(prenotazioneEvent, modifica));
                            close();
                        }
                    } catch (InvalidValueException | CommitException e) {
                        Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
                                Type.ERROR_MESSAGE);
                    }
                }
            });
            ok.focus();
            Button delete = new Button("Elimina Prenotazione");
            delete.addStyleName(ValoTheme.BUTTON_PRIMARY);
            delete.addClickListener(new ClickListener() {
                /**
                 *
                 */
                private static final long serialVersionUID = -1522725903867568549L;

                @Override
                public void buttonClick(ClickEvent event) {

                    DashboardEventBus.post(new DeletePrenotazioneAction(bean));
                    close();
                }
            });
            footer.addComponent(ok);
            footer.setSpacing(true);
            footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
            if (modifica) {
                footer.addComponent(delete);
            }
            return footer;
        }

        public IPrenotazioneWindow self() {
            return this;
        }

    }
}