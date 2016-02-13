package it.cnr.missioni.dashboard.component.table.admin;

import com.vaadin.data.util.BeanItemContainer;

import it.cnr.missioni.dashboard.component.table.ITable;
import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;

/**
 * @author Salvia Vito
 */
public final class ElencoRimborsoKmTable extends ITable.AbstractTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -88889187871994144L;

	public ElencoRimborsoKmTable() {
		super();
		buildTable();
	}

	/**
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 */

	public <T> void aggiornaTable(T rimborsoKmStore) {
		this.removeAllItems();

		if (((RimborsoKmStore)rimborsoKmStore).getTotale() > 0) {

			setVisible(true);
			setContainerDataSource(new BeanItemContainer<RimborsoKm>(RimborsoKm.class,
					((RimborsoKmStore) rimborsoKmStore).getRimborsoKm()));

			setVisibleColumns("value");
			setColumnHeaders("Valore");
			Object[] properties = { "value" };
			boolean[] ordering = { true };
			sort(properties, ordering);
			setId("id");

		}

	}



}
