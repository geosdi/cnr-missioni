package it.cnr.missioni.dashboard.view;

import java.util.List;

import org.vaadin.pagingcomponent.PagingComponent;
import org.vaadin.pagingcomponent.PagingComponent.Builder;
import org.vaadin.pagingcomponent.utilities.FakeList;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.event.DashboardEventBus;

/**
 * @author Salvia Vito
 * @param <T>
 */
public abstract class GestioneTemplateView<T> extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2229015666954113261L;
	private VerticalLayout layoutTable;
	protected TextField multiMatchField;
	protected PagingComponent<T> pagingComponent;
	protected final VerticalLayout itemsArea = new VerticalLayout();

	// private CssLayout panel = new CssLayout();

	public GestioneTemplateView() {
		build();
		initialize();

	}

	private void build() {
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		addStyleName("missione-view");
		setSizeFull();
		DashboardEventBus.register(this);
		setHeight("96%");
		setWidth("98%");
		addStyleName(ValoTheme.LAYOUT_CARD);
		addStyleName("panel-view");
		Responsive.makeResponsive(this);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.setWidth("100%");
		toolbar.setStyleName("toolbar-search");
		HorizontalLayout fullTextsearchLayout = new HorizontalLayout();

		Component filter = buildFilter();
		Button buttonSearch = createButtonSearch();
		if (filter != null)
			fullTextsearchLayout.addComponent(filter);
		if (buttonSearch != null)
			fullTextsearchLayout.addComponent(buttonSearch);

		fullTextsearchLayout.setSpacing(true);
		fullTextsearchLayout.setStyleName("full-text-search");

		HorizontalLayout newObjectLayout = new HorizontalLayout();

		Button buttonNew = createButtonNew();
		if (buttonNew != null)
			newObjectLayout.addComponent(createButtonNew());
		newObjectLayout.addComponent(buildButtons());

		newObjectLayout.setSpacing(true);
		newObjectLayout.setStyleName("button-new-object");

		layoutTable = buildTable();
		layoutTable.setStyleName("layout-table-object");
		toolbar.addComponent(newObjectLayout);
		toolbar.addComponent(fullTextsearchLayout);
		toolbar.setComponentAlignment(fullTextsearchLayout, Alignment.BOTTOM_RIGHT);
		addComponent(toolbar);
		addComponent(layoutTable);
		addComponent(layoutTable);
		setExpandRatio(layoutTable, new Float(1));
	}

	/**
	 * * @param totale
	 */
	protected void buildPagination(Long totale) {

		List<T> fakeList = new FakeList<T>(totale.intValue());
		Builder<T> builder = PagingComponent.paginate(fakeList);
		pagingComponent = builder.build();
		if (pagingComponent.getComponentsManager().getElementsBuilder().getButtonFirst() != null)
			pagingComponent.getComponentsManager().getElementsBuilder().getButtonFirst().setCaption("Prima");
		if (pagingComponent.getComponentsManager().getElementsBuilder().getButtonLast() != null)
			pagingComponent.getComponentsManager().getElementsBuilder().getButtonLast().setCaption("Ultima");
		if (pagingComponent.getComponentsManager().getElementsBuilder().getButtonNext() != null)
			pagingComponent.getComponentsManager().getElementsBuilder().getButtonNext().setCaption("Successiva");
		if (pagingComponent.getComponentsManager().getElementsBuilder().getButtonPrevious() != null)
			pagingComponent.getComponentsManager().getElementsBuilder().getButtonPrevious().setCaption("Precendente");
//		this.pagingComponent
//				.setVisible(pagingComponent.getComponentsManager().getNumberOfItemsPerPage() < totale ? true : false);
		
		
		
		addComponent(this.pagingComponent);
		pagingComponent.setStyleName("pagination");
		setComponentAlignment(pagingComponent, Alignment.MIDDLE_CENTER);

	}

	protected abstract VerticalLayout buildTable();

	protected abstract void initialize();

	protected abstract Button createButtonNew();

	protected abstract Button createButtonSearch();

	protected abstract GridLayout buildButtons();

	protected abstract void addListenerPagination();

	protected abstract void enableDisableButtons(boolean enabled);

	protected Component buildFilter() {
		multiMatchField = new TextField();
		multiMatchField.setWidth("500px");
		multiMatchField.setInputPrompt("Testo da ricercare");
		multiMatchField.setIcon(FontAwesome.SEARCH);
		multiMatchField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		multiMatchField.addStyleName("field-full-text-search");
		return multiMatchField;
	}

	@Override
	public void enter(final ViewChangeEvent event) {

	}

}