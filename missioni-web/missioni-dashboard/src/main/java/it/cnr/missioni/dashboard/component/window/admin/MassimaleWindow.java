package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
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
	private boolean modifica;
	private MassimaleForm massimaleForm;
	private final Massimale massimale;

	private MassimaleWindow(final Massimale massimale, boolean modifica) {

		super();
		this.massimale = massimale;
		this.modifica = modifica;
		setId(ID);
		build();
		detailsWrapper.addComponent(addTab());
		content.addComponent(buildFooter());
	}
	
	private HorizontalLayout addTab(){
		this.massimaleForm = new MassimaleForm(this.massimale,true,true,modifica);
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Nazione");
		root.setIcon(FontAwesome.GLOBE);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);
		root.addComponent(this.massimaleForm);
		return root;
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

	public static void open(final Massimale massimale, boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new MassimaleWindow(massimale, modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
