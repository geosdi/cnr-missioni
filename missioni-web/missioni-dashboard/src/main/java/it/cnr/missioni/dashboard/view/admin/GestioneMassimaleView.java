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
import it.cnr.missioni.dashboard.component.table.admin.ElencoMassimaleTable;
import it.cnr.missioni.dashboard.component.window.admin.MassimaleWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableMassimaleUpdatedEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.model.configuration.Massimale;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

/**
 * @author Salvia Vito
 */
public class GestioneMassimaleView extends GestioneTemplateView<Massimale> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9207649476781253454L;

	private ElencoMassimaleTable elencoMassimaleTable;
	private Massimale selectedMassimale;
	private MassimaleStore massimaleStore;
	private MassimaleSearchBuilder massimaleSearchBuilder;

	public GestioneMassimaleView() {
		super();
	}

	protected void inizialize() {
		this.massimaleSearchBuilder = MassimaleSearchBuilder.getMassimaleSearchBuilder();
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		try {
			massimaleStore = ClientConnector.getMassimale(massimaleSearchBuilder);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		this.elencoMassimaleTable = new ElencoMassimaleTable();
		this.elencoMassimaleTable.aggiornaTable(massimaleStore);
		this.elencoMassimaleTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6977010012114856216L;

			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedMassimale = (Massimale) itemClickEvent.getItemId();
				enableButtons();
			}
		});

		v.addComponent(this.elencoMassimaleTable);
		v.setComponentAlignment(elencoMassimaleTable,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		return v;
	}

	@Override
	public void enter(final ViewChangeEvent event) {

	}

	protected HorizontalLayout addActionButtons() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);

		buttonNew = buildButton("Aggiungi Massimale", "Inserisce un nuovo massimale", FontAwesome.PLUS);
		buttonNew.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6307336137515066491L;

			@Override
			public void buttonClick(ClickEvent event) {
				MassimaleWindow.open(new Massimale(), true,true,false);
			}

		});

		buttonModifica = buildButton("Modifica", "Modifica", FontAwesome.PENCIL);

		buttonModifica.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1519496930540936270L;

			@Override
			public void buttonClick(ClickEvent event) {
				MassimaleWindow.open(selectedMassimale, true,true,true);

			}

		});

		layout.addComponents(buttonNew, buttonModifica);

		disableButtons();

		return layout;

	}


	/**
	 * 
	 * Inizializzazione
	 * 
	 */
	@Override
	protected void initPagination() {
		buildPagination(massimaleStore != null ? massimaleStore.getTotale() : 0);

		addListenerPagination();
	}

	/**
	 * 
	 * Aggiunge il listener alla paginazione
	 * 
	 */
	protected void addListenerPagination() {

		pagingComponent.addListener(new LazyPagingComponentListener<Massimale>(itemsArea) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2495553081554726113L;

			@Override
			protected Collection<Massimale> getItemsList(int startIndex, int endIndex) {

				try {
					massimaleStore = ClientConnector.getMassimale(massimaleSearchBuilder.withFrom(startIndex));
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				elencoMassimaleTable.aggiornaTable(massimaleStore);
				return massimaleStore != null ? massimaleStore.getMassimale() : null;

			}

			@Override
			protected Component displayItem(int index, Massimale item) {
				return new Label(item.toString());
			}
		});
	}

	/**
	 * @return
	 */
	@Override
	protected Button createButtonSearch() {
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
	public void aggiornaTableMassimale(final TableMassimaleUpdatedEvent event) {

		try {
			this.massimaleStore = ClientConnector.getMassimale(this.massimaleSearchBuilder);
			elencoMassimaleTable.aggiornaTable(this.massimaleStore);
			buildPagination(massimaleStore.getTotale());
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