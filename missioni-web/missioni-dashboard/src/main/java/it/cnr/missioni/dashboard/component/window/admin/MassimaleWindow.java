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
import it.cnr.missioni.dashboard.action.admin.MassimaleAction;
import it.cnr.missioni.dashboard.component.form.massimale.IMassimaleForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.Massimale;

public class MassimaleWindow extends IWindow.AbstractWindow<Massimale, MassimaleWindow> {

    public static final String ID = "massimalewindow";
    /**
     *
     */
    private static final long serialVersionUID = 5414281952989114531L;
    private IMassimaleForm massimaleForm;

    protected MassimaleWindow() {
    }

    public static MassimaleWindow getMassimaleWindow() {
        return new MassimaleWindow();
    }

    public MassimaleWindow build() {
        setId(ID);
        buildWindow();
        detailsWrapper.addComponent(buildTab());
        content.addComponent(buildFooter());
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }

    protected HorizontalLayout buildTab() {
//		this.massimaleForm = new MassimaleForm(this.massimale,true,true,modifica);
        this.massimaleForm = IMassimaleForm.MassimaleForm.getMassimaleForm().withBean(this.bean).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        HorizontalLayout tab = super.buildTab("Massimale", FontAwesome.EURO, this.massimaleForm);
        return tab;
    }

    protected Component buildFooter() {
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -8838118439172249530L;

            @Override
            public void buttonClick(ClickEvent event) {

                try {
                    Massimale massimale = massimaleForm.validate();

                    DashboardEventBus.post(new MassimaleAction(massimale, modifica));
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

    public MassimaleWindow self() {
        return this;
    }

}
