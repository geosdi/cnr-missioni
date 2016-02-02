package it.cnr.missioni.dashboard.component.table;

import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.event.DashboardEventBus;

/**
 * @author Salvia Vito
 */
public interface ITable{
	
	 void  buildTable();
	 
	 <T>  void aggiornaTable(T store);
	
	public abstract class AbstractTable extends Table  implements ITable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 2581487257432292657L;
		
		public AbstractTable(){
		}
		
		
		public void buildTable() {
			DashboardEventBus.register(this);
			// Stile
			addStyleName(ValoTheme.TABLE_NO_STRIPES);
			addStyleName(ValoTheme.TABLE_BORDERLESS);
			// addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
			addStyleName(ValoTheme.TABLE_SMALL);
			//
			setColumnAlignment("revenue", Align.RIGHT);
			setRowHeaderMode(RowHeaderMode.HIDDEN);
			setSizeFull();

			setPageLength(10);
			setSelectable(true);
			setSortEnabled(true);
			setVisible(false);
			setImmediate(true);
			setNullSelectionAllowed(false);

		}
		
		
	}
	
}
