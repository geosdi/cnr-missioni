package it.cnr.missioni.dashboard.component.window;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.UpdateUserAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.form.user.IAnagraficaUserForm;
import it.cnr.missioni.dashboard.component.form.user.IDatiCNRUserForm;
import it.cnr.missioni.dashboard.component.form.user.IPatenteUserForm;
import it.cnr.missioni.dashboard.component.form.user.IResidenzaUserForm;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.IMassimaleSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione.AreaGeograficaEnum;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.user.*;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

public class UserWindow extends IWindow.AbstractWindow<User, UserWindow> {

    public static final String ID = "profilepreferenceswindow";
    /**
     *
     */
    private static final long serialVersionUID = 9064172202629030178L;
    //	private User user;
    private IAnagraficaUserForm anagraficaForm;
    private IDatiCNRUserForm datiCNRTabLayout;
    private IPatenteUserForm patenteTablLayout;
    private IResidenzaUserForm residenzaTabLayout;

    protected UserWindow() {
    }

    public static UserWindow getUserWindow() {
        return new UserWindow();
    }

    public UserWindow build() {
        setId(ID);
        buildWindow();
        buildTabs();
        if (!isAdmin)
            content.addComponent(buildFooter());
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }


    private void buildTabs() {
        buildTabAnagrafica();
        buildTabPatente();
        buildTabResidenza();
        buildTabDatiCNR();
        detailsWrapper.addComponent(buildMassimaleTab());
    }

    private void buildTabAnagrafica() {
//		this.anagraficaForm = new AnagraficaUserForm(user, false, true, true);
        this.anagraficaForm = IAnagraficaUserForm.AnagraficaUserForm.getAnagraficaUserForm().withBean(bean.getAnagrafica()).withUser(bean).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Anagrafica", FontAwesome.USER, this.anagraficaForm));
    }

    private void buildTabPatente() {
//		this.patenteTablLayout = new PatenteUserForm(user, false, true, true);
        this.patenteTablLayout = IPatenteUserForm.PatenteUserForm.getPatenteUserForm().withBean(bean.getPatente()).withUser(bean).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Patente", FontAwesome.CAR, this.patenteTablLayout));
    }

    private void buildTabResidenza() {
//		this.residenzaTabLayout = new ResidenzaUserForm(user, false, true, true);
        this.residenzaTabLayout = IResidenzaUserForm.ResidenzaUserForm.getResidenzaUserForm().withBean(bean.getResidenza()).withIsAdmin(isAdmin).withEnabled(enabled).withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Residenza", FontAwesome.HOME, this.residenzaTabLayout));
    }

    private void buildTabDatiCNR() {
//		this.datiCNRTabLayout = new DatiCNRUserForm(user, false, true, true);
        this.datiCNRTabLayout = IDatiCNRUserForm.DatiCNRUserForm.getDatiCNRUserForm().withBean(bean.getDatiCNR()).withIsAdmin(isAdmin).withEnabled(enabled).withUser(bean).withModifica(modifica).build();
        detailsWrapper.addComponent(buildTab("Dati CNR", FontAwesome.INSTITUTION, this.datiCNRTabLayout));
    }

    private Component buildMassimaleTab() {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Info Massimali");
        root.setIcon(FontAwesome.EURO);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);
        for (AreaGeograficaEnum a : AreaGeograficaEnum.values()) {
            try {
                double massimaleTAM = 0.0;
                double massimaleRimborsoDocumentato = 0.0;

                MassimaleStore massimaleStore = ClientConnector
                        .getMassimale(IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder()
                                .withLivello(bean.getDatiCNR().getLivello().name()).withAreaGeografica(a.name())
                                .withTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name()));
                if (massimaleStore.getTotale() > 0)
                    massimaleTAM = massimaleStore.getMassimale().get(0).getValue();

                massimaleStore = ClientConnector.getMassimale(IMassimaleSearchBuilder.MassimaleSearchBuilder.getMassimaleSearchBuilder()
                        .withLivello(bean.getDatiCNR().getLivello().name()).withAreaGeografica(a.name())
                        .withTipo(TrattamentoMissioneEsteraEnum.RIMBORSO_DOCUMENTATO.name()));
                if (massimaleStore.getTotale() > 0)
                    massimaleRimborsoDocumentato = massimaleStore.getMassimale().get(0).getValue();
                details.addComponent(new Label("<b>Area geografica: </b>" + a.name() + " <b>TAM:</b> "
                        + Double.toString(massimaleTAM) + " € " + "<b>Rimborso documentato:</b> "
                        + Double.toString(massimaleRimborsoDocumentato) + " €", ContentMode.HTML));
            } catch (Exception e) {
                Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
                        Type.ERROR_MESSAGE);
            }
        }

        return root;
    }

    protected Component buildFooter() {
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 5281413805855055939L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    Anagrafica anagrafica = anagraficaForm.validate();
                    Patente patente = patenteTablLayout.validate();
                    DatiCNR datiCNR = datiCNRTabLayout.validate();
                    Residenza residenza = residenzaTabLayout.validate();
                    bean.setAnagrafica(anagrafica);
                    bean.setPatente(patente);
                    bean.setDatiCNR(datiCNR);
                    bean.setResidenza(residenza);
                    DashboardEventBus.post(new UpdateUserAction(bean));
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

    public UserWindow self() {
        return this;
    }

}
