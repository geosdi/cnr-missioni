package it.cnr.missioni.dashboard.view.admin;

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
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.admin.ElencoRimborsoKmTable;
import it.cnr.missioni.dashboard.component.window.admin.RimborsoKmWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.DisableButtonNewEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.RimborsoKmSearchBuilder;
import it.cnr.missioni.model.configuration.RimborsoKm;
import it.cnr.missioni.rest.api.response.rimborsoKm.RimborsoKmStore;

/**
 * @author Salvia Vito
 */
public class GestioneRimborsoKmView extends GestioneTemplateView<RimborsoKm>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8210899238444116295L;
	private ElencoRimborsoKmTable elencoRimborsoKmTable;
	private RimborsoKm selectedRimborsoKm;
	private RimborsoKmStore rimborsoKmStore;

	public GestioneRimborsoKmView() {
		super();
		DashboardEventBus.register(this);
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	protected VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		this.elencoRimborsoKmTable = new ElencoRimborsoKmTable();

		try {
			rimborsoKmStore = ClientConnector.getRimborsoKm(RimborsoKmSearchBuilder.getRimborsoKmSearchBuilder());

			if (rimborsoKmStore == null)
				buttonNew.setVisible(true);
			else
				buttonNew.setVisible(false);
			this.elencoRimborsoKmTable.aggiornaTable(rimborsoKmStore);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		this.elencoRimborsoKmTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent itemClickEvent) {
				selectedRimborsoKm = (RimborsoKm) itemClickEvent.getItemId();
				enableDisableButtons(true);
			}
		});

		v.addComponent(this.elencoRimborsoKmTable);
		v.setComponentAlignment(elencoRimborsoKmTable,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		return v;
	}

	@Override
	public void enter(final ViewChangeEvent event) {

	}

	protected Button createButtonNew() {
		buttonNew = buildButton("Aggiungi Rimborso Km", "Inserisce un nuovo rimborso km",FontAwesome.PLUS);
		this.buttonNew.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				RimborsoKmWindow.open(new RimborsoKm(), false);
			}

		});
		return buttonNew;
	}

	protected GridLayout addActionButtons() {
		GridLayout layout = new GridLayout(4, 1);
		layout.setSpacing(true);
		buttonModifica = buildButton("Modifica", "Modifica",FontAwesome.PENCIL);

		buttonModifica.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				RimborsoKmWindow.open(selectedRimborsoKm, true);

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
		buildPagination(rimborsoKmStore.getTotale());

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
	 * Disabilita il button al primo inserimento del rimborso km
	 * 
	 * @param disableButtonNew
	 */
	@Subscribe
	public void disabledButtonNew(final DisableButtonNewEvent disableButtonNew) {
		this.buttonNew.setVisible(false);
	}

	/**
	 * 
	 */
	@Override
	protected void addListenerPagination() {
		// TODO Auto-generated method stub
		
	}

}