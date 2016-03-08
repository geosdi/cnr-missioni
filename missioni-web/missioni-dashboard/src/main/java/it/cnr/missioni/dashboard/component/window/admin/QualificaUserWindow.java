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

import it.cnr.missioni.dashboard.action.admin.QualificaUserAction;
import it.cnr.missioni.dashboard.component.form.qualificaUser.QualificaUserForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.QualificaUser;

public class QualificaUserWindow extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5429194287713891357L;

	/**
	 * 
	 */

	public static final String ID = "qualificaUserwindow";

	private final QualificaUser qualificaUser;
	private QualificaUserForm qualificaUserForm;

	private QualificaUserWindow(final QualificaUser qualificaUser,final boolean isAdmin,final boolean enabled,final  boolean modifica) {
		super(isAdmin,enabled,modifica);
		this.qualificaUser = qualificaUser;
		setId(ID);
		build();
		detailsWrapper.addComponent(addTab());
		content.addComponent(buildFooter());

	}

	private HorizontalLayout addTab() {
		this.qualificaUserForm = new QualificaUserForm(qualificaUser,true,true,modifica);
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Qaulifica");
		root.setIcon(FontAwesome.GRADUATION_CAP);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);
		root.addComponent(this.qualificaUserForm);
		return root;
	}

	protected Component buildFooter() {

		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4972204396092590093L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					QualificaUser qualificaUser = qualificaUserForm.validate();

					DashboardEventBus.post(new QualificaUserAction(qualificaUser, modifica));
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

	public static void open(final QualificaUser qualificaUser,final boolean isAdmin,final boolean enabled,final boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new QualificaUserWindow(qualificaUser,isAdmin,enabled, modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
