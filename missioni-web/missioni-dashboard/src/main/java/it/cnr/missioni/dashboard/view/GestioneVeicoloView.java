package it.cnr.missioni.dashboard.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import com.google.common.eventbus.Subscribe;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.component.table.ElencoVeicoliTable;
import it.cnr.missioni.dashboard.component.window.VeicoloWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableVeicoliUpdatedEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.Veicolo;

/**
 * @author Salvia Vito
 */
public class GestioneVeicoloView extends GestioneTemplateView<Veicolo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 93612577398232810L;
	/**
	 * 
	 */
	private ElencoVeicoliTable elencoVeicoliTable;
	private VerticalLayout layoutTable;
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
		this.elencoVeicoliTable
				.aggiornaTable(new ArrayList<Veicolo>(DashboardUI.getCurrentUser().getMappaVeicolo().values()));

		this.elencoVeicoliTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5017833149328634827L;

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

		buttonNew = buildButton("Nuovo Veicolo", "Aggiunge un nuovo Veicolo", FontAwesome.PLUS);
		buttonNew.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4326582344154509066L;

			@Override
			public void buttonClick(ClickEvent event) {
				VeicoloWindow.open(new Veicolo(), false);
			}

		});
		return buttonNew;
	}

	protected GridLayout addActionButtons() {
		GridLayout layout = new GridLayout(4, 1);
		layout.setSpacing(true);
		buttonModifica = buildButton("Modifica", "Modifica i dati del vieicolo", FontAwesome.PENCIL);

		buttonModifica.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4764560187783814285L;

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

		if (!DashboardUI.getCurrentUser().getMappaVeicolo().isEmpty()) {
			buildPagination(new Long(DashboardUI.getCurrentUser().getMappaVeicolo().size()));
			addListenerPagination();
		}

	}

	/**
	 * 
	 * Aggiunge il listener alla paginazione
	 * 
	 */
	protected void addListenerPagination() {

		pagingComponent.addListener(new LazyPagingComponentListener<Veicolo>(itemsArea) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3136362902223702835L;

			@Override
			protected Collection<Veicolo> getItemsList(int startIndex, int endIndex) {

				List<Veicolo> listaVeicolo = new ArrayList<Veicolo>();
				DashboardUI.getCurrentUser().getMappaVeicolo().values().forEach(v -> {
					int i = 0;
					while (i <= endIndex) {
						listaVeicolo.add(v);
					}
				});

				elencoVeicoliTable
						.aggiornaTable(new ArrayList<Veicolo>(DashboardUI.getCurrentUser().getMappaVeicolo().values()));
				return listaVeicolo;

			}

			@Override
			protected Component displayItem(int index, Veicolo item) {
				return new Label(item.toString());
			}
		});
	}

	/**
	 * @return
	 */
	@Override
	protected Button createButtonSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Component buildFilter() {
		return null;
	}

	/**
	 * 
	 * Aggiorna la table e la paginazione a seguito di un inserimento o una
	 * modifica
	 * 
	 */
	@Subscribe
	public void aggiornaTableMissione(final TableVeicoliUpdatedEvent event) {

		try {
			this.elencoVeicoliTable
					.aggiornaTable(new ArrayList<Veicolo>(DashboardUI.getCurrentUser().getMappaVeicolo().values()));
			updatePagination(new Long(DashboardUI.getCurrentUser().getMappaVeicolo().size()));
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}

}