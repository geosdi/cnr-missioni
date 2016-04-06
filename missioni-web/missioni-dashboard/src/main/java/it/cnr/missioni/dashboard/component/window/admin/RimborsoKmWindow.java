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
import it.cnr.missioni.dashboard.action.admin.RimborsoKmAction;
import it.cnr.missioni.dashboard.component.form.rimborsokm.IRimborsoKmForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.RimborsoKm;

public class RimborsoKmWindow extends IWindow.AbstractWindow<RimborsoKm, RimborsoKmWindow> {

    public static final String ID = "rimborsoKmwindow";
    /**
     *
     */
    private static final long serialVersionUID = 5484936598745173136L;
    private IRimborsoKmForm rimborsoKmForm;

    protected RimborsoKmWindow() {
    }

    public static RimborsoKmWindow getRimborsoKmWindow() {
        return new RimborsoKmWindow();
    }

    public RimborsoKmWindow build() {
        setId(ID);
        buildWindow();
        detailsWrapper.addComponent(buildTab());
        content.addComponent(buildFooter());
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }

    protected HorizontalLayout buildTab() {
        // this.rimborsoKmForm = new IRimborsoKmForm(rimborsoKm, true, true,
        // modifica);
        this.rimborsoKmForm = IRimborsoKmForm.RimborsoKmForm.getRimborsoKmForm().withBean(bean).withIsAdmin(isAdmin)
                .withEnabled(enabled).withModifica(modifica).build();
        HorizontalLayout tab = super.buildTab("Rimborso", FontAwesome.EURO, this.rimborsoKmForm);
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

    public RimborsoKmWindow self() {
        return this;
    }

}
