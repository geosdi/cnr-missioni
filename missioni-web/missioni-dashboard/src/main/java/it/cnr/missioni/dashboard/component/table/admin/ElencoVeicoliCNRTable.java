package it.cnr.missioni.dashboard.component.table.admin;

import com.vaadin.data.util.BeanItemContainer;
import it.cnr.missioni.dashboard.component.table.ITable;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;

/**
 * @author Salvia Vito
 */
public final class ElencoVeicoliCNRTable extends ITable.AbstractTable {

    /**
     *
     */
    private static final long serialVersionUID = 3858138091665164557L;

    public ElencoVeicoliCNRTable() {
        super();
        buildTable();
    }

    /**
     * Aggiorna la tabella con la nuova lista derivante dalla query su ES
     */
    public <T> void aggiornaTable(T veicoloCNRStore) {
        this.removeAllItems();

        if (((VeicoloCNRStore) veicoloCNRStore).getTotale() > 0) {
            BeanItemContainer<VeicoloCNR> listaVeicoliCNR =
                    new BeanItemContainer<VeicoloCNR>(VeicoloCNR.class);
            listaVeicoliCNR.addNestedContainerProperty("stato");
            listaVeicoliCNR.addNestedContainerProperty("tipo");
            listaVeicoliCNR.addNestedContainerProperty("targa");
            listaVeicoliCNR.addNestedContainerProperty("cartaCircolazione");
            listaVeicoliCNR.addNestedContainerProperty("polizzaAssicurativa");
            listaVeicoliCNR.addAll(((VeicoloCNRStore) veicoloCNRStore).getVeicoliCNR());
            setVisible(true);
            setContainerDataSource(listaVeicoliCNR);
            setVisibleColumns("tipo", "targa", "cartaCircolazione", "polizzaAssicurativa", "stato");
            setColumnHeaders("Tipo", "targa", "Carta Circolazione", "Polizza Assicurativa", "Stato");
            setId("targa");
//            Object[] properties = {"tipo", "targa"};
//            boolean[] ordering = {true, false};
//            sort(properties, ordering);
            setId("targa");
        }
    }


    /**
     *
     */
    @Override
    public void addGeneratedColumn() {
    }

}
