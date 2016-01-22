package it.cnr.missioni.dashboard.component.table.admin;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.event.DashboardEvent.TableUserUpdatedEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */

public final class ElencoUserTable extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7510913321015554529L;

	/**
	 * 
	 */

	public ElencoUserTable() {
		buildTable();
	}

	/**
	 * 
	 * Costruisce la tabella per la visualizzazione dei dati
	 * 
	 * @param neetWrapper
	 */
	private void buildTable() {
		DashboardEventBus.register(this);
		// Stile
		addStyleName(ValoTheme.TABLE_NO_STRIPES);
		addStyleName(ValoTheme.TABLE_BORDERLESS);
		// addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		addStyleName(ValoTheme.TABLE_SMALL);
		//
		// setSortEnabled(false);
		setColumnAlignment("revenue", Align.RIGHT);
		setRowHeaderMode(RowHeaderMode.HIDDEN);
		setSizeFull();

		setPageLength(10);
		setSelectable(true);
		setSortEnabled(true);
		setVisible(false);
		setImmediate(true);
		// setFilterBarVisible(true);
		// select(null);
		// setNullSelectionAllowed(true);
		// unselect(itemId);

	}

	/**
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 *
	 * @param missioniStore
	 */
	public void aggiornaTable(UserStore userStore) {
		this.removeAllItems();

		if (userStore != null) {
			
			
			BeanItemContainer<User> listaUser =
				    new BeanItemContainer<User>(User.class);
			listaUser.addNestedContainerProperty("anagrafica.cognome");
			listaUser.addNestedContainerProperty("anagrafica.nome");
			listaUser.addNestedContainerProperty("anagrafica.codiceFiscale");
			listaUser.addNestedContainerProperty("datiCNR.matricola");

			listaUser.addAll(userStore.getUsers());
			
			setVisible(true);
			setContainerDataSource(listaUser);
			setVisibleColumns("anagrafica.cognome", "anagrafica.nome", "anagrafica.codiceFiscale", "datiCNR.matricola");
			setColumnHeaders("Cognome", "Nome", "Codice Fiscale", "Matricola");
			setId("id");
			Object[] properties = { "anagrafica.cognome", "anagrafica.nome" };
			boolean[] ordering = { true, true };
			sort(properties, ordering);
//			setColumnWidth("dataInserimento", 160);
//			setColumnWidth("oggetto", 160);
//			setColumnExpandRatio("oggetto", 2);
//			setColumnExpandRatio("localita", 2);
//			setColumnExpandRatio("id", 1);
			// setColumnExpandRatio("dataInserimento",1);
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
	 * Aggiorna la table user a seguito di un inserimento o modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableUser(TableUserUpdatedEvent tableUserUpdatedEvent) {
		aggiornaTable(tableUserUpdatedEvent.getUserStore());
	}

}
