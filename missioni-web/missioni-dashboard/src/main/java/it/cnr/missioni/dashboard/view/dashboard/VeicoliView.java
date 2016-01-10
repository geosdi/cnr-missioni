package it.cnr.missioni.dashboard.view.dashboard;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import it.cnr.missioni.dashboard.component.ElencoVeicoliTable;


@SuppressWarnings("serial")
public final class VeicoliView extends Panel implements View{

	private ElencoVeicoliTable table;

	private ComboBox selectPage = new ComboBox();;

	public VeicoliView() {

		setSizeFull();
		buildTable();

//		Component componentForm = DashboardUI.createContentWrapper(buildForm());
//		componentForm.setWidth("40%");
//		addComponent(componentForm);
//
//		Component componentTable = DashboardUI.createContentWrapper(buildTable());
//		componentTable.setWidth("90%");
//		addComponent(componentTable);
//		setExpandRatio(componentTable, 1);
//		setComponentAlignment(componentForm,
//				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
//
//		setComponentAlignment(componentTable,
//				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	private VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();


		table = new ElencoVeicoliTable();

		HorizontalLayout h = new HorizontalLayout();
		h.addComponent(selectPage);

		h.setMargin(true);
		v.addComponent(table);
		v.addComponent(h);
		v.setComponentAlignment(table,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
		v.setComponentAlignment(h, new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		return v;
	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}

}
