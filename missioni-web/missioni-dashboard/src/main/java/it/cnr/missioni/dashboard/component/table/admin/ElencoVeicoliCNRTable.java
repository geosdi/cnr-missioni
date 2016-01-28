package it.cnr.missioni.dashboard.component.table.admin;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableVeicoliCNRUpdatedEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableVeicoliUpdatedEvent;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;

/**
 * @author Salvia Vito
 */
public final class ElencoVeicoliCNRTable extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3858138091665164557L;

	public ElencoVeicoliCNRTable() {
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

	public void aggiornaTable(VeicoloCNRStore veicoloCNRStore) {
		this.removeAllItems();

		if (!veicoloCNRStore.getVeicoliCNR().isEmpty()) {
			
			
			BeanItemContainer<VeicoloCNR> listaVeicoliCNR=
				    new BeanItemContainer<VeicoloCNR>(VeicoloCNR.class);
			listaVeicoliCNR.addNestedContainerProperty("stato");
			listaVeicoliCNR.addNestedContainerProperty("tipo");
			listaVeicoliCNR.addNestedContainerProperty("targa");
			listaVeicoliCNR.addNestedContainerProperty("cartaCircolazione");
			listaVeicoliCNR.addNestedContainerProperty("polizzaAssicurativa");

			listaVeicoliCNR.addAll(veicoloCNRStore.getVeicoliCNR());
			
			setVisible(true);
			setContainerDataSource(listaVeicoliCNR);	

			setVisibleColumns("tipo", "targa", "cartaCircolazione", "polizzaAssicurativa", "stato");
			setColumnHeaders("Tipo", "targa", "Carta Circolazione", "Polizza Assicurativa", "Stato");
			setId("targa");
			Object[] properties = { "tipo", "targa" };
			boolean[] ordering = { true, false };
			sort(properties, ordering);
			setId("targa");

		}

	}



	/**
	 * 
	 * Aggiorna la table veicoli a seguito di un inserimento o modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableCNRVeicoli(final TableVeicoliCNRUpdatedEvent tableVeicoliCNRUpdatedEvent) {
		aggiornaTable(tableVeicoliCNRUpdatedEvent.getVeicoloCNRStore());
	}

}
