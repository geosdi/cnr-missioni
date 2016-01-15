package it.cnr.missioni.dashboard.component.table;

import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.window.VeicoloWindow;
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
		setVisible(false);
		aggiornaTable();
		
		addGeneratedColumn("veicoloPrincipale", new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {

				Veicolo v = (Veicolo) itemId;
				if (v.isVeicoloPrincipale()) {
					Label l = new Label();
					l.setContentMode(ContentMode.HTML);
					l.setValue(FontAwesome.CHECK.getHtml());
					return l;
				}
				return null;
			}
		});

		addActionHandler(new TransactionsActionHandler());

	}

	/**
	 * 
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 * 
	 * @param listaNeet
	 * @param totNeets
	 */
	@Subscribe
	public void aggiornaTable() {
		this.removeAllItems();
		User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());

		if (!user.getMappaVeicolo().isEmpty()) {
			setVisible(true);
			setContainerDataSource(new BeanItemContainer<Veicolo>(Veicolo.class, user.getMappaVeicolo().values()));

			setVisibleColumns("tipo", "targa", "cartaCircolazione", "polizzaAssicurativa", "veicoloPrincipale");
			setColumnHeaders("Tipo", "targa", "Carta Circolazione", "Polizza Assicurativa", "");
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
				VeicoloWindow.open(((Veicolo) target), true);
			}
		}

		@Override
		public Action[] getActions(final Object target, final Object sender) {
			return new Action[] { seleziona };
		}
	}

}
