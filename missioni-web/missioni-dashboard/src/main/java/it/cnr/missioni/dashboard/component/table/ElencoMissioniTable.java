package it.cnr.missioni.dashboard.component.table;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;

import it.cnr.missioni.dashboard.event.DashboardEvent.TableMissioniUpdateUpdatedEvent;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 * @author Salvia Vito
 */

public final class ElencoMissioniTable extends ITable.AbstractTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6439677451802448635L;

	/**
	 * 
	 */

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

		if (missioniStore != null) {
			setVisible(true);
			setContainerDataSource(new BeanItemContainer<Missione>(Missione.class, ((MissioniStore)missioniStore).getMissioni()));
			setVisibleColumns("id", "localita", "oggetto", "stato", "dataInserimento");
			setColumnHeaders("ID", "Località", "Oggetto", "Stato", "Data Inserimento");
			setId("id");
			Object[] properties = { "dataInserimento", "id" };
			boolean[] ordering = { false, true };
			sort(properties, ordering);
			setColumnWidth("dataInserimento", 160);
			setColumnWidth("oggetto", 160);
			setColumnExpandRatio("oggetto", 2);
			setColumnExpandRatio("localita", 2);
			setColumnExpandRatio("id", 1);
		}

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
	 * Aggiorna la table missioni a seguito di un inserimento o modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableMissioni(TableMissioniUpdateUpdatedEvent tableMissioniUpdatedEvent) {
		aggiornaTable(tableMissioniUpdatedEvent.getMissioniStore());
	}



}
