package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.admin.VeicoloCNRAction;
import it.cnr.missioni.dashboard.component.form.veicolocnr.IVeicoloCNRForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;

public class VeicoloCNRWindow extends IWindow.AbstractWindow<VeicoloCNR, VeicoloCNRWindow> {

    public static final String ID = "veicoloCNRwindow";
    /**
     *
     */
    private static final long serialVersionUID = -2904991157664896304L;
    private IVeicoloCNRForm veicoloCNRForm;

    protected VeicoloCNRWindow() {
    }

    public static VeicoloCNRWindow getVeicoloCNRWindow() {
        return new VeicoloCNRWindow();
    }

    public VeicoloCNRWindow build() {
        setId(ID);
        buildWindow();
        buildTab();
        content.addComponent(buildFooter());
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }

    private void buildTab() {
//		this.veicoloCNRForm = new VeicoloCNRForm(veicoloCNR, true,true,modifica);
        this.veicoloCNRForm = IVeicoloCNRForm.VeicoloCNRForm.getVeicoloCNRForm().withBean(bean).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Veicolo CNR", FontAwesome.CAR, this.veicoloCNRForm));
    }

    protected Component buildFooter() {
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -1658601658889451200L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    VeicoloCNR new_veicoloCNR = veicoloCNRForm.validate();
                    DashboardEventBus.post(new VeicoloCNRAction(new_veicoloCNR, modifica));
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

    public VeicoloCNRWindow self() {
        return this;
    }

}
