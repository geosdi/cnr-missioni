package it.cnr.missioni.dashboard.component.table.admin;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import it.cnr.missioni.dashboard.component.table.ITable;
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

		if (((TipologiaSpesaStore)tipologiaSpesaStore).getTotale() > 0) {

			setVisible(true);
			setContainerDataSource(
					new BeanItemContainer<TipologiaSpesa>(TipologiaSpesa.class, ((TipologiaSpesaStore)tipologiaSpesaStore).getTipologiaSpesa()));

			setVisibleColumns("value","estera","italia","voceSpesa");
			setColumnHeaders("Descrizione","Estera","italia","Voce Spesa");
			Object[] properties = { "value" };
			boolean[] ordering = { true };
			sort(properties, ordering);
			setId("id");

		}

	}

	/**
	 * 
	 */
	@Override
	public void addGeneratedColumn() {
		addGeneratedColumn("estera", new ColumnGenerator() {



			/**
			 * 
			 */
			private static final long serialVersionUID = 1667056005984020872L;

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {

				TipologiaSpesa t = (TipologiaSpesa) itemId;
				if (t.isEstera()) {
					Label l = new Label();
					l.setContentMode(ContentMode.HTML);
					l.setValue(FontAwesome.CHECK.getHtml());
					return l;
				}
				return null;
			}
		});	
		
		
		addGeneratedColumn("italia", new ColumnGenerator() {



			/**
			 * 
			 */
			private static final long serialVersionUID = 1132405126407756800L;

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {

				TipologiaSpesa t = (TipologiaSpesa) itemId;
				if (t.isItalia()) {
					Label l = new Label();
					l.setContentMode(ContentMode.HTML);
					l.setValue(FontAwesome.CHECK.getHtml());
					return l;
				}
				return null;
			}
		});	
		
	}




}
