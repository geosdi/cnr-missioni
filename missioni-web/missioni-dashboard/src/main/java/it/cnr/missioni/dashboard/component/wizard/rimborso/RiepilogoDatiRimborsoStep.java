package it.cnr.missioni.dashboard.component.wizard.rimborso;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.RimborsoAction;
import it.cnr.missioni.dashboard.action.admin.UpdateRimborsoAction;
import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.teemu.wizards.WizardStep;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Salvia Vito
 */
public class RiepilogoDatiRimborsoStep implements WizardStep {

    private HorizontalLayout mainLayout;
    ;

    private Missione missione;
    private boolean modifica;
    private boolean isAdmin;
    private boolean firstStep;

    public RiepilogoDatiRimborsoStep(Missione missione, boolean modifica, boolean isAdmin, boolean firstStep) {
        this.missione = missione;
        this.modifica = modifica;
        this.isAdmin = isAdmin;
        this.firstStep = firstStep;
    }

    public String getCaption() {
        return "Step 3";
    }

    public Component getContent() {
        return buildPanel();
    }

    private Component buildPanel() {
        VerticalLayout root = new VerticalLayout();
        root.setIcon(FontAwesome.MONEY);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        CssLayout details = new CssLayout();
        details.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
        root.addComponent(details);
        // root.setExpandRatio(details, 1);
        if (isAdmin) {
            details.addComponent(Utility.buildLabel("Pagata: ", missione.getRimborso().isPagata() ? "Si" : "No"));
            details.addComponent(Utility.buildLabel("Mandato di pagamento: ", missione.getRimborso().getMandatoPagamento()));
        }
        details.addComponent(
                Utility.buildLabel("Importo da Terzi: ", Double.toString(missione.getRimborso().getImportoDaTerzi()) + " €"));
        details.addComponent(Utility.buildLabel("Avviso Pagamento: ", (missione.getRimborso().getAvvisoPagamento() != null
                ? missione.getRimborso().getAvvisoPagamento() : "")));
        details.addComponent(
                Utility.buildLabel("Anticipazione Pagamento: ", missione.getRimborso().getAnticipazionePagamento() != null
                        ? Double.toString(missione.getRimborso().getAnticipazionePagamento()) + " €" : "0.0 €"));
        if (missione.isMezzoProprio())
            details.addComponent(
                    Utility.buildLabel("Rimborso KM: ", Double.toString(missione.getRimborso().getRimborsoKm()) + " €"));
        if (missione.isMissioneEstera())
            details.addComponent(
                    Utility.buildLabel("Tot. lordo TAM: ", Double.toString(missione.getRimborso().getTotaleTAM()) + " €"));
        details.addComponent(
                Utility.buildLabel("Totale Fatture: ", Double.toString(missione.getRimborso().getTotale()) + " €"));
        details.addComponent(
                Utility.buildLabel("Totale Fatture Spettante: ", Double.toString(missione.getRimborso().getTotaleSpettante()) + " €"));

        if (missione.isMissioneEstera()) {
            details.addComponent(Utility.buildLabel("GG all'estero: ", Integer.toString(Utility.getDaysEstero(missione))));
            details.addComponent(Utility.buildLabel("Tot. lordo TAM: ", missione.getRimborso().getTotaleTAM().toString()));
            details.addComponent(Utility.buildLabel("Attraversamento Frontiera Andata: ",
                    new SimpleDateFormat("dd/MM/yyyy HH:mm").format(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata().toDate())));
            details.addComponent(Utility.buildLabel("Attraversamento Frontiera Ritorno: ",
                    new SimpleDateFormat("dd/MM/yyyy HH:mm").format(missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno().toDate())));
        }
        ElencoFattureTable elencoFattureTable = new ElencoFattureTable(missione);
        elencoFattureTable.setStyleName("margin-table_fatture");
        elencoFattureTable
                .aggiornaTableRiepilogo(new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
        root.addComponent(elencoFattureTable);
        return root;
    }

    public boolean onAdvance() {
        if (!firstStep) {

            String conferma = isAdmin ? "Verrà inviata una mail all'utente con i dati dell rimborso inserita." : "Il rimborso inserito non potrà essere più modificato. Verrà inviata una mail all'amministratore con i dati del rimborso inserita.";
            ConfirmDialog.show(UI.getCurrent(), "Conferma", conferma, "Ok", "No", new ConfirmDialog.Listener() {

                /**
                 *
                 */
                private static final long serialVersionUID = -4768987961330740317L;

                public void onClose(ConfirmDialog dialog) {
                    if (dialog.isConfirmed()) {
                        confirmed();
                    } else {
                    }
                }
            });
        }
        return true;
    }

    private void confirmed() {
        if (!modifica)
            DashboardEventBus.post(new RimborsoAction(missione));
        else
            DashboardEventBus.post(new UpdateRimborsoAction(missione));
        DashboardEventBus.post(new CloseOpenWindowsEvent());
    }

    public boolean onBack() {
        return true;
    }

    public HorizontalLayout getMainLayout() {
        return mainLayout;
    }

}
