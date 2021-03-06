package it.cnr.missioni.dashboard.component.table;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.data.util.ItemSorter;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import it.cnr.missioni.dashboard.utility.CaseInsensitivePropertyComparator;
import it.cnr.missioni.model.user.Veicolo;

import java.util.List;

/**
 * @author Salvia Vito
 */
public final class ElencoVeicoliTable extends ITable.AbstractTable {

    /**
     *
     */
    private static final long serialVersionUID = 3858138091665164557L;

    public ElencoVeicoliTable() {
        super();
        buildTable();
    }

    /**
     * Aggiorna la tabella con la nuova lista derivante dalla query su ES
     */
    public <T> void aggiornaTable(T lista) {
        this.removeAllItems();
        if (!((List<Veicolo>) lista).isEmpty()) {
            setVisible(true);
            setContainerDataSource(new BeanItemContainer<Veicolo>(Veicolo.class, ((List<Veicolo>) lista)));
            setVisibleColumns("tipo", "targa", "cartaCircolazione", "polizzaAssicurativa", "veicoloPrincipale");
            setColumnHeaders("Tipo", "targa", "Carta Circolazione", "Polizza Assicurativa", "Veicolo Principale");
            setId("targa");
			ItemSorter itemSort = new DefaultItemSorter(new CaseInsensitivePropertyComparator());
			BeanItemContainer ic = (BeanItemContainer) this.getContainerDataSource();
			ic.setItemSorter(itemSort);
            Object[] properties = {"tipo", "targa"};
            boolean[] ordering = {false, false};
            sort(properties, ordering);
            setId("targa");
        }
    }


    /**
     *
     */
    @Override
    public void addGeneratedColumn() {
        addGeneratedColumn("veicoloPrincipale", new ColumnGenerator() {
            /**
             *
             */
            private static final long serialVersionUID = -4545280611752519667L;

            @Override
            public Object generateCell(final Table source, final Object itemId, Object columnId) {
                Veicolo v = (Veicolo) itemId;
                if (v.isVeicoloPrincipale()) {
                    Label l = new Label();
                    l.setContentMode(ContentMode.HTML);
                    l.setValue(FontAwesome.CHECK.getHtml());
                    return l;
                }
                return null;
            }
        });
    }
}
