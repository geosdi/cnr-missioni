package it.cnr.missioni.dashboard.component.window;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.AddUpdatePrenotazioneAction;
import it.cnr.missioni.dashboard.action.DeletePrenotazioneAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.StatoVeicoloEnum;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;

public class PrenotazioneWindow extends IWindow.AbstractWindow  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7733724385057534169L;

	public static final String ID = "prenotazionewindow";

	private final BeanFieldGroup<PrenotazioneEvent> fieldGroupPrenotazione;
	private FieldGroupFieldFactory fieldFactory;

	// FIELD PRENOTAZIONE
	private DateField dataFrom;
	private DateField dataTo;
	private ComboBox veicoliCNRField;
	private boolean isPresent = false;
	private boolean modifica;
	private Calendar calendarComponent;
	private PrenotazioneEvent prenotazione;

	private PrenotazioneWindow(final Calendar calendarComponent, final PrenotazioneEvent prenotazione,
			boolean modifica) {

		super();
		this.modifica = modifica;
		this.prenotazione = prenotazione;
		this.calendarComponent = calendarComponent;
		setId(ID);
		build();
		fieldGroupPrenotazione = new BeanFieldGroup<PrenotazioneEvent>(PrenotazioneEvent.class);
		buildFieldGroup();
		detailsWrapper.addComponent(buildPrenotazioneTab());

	}

	private void buildFieldGroup() {
		fieldGroupPrenotazione.setItemDataSource(prenotazione);
		fieldGroupPrenotazione.setBuffered(true);

		fieldFactory = new BeanFieldGrouFactory();
		fieldGroupPrenotazione.setFieldFactory(fieldFactory);
	}
	private Component buildPrenotazioneTab() {
		VerticalLayout root = new VerticalLayout();
		root.setCaption("Evento");
		root.setIcon(FontAwesome.CALENDAR_O);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);

		FormLayout details = new FormLayout();
		details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		root.addComponent(details);
		root.setExpandRatio(details, 1);

		dataFrom = (DateField) fieldGroupPrenotazione.buildAndBind("Inizio", "start");
		details.addComponent(dataFrom);
		dataTo = (DateField) fieldGroupPrenotazione.buildAndBind("Fine", "end");
		details.addComponent(dataTo);
		List<VeicoloCNR> lista = new ArrayList<VeicoloCNR>();
		veicoliCNRField = new ComboBox("Veicolo");
		try {
			lista = ClientConnector.getVeicoloCNR(
					VeicoloCNRSearchBuilder.getVeicoloCNRSearchBuilder().withStato(StatoVeicoloEnum.DISPONIBILE.name()))
					.getVeicoliCNR();
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		lista.forEach(v -> {
			veicoliCNRField.addItem(v.getId());
			veicoliCNRField.setItemCaption(v.getId(), v.getTipo() + " " + v.getTarga());
		});

		veicoliCNRField.setValidationVisible(false);
		veicoliCNRField.addBlurListener(new BlurListener() {

			@Override
			public void blur(BlurEvent event) {
				try {
					veicoliCNRField.validate();
				} catch (Exception e) {
					veicoliCNRField.setValidationVisible(true);
				}
			}

		});
		fieldGroupPrenotazione.bind(veicoliCNRField, "veicolo");
		details.addComponent(veicoliCNRField);

		CheckBox cb = new CheckBox("Tutto il giorno");
		cb.setImmediate(true);
		details.addComponent(cb);

		cb.addListener(new Listener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2906867985601976653L;

			@Override
			public void componentEvent(Event event) {
				boolean value = cb.getValue();
				if (value) {
					dataFrom.setResolution(Resolution.DAY);
					dataTo.setResolution(Resolution.DAY);
					dataFrom.setDateFormat("dd/MM/yyyy");
					dataTo.setDateFormat("dd/MM/yyyy");

				} else {
					dataFrom.setResolution(Resolution.MINUTE);
					dataTo.setResolution(Resolution.MINUTE);
					dataFrom.setDateFormat("dd/MM/yyyy HH:mm");
					dataTo.setDateFormat("dd/MM/yyyy HH:mm");

				}
			}

		});
		fieldGroupPrenotazione.bind(cb, "allDay");

		HorizontalLayout footer = new HorizontalLayout();
		root.addComponent(footer);
		Button ok = new Button("OK");
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				try {

					for (Field<?> f : fieldGroupPrenotazione.getFields()) {
						((AbstractField<?>) f).setValidationVisible(true);
					}
					fieldGroupPrenotazione.commit();
					// fieldGroupPrenotazione.isValid();

					BeanItem<PrenotazioneEvent> beanItem = (BeanItem<PrenotazioneEvent>) fieldGroupPrenotazione
							.getItemDataSource();
					PrenotazioneEvent prenotazioneEvent = beanItem.getBean();

					List<CalendarEvent> lista = calendarComponent.getEvents(prenotazioneEvent.getStart(),
							prenotazioneEvent.getEnd());

					lista.forEach(c -> {
						if (((PrenotazioneEvent) c).getVeicolo().equals(veicoliCNRField.getValue())) {
							Utility.getNotification(Utility.getMessage("error_message"),
									Utility.getMessage("event_present"), Type.ERROR_MESSAGE);
							PrenotazioneWindow.this.isPresent = true;
						}
					});
					if (!isPresent) {

						prenotazioneEvent
								.setDescrizione(veicoliCNRField.getItemCaption(prenotazioneEvent.getVeicolo()));
						DashboardEventBus.post(new AddUpdatePrenotazioneAction(prenotazioneEvent, modifica));
						close();
					}
				} catch (InvalidValueException | CommitException e) {
					Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("commit_failed"),
							Type.ERROR_MESSAGE);
				}

			}
		});
		ok.focus();


		Button delete = new Button("Elimina Prenotazione");
		delete.addStyleName(ValoTheme.BUTTON_PRIMARY);
		delete.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				DashboardEventBus.post(new DeletePrenotazioneAction(prenotazione ));
				close();

			}
		});

		footer.addComponents(ok,delete);
		footer.setSpacing(true);
		root.setComponentAlignment(footer, Alignment.MIDDLE_CENTER);
		return root;

	}

	public static void open(final Calendar calendarComponent, final PrenotazioneEvent prenotazione, boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new PrenotazioneWindow(calendarComponent, prenotazione, modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
