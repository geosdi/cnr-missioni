package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.admin.NazioneAction;
import it.cnr.missioni.dashboard.component.form.nazione.NazioneForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.Nazione;

public class NazioneWindow extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6599225757081293003L;

	public static final String ID = "nazionewindow";

	private NazioneForm nazioneForm;

	private  Nazione nazione;

	private NazioneWindow(final Nazione nazione,final boolean isAdmin,final boolean enabled, final boolean modifica) {

		super(isAdmin,enabled,modifica);
		this.nazione = nazione;
		setId(ID);
		build();
		detailsWrapper.addComponent(buildTab());
		content.addComponent(buildFooter());

	}

    protected HorizontalLayout buildTab(){
        this.nazioneForm = new NazioneForm(nazione,true,true,modifica);
        HorizontalLayout tab = super.buildTab("Nazione",FontAwesome.GLOBE,this.nazioneForm);
        return tab;
    }
	
	protected Component buildFooter() {
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6503857843364882408L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {

					nazione = nazioneForm.validate();

					DashboardEventBus.post(new NazioneAction(nazione, modifica));
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

	public static void open(final Nazione nazione,final boolean isAdmin,final boolean enabled,final  boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new NazioneWindow(nazione,isAdmin,enabled, modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
