package it.cnr.missioni.dashboard.view;

import java.util.ArrayList;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.component.table.ElencoVeicoliTable;
import it.cnr.missioni.dashboard.component.window.VeicoloWindow;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
public class GestioneVeicoloView extends GestioneTemplateView implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 93612577398232810L;
	/**
	 * 
	 */
	private ElencoVeicoliTable elencoVeicoliTable;
	private VerticalLayout layoutTable;
	private Button buttonModifica;
	private Veicolo selectedVeicolo;

	// private CssLayout panel = new CssLayout();

	public GestioneVeicoloView() {
		super();
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoVeicoliTable = new ElencoVeicoliTable();
		this.elencoVeicoliTable .aggiornaTable(new ArrayList<Veicolo>(DashboardUI.getCurrentUser().getMappaVeicolo().values()));

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

	protected Button createButtonNew() {
		final Button buttonNewVeicolo = new Button("Nuovo Veicolo");
		buttonNewVeicolo.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonNewVeicolo.setIcon(FontAwesome.PLUS);
		buttonNewVeicolo.setDescription("Aggiunge un nuovo Veicolo");
		buttonNewVeicolo.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

		buttonNewVeicolo.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				VeicoloWindow.open(new Veicolo(), false);
			}

		});
		return buttonNewVeicolo;
	}

	protected GridLayout buildButtons() {
		GridLayout layout = new GridLayout(4, 1);
		layout.setSpacing(true);
		buttonModifica = new Button("Modifica");
		buttonModifica.setDescription("Modifica i dati del vieicolo");
		buttonModifica.setIcon(FontAwesome.PENCIL);
		buttonModifica.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonModifica.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

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

	protected void enableDisableButtons(boolean enabled) {
		this.buttonModifica.setEnabled(enabled);
	}

	/**
	 * 
	 */
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	@Override
	protected void buildComboPage() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return
	 */
	@Override
	protected Button createButtonSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	protected  Component buildFilter(){
		return null;
	}

}