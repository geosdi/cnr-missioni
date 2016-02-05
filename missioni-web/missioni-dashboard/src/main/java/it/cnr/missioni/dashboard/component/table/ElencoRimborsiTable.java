package it.cnr.missioni.dashboard.component.table;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;

import it.cnr.missioni.dashboard.event.DashboardEvent.TableRimborsiUpdatedEvent;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 * @author Salvia Vito
 */

public final class ElencoRimborsiTable  extends ITable.AbstractTable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6439677451802448635L;

	public ElencoRimborsiTable() {
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

		if (missioniStore != null) {
			
			BeanItemContainer<Missione> listaMissioni =
				    new BeanItemContainer<Missione>(Missione.class);
			listaMissioni.addNestedContainerProperty("rimborso.numeroOrdine");
			listaMissioni.addNestedContainerProperty("rimborso.dataRimborso");
			listaMissioni.addNestedContainerProperty("rimborso.totale");
			listaMissioni.addAll(((MissioniStore)missioniStore).getMissioni());

			
			setVisible(true);
			setContainerDataSource(listaMissioni);
			setVisibleColumns("rimborso.numeroOrdine","rimborso.dataRimborso","rimborso.totale","id","oggetto","localita");
			setColumnHeaders("Numero Ordine","Data Rimborso","Totale","ID Missione","Oggetto","Località");
			setId("rimborso.numeroOrdine");
			Object[] properties = { "rimborso.dataRimborso", "rimborso.numeroOrdine" };
			boolean[] ordering = { false, true };
			sort(properties, ordering);
//			setColumnWidth("dataInserimento", 160);
//			setColumnWidth("oggetto", 160);
			setColumnExpandRatio("oggetto", 2);
			setColumnExpandRatio("localita", 1);
//			setColumnExpandRatio("id", 1);
			// setColumnExpandRatio("dataInserimento",1);
		}
		// else{
		// setVisible(false);
		// }

	}


	/**
	 * 
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
	 * Aggiorna la table rimborsi a seguito di un inserimento o modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableMissioni(TableRimborsiUpdatedEvent tableRimborsiUpdatedEvent) {
		aggiornaTable(tableRimborsiUpdatedEvent.getMissioniStore());
	}

}
