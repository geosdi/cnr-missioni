package it.cnr.missioni.dashboard.component.table;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 * @author Salvia Vito
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
		addActionHandler(new TransactionsActionHandler());
		setVisible(false);


	}

	/**
	 * 
	 * Aggiorna la tabella con la nuova lista derivante dalla query su ES
	 * 
	 * @param listaNeet
	 * @param totNeets
	 */
	public void aggiornaTable(MissioniStore missioniStore) {
		this.removeAllItems();

		if (missioniStore != null) {
			setVisible(true);
			setContainerDataSource(new BeanItemContainer<Missione>(Missione.class, missioniStore.getMissioni()));
			setVisibleColumns("id","localita", "oggetto", "stato", "dataInserimento");
			setColumnHeaders("ID","Località", "Oggetto", "Stato", "Data Inserimento");
			setId("id");
			Object[] properties = { "dataInserimento", "id" };
			boolean[] ordering = { false, true };
			sort(properties, ordering);
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
		private final Action rimborso = new Action("Rimborso");

		@Override
		public void handleAction(final Action action, final Object sender, final Object target) {
			if (action == seleziona) {
				WizardSetupWindow.getWizardSetup().withModifica(true).withTipo("missione").withMissione((Missione) target).build();;

			}
			if (action == rimborso) {
				Rimborso rimborso = null;
				boolean modifica;
				// se non è stato ancora associato il rimborso
				if (((Missione) target).getRimborso() != null) {
					rimborso = ((Missione) target).getRimborso();
					modifica = true;
				} else {
					rimborso = new Rimborso();
					modifica = false;
				}
				((Missione) target).setRimborso(rimborso);
				WizardSetupWindow.getWizardSetup().withModifica(modifica).withTipo("rimborso").withMissione((Missione) target).build();;

				
//				WizardWindow.openWizardMissioneRimborso(modifica, ((Missione) target), "rimborso");
			}
		}

		@Override
		public Action[] getActions(final Object target, final Object sender) {
			return new Action[] { seleziona, rimborso };
		}
	}

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

}
