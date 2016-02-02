package it.cnr.missioni.dashboard.component.table.admin;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Table;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.action.UpdateUserAction;
import it.cnr.missioni.dashboard.component.table.ITable;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableUserUpdatedEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.user.UserStore;

/**
 * @author Salvia Vito
 */

public final class ElencoUserTable extends ITable.AbstractTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7510913321015554529L;

	/**
	 * 
	 */

	public ElencoUserTable() {
		super();
		buildTable();
	}



	/**
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 *
	 * @param missioniStore
	 */
	public <T> void aggiornaTable(T userStore) {
		this.removeAllItems();

		if (userStore != null) {
			
			
			BeanItemContainer<User> listaUser =
				    new BeanItemContainer<User>(User.class);
			listaUser.addNestedContainerProperty("anagrafica.cognome");
			listaUser.addNestedContainerProperty("anagrafica.nome");
			listaUser.addNestedContainerProperty("anagrafica.codiceFiscale");
			listaUser.addNestedContainerProperty("datiCNR.matricola");

			listaUser.addAll(((UserStore)userStore).getUsers());
			
			setVisible(true);
			setContainerDataSource(listaUser);
			setVisibleColumns("responsabileGruppo","anagrafica.cognome", "anagrafica.nome", "anagrafica.codiceFiscale", "datiCNR.matricola");
			setColumnHeaders("Responsabile Gruppo","Cognome", "Nome", "Codice Fiscale", "Matricola");
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
			
			addGeneratedColumn("responsabileGruppo", new Table.ColumnGenerator() {
			      @Override
			      public Object generateCell(Table source, final Object itemId, Object columnId) {
//			        boolean selected = selectedItemIds.contains(itemId);
			        /* When the chekboc value changes, add/remove the itemId from the selectedItemIds set */
			        final CheckBox cb = new CheckBox("",((User)itemId).isResponsabileGruppo());
			        
			        cb.addValueChangeListener(new ValueChangeListener() {
						

						@Override
						public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
							
							User user = ((User)itemId);
							user.setResponsabileGruppo(cb.getValue());
							DashboardUI.getCurrentUser().setResponsabileGruppo(cb.getValue());
							DashboardEventBus.post(new UpdateUserAction(user));

						}
					});
			        

			        return cb;
			      }
			    });
			
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
