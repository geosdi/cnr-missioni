package it.cnr.missioni.dashboard.component.window;

import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.action.AddUpdatePrenotazioneAction;
import it.cnr.missioni.dashboard.action.DeletePrenotazioneAction;
import it.cnr.missioni.dashboard.component.calendar.PrenotazioneEvent;
import it.cnr.missioni.dashboard.component.form.prenotazione.PrenotazioneForm;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.RuoloUserEnum;

public class PrenotazioneWindow extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7733724385057534169L;

	public static final String ID = "prenotazionewindow";

	// FIELD PRENOTAZIONE
	private PrenotazioneEvent prenotazione;
	private PrenotazioneForm prenotazioneForm;
	private final Calendar calendarComponent;

	private PrenotazioneWindow(Calendar calendarComponent, final PrenotazioneEvent prenotazione,boolean isAdmin,boolean enabled, boolean modifica) {

		super(isAdmin,enabled,modifica);
		build();
		this.calendarComponent = calendarComponent;
		this.prenotazione = prenotazione;
		setId(ID);
		// solo se la prenotazione è dell'user loggato o è admin
		if(modifica)
			enabled = DashboardUI.getCurrentUser().getCredenziali().getRuoloUtente() == RuoloUserEnum.UTENTE_ADMIN
				|| prenotazione.getIdUser().equals(DashboardUI.getCurrentUser().getId());
		
		this.prenotazioneForm = new PrenotazioneForm(calendarComponent, prenotazione, false, enabled, modifica);
		detailsWrapper.addComponent(buildTab("Prenotazione", FontAwesome.CALENDAR_O, this.prenotazioneForm));

		if (enabled)
			content.addComponent(buildFooter());

	}

	protected Component buildFooter() {
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8098718471864221266L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {

					PrenotazioneEvent prenotazioneEvent = prenotazioneForm.validate();
					if (prenotazioneEvent != null) {
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
			/**
			 * 
			 */
			private static final long serialVersionUID = -1522725903867568549L;

			@Override
			public void buttonClick(ClickEvent event) {

				DashboardEventBus.post(new DeletePrenotazioneAction(prenotazione));
				close();

			}
		});

		footer.addComponent(ok);

		footer.setSpacing(true);
		footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
		if (modifica){
			footer.addComponent(delete);
//			footer.setComponentAlignment(delete, Alignment.TOP_RIGHT);
		}
		return footer;
	}

	public static void open(final Calendar calendarComponent, final PrenotazioneEvent prenotazione,final boolean isAdmin,final boolean enabled, final boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new PrenotazioneWindow(calendarComponent, prenotazione, isAdmin,enabled,modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
