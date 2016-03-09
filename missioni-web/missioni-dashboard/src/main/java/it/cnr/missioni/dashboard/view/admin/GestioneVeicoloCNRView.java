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
import com.vaadin.ui.HorizontalLayout;
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

	protected void inizialize() {
		veicoloCNRSearchBuilder = VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder();
	}
	
	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoVeicoliCNRTable = new ElencoVeicoliCNRTable();

		try {
			veicoloCNRStore = ClientConnector.getVeicoloCNR(veicoloCNRSearchBuilder);
			this.elencoVeicoliCNRTable.aggiornaTable(veicoloCNRStore);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		this.elencoVeicoliCNRTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1048174507029941634L;

			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedVeicoloCNR = (VeicoloCNR) itemClickEvent.getItemId();
				enableButtons();
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


	protected HorizontalLayout addActionButtons() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		buttonNew = buildButton("Aggiungi Veicolo CNR", "Inserisce un nuovo veicolo CNR", FontAwesome.PLUS);
		buttonNew.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6878805730356757023L;

			@Override
			public void buttonClick(ClickEvent event) {
				VeicoloCNRWindow.open(new VeicoloCNR(), true,true,false);
			}

		});
		
		buttonModifica = buildButton("Modifica", "Modifica", FontAwesome.PENCIL);

		buttonModifica.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -3239582768973595404L;

			@Override
			public void buttonClick(ClickEvent event) {
				VeicoloCNRWindow.open(selectedVeicoloCNR, true,true,true);

			}

		});

		layout.addComponents(buttonNew,buttonModifica);

		disableButtons();

		return layout;

	}


	/**
	 * 
	 */
	@Override
	protected void initPagination() {
		buildPagination(veicoloCNRStore != null ? veicoloCNRStore.getTotale() : 0);
		addListenerPagination();
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
			buildPagination(veicoloCNRStore.getTotale());
			addListenerPagination();	
			disableButtons();

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}

	/**
	 * 
	 */
	@Override
	protected void enableButtons() {
		this.buttonModifica.setEnabled(true);
		
	}

	/**
	 * 
	 */
	@Override
	protected void disableButtons() {
		this.buttonModifica.setEnabled(false);
		
	}

}