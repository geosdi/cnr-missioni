package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.admin.NazioneAction;
import it.cnr.missioni.dashboard.component.form.nazione.INazioneForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.Nazione;

public class NazioneWindow extends IWindow.AbstractWindow<Nazione, NazioneWindow> {

    public static final String ID = "nazionewindow";
    /**
     *
     */
    private static final long serialVersionUID = -6599225757081293003L;
    private INazioneForm nazioneForm;

    protected NazioneWindow() {
    }

    public static NazioneWindow getNazioneWindow() {
        return new NazioneWindow();
    }

    public NazioneWindow build() {
        setId(ID);
        buildWindow();
        detailsWrapper.addComponent(buildTab());
        content.addComponent(buildFooter());
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }

    protected HorizontalLayout buildTab() {
//        this.nazioneForm = new NazioneForm(nazione,true,true,modifica);
        this.nazioneForm = INazioneForm.NazioneForm.getNazioneForm().withBean(bean).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        HorizontalLayout tab = super.buildTab("Nazione", FontAwesome.GLOBE, this.nazioneForm);
        return tab;
    }

    protected Component buildFooter() {
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 6503857843364882408L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    bean = nazioneForm.validate();
                    DashboardEventBus.post(new NazioneAction(bean, modifica));
                    close();
                } catch (InvalidValueException | CommitException e) {
                    Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
                            Type.ERROR_MESSAGE);
                }
            }
        });
        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }

    public NazioneWindow self() {
        return this;
    }

}
