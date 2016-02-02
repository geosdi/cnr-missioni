package it.cnr.missioni.dashboard.component.table.admin;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.event.DashboardEvent.TableQualificaUserUpdatedEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.configuration.QualificaUser;
import it.cnr.missioni.rest.api.response.qualificaUser.QualificaUserStore;

/**
 * @author Salvia Vito
 */
public final class ElencoQualificaUserTable extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = -165216638734382809L;

	/**
	 * 
	 */

	public ElencoQualificaUserTable() {
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
		addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		addStyleName(ValoTheme.TABLE_SMALL);

		setSortEnabled(false);
		setColumnAlignment("revenue", Align.RIGHT);
		setRowHeaderMode(RowHeaderMode.HIDDEN);
		setSizeFull();
		setPageLength(10);
		setSelectable(true);
		setSortEnabled(true);
		setVisible(false);
		setNullSelectionAllowed(false);

		// addActionHandler(new TransactionsActionHandler());

	}

	/**
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 */

	public void aggiornaTable(QualificaUserStore qualificaUserStore) {
		this.removeAllItems();

		if (!qualificaUserStore.getQualificaUser().isEmpty()) {

			setVisible(true);
			setContainerDataSource(
					new BeanItemContainer<QualificaUser>(QualificaUser.class, qualificaUserStore.getQualificaUser()));

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
