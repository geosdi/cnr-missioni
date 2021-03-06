package it.cnr.missioni.dashboard.component.table.admin;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.data.util.ItemSorter;

import it.cnr.missioni.dashboard.component.table.ITable;
import it.cnr.missioni.dashboard.utility.CaseInsensitivePropertyComparator;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;

/**
 * @author Salvia Vito
 */
public final class ElencoNazioneTable extends ITable.AbstractTable {

    private static final long serialVersionUID = -617386765211975221L;

    public ElencoNazioneTable() {
        super();
        buildTable();
    }

    /**
     * Aggiorna la tabella con la nuova lista derivante dalla query su ES
     */
    public <T> void aggiornaTable(T nazioneStore) {
        this.removeAllItems();
        if (((NazioneStore) nazioneStore).getTotale() > 0) {

            setVisible(true);
            setContainerDataSource(
                    new BeanItemContainer<Nazione>(Nazione.class, ((NazioneStore) nazioneStore).getNazione()));
            setVisibleColumns("value", "areaGeografica");
            setColumnHeaders("Nazione", "Area Geografica");
			ItemSorter itemSort = new DefaultItemSorter(new CaseInsensitivePropertyComparator());
			BeanItemContainer ic = (BeanItemContainer) this.getContainerDataSource();
			ic.setItemSorter(itemSort);
            Object[] properties = {"value"};
            boolean[] ordering = {true};
            sort(properties, ordering);
            setId("id");
        }
    }

    /**
     *
     */
    @Override
    public void addGeneratedColumn() {
    }

}
