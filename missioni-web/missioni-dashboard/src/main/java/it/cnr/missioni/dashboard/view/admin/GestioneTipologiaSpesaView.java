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
import it.cnr.missioni.dashboard.component.table.admin.ElencoTipologiaSpesaTable;
import it.cnr.missioni.dashboard.component.window.admin.TipologiaSpesaWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableTipologiaSpesaUpdatedEvent;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * @author Salvia Vito
 */
public class GestioneTipologiaSpesaView extends GestioneTemplateView<TipologiaSpesa> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1136332279224937769L;
	/**
	 * 
	 */
	private ElencoTipologiaSpesaTable elencoTipologiaSpesaTable;
	private TipologiaSpesa selectedTipologiaSpesa;
	private TipologiaSpesaStore tipologiaSpesaStore;
	private TipologiaSpesaSearchBuilder tipologiaSpesaSearchBuilder;

	public GestioneTipologiaSpesaView() {
		super();
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoTipologiaSpesaTable = new ElencoTipologiaSpesaTable();
		this.tipologiaSpesaSearchBuilder = TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder();

		try {
			tipologiaSpesaStore = ClientConnector.getTipologiaSpesa(tipologiaSpesaSearchBuilder);
			this.elencoTipologiaSpesaTable.aggiornaTable(tipologiaSpesaStore);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		this.elencoTipologiaSpesaTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7822436082056338124L;

			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedTipologiaSpesa = (TipologiaSpesa) itemClickEvent.getItemId();
				enableDisableButtons(true);
			}
		});

		v.addComponent(this.elencoTipologiaSpesaTable);
		v.setComponentAlignment(elencoTipologiaSpesaTable,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		return v;
	}

	@Override
	public void enter(final ViewChangeEvent event) {

	}

	protected Button createButtonNew() {
		buttonNew = buildButton("Aggiungi Tipologia Spesa", "Inserisce una nuova tipologia spesa", FontAwesome.PLUS);
		buttonNew.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6191290822465375120L;

			@Override
			public void buttonClick(ClickEvent event) {
				TipologiaSpesaWindow.open(new TipologiaSpesa(), false);
			}

		});
		return buttonNew;
	}

	protected GridLayout addActionButtons() {
		GridLayout layout = new GridLayout(4, 1);
		layout.setSpacing(true);
		buttonModifica = buildButton("Modifica", "Modifica", FontAwesome.PENCIL);

		buttonModifica.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8375255955679741739L;

			@Override
			public void buttonClick(ClickEvent event) {
				TipologiaSpesaWindow.open(selectedTipologiaSpesa, true);

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
		buildPagination(tipologiaSpesaStore != null ? tipologiaSpesaStore.getTotale() : 0);
		addListenerPagination();

	}

	/**
	 * 
	 * Aggiunge il listener alla paginazione
	 * 
	 */
	protected void addListenerPagination() {

		pagingComponent.addListener(new LazyPagingComponentListener<TipologiaSpesa>(itemsArea) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3631013947031172499L;

			@Override
			protected Collection<TipologiaSpesa> getItemsList(int startIndex, int endIndex) {

				try {
					tipologiaSpesaStore = ClientConnector
							.getTipologiaSpesa(tipologiaSpesaSearchBuilder.withFrom(startIndex));
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				elencoTipologiaSpesaTable.aggiornaTable(tipologiaSpesaStore);
				return tipologiaSpesaStore != null ? tipologiaSpesaStore.getTipologiaSpesa() : null;

			}

			@Override
			protected Component displayItem(int index, TipologiaSpesa item) {
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
	public void aggiornaTableTipologiaSpesa(final TableTipologiaSpesaUpdatedEvent event) {

		try {
			this.tipologiaSpesaStore = ClientConnector.getTipologiaSpesa(this.tipologiaSpesaSearchBuilder);
			elencoTipologiaSpesaTable.aggiornaTable(this.tipologiaSpesaStore);
			updatePagination(tipologiaSpesaStore.getTotale());
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

	}

}