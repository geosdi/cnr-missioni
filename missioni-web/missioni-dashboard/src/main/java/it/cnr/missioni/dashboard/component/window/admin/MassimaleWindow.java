package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.admin.MassimaleAction;
import it.cnr.missioni.dashboard.component.form.massimale.MassimaleForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.Massimale;

public class MassimaleWindow extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5414281952989114531L;

	public static final String ID = "massimalewindow";
	private MassimaleForm massimaleForm;
	private final Massimale massimale;

	private MassimaleWindow(final Massimale massimale,final boolean isAdmin,final boolean enabled,final boolean modifica) {

		super(isAdmin,enabled,modifica);
		this.massimale = massimale;
		setId(ID);
		build();
		detailsWrapper.addComponent(buildTab());
		content.addComponent(buildFooter());
	}

	protected HorizontalLayout buildTab(){
		this.massimaleForm = new MassimaleForm(this.massimale,true,true,modifica);
        HorizontalLayout tab = super.buildTab("Massimale",FontAwesome.EURO,this.massimaleForm);
        return tab;
    }

	protected Component buildFooter() {

		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8838118439172249530L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					Massimale massimale = massimaleForm.validate();

					DashboardEventBus.post(new MassimaleAction(massimale, modifica));
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

	public static void open(final Massimale massimale,final boolean isAdmin,final boolean enabled, boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new MassimaleWindow(massimale,isAdmin,enabled, modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
