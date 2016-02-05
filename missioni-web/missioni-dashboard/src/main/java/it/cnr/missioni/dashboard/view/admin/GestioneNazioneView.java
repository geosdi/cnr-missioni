package it.cnr.missioni.dashboard.view.admin;

import java.util.Collection;

import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

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
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.admin.ElencoNazioneTable;
import it.cnr.missioni.dashboard.component.window.admin.NazioneWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;

/**
 * @author Salvia Vito
 */
public class GestioneNazioneView extends GestioneTemplateView<Nazione> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8210899238444116295L;
	private ElencoNazioneTable elencoNazioneTable;
	private Nazione selectedNazione;
	private NazioneStore nazioneStore;
	private NazioneSearchBuilder nazioneSearchBuilder;

	public GestioneNazioneView() {
		super();
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoNazioneTable = new ElencoNazioneTable();
		this.nazioneSearchBuilder = NazioneSearchBuilder.getNazioneSearchBuilder();

		try {
			nazioneStore = ClientConnector.getNazione(nazioneSearchBuilder);
			this.elencoNazioneTable.aggiornaTable(nazioneStore);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		this.elencoNazioneTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5989135363149394894L;

			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedNazione = (Nazione) itemClickEvent.getItemId();
				enableDisableButtons(true);
			}
		});

		v.addComponent(this.elencoNazioneTable);
		v.setComponentAlignment(elencoNazioneTable,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		return v;
	}

	@Override
	public void enter(final ViewChangeEvent event) {

	}

	protected Button createButtonNew() {
		buttonNew = buildButton("Aggiungi Nazione", "Inserisce una nuova nazione",FontAwesome.PLUS);
		buttonNew.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -9055323128504245147L;

			@Override
			public void buttonClick(ClickEvent event) {
				NazioneWindow.open(new Nazione(), false);
			}

		});
		return buttonNew;
	}

	protected GridLayout addActionButtons() {
		GridLayout layout = new GridLayout(4, 1);
		layout.setSpacing(true);
		buttonModifica = buildButton("Modifica", "Modifica",FontAwesome.PENCIL);

		buttonModifica.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2128352915934713836L;

			@Override
			public void buttonClick(ClickEvent event) {
				NazioneWindow.open(selectedNazione, true);

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

		if (nazioneStore != null)
			buildPagination(nazioneStore.getTotale());
		addListenerPagination();
	}

	/**
	 * 
	 * Aggiunge il listener alla paginazione
	 * 
	 */
	protected void addListenerPagination() {

		pagingComponent.addListener(new LazyPagingComponentListener<Nazione>(itemsArea) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4769881945185530673L;

			@Override
			protected Collection<Nazione> getItemsList(int startIndex, int endIndex) {

				try {
					nazioneStore = ClientConnector.getNazione(nazioneSearchBuilder.withFrom(startIndex));
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}
				DashboardEventBus.post(new DashboardEvent.TableNazioneUpdatedEvent(nazioneStore));
				return nazioneStore != null ? nazioneStore.getNazione() : null;

			}

			@Override
			protected Component displayItem(int index, Nazione item) {
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

}