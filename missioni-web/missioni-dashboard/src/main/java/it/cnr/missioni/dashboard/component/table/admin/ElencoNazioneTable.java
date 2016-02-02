package it.cnr.missioni.dashboard.component.table.admin;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;

import it.cnr.missioni.dashboard.component.table.ITable;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableNazioneUpdatedEvent;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;

/**
 * @author Salvia Vito
 */
public final class ElencoNazioneTable extends ITable.AbstractTable {


	private static final long serialVersionUID = -617386765211975221L;


	public ElencoNazioneTable() {
		super();
		buildTable();
	}



	/**
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 */

	public <T> void aggiornaTable(T nazioneStore) {
		this.removeAllItems();

		if (!((NazioneStore)nazioneStore).getNazione().isEmpty()) {

			setVisible(true);
			setContainerDataSource(
					new BeanItemContainer<Nazione>(Nazione.class, ((NazioneStore)nazioneStore).getNazione()));

			setVisibleColumns("value");
			setColumnHeaders("Nazione");
			Object[] properties = { "value" };
			boolean[] ordering = { true };
			sort(properties, ordering);
			setId("id");

		}

	}

	/**
	 * 
	 * Aggiorna la table nazione a seguito di un inserimento o modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableNazione(final TableNazioneUpdatedEvent tableNazioneUpdatedEvent) {
		aggiornaTable(tableNazioneUpdatedEvent.getNazioneStore());
	}

}
