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
import it.cnr.missioni.dashboard.action.AnticipoPagamentoAction;
import it.cnr.missioni.dashboard.component.form.anticipopagamenti.IDatiAnticipoPagamentoForm;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.DatiAnticipoPagamenti;
import it.cnr.missioni.model.missione.Missione;
import org.vaadin.dialogs.ConfirmDialog;

public class AnticipoPagamentiWindow extends IWindow.AbstractWindow<Missione, AnticipoPagamentiWindow> {

    public static final String ID = "anticipoPagamentiwindow";
    /**
     *
     */
    private static final long serialVersionUID = -5199469615215392155L;
    private DatiAnticipoPagamenti anticipoPagamenti;
    private IDatiAnticipoPagamentoForm datiAnticipoPagamentoForm;

    protected AnticipoPagamentiWindow() {
    }

    public static AnticipoPagamentiWindow getAnticipoPagamentiWindow() {
        return new AnticipoPagamentiWindow();
    }

    public AnticipoPagamentiWindow build() {
        this.anticipoPagamenti = bean.getDatiAnticipoPagamenti();
        setId(ID);
        buildWindow();
        buildTab();
        content.addComponent(buildFooter());
        this.setWidth("40%");
        UI.getCurrent().addWindow(this);
        this.focus();
        return self();
    }

    private void buildTab() {
        // this.datiAnticipoPagamentoForm = new
        // DatiAnticipoPagamentoForm(anticipoPagamenti,missione.getDatiMissioneEstera().getTrattamentoMissioneEsteraEnum(),
        // isAdmin, enabled, modifica);
        this.datiAnticipoPagamentoForm = IDatiAnticipoPagamentoForm.DatiAnticipoPagamentoForm
                .getDatiAnticipoPagamentoForm()
                .withTrattamentoMissioneEstera(bean.getDatiMissioneEstera().getTrattamentoMissioneEsteraEnum())
                .withBean(anticipoPagamenti).withEnabled(enabled).withIsAdmin(isAdmin).withModifica(modifica);
        detailsWrapper
                .addComponent(buildTab("Anticipo di Pagamento", FontAwesome.EURO, this.datiAnticipoPagamentoForm));

    }

    protected Component buildFooter() {

        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = -4078143920654711489L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    DatiAnticipoPagamenti datiAnticipoPagamenti = datiAnticipoPagamentoForm.validate();
                    datiAnticipoPagamenti.setInserted(true);
                    bean.setDatiAnticipoPagamenti(datiAnticipoPagamenti);
                    if (!isAdmin) {
                        ConfirmDialog.show(UI.getCurrent(), "Conferma",
                                "L'anticipo di pagamento inserito non potrà essere più modificato. Verrà inviata una mail all'amministratore con i dati dell'anticipo di pagamento inserito.",
                                "Ok", "No", new ConfirmDialog.Listener() {

                                    /**
                                     *
                                     */
                                    private static final long serialVersionUID = -7447338746066450343L;

                                    public void onClose(ConfirmDialog dialog) {
                                        if (dialog.isConfirmed()) {
                                            conclude();
                                        } else {
                                        }
                                    }
                                });
                    } else {
                        conclude();
                    }
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

    private void conclude() {
        DashboardEventBus.post(new AnticipoPagamentoAction(bean));
        DashboardEventBus.post(new CloseOpenWindowsEvent());
    }

    public AnticipoPagamentiWindow self() {
        return this;
    }

}