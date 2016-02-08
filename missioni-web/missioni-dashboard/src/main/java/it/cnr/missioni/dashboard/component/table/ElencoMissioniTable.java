package it.cnr.missioni.dashboard.component.table;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.user.Veicolo;
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
		
		addGeneratedColumn("stato", new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {

				Missione missione = (Missione) itemId;
				Label l = new Label(missione.getStato().getStato());
				if (missione.getStato() == StatoEnum.PAGATA) {
					l.setStyleName(ValoTheme.LABEL_SUCCESS);
				}
				else{
					l.setStyleName(ValoTheme.LABEL_FAILURE);
				}
				return l;
			}
		});
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
			setVisibleColumns("localita", "oggetto", "stato", "dataInserimento");
			setColumnHeaders("Località", "Oggetto", "Stato", "Data Inserimento");
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
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 *
	 * @param missioniStore
	 */
	public <T> void aggiornaTableAdmin(T missioniStore) {
		this.removeAllItems();

		if (missioniStore != null) {
			setVisible(true);
			setContainerDataSource(new BeanItemContainer<Missione>(Missione.class, ((MissioniStore)missioniStore).getMissioni()));
			setVisibleColumns("shortResponsabileGruppo","localita", "oggetto", "stato", "dataInserimento");
			setColumnHeaders("User","Localita", "Oggetto", "Stato", "Data Inserimento");
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




}
