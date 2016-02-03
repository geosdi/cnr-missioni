package it.cnr.missioni.dashboard.component.table.admin;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;

import it.cnr.missioni.dashboard.component.table.ITable;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableTipologiaSpesaUpdatedEvent;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * @author Salvia Vito
 */
public final class ElencoTipologiaSpesaTable extends ITable.AbstractTable {




	/**
	 * 
	 */
	private static final long serialVersionUID = 5848805564266198608L;

	public ElencoTipologiaSpesaTable() {
		super();
		buildTable();
	}



	/**
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 */

	public <T> void aggiornaTable(T tipologiaSpesaStore) {
		this.removeAllItems();

		if (tipologiaSpesaStore != null) {

			setVisible(true);
			setContainerDataSource(
					new BeanItemContainer<TipologiaSpesa>(TipologiaSpesa.class, ((TipologiaSpesaStore)tipologiaSpesaStore).getTipologiaSpesa()));

			setVisibleColumns("value");
			setColumnHeaders("Descrizione");
			Object[] properties = { "value" };
			boolean[] ordering = { true };
			sort(properties, ordering);
			setId("id");

		}

	}

	/**
	 * 
	 * Aggiorna la table tipologia spesa a seguito di un inserimento o modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableTipologiaSpesa(final TableTipologiaSpesaUpdatedEvent tableTipologiaSpesaUpdatedEvent) {
		aggiornaTable(tableTipologiaSpesaUpdatedEvent.getTipologiaSpesaStore());
	}

}