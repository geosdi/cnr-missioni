package it.cnr.missioni.dashboard.view.admin;

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
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.admin.ElencoNazioneTable;
import it.cnr.missioni.dashboard.component.table.admin.ElencoTipologiaSpesaTable;
import it.cnr.missioni.dashboard.component.window.admin.NazioneWindow;
import it.cnr.missioni.dashboard.component.window.admin.TipologiaSpesaWindow;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.dashboard.view.GestioneTemplateView;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.TipologiaSpesaSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.configuration.TipologiaSpesa;
import it.cnr.missioni.rest.api.response.nazione.NazioneStore;
import it.cnr.missioni.rest.api.response.tipologiaSpesa.TipologiaSpesaStore;

/**
 * @author Salvia Vito
 */
public class GestioneTipologiaSpesaView extends GestioneTemplateView implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1136332279224937769L;
	/**
	 * 
	 */
	private ElencoTipologiaSpesaTable elencoTipologiaSpesaTable;
	private Button buttonModifica;
	private TipologiaSpesa selectedTipologiaSpesa;
	private TipologiaSpesaStore tipologiaSpesaStore;

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

		try {
			tipologiaSpesaStore = ClientConnector.getTipologiaSpesa(TipologiaSpesaSearchBuilder.getTipologiaSpesaSearchBuilder());
			this.elencoTipologiaSpesaTable.aggiornaTable(tipologiaSpesaStore);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}
		this.elencoTipologiaSpesaTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
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
		final Button buttonNew = new Button("Aggiungi Tipologia Spesa");
		buttonNew.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonNew.setIcon(FontAwesome.PLUS);
		buttonNew.setDescription("Inserisce una nuova tipologia spesa");
		buttonNew.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		buttonNew.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				TipologiaSpesaWindow.open(new TipologiaSpesa(), false);
			}

		});
		return buttonNew;
	}

	protected GridLayout buildButtons() {
		GridLayout layout = new GridLayout(4, 1);
		layout.setSpacing(true);
		buttonModifica = new Button("Modifica");
		buttonModifica.setDescription("Modifica");
		buttonModifica.setIcon(FontAwesome.PENCIL);
		buttonModifica.setStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonModifica.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

		buttonModifica.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				TipologiaSpesaWindow.open(selectedTipologiaSpesa, false);

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

	protected Component buildFilter() {
		return null;
	}

}