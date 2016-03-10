package it.cnr.missioni.dashboard.component.table.admin;

import com.vaadin.data.util.BeanItemContainer;

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

			setVisibleColumns("value","tipo","voceSpesa");
			setColumnHeaders("Descrizione","Tipo","Voce Spesa");
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
	
	}


}
