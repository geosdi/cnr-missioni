package it.cnr.missioni.dashboard.component.table;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;

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

		if (((MissioniStore)missioniStore).getTotale() > 0) {
			
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
			setColumnExpandRatio("oggetto", 2);
			setColumnExpandRatio("localita", 1);
		}

	}
	
	public <T> void aggiornaTableAdmin(T missioniStore) {
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
			setVisibleColumns("shortResponsabileGruppo","rimborso.numeroOrdine","rimborso.dataRimborso","rimborso.totale","oggetto","localita");
			setColumnHeaders("User","Numero Ordine","Data Rimborso","Totale","Oggetto","Località");
			setId("rimborso.numeroOrdine");
			Object[] properties = { "rimborso.dataRimborso", "rimborso.numeroOrdine" };
			boolean[] ordering = { false, true };
			sort(properties, ordering);
			setColumnExpandRatio("oggetto", 2);
			setColumnExpandRatio("localita", 1);
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
