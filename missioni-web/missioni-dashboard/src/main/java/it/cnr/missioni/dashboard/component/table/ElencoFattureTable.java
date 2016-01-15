package it.cnr.missioni.dashboard.component.table;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.joda.time.DateTime;

import com.gargoylesoftware.htmlunit.javascript.host.intl.NumberFormat;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Table;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;
import it.cnr.missioni.model.rimborso.Rimborso;

/**
 * @author Salvia Vito
 */
@SuppressWarnings("serial")
public final class ElencoFattureTable extends Table {

	private BeanFieldGroup<Fattura> beanFieldGroup;
	private Missione missione;

	public ElencoFattureTable(BeanFieldGroup<Fattura> beanFieldGroup, Missione missione) {
		this.beanFieldGroup = beanFieldGroup;
		this.missione = missione;
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
		setSizeFull();
		// setPageLength(10);
		setSelectable(true);
		setSortEnabled(true);
		aggiornaTable();
		setFooterVisible(true);
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

		if (!missione.getRimborso().getMappaFattura().isEmpty()) {

			setContainerDataSource(
					new BeanItemContainer<Fattura>(Fattura.class, missione.getRimborso().getMappaFattura().values()));

			setVisibleColumns("numeroFattura", "data", "tipologiaSpesa", "valuta", "altro", "importo");
			setColumnHeaders("Numero Fattura", "Data", "Tipologia Spesa", "Valuta", "Altro", "Importo");
			setId("numeroFattura");
			Object[] properties = { "data", "numeroFattura" };
			boolean[] ordering = { false, false };
			sort(properties, ordering);
			
		}

	}

	
	public void aggiornaTotale(double totale){
		DecimalFormat df = new DecimalFormat("#0.00");		
		setColumnFooter("importo", "Tot.: "+df.format(totale));
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
		private final Action elimina = new Action("Elimina");

		@Override
		public void handleAction(final Action action, final Object sender, final Object target) {
			if (action == seleziona) {
				beanFieldGroup.setItemDataSource((Fattura) target);
			}
			if (action == elimina) {
				missione.getRimborso().getMappaFattura().remove(((Fattura) target).getId());
				aggiornaTable();
				aggiornaTotale(missione.getRimborso().getTotale());
				Utility.getNotification(Utility.getMessage("success_message"), null,
						Type.HUMANIZED_MESSAGE);
			}
		}

		@Override
		public Action[] getActions(final Object target, final Object sender) {
			return new Action[] { seleziona, elimina };
		}
	}


}
