package it.cnr.missioni.dashboard.component.form.prenotazione;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.Component.Listener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.AddUpdatePrenotazioneAction;
import it.cnr.missioni.dashboard.action.DeletePrenotazioneAction;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.component.form.IForm;
import it.cnr.missioni.dashboard.component.window.PrenotazioneWindow;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.VeicoloCNRSearchBuilder;
import it.cnr.missioni.model.prenotazione.StatoVeicoloEnum;
import it.cnr.missioni.model.prenotazione.VeicoloCNR;

/**
 * @author Salvia Vito
 */
public class PrenotazioneForm extends IForm.FormAbstract<PrenotazioneEvent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5193133236078976093L;
	private DateField dataFrom;
	private DateField dataTo;
	private ComboBox veicoliCNRField;
	private boolean isPresent = false;
	private Calendar calendarComponent;
	private CheckBox allDay;

	public PrenotazioneForm(Calendar calendarComponent, PrenotazioneEvent prenotaioneEvent, boolean isAdmin,
			boolean enabled, boolean modifica) {
		super(prenotaioneEvent, isAdmin, enabled, modifica);
		this.calendarComponent = calendarComponent;
		setFieldGroup(new BeanFieldGroup<PrenotazioneEvent>(PrenotazioneEvent.class));

		buildFieldGroup();
		buildTab();

	}

	public void buildTab() {

		addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		dataFrom = (DateField) getFieldGroup().buildAndBind("Inizio", "start");
		addComponent(dataFrom);
		dataTo = (DateField) getFieldGroup().buildAndBind("Fine", "end");
		addComponent(dataTo);
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

		getFieldGroup().bind(veicoliCNRField, "veicolo");
		addComponent(veicoliCNRField);

		allDay = new CheckBox("Tutto il giorno");
		allDay.setImmediate(true);
		addComponent(allDay);

		getFieldGroup().bind(allDay, "allDay");
		setAllDayResolution(allDay.getValue());
		addListener();
	}

	public PrenotazioneEvent validate() throws CommitException, InvalidValueException {
		for (Field<?> f : getFieldGroup().getFields()) {
			((AbstractField<?>) f).setValidationVisible(true);
		}
		getFieldGroup().commit();

		BeanItem<PrenotazioneEvent> beanItem = (BeanItem<PrenotazioneEvent>) getFieldGroup().getItemDataSource();
		PrenotazioneEvent prenotazione = beanItem.getBean();

		List<CalendarEvent> lista = calendarComponent.getEvents(prenotazione.getStart(), prenotazione.getEnd());
		prenotazione.setDescrizione(veicoliCNRField.getItemCaption(prenotazione.getVeicolo()));
		lista.forEach(c -> {
			if (((PrenotazioneEvent) c).getVeicolo().equals(veicoliCNRField.getValue()) && !((PrenotazioneEvent) c).getId().equals(bean.getId()) ) {
				Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("event_present"),
						Type.ERROR_MESSAGE);
				isPresent = true;
			}
		});
		if (!isPresent)
			return prenotazione;
		return null;
	}

	/**
	 * 
	 */
	@Override
	public void addValidator() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	@Override
	public void addListener() {
		
		veicoliCNRField.addBlurListener(new BlurListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2442322875270937559L;

			@Override
			public void blur(BlurEvent event) {
				try {
					veicoliCNRField.validate();
				} catch (Exception e) {
					veicoliCNRField.setValidationVisible(true);
				}
			}

		});
		
		allDay.addListener(new Listener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2906867985601976653L;

			@Override
			public void componentEvent(Event event) {
				boolean value = allDay.getValue();
				setAllDayResolution(value);
			}

		});
	}
	
	private void setAllDayResolution(boolean allDay){
		if (allDay) {
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

}
