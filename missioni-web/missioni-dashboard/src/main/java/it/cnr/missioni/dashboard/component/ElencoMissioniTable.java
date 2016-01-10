package it.cnr.missioni.dashboard.component;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Table;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 * @author Salvia Vito
 * @email salvia@e-sintesi.it
 */
@SuppressWarnings("serial")
public final class ElencoMissioniTable extends Table {

	public ElencoMissioniTable() {
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

		try {
			User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());

			MissioneSearchBuilder missioneSearchBuilder = new MissioneSearchBuilder();
			missioneSearchBuilder.setIdUser(user.getId());
			MissioniStore missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
			if (missioniStore != null) {
				setContainerDataSource(new BeanItemContainer<Missione>(Missione.class, missioniStore.getMissioni()));
				setVisibleColumns("localita", "oggetto", "stato", "dataInserimento");
				setColumnHeaders("Località", "Oggetto", "Stato", "Data Inserimento");
				setId("id");
				Object[] properties = { "dataInserimento", "id" };
				boolean[] ordering = { false, true };
				sort(properties, ordering);
				addActionHandler(new TransactionsActionHandler());
			}
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
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
			}
		}

		@Override
		public Action[] getActions(final Object target, final Object sender) {
			return new Action[] { seleziona };
		}
	}

	@Override
	protected String formatPropertyValue(Object rowId, Object colId, Property property) {
		Object v = property.getValue();
		if (v instanceof DateTime) {
			DateTime dateValue = (DateTime) v;

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			return format.format(dateValue.toDate());

		}
		return super.formatPropertyValue(rowId, colId, property);
	}

}
