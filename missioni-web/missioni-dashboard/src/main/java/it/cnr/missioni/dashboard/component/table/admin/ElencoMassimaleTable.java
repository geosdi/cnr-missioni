package it.cnr.missioni.dashboard.component.table.admin;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.data.util.ItemSorter;

import it.cnr.missioni.dashboard.component.table.ITable;
import it.cnr.missioni.dashboard.utility.CaseInsensitivePropertyComparator;
import it.cnr.missioni.dashboard.utility.CaseInsensitivePropertyEnumComparator;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

/**
 * @author Salvia Vito
 */
public final class ElencoMassimaleTable extends ITable.AbstractTable {

    /**
     *
     */
    private static final long serialVersionUID = -623292542160514570L;

    public ElencoMassimaleTable() {
        super();
        buildTable();
    }

    /**
     * Aggiorna la tabella con la nuova lista derivante dalla query su ES
     */
    public <T> void aggiornaTable(T massimaleStore) {
        this.removeAllItems();
        if (((MassimaleStore) massimaleStore).getTotale() > 0) {
            setVisible(true);
            setContainerDataSource(
                    new BeanItemContainer<Massimale>(Massimale.class, ((MassimaleStore) massimaleStore).getMassimale()));

            setVisibleColumns("value", "valueMezzaGiornata", "descrizione", "livello", "areaGeografica", "tipo");
            setColumnHeaders("Importo", "Importo Mezza Giornata", "Descrizione", "Livello", "Area Geografica", "tipo");
			ItemSorter itemSort = new DefaultItemSorter(new CaseInsensitivePropertyEnumComparator());
			BeanItemContainer ic = (BeanItemContainer) this.getContainerDataSource();
			ic.setItemSorter(itemSort);
            Object[] properties = {"areaGeografica"};
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
