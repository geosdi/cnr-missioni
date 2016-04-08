package it.cnr.missioni.dashboard.component.table;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification.Type;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;
import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Salvia Vito
 */
public final class ElencoFattureTable extends ITable.AbstractTable {

    /**
     *
     */
    private static final long serialVersionUID = -6369755826586396817L;
    private BeanFieldGroup<Fattura> beanFieldGroup;
    private DateField dateField;
    private Missione missione;

    public ElencoFattureTable(BeanFieldGroup<Fattura> beanFieldGroup, DateField dateField, Missione missione) {
        super();
        buildTable();
        this.beanFieldGroup = beanFieldGroup;
        this.dateField = dateField;
        this.missione = missione;
        addActionHandler(new TransactionsActionHandler());
    }

    public ElencoFattureTable(Missione missione) {
        super();
        buildTable();
        this.missione = missione;
    }

    @Override
    public void buildTable() {
        setSortEnabled(false);
        setColumnAlignment("revenue", Align.RIGHT);
        setRowHeaderMode(RowHeaderMode.HIDDEN);
        setWidth("100%");
        setPageLength(0);
        setSelectable(true);
        setSortEnabled(true);
        setNullSelectionAllowed(false);
        setVisible(false);
        setFooterVisible(false);
    }

    /**
     * Aggiorna la tabella con la nuova lista derivante dalla query su ES
     *
     * @param lista
     */
    public <T> void aggiornaTable(T lista) {
        this.removeAllItems();
        if (lista != null) {
            setContainerDataSource(
                    new BeanItemContainer<Fattura>(Fattura.class, ((List<Fattura>) lista)));
            setVisibleColumns("numeroFattura", "data", "shortDescriptionTipologiaSpesa", "valuta", "altro", "importo");
            setColumnHeaders("N.Fattura", "Data", "Tipologia Spesa", "Valuta", "Altro", "Importo €");
            setId("numeroFattura");
            Object[] properties = {"data", "numeroFattura"};
            boolean[] ordering = {false, false};
            sort(properties, ordering);
            setVisible(true);
            setFooterVisible(true);
        }
    }

    /**
     * Aggiorna la tabella con la nuova lista derivante dalla query su ES
     *
     * @param lista
     */
    public <T> void aggiornaTableRiepilogo(T lista) {
        this.removeAllItems();
        if (lista != null) {
            setContainerDataSource(
                    new BeanItemContainer<Fattura>(Fattura.class, ((List<Fattura>) lista)));
            setVisibleColumns("numeroFattura", "data", "shortDescriptionTipologiaSpesa", "valuta", "altro", "importo", "importoSpettante");
            setColumnHeaders("N.Fattura", "Data", "Tipologia Spesa", "Valuta", "Altro", "Importo €", "Importo Spettante €");
            setId("numeroFattura");
            Object[] properties = {"data", "numeroFattura"};
            boolean[] ordering = {false, false};
            sort(properties, ordering);
            setVisible(true);
            setFooterVisible(true);
        }
    }

    public void aggiornaTotale(double totale) {
        DecimalFormat df = new DecimalFormat("#0.00");
        setColumnFooter("importo", "Tot. €: " + df.format(totale));
    }

    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        Object v = property.getValue();
        if (v instanceof DateTime) {
            DateTime dateValue = (DateTime) v;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return format.format(dateValue.toDate());
        }
        return super.formatPropertyValue(rowId, colId, property);
    }

    /**
     *
     */
    @Override
    public void addGeneratedColumn() {
    }

    /**
     * Crea un context menù quando si clicca col tasto destro su una riga della
     * tabella
     *
     * @author Salvia Vito
     */
    private class TransactionsActionHandler implements Handler {
        /**
         *
         */
        private static final long serialVersionUID = -6206311197910127065L;
        private final Action seleziona = new Action("Seleziona");
        private final Action elimina = new Action("Elimina");

        @Override
        public void handleAction(final Action action, final Object sender, final Object target) {
            if (action == seleziona) {
                beanFieldGroup.setItemDataSource((Fattura) target);
                dateField.setValue(((Fattura) target).getData().toDate());
                if (missione.isMissioneEstera())
                    DashboardEventBus.post(new DashboardEvent.ComboBoxListaFatturaUpdatedEvent(((Fattura) target)));

            }
            if (action == elimina) {
                missione.getRimborso().getMappaFattura().remove(((Fattura) target).getId());
                aggiornaTable(new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
                aggiornaTotale(missione.getRimborso().getTotale());
                Utility.getNotification(Utility.getMessage("success_message"), null, Type.HUMANIZED_MESSAGE);
            }
        }

        @Override
        public Action[] getActions(final Object target, final Object sender) {
            return new Action[]{seleziona, elimina};
        }
    }
}
