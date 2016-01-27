package it.cnr.missioni.dashboard.view;

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
 */
public abstract class GestioneTemplateView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2229015666954113261L;
	private VerticalLayout layoutTable;
	protected TextField multiMatchField;

	// private CssLayout panel = new CssLayout();

	public GestioneTemplateView() {

		initialize();

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
		if(filter != null)
			fullTextsearchLayout.addComponent(filter);
		if(buttonSearch != null)
			fullTextsearchLayout.addComponent(buttonSearch);
		
		fullTextsearchLayout.setSpacing(true);
		fullTextsearchLayout.setStyleName("full-text-search");

		HorizontalLayout newObjectLayout = new HorizontalLayout();
		
		Button buttonNew = createButtonNew();
		if(buttonNew != null)
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
		setExpandRatio(layoutTable, new Float(1));
	}


	protected abstract VerticalLayout buildTable();

	protected abstract void initialize();

	protected abstract void buildComboPage();

	protected abstract Button createButtonNew();

	protected abstract Button createButtonSearch();

	protected abstract GridLayout buildButtons();

	protected abstract void enableDisableButtons(boolean enabled);

	protected  Component buildFilter(){
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