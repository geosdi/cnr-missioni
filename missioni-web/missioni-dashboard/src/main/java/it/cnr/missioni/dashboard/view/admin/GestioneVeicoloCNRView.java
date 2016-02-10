package it.cnr.missioni.dashboard.view.admin;

import java.util.Collection;

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

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.admin.ElencoVeicoliCNRTable;
import it.cnr.missioni.dashboard.component.window.admin.VeicoloCNRWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableVeicoliCNRUpdatedEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;
import it.cnr.missioni.rest.api.response.veicoloCNR.VeicoloCNRStore;

/**
 * @author Salvia Vito
 */
public class GestioneVeicoloCNRView extends GestioneTemplateView<VeicoloCNR> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 93612577398232810L;
	/**
	 * 
	 */
	private ElencoVeicoliCNRTable elencoVeicoliCNRTable;
	private VeicoloCNR selectedVeicoloCNR;
	private VeicoloCNRStore veicoloCNRStore;
	private VeicoloCNRSearchBuilder veicoloCNRSearchBuilder;

	public GestioneVeicoloCNRView() {
		super();
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoVeicoliCNRTable = new ElencoVeicoliCNRTable();
		veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder();

		try {
			veicoloCNRStore = ClientConnector.getVeicoloCNR(veicoloCNRSearchBuilder);
			this.elencoVeicoliCNRTable.aggiornaTable(veicoloCNRStore);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		this.elencoVeicoliCNRTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedVeicoloCNR = (VeicoloCNR) itemClickEvent.getItemId();
				enableDisableButtons(true);
			}
		});

		v.addComponent(this.elencoVeicoliCNRTable);
		v.setComponentAlignment(elencoVeicoliCNRTable,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		return v;
	}

	@Override
	public void enter(final ViewChangeEvent event) {

	}

	protected Button createButtonNew() {
		buttonNew = buildButton("Aggiungi Veicolo CNR", "Inserisce un nuovo veicolo CNR", FontAwesome.PLUS);
		buttonNew.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				VeicoloCNRWindow.open(new VeicoloCNR(), false);
			}

		});
		return buttonNew;
	}

	protected GridLayout addActionButtons() {
		GridLayout layout = new GridLayout(4, 1);
		layout.setSpacing(true);
		buttonModifica = buildButton("Modifica", "Modifica", FontAwesome.PENCIL);

		buttonModifica.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				VeicoloCNRWindow.open(selectedVeicoloCNR, true);

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

		if (veicoloCNRStore != null) {
			buildPagination(veicoloCNRStore.getTotale());
			addListenerPagination();
		}

	}

	/**
	 * 
	 * Aggiunge il listener alla paginazione
	 * 
	 */
	protected void addListenerPagination() {

		pagingComponent.addListener(new LazyPagingComponentListener<VeicoloCNR>(itemsArea) {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7027884787586196600L;

			@Override
			protected Collection<VeicoloCNR> getItemsList(int startIndex, int endIndex) {

				try {
					veicoloCNRStore = ClientConnector.getVeicoloCNR(veicoloCNRSearchBuilder.withFrom(startIndex));
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				elencoVeicoliCNRTable.aggiornaTable(veicoloCNRStore);
				return veicoloCNRStore != null ? veicoloCNRStore.getVeicoliCNR() : null;

			}

			@Override
			protected Component displayItem(int index, VeicoloCNR item) {
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
	public void aggiornaTableVeicoloCNR(final TableVeicoliCNRUpdatedEvent event) {

		try {
			this.veicoloCNRStore = ClientConnector.getVeicoloCNR(this.veicoloCNRSearchBuilder);
			elencoVeicoliCNRTable.aggiornaTable(this.veicoloCNRStore);
			updatePagination(veicoloCNRStore.getTotale());
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}

}