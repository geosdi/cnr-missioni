package it.cnr.missioni.dashboard.component.table.admin;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.event.DashboardEvent.TableNazioneUpdatedEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;

/**
 * @author Salvia Vito
 */
public final class ElencoNazioneTable extends Table {


	private static final long serialVersionUID = -617386765211975221L;


	public ElencoNazioneTable() {
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

	public void aggiornaTable(NazioneStore nazioneStore) {
		this.removeAllItems();

		if (!nazioneStore.getNazione().isEmpty()) {

			setVisible(true);
			setContainerDataSource(
					new BeanItemContainer<Nazione>(Nazione.class, nazioneStore.getNazione()));

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
