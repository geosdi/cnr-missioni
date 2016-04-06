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
import it.cnr.missioni.dashboard.action.admin.TipologiaSpesaAction;
import it.cnr.missioni.dashboard.component.form.tipologiaspesa.ITipologiaSpesaForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.TipologiaSpesa;

public class TipologiaSpesaWindow extends IWindow.AbstractWindow<TipologiaSpesa, TipologiaSpesaWindow> {

    public static final String ID = "tipologiaspesawindow";
    /**
     *
     */
    private static final long serialVersionUID = -6599225757081293003L;
    private ITipologiaSpesaForm tipologiaSpesaForm;

    protected TipologiaSpesaWindow() {
    }

    public static TipologiaSpesaWindow getTipologiaSpesaWindow() {
        return new TipologiaSpesaWindow();
    }

    public TipologiaSpesaWindow build() {
        setId(ID);
        buildWindow();
        detailsWrapper.addComponent(buildTab());
        content.addComponent(buildFooter());
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }

    protected HorizontalLayout buildTab() {
//        this.tipologiaSpesaForm = new TipologiaSpesaForm(tipologiaSpesa,true,true,modifica);
        this.tipologiaSpesaForm = ITipologiaSpesaForm.TipologiaSpesaForm.getTipologiaSpesaForm().withBean(bean).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        HorizontalLayout tab = super.buildTab("RimbTipologia Spesorso", FontAwesome.EURO, this.tipologiaSpesaForm);
        return tab;
    }

    protected Component buildFooter() {

        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 4000358346700874351L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    TipologiaSpesa tipologiaSpesa = tipologiaSpesaForm.validate();
                    DashboardEventBus.post(new TipologiaSpesaAction(tipologiaSpesa, modifica));
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

    public TipologiaSpesaWindow self() {
        return this;
    }

}
