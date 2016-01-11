package it.cnr.missioni.dashboard.component;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 * @email salvia@e-sintesi.it
 */
@SuppressWarnings("serial")
public final class ElencoVeicoliTable extends Table {

	public ElencoVeicoliTable() {
		buildTable();
	}

	/**
	 * 
	 * Costruisce la tabella per la visualizzazione dei dati
	 * 
	 * @param neetWrapper
	 */
	private void buildTable() {

		// Stile
		addStyleName(ValoTheme.TABLE_NO_STRIPES);
		addStyleName(ValoTheme.TABLE_BORDERLESS);
		addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		addStyleName(ValoTheme.TABLE_SMALL);

		setSortEnabled(false);
		setColumnAlignment("revenue", Align.RIGHT);
		setRowHeaderMode(RowHeaderMode.HIDDEN);
		setWidth("100%");
		setHeight("80%");
		setPageLength(10);
		setSelectable(true);
		setSortEnabled(true);

		aggiornaTable();
		addActionHandler(new TransactionsActionHandler());

	}

	/**
	 * 
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 * 
	 * @param listaNeet
	 * @param totNeets
	 */
	public void aggiornaTable() {
		this.removeAllItems();
		User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());

		if (user.getMappaVeicolo().values().size() > 0) {
			setContainerDataSource(new BeanItemContainer<Veicolo>(Veicolo.class, user.getMappaVeicolo().values()));


			setVisibleColumns("tipo", "targa", "cartaCircolazione", "polizzaAssicurativa");
			setColumnHeaders("Tipo", "targa", "Carta Circolazione", "Polizza Assicurativa");
			setId("targa");
			Object[] properties = { "tipo", "targa" };
			boolean[] ordering = { true, false };
			sort(properties, ordering);
			setId("targa");
		}

	}

	/**
	 * 
	 * Crea un context menù quando si clicca col tasto destro su una riga della
	 * tabella
	 * 
	 * @author Salvia Vito
	 *
	 */
	private class TransactionsActionHandler implements Handler {
		private final Action seleziona = new Action("Seleziona");

		@Override
		public void handleAction(final Action action, final Object sender, final Object target) {
			if (action == seleziona) {
				VeicoloWindow.open(((Veicolo) target), true,ElencoVeicoliTable.this);
			}
		}

		@Override
		public Action[] getActions(final Object target, final Object sender) {
			return new Action[] { seleziona };
		}
	}

}
