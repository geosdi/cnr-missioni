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
import it.cnr.missioni.dashboard.action.MissioneAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.missione.*;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IUserSearchBuilder;
import it.cnr.missioni.model.missione.DatiPeriodoMissione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

public class MissioneWindowAdmin extends IWindow.AbstractWindow<Missione, MissioneWindowAdmin> {

    /**
     *
     */

    public static final String ID = "missioneadminwindow";

    private static final long serialVersionUID = -233114930128321445L;
    private User user;
    private ITipoMissioneForm tipoMissioneForm;
    private ILocalitaOggettoMissioneForm localitaOggettoMissioneForm;
    private IFondoGAEMissioneForm fondoGAEMissioneForm;
    private IVeicoloMissioneForm veicoloMissioneForm;
    private IDatiPeriodoMissioneForm datiPeriodoMissioneForm;

    protected MissioneWindowAdmin() {
    }

    public static MissioneWindowAdmin getMissioneWindowAdmin() {
        return new MissioneWindowAdmin();
    }

    public MissioneWindowAdmin build() {
        getUser();
        setId(ID);
        buildWindow();
        buildTabs();
        if (enabled)
            content.addComponent(buildFooter());
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }

    private void buildTabs() {
        buildTabTipoMissione();
        buildTabLocalitaOggetto();
        buildTabFondoGAE();
        buildTabVeicoloMissione();
        buildTabPeriodoMissione();
    }

    private void buildTabTipoMissione() {
        // this.tipoMissioneForm = new TipoMissioneForm(missione, isAdmin,
        // enabled,modifica);
        this.tipoMissioneForm = ITipoMissioneForm.TipoMissioneForm.getTipoMissioneForm().withBean(bean)
                .withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Tipo Missione", FontAwesome.GEARS, this.tipoMissioneForm));

    }

    private void buildTabLocalitaOggetto() {
        // this.localitaOggettoMissioneForm = new
        // LocalitaOggettoMissioneForm(missione, isAdmin, enabled,modifica);
        this.localitaOggettoMissioneForm = ILocalitaOggettoMissioneForm.LocalitaOggettoMissioneForm
                .getDatiPeriodoMissioneForm().withBean(bean).withIsAdmin(isAdmin).withEnabled(enabled)
                .withModifica(modifica).build().withIsVisible(bean.isMissioneEstera());
        // this.localitaOggettoMissioneForm.setVisibleField(missione.isMissioneEstera());
        detailsWrapper
                .addComponent(buildTab("Localita\\Oggetto", FontAwesome.SUITCASE, this.localitaOggettoMissioneForm));

    }

    private void buildTabFondoGAE() {
        // this.fondoGAEMissioneForm = new IFondoGAEMissioneForm(missione,
        // isAdmin, enabled,modifica);
        this.fondoGAEMissioneForm = IFondoGAEMissioneForm.FondoGAEMissioneForm.getFondoGAEMissioneForm()
                .withBean(bean).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Fondo\\GAE", FontAwesome.SUITCASE, this.fondoGAEMissioneForm));
    }

    private void buildTabVeicoloMissione() {
        // this.veicoloMissioneForm = new VeicoloMissioneForm(missione, isAdmin,
        // enabled,modifica);
        this.veicoloMissioneForm = IVeicoloMissioneForm.VeicoloMissioneForm.getVeicoloMissioneForm().withBean(bean)
                .withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Veicolo", FontAwesome.CAR, this.veicoloMissioneForm));

    }

    private void buildTabPeriodoMissione() {
        // this.datiPeriodoMissioneForm = new
        // DatiPeriodoMissioneForm(missione.getDatiPeriodoMissione(), isAdmin,
        // enabled,modifica);
        this.datiPeriodoMissioneForm = IDatiPeriodoMissioneForm.DatiPeriodoMissioneForm.getDatiPeriodoMissioneForm()
                .withBean(bean.getDatiPeriodoMissione()).withIsAdmin(isAdmin).withEnabled(enabled)
                .withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Inizio\\Fine", FontAwesome.CALENDAR, this.datiPeriodoMissioneForm));
    }

    private void getUser() {
        try {
            IUserSearchBuilder userSearchBuilder = IUserSearchBuilder.UserSearchBuilder.getUserSearchBuilder()
                    .withId(bean.getIdUser());

            UserStore userStore = ClientConnector.getUser(userSearchBuilder);
            this.user = userStore.getUsers().get(0);
        } catch (Exception e) {
            Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                    Type.ERROR_MESSAGE);
        }
    }

    protected Component buildFooter() {
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 7886873565009642842L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    tipoMissioneForm.validate();
                    localitaOggettoMissioneForm.validate();
                    DatiPeriodoMissione datiPeriodoMissione = datiPeriodoMissioneForm.validate();
                    fondoGAEMissioneForm.validate();

                    veicoloMissioneForm.validate();
                    bean.setDatiPeriodoMissione(datiPeriodoMissione);

                    DashboardEventBus.post(new MissioneAction(bean, modifica));
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

    public MissioneWindowAdmin self() {
        return this;
    }

}
