package it.cnr.missioni.dashboard.view;

import java.util.List;

import org.vaadin.pagingcomponent.PagingComponent;
import org.vaadin.pagingcomponent.PagingComponent.Builder;
import org.vaadin.pagingcomponent.utilities.FakeList;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.event.DashboardEventBus;

/**
 * @param <T>
 * @author Salvia Vito
 */
public abstract class GestioneTemplateView<T> extends VerticalLayout implements View {

    /**
     *
     */
    private static final long serialVersionUID = -2229015666954113261L;
    protected  VerticalLayout itemsArea = new VerticalLayout();
    protected TextField multiMatchField;
    protected PagingComponent<T> pagingComponent;
    protected Button buttonCerca;
    protected Button buttonNew;
    protected Button buttonDettagli;
    protected Button buttonModifica;
    protected Button buttonRimborso;
    protected Button buttonPDF;
    protected Button buttonMissione;
    protected Button buttonVeicoloMissionePDF;
    protected Button buttonAnticipoPagamento;
    protected Button buttonAnticipoPagamentoPdf;
    private VerticalLayout layoutTable;
    private VerticalLayout layoutPagination = new VerticalLayout();
    protected Builder<T> builder;

    public GestioneTemplateView() {
        inizialize();
        build();
        initPagination();

    }

    private void build() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        DashboardEventBus.register(this);
        setHeight("96%");
        setWidth("98%");
        addStyleName(ValoTheme.LAYOUT_CARD);
        addStyleName("panel-view");
        addStyleName("missione-view");
        Responsive.makeResponsive(this);
//        layoutPagination.setHeight(50, Unit.PIXELS);
//        layoutPagination.setWidth(100, Unit.PERCENTAGE);
        layoutPagination.setStyleName("layout-pagination");
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
        //fullTextsearchLayout.setWidthUndefined();
        //fullTextsearchLayout.setWidth("600px");
        layoutTable = buildTable();
        layoutTable.setStyleName("layout-table-object");
        toolbar.addComponents(addActionButtons());
//		toolbar.setComponentAlignment(fullTextsearchLayout, Alignment.BOTTOM_RIGHT);
        addComponent(fullTextsearchLayout);
        setComponentAlignment(fullTextsearchLayout, Alignment.BOTTOM_RIGHT);
        addComponent(toolbar);
        addComponent(layoutPagination);
        addComponent(layoutTable);
        setExpandRatio(layoutTable, new Float(1));
    }

    protected Button buildButton(String caption, String description, FontIcon icon) {
        Button b = new Button(caption);
        b.setDescription(description);
        b.setStyleName(ValoTheme.BUTTON_PRIMARY);
        b.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        b.setIcon(icon);
        return b;
    }

    /**
     * * @param totale
     */
    protected void buildPagination(Long totale) {
        List<T> fakeList = new FakeList<T>(totale.intValue());
        builder = PagingComponent.paginate(fakeList);
        pagingComponent = builder.build();
        //builder.page(org.vaadin.pagingcomponent.Page.FIRST);
        setVisibleButtons(totale);
        this.pagingComponent
                .setVisible(pagingComponent.getComponentsManager().getNumberOfItemsPerPage() < totale ? true : false);
    }


    private void setVisibleButtons(Long totale) {
        layoutPagination.removeAllComponents();
        layoutPagination.addComponent(this.pagingComponent);
        layoutPagination.setComponentAlignment(pagingComponent, Alignment.MIDDLE_CENTER);
        if (pagingComponent.getComponentsManager().getNumberOfItemsPerPage() < totale) {
            pagingComponent.getComponentsManager().getElementsBuilder().getButtonFirst().setCaption("Prima");
            pagingComponent.getComponentsManager().getElementsBuilder().getButtonLast().setCaption("Ultima");
            pagingComponent.getComponentsManager().getElementsBuilder().getButtonNext().setCaption("Successiva");
            pagingComponent.getComponentsManager().getElementsBuilder().getButtonPrevious().setCaption("Precendente");
        }
    }


    protected abstract VerticalLayout buildTable();


    protected abstract void initPagination();

    protected abstract void inizialize();

    protected abstract Button createButtonSearch();

    protected abstract HorizontalLayout addActionButtons();

    protected abstract void addListenerPagination();

    protected abstract void enableButtons();

    protected abstract void disableButtons();

    protected Component buildFilter() {
        multiMatchField = new TextField();
        multiMatchField.setWidth("250px");
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