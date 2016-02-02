package it.cnr.missioni.dashboard.component.table.admin;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;

import it.cnr.missioni.dashboard.component.table.ITable;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableQualificaUserUpdatedEvent;
import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.rest.api.response.qualificaUser.QualificaUserStore;

/**
 * @author Salvia Vito
 */
public final class ElencoQualificaUserTable extends ITable.AbstractTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -165216638734382809L;

	/**
	 * 
	 */

	public ElencoQualificaUserTable() {
	super();
	buildTable();
	}



	/**
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 */

	public <T> void aggiornaTable(T qualificaUserStore) {
		this.removeAllItems();

		if (qualificaUserStore != null) {

			setVisible(true);
			setContainerDataSource(
					new BeanItemContainer<QualificaUser>(QualificaUser.class, ((QualificaUserStore)qualificaUserStore).getQualificaUser()));

			setVisibleColumns("value");
			setColumnHeaders("Qualifica");
			Object[] properties = { "value" };
			boolean[] ordering = { true };
			sort(properties, ordering);
			setId("id");

		}

	}

	/**
	 * 
	 * Aggiorna la table qualifica a seguito di un inserimento o modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableQualifica(final TableQualificaUserUpdatedEvent tableQualificaUserUpdatedEvent) {
		aggiornaTable(tableQualificaUserUpdatedEvent.getQualificaStore());
	}

}
