package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.admin.UpdateRimborsoAction;
import it.cnr.missioni.dashboard.component.form.rimborso.LayoutFatturaRimborso;
import it.cnr.missioni.dashboard.component.form.rimborso.LayoutFatturaRimborso.TableTypeEnum;
import it.cnr.missioni.dashboard.component.form.rimborso.IDatiGeneraliRimborsoForm;
import it.cnr.missioni.dashboard.component.form.rimborso.IDatiPeriodoEsteraMissioneForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;

public class RimborsoWindowAdmin extends IWindow.AbstractWindow<Missione, RimborsoWindowAdmin> {

    public static final String ID = "rimborsoadminwindow";
    /**
     *
     */
    private static final long serialVersionUID = -8551406658251796691L;

    private IDatiGeneraliRimborsoForm datiGeneraliForm;
    private LayoutFatturaRimborso fatturaForm;
    private IDatiPeriodoEsteraMissioneForm datiPeriodoEsteraMissioneForm;

    protected RimborsoWindowAdmin() {
    }

    public static RimborsoWindowAdmin getRimborsoWindowAdmin() {
        return new RimborsoWindowAdmin();
    }

    public RimborsoWindowAdmin build() {
        setId(ID);
        Responsive.makeResponsive(this);
        buildWindow();
        buildTabs();
        if (enabled)
            content.addComponent(buildFooter());
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }

    private void buildTabs() {
        buildRimborsoTab();
        if (bean.isMissioneEstera())
            buildDatiEsteriTab();
        buildFatturaTab();
    }


    private void buildRimborsoTab() {
//        this.datiGeneraliForm = new DatiGeneraliRimborsoForm(missione, isAdmin, enabled, modifica);
        this.datiGeneraliForm = IDatiGeneraliRimborsoForm.DatiGeneraliRimborsoForm.getDatiGeneraliRimborsoForm().withMissione(bean).withBean(bean.getRimborso()).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Generale", FontAwesome.GEAR, this.datiGeneraliForm));
    }


    private void buildFatturaTab() {
//        this.fatturaForm = new LayoutFatturaRimborso(bean, isAdmin, enabled, modifica);
        this.fatturaForm = LayoutFatturaRimborso.getFatturaRimborsoForm().withMissione(bean).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build().withTableType(TableTypeEnum.FULL);
        detailsWrapper.addComponent(buildTab("Fattura", FontAwesome.EURO, this.fatturaForm));
    }

    private void buildDatiEsteriTab() {
//        this.datiPeriodoEsteraMissioneForm = new DatiPeriodoEsteraMissioneForm(missione.getDatiMissioneEstera(), isAdmin, enabled, modifica, missione);
        this.datiPeriodoEsteraMissioneForm = IDatiPeriodoEsteraMissioneForm.DatiPeriodoEsteraMissioneForm.getDatiPeriodoEsteraMissioneForm().withBean(bean.getDatiMissioneEstera()).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Dati Missione Estera", FontAwesome.CALENDAR, this.datiPeriodoEsteraMissioneForm));
    }

    protected Component buildFooter() {

        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 127413665855903412L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    Rimborso r = datiGeneraliForm.validate();
                    bean.setRimborso(r);
                    DashboardEventBus.post(new UpdateRimborsoAction(bean));
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

    public RimborsoWindowAdmin self() {
        return this;
    }

}
