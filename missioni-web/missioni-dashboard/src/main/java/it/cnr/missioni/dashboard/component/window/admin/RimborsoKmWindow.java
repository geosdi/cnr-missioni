package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.admin.RimborsoKmAction;
import it.cnr.missioni.dashboard.component.form.rimborsokm.RimborsoKmForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.RimborsoKm;

public class RimborsoKmWindow extends IWindow.AbstractWindow {


    /**
     *
     */
    private static final long serialVersionUID = 5484936598745173136L;

    public static final String ID = "rimborsoKmwindow";
    private RimborsoKmForm rimborsoKmForm;


    private final RimborsoKm rimborsoKm;

    private RimborsoKmWindow(final RimborsoKm rimborsoKm, boolean isAdmin, boolean enabled, boolean modifica) {
        super(isAdmin, enabled, modifica);
        this.rimborsoKm = rimborsoKm;
        setId(ID);
        build();
        detailsWrapper.addComponent(buildTab());
        content.addComponent(buildFooter());
    }

    protected HorizontalLayout buildTab(){
        this.rimborsoKmForm = new RimborsoKmForm(rimborsoKm, true, true, modifica);
        HorizontalLayout tab = super.buildTab("Rimborso",FontAwesome.EURO,this.rimborsoKmForm);
        return tab;
    }

    protected Component buildFooter() {

        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 4140141100852159590L;

            @Override
            public void buttonClick(ClickEvent event) {

                try {
                    RimborsoKm rimborsoKm = rimborsoKmForm.validate();
                    DashboardEventBus.post(new RimborsoKmAction(rimborsoKm, modifica));
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

    public static void open(final RimborsoKm rimborsoKm, final boolean isAdmin, final boolean enabled, final boolean modifica) {
        DashboardEventBus.post(new CloseOpenWindowsEvent());
        Window w = new RimborsoKmWindow(rimborsoKm, isAdmin, enabled, modifica);
        UI.getCurrent().addWindow(w);
        w.focus();
    }

}
