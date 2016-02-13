package it.cnr.missioni.dashboard.component.window.admin;

import org.joda.time.Days;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.action.admin.UpdateRimborsoAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.tab.rimborso.DatiGeneraliTabLayout;
import it.cnr.missioni.dashboard.component.tab.rimborso.FatturaTabLayout;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MassimaleSearchBuilder;
import it.cnr.missioni.el.model.search.builder.NazioneSearchBuilder;
import it.cnr.missioni.model.configuration.Nazione;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.missione.TrattamentoMissioneEsteraEnum;
import it.cnr.missioni.model.rimborso.Rimborso;
import it.cnr.missioni.rest.api.response.massimale.MassimaleStore;

public class RimborsoWindowAdmin extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8551406658251796691L;

	public static final String ID = "rimborsoadminwindow";


	private Rimborso rimborso;
	private Missione missione;
	private boolean enabled;
	
	private DatiGeneraliTabLayout datiGeneraliTab;
	private FatturaTabLayout fatturaTab;

	private RimborsoWindowAdmin(final Missione missione,boolean enabled) {
		super();
		this.missione = missione;
		this.rimborso = missione.getRimborso();
		this.enabled = enabled;

		addStyleName("profile-window");
		setId(ID);
		Responsive.makeResponsive(this);

		build();
		buildTabs();
		if (enabled)
			content.addComponent(buildFooter());
	}

	private void buildTabs() {
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
		
		
		int days = 0;
		
		if (missione.getDatiMissioneEstera()
				.getTrattamentoMissioneEsteraEnum() == TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO) {

			try {
				Nazione nazione = ClientConnector
						.getNazione(NazioneSearchBuilder.getNazioneSearchBuilder().withId(missione.getIdNazione()))
						.getNazione().get(0);
				MassimaleStore massimaleStore;
				massimaleStore = ClientConnector
						.getMassimale(MassimaleSearchBuilder.getMassimaleSearchBuilder()
								.withLivello(DashboardUI.getCurrentUser().getDatiCNR().getLivello().name())
								.withAreaGeografica(nazione.getAreaGeografica().name()).withTipo(TrattamentoMissioneEsteraEnum.TRATTAMENTO_ALTERNATIVO.name()));
				if (massimaleStore != null) {
					rimborso.calcolaTotaleTAM(massimaleStore.getMassimale().get(0),
							missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata(),
							missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno());
				}
			} catch (Exception e) {
				Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
						Type.ERROR_MESSAGE);
			}


				
			days = Days.daysBetween(missione.getDatiMissioneEstera().getAttraversamentoFrontieraAndata(), missione.getDatiMissioneEstera().getAttraversamentoFrontieraRitorno())
						.getDays();

			}

	
		
		this.datiGeneraliTab = new DatiGeneraliTabLayout(missione.getRimborso(), days, missione.isMezzoProprio(),enabled);
		root.addComponent(datiGeneraliTab);
		root.setExpandRatio(datiGeneraliTab, 1);


		HorizontalLayout footer = new HorizontalLayout();
		root.addComponent(footer);
		root.setComponentAlignment(footer, Alignment.BOTTOM_RIGHT);
		return root;
	}



	private Component buildFatturaTab() {


		VerticalLayout root = new VerticalLayout();
		
		fatturaTab = new FatturaTabLayout(this.missione,this.enabled);
		root.addComponent(fatturaTab);

		root.setCaption("Fattura");
		root.setIcon(FontAwesome.BARCODE);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		return root;
	}

	private Component buildFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth(100.0f, Unit.PERCENTAGE);

		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 127413665855903412L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {

					for (Field<?> f : datiGeneraliTab.getFieldGroup().getFields()) {
						((AbstractField<?>) f).setValidationVisible(true);
					}
					datiGeneraliTab.getFieldGroup().commit();

					BeanItem<Rimborso> beanItem = (BeanItem<Rimborso>) datiGeneraliTab.getFieldGroup().getItemDataSource();
					Rimborso new_rimborso = beanItem.getBean();
					missione.setRimborso(new_rimborso);
					DashboardEventBus.post(new UpdateRimborsoAction(missione));
					close();

				} catch (InvalidValueException | CommitException e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();
		footer.addComponent(ok);
		footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
		return footer;
	}

	public static void open(final Missione missione,boolean enabled) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new RimborsoWindowAdmin(missione,enabled);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
