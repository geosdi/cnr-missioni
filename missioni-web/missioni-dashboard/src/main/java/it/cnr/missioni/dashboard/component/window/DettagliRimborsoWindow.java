package it.cnr.missioni.dashboard.component.window;

import java.util.ArrayList;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.table.ElencoFattureTable;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.rimborso.Fattura;

public class DettagliRimborsoWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4269186437699906289L;

	/**
	 * 
	 */

	public static final String ID = "rimborsowindow";

	private TabSheet detailsWrapper;

	private Missione missione;

	private DettagliRimborsoWindow(Missione missione) {

		this.missione = missione;

		addStyleName("profile-window");
		setId(ID);
		Responsive.makeResponsive(this);

		setModal(true);
		setCloseShortcut(KeyCode.ESCAPE, null);
		setResizable(false);
		setClosable(true);
		setHeight(90.0f, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setMargin(new MarginInfo(true, false, false, false));
		setContent(content);

		detailsWrapper = new TabSheet();
		detailsWrapper.setSizeFull();
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
		detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
		content.addComponent(detailsWrapper);
		content.setExpandRatio(detailsWrapper, 1f);

		detailsWrapper.addComponent(buildRimborsoTab());
		detailsWrapper.addComponent(buildFatturaTab());

	}

	private Component buildRimborsoTab() {
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Rimborso");
		root.setIcon(FontAwesome.MONEY);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		details.addComponent(new Label("Numero Ordine: " + missione.getRimborso().getNumeroOrdine()));
		details.addComponent(new Label("Avviso Pagamento : " + missione.getRimborso().getAvvisoPagamento()));
		details.addComponent(
				new Label("Anticipazione Pagamento : " + missione.getRimborso().getAnticipazionePagamento()));
		details.addComponent(new Label("Data Inserimento : " + missione.getRimborso().getDataRimborso()));
		details.addComponent(new Label("Data Ultima Modifica : " + missione.getRimborso().getDateLastModified()));
		return root;

	}

	private Component buildFatturaTab() {

		VerticalLayout root = new VerticalLayout();

		root.setCaption("Fattura");
		root.setIcon(FontAwesome.BARCODE);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		ElencoFattureTable elencoFattureTable = new ElencoFattureTable(missione);
		elencoFattureTable.aggiornaTable(new ArrayList<Fattura>(missione.getRimborso().getMappaFattura().values()));
		elencoFattureTable.aggiornaTotale(missione.getRimborso().getTotale());
		root.addComponent(elencoFattureTable);
		return root;
	}

	public static void open(Missione missione) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new DettagliRimborsoWindow(missione);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
