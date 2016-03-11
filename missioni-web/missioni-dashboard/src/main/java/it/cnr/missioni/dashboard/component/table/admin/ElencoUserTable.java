package it.cnr.missioni.dashboard.component.table.admin;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Table;

import it.cnr.missioni.dashboard.action.admin.AdminUpdateUserAction;
import it.cnr.missioni.dashboard.component.table.ITable;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.user.RuoloUserEnum;
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

		if (((UserStore)userStore).getTotale() > 0) {
			
			
			BeanItemContainer<User> listaUser =
				    new BeanItemContainer<User>(User.class);
			listaUser.addNestedContainerProperty("anagrafica.cognome");
			listaUser.addNestedContainerProperty("anagrafica.nome");
			listaUser.addNestedContainerProperty("anagrafica.codiceFiscale");
			listaUser.addNestedContainerProperty("credenziali.ruoloUtente");
			listaUser.addNestedContainerProperty("datiCNR.matricola");

			listaUser.addAll(((UserStore)userStore).getUsers());
			
			setVisible(true);
			setContainerDataSource(listaUser);
			setVisibleColumns("responsabileGruppo","credenziali.ruoloUtente","anagrafica.cognome", "anagrafica.nome", "anagrafica.codiceFiscale", "datiCNR.matricola");
			setColumnHeaders("Responsabile Gruppo","Admin","Cognome", "Nome", "Codice Fiscale", "Matricola");
			setColumnWidth("responsabileGruppo", 150);
			setColumnWidth("credenziali.ruoloUtente", 70);
			setId("id");
			Object[] properties = { "anagrafica.cognome", "anagrafica.nome" };
			boolean[] ordering = { true, true };
			sort(properties, ordering);

			
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
	 */
	@Override
	public void addGeneratedColumn() {

		
		addGeneratedColumn("responsabileGruppo", new Table.ColumnGenerator() {
		      /**
			 * 
			 */
			private static final long serialVersionUID = 6589113831511771042L;

			@Override
		      public Object generateCell(Table source, final Object itemId, Object columnId) {
		        final CheckBox cb = new CheckBox("",((User)itemId).isResponsabileGruppo());
		        
		        cb.addValueChangeListener(new ValueChangeListener() {
					

					/**
					 * 
					 */
					private static final long serialVersionUID = 2555372007125474494L;

					@Override
					public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
						
						User user = ((User)itemId);
						user.setResponsabileGruppo(cb.getValue());
						DashboardEventBus.post(new AdminUpdateUserAction(user));

					}
				});
		        

		        return cb;
		      }
		    });
		
		addGeneratedColumn("credenziali.ruoloUtente", new Table.ColumnGenerator() {
		      /**
			 * 
			 */
			private static final long serialVersionUID = 2981409762119298531L;

			@Override
		      public Object generateCell(Table source, final Object itemId, Object columnId) {
		        final CheckBox cb = new CheckBox("",((User)itemId).getCredenziali().getRuoloUtente() == RuoloUserEnum.UTENTE_ADMIN ? true : false);
		        
		        cb.addValueChangeListener(new ValueChangeListener() {
					

					/**
					 * 
					 */
					private static final long serialVersionUID = -1318077294324314158L;

					@Override
					public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
						
						User user = ((User)itemId);
						user.getCredenziali().setRuoloUtente(cb.getValue() ? RuoloUserEnum.UTENTE_ADMIN : RuoloUserEnum.UTENTE_SEMPLICE);
						DashboardEventBus.post(new AdminUpdateUserAction(user));

					}
				});
		        

		        return cb;
		      }
		    });		
	}

}
