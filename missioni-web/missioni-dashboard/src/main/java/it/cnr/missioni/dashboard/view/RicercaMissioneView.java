package it.cnr.missioni.dashboard.view;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.model.missione.StatoEnum;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

/**
 * @author Salvia Vito
 */
@SuppressWarnings("serial")
public class RicercaMissioneView extends VerticalLayout implements View {

	private ElencoMissioniTable elencoMissioniTable;
	private ComboBox selectPage = new ComboBox();
	private TextField idMissioneField;
	private DateField dataFromInserimentoMissioneField;
	private DateField dataToInserimentoMissioneField;
	private User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());


	public RicercaMissioneView() {

		setSizeFull();

		// Component componentForm =
		// DashboardUI.createContentWrapper(buildForm());
		Component componentForm = createContentWrapper(buildForm());
		componentForm.setWidth("40%");
		addComponent(componentForm);

		Component componentTable = createContentWrapper(buildTable());
		componentTable.setWidth("90%");
		addComponent(componentTable);
		setExpandRatio(componentTable, 1);
		setComponentAlignment(componentForm,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		setComponentAlignment(componentTable,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
	}

	/**
	 * 
	 * @return VerticalLayout
	 */
	private VerticalLayout buildTable() {
		VerticalLayout v = new VerticalLayout();

		User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
		MissioneSearchBuilder missioneSearchBuilder = new MissioneSearchBuilder();
		missioneSearchBuilder.setIdUser(user.getId());
		elencoMissioniTable = new ElencoMissioniTable();

		try {
			MissioniStore missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
			elencoMissioniTable.aggiornaTable(missioniStore);

			if (missioniStore != null) {
				elencoMissioniTable.aggiornaTable(missioniStore);
				buildComboPage();
			}

		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		HorizontalLayout h = new HorizontalLayout();
		h.addComponent(selectPage);

		h.setMargin(true);
		v.addComponent(elencoMissioniTable);
		v.addComponent(h);
		v.setComponentAlignment(elencoMissioniTable,
				new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
		v.setComponentAlignment(h, new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));

		return v;
	}

	/**
	 * 
	 * Costruisce la form di ricerca
	 * 
	 * @return HorizontalLayout
	 */
	private HorizontalLayout buildForm() {
		HorizontalLayout v = new HorizontalLayout();
		v.setMargin(true);
		FormLayout form = new FormLayout();
		form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		form.setSizeUndefined();
		v.setWidth("98%");

		// List<Comune> listaComune = (List<Comune>)
		// VaadinSession.getCurrent().getAttribute("listaComune");

		// List<Comune> listaComune = ((ElencoComuniRequest)
		// WebClientSingleton.getRequest(CostantiRequest.ELENCO_COMUNI_REQUEST))
		// .getListaComuni();

		// final ComboBox comuneSelect = new ComboBox();
		final ComboBox statoSelect = new ComboBox();
		// final ComboBox profiloSelect = new ComboBox();

		// comuneSelect.setCaption("Comune");
		statoSelect.setCaption("Stato");
		// profiloSelect.setCaption("Profilo");

		// listaComune.forEach(c->{
		// comuneSelect.addItem(c.getCodIstat());
		// comuneSelect.setItemCaption(c.getCodIstat(), c.getNome());
		// });

		// Map<String, StatoEnum> mappaP = ProfiloEnum.getMappa();
		//
		// mappaP.values().forEach(p-> {
		// profiloSelect.addItem(p.getValore());
		// profiloSelect.setItemCaption(p.getValore(), p.getLabel());
		// });

		Map<String, StatoEnum> mappaS = StatoEnum.getMappa();

		mappaS.values().forEach(s -> {
			statoSelect.addItem(s);
			statoSelect.setItemCaption(s, s.getStato());
		});

		idMissioneField = new TextField();
		idMissioneField.setCaption("Id Missione");
		dataFromInserimentoMissioneField = new DateField("Data dal");
		dataToInserimentoMissioneField = new DateField("Data al");
		dataToInserimentoMissioneField.setImmediate(true);
		dataFromInserimentoMissioneField.setValidationVisible(false);
		dataToInserimentoMissioneField.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				Date v = (Date) value;
				if (v != null && v.before(dataFromInserimentoMissioneField.getValue()))
					throw new InvalidValueException(Utility.getMessage("data_range_error"));

			}

		});
		dataToInserimentoMissioneField.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				try {
					dataFromInserimentoMissioneField.validate();
				} catch (Exception e) {
					dataFromInserimentoMissioneField.setValidationVisible(true);
				}

			}
		});

		// form.addComponent(comuneSelect);
		form.addComponent(statoSelect);
		// form.addComponent(profiloSelect);
		form.addComponent(idMissioneField);
		form.addComponent(dataFromInserimentoMissioneField);
		form.addComponent(dataToInserimentoMissioneField);
		final Button buttonCerca = new Button("Cerca");
		buttonCerca.setEnabled(true);
		buttonCerca.addStyleName(ValoTheme.BUTTON_PRIMARY);

		buttonCerca.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

				MissioneSearchBuilder missioneSearchBuilder = new MissioneSearchBuilder();
				missioneSearchBuilder.setIdUser(user.getId());
				//se il valore della form è null JODATIME ritorna comunque la data odierna
				if(dataFromInserimentoMissioneField.getValue() != null)
					missioneSearchBuilder.setFromDataInserimento(new DateTime(dataFromInserimentoMissioneField.getValue()));
				if(dataToInserimentoMissioneField.getValue() != null)
					missioneSearchBuilder.setToDataInserimento(new DateTime(dataToInserimentoMissioneField.getValue()));
				missioneSearchBuilder.setIdMissione(idMissioneField.getValue());
				
				missioneSearchBuilder.setStato(((StatoEnum)statoSelect.getValue()).name());
				try {
					MissioniStore missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
					elencoMissioniTable.aggiornaTable(missioniStore);
				} catch (Exception e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
							Type.ERROR_MESSAGE);
				}

				// NeetRequest request = (NeetRequest)
				// WebClientSingleton.getRequest(CostantiRequest.NEET_REQUEST);
				//
				// codIstat = comuneSelect.getValue() != null ?
				// comuneSelect.getValue().toString() : "";
				// stato = statoSelect.getValue() == null ? "" :
				// statoSelect.getValue().toString();
				// profilo = profiloSelect.getValue() != null ?
				// profiloSelect.getValue().toString() : "";
				// cognome = textFielCognome.getValue();
				// RicercaMissioneView.this.neetWrapper =
				// request.getListaNeetByCriteria(0, size, codIstat,
				// textFielCognome.getValue().toString(), stato, profilo);
				// table.aggiornaTable(neetWrapper.getListaNeet(),
				// neetWrapper.getTotale());
				// buildComboPage();
			}
		});

		form.addComponent(buttonCerca);
		v.addComponent(form);
		v.setComponentAlignment(form, new Alignment(Bits.ALIGNMENT_HORIZONTAL_CENTER));
		v.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		return v;
	}

	/**
	 * Costruisce la Select per la paginazione
	 */
	private void buildComboPage() {
		// long totNeet = neetWrapper.getTotale();
		// long totPage = totNeet % size == 0 ? totNeet / size : 1+(totNeet /
		// size);
		// this.selectPage.removeAllItems();
		// this.selectPage.setValue(1);
		// // selectPage.setNullSelectionAllowed(false);
		// this.selectPage.setImmediate(true);
		// this.selectPage.setCaption("Seleziona Pagina");
		// for (int j = 1; j <= totPage; j++) {
		// this.selectPage.addItem(j);
		// this.selectPage.setItemCaption(j, Integer.toString(j));
		// }

		this.selectPage.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				// int nextPage =
				// Integer.parseInt(String.valueOf(event.getProperty().getValue()));
				// int from = (nextPage - 1) * 100;
				// neetWrapper = ((NeetRequest)
				// WebClientSingleton.getRequest(CostantiRequest.NEET_REQUEST))
				// .getListaNeetByCriteria(from, size, codIstat, cognome, stato,
				// profilo);
				// table.aggiornaTable(neetWrapper.getListaNeet(),
				// neetWrapper.getTotale());
			}
		});

	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}
	
	private Component createContentWrapper(final Component content) {
		final CssLayout slot = new CssLayout();
		slot.setWidth("100%");
		slot.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");

		Label caption = new Label(content.getCaption());
		caption.addStyleName(ValoTheme.LABEL_H4);
		caption.addStyleName(ValoTheme.LABEL_COLORED);
		caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		content.setCaption(null);

//		MenuBar tools = new MenuBar();
//		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
//		MenuItem root = tools.addItem("", FontAwesome.COG, null);
//		root.addItem("Configure", new Command() {
//			@Override
//			public void menuSelected(final MenuItem selectedItem) {
//				Notification.show("Not implemented in this demo");
//			}
//		});
//		root.addSeparator();
//		root.addItem("Close", new Command() {
//			@Override
//			public void menuSelected(final MenuItem selectedItem) {
//				Notification.show("Not implemented in this demo");
//			}
//		});

		toolbar.addComponent(caption);
		toolbar.setExpandRatio(caption, 1);
		toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, content);
		slot.addComponent(card);
		return slot;
	}
	
	
}