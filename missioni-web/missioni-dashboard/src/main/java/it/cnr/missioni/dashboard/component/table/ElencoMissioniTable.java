package it.cnr.missioni.dashboard.component.table;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;

/**
 * @author Salvia Vito
 */

public final class ElencoMissioniTable extends ITable.AbstractTable {

    /**
     *
     */
    private static final long serialVersionUID = 6439677451802448635L;

    public ElencoMissioniTable() {
        super();
        buildTable();
    }

    /**
     * Aggiorna la tabella con la nuova lista derivante dalla query su ES
     *
     * @param missioniStore
     */
    public <T> void aggiornaTable(T missioniStore) {
        this.removeAllItems();
        if (((MissioniStore) missioniStore).getTotale() > 0) {
            BeanItemContainer<Missione> listaMissioni =
                    new BeanItemContainer<Missione>(Missione.class);
            listaMissioni.addNestedContainerProperty("rimborso.sigla");
            setVisible(true);
            listaMissioni.addAll(((MissioniStore) missioniStore).getMissioni());
            setContainerDataSource(listaMissioni);
            setVisibleColumns("progressivo","rimborso.sigla","localita", "oggetto", "stato", "dataInserimento");
            setColumnHeaders("Id","Sigla","Località", "Oggetto", "Stato", "Data Inserimento");
            setId("id");
            setColumnWidth("dataInserimento", 160);
            setColumnWidth("oggetto", 160);
            setColumnExpandRatio("oggetto", 2);
            setColumnExpandRatio("localita", 1);
        }
    }

    /**
     * Aggiorna la tabella con la nuova lista derivante dalla query su ES
     *
     * @param missioniStore
     */
    public <T> void aggiornaTableAdmin(T missioniStore) {
        this.removeAllItems();
        if (((MissioniStore) missioniStore).getTotale() > 0) {
        	BeanItemContainer<Missione> listaMissioni =
                    new BeanItemContainer<Missione>(Missione.class);
            listaMissioni.addNestedContainerProperty("rimborso.sigla");
            setVisible(true);
            setContainerDataSource(listaMissioni);
            listaMissioni.addAll(((MissioniStore) missioniStore).getMissioni());
            //setContainerDataSource(new BeanItemContainer<Missione>(Missione.class, ((MissioniStore) missioniStore).getMissioni()));
            setVisibleColumns("progressivo","rimborso.sigla","shortUser", "localita", "oggetto", "stato", "dataInserimento");
            setColumnHeaders("Id","Sigla","User", "Localita", "Oggetto", "Stato", "Data Inserimento");
            setId("id");
            Object[] properties = {"dataInserimento"};
            boolean[] ordering = {false};
            sort(properties, ordering);
            setColumnWidth("dataInserimento", 160);
            setColumnWidth("oggetto", 160);
            setColumnExpandRatio("oggetto", 2);
            setColumnExpandRatio("localita", 2);
//			setColumnWidth("id", 30);
        }
    }


    /**
     * @param rowId
     * @param colId
     * @param property
     * @return
     */
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
        addGeneratedColumn("stato", new ColumnGenerator() {

            /**
             *
             */
            private static final long serialVersionUID = -1135658612453220960L;

            @Override
            public Object generateCell(final Table source, final Object itemId, Object columnId) {

                Missione missione = (Missione) itemId;
                Resource res = null;
                if (missione.getRimborso() != null && missione.getRimborso().isPagata()) {
                    res = new ThemeResource("img/circle_green_16_ns.png"); // get the resource depending the integer value
                } 
                else if(missione.getStato() == StatoEnum.ANNULLATA)
                	res = new ThemeResource("img/serve-red-circle-icone-8206-16.png"); 
                else 
                    res = new ThemeResource("img/orange-circle-icone-6032-16.png"); // get the resource depending the integer value
                
                Image i = new Image(missione.getStato().getStato(), res);
                i.setDescription(missione.getStato().getStato());
                return i;
            }
        });
    }
}
