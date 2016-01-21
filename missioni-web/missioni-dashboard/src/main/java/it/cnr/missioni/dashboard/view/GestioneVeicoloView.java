package it.cnr.missioni.dashboard.view;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.table.ElencoVeicoliTable;
import it.cnr.missioni.dashboard.component.window.VeicoloWindow;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 * @author Salvia Vito
 */
public class GestioneVeicoloView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 93612577398232810L;
	/**
	 * 
	 */
	private ElencoVeicoliTable elencoVeicoliTable;
	private User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
	private VerticalLayout layoutTable;
	private Button buttonModifica;
	private Veicolo selectedVeicolo;

	private MissioniStore missioniStore;

	// private CssLayout panel = new CssLayout();

	public GestioneVeicoloView() {
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		addStyleName("missione-view");
		setSizeFull();
		DashboardEventBus.register(this);
		setHeight("96%");
		setWidth("98%");
		addStyleName(ValoTheme.LAYOUT_CARD);
		addStyleName("panel-view");
		Responsive.makeResponsive(this);

		CssLayout toolbar = new CssLayout();
		toolbar.setWidth("100%");
		toolbar.setStyleName("toolbar-search");

		HorizontalLayout newMissioneLayout = new HorizontalLayout(createButtonNewVeicolo());
		newMissioneLayout.setSpacing(true);
		newMissioneLayout.setStyleName("button-new-missione");

		layoutTable = buildTable();
		layoutTable.setStyleName("layout-table-missione");

		GridLayout buttonsLayout = buildButtonsVeicolo();
		buttonsLayout.setSpacing(true);
		buttonsLayout.setStyleName("buttons-layout");

		toolbar.addComponent(newMissioneLayout);
		addComponent(toolbar);
		addComponent(layoutTable);
		addComponent(buttonsLayout);
		setExpandRatio(layoutTable, new Float(1));
		setComponentAlignment(buttonsLayout, Alignment.TOP_CENTER);
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	private VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoVeicoliTable = new ElencoVeicoliTable();

		this.elencoVeicoliTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedVeicolo = (Veicolo) itemClickEvent.getItemId();
				enableDisableButtons(true);
			}
		});

		v.addComponent(this.elencoVeicoliTable);
		v.setComponentAlignment(elencoVeicoliTable,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		return v;
	}

	@Override
	public void enter(final ViewChangeEvent event) {

	}

	private Button createButtonNewVeicolo() {
		final Button buttonNewMissione = new Button();
		buttonNewMissione.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonNewMissione.setIcon(FontAwesome.PLUS);
		buttonNewMissione.setDescription("Nuova Missione");
		buttonNewMissione.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				VeicoloWindow.open(new Veicolo(), false);
			}

		});
		return buttonNewMissione;
	}

	private GridLayout buildButtonsVeicolo() {
		GridLayout layout = new GridLayout(4, 1);

		buttonModifica = new Button();
		buttonModifica.setDescription("Modifica");
		buttonModifica.setIcon(FontAwesome.PENCIL);
		buttonModifica.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonModifica.addStyleName("button-margin");
		buttonModifica.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				VeicoloWindow.open(selectedVeicolo, true);

			}

		});

		layout.addComponents(buttonModifica);

		enableDisableButtons(false);

		return layout;

	}

	private void enableDisableButtons(boolean enabled) {
		this.buttonModifica.setEnabled(enabled);
	}

}