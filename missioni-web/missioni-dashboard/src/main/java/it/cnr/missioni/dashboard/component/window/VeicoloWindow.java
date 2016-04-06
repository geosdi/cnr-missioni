package it.cnr.missioni.dashboard.component.window;

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
import it.cnr.missioni.dashboard.action.VeicoloAction;
import it.cnr.missioni.dashboard.component.form.veicolo.IVeicoloForm;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.Veicolo;

public class VeicoloWindow extends IWindow.AbstractWindow<Veicolo, VeicoloWindow> {

    public static final String ID = "veicolowindow";
    /**
     *
     */
    private static final long serialVersionUID = -333945068835078811L;
    private IVeicoloForm veicoloForm;

    protected VeicoloWindow() {
    }

    public static VeicoloWindow getVeicoloWindow() {
        return new VeicoloWindow();
    }

    public VeicoloWindow build() {
        setId(ID);
        buildWindow();
        buildTab();
        content.addComponent(buildFooter());
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }


    private void buildTab() {
//		this.veicoloForm = new VeicoloForm(veicolo, false,true,modifica);
        this.veicoloForm = IVeicoloForm.VeicoloForm.getVeicoloForm().withBean(bean).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Veicolo", FontAwesome.CAR, this.veicoloForm));
    }


    protected Component buildFooter() {
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -932339804718579357L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    Veicolo new_veicolo = veicoloForm.validate();
                    DashboardEventBus.post(new VeicoloAction(new_veicolo, modifica));
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

    public VeicoloWindow self() {
        return this;
    }

}
