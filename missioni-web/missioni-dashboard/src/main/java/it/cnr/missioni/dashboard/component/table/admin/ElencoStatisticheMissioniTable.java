package it.cnr.missioni.dashboard.component.table.admin;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

import it.cnr.missioni.dashboard.component.table.ITable;
import it.cnr.missioni.el.model.bean.StatisticheMissioni;
import it.cnr.missioni.model.missione.StatoEnum;

/**
 * @author Salvia Vito
 */
public final class ElencoStatisticheMissioniTable extends ITable.AbstractTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -88889187871994144L;

	public ElencoStatisticheMissioniTable() {
		super();
		buildTable();
	}

	/**
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 */

	public <T> void aggiornaTable(T statisticheMissioni) {
		this.removeAllItems();

		StatisticheMissioni s = (StatisticheMissioni)statisticheMissioni;
		
		
		if (!s.getMappaStatistiche().isEmpty()) {

			setVisible(true);
			
			IndexedContainer cont = new IndexedContainer();
			cont.addContainerProperty("Stato", String.class, null);
			cont.addContainerProperty("Totale", Long.class, null);
			
			s.getMappaStatistiche().keySet().forEach(c -> {
				Item newItem = cont.getItem(cont.addItem());
			    newItem.getItemProperty("Stato").setValue(c);
			    newItem.getItemProperty("Totale").setValue(s.getMappaStatistiche().get(c));
			});

			
	

			

			setContainerDataSource(cont);
			

		}

	}



}
