package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.admin.RimborsoKmAction;
import it.cnr.missioni.dashboard.component.form.qualificaUser.QualificaUserForm;
import it.cnr.missioni.dashboard.component.form.rimborsoKm.RimborsoKmForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.RimborsoKm;

public class RimborsoKmWindow extends IWindow.AbstractWindow {



	/**
	 * 
	 */
	private static final long serialVersionUID = 5484936598745173136L;

	public static final String ID = "rimborsoKmwindow";
	private RimborsoKmForm rimborsoKmForm;


	private final RimborsoKm rimborsoKm;
	private boolean modifica;

	private RimborsoKmWindow(final RimborsoKm rimborsoKm, boolean modifica) {
		super();
		this.rimborsoKm = rimborsoKm;
		this.modifica = modifica;

		setId(ID);
		build();
		detailsWrapper.addComponent(addTab());

		content.addComponent(buildFooter());

	}
	
	private HorizontalLayout addTab() {
		this.rimborsoKmForm = new RimborsoKmForm(rimborsoKm,true,true,modifica);
		HorizontalLayout root = new HorizontalLayout();
		root.setCaption("Rimborso");
		root.setIcon(FontAwesome.EURO);
		root.setWidth(100.0f, Unit.PERCENTAGE);
		root.setSpacing(true);
		root.setMargin(true);
		root.addComponent(this.rimborsoKmForm);
		return root;
	}

	protected Component buildFooter() {

		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4140141100852159590L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					RimborsoKm rimborsoKm = rimborsoKmForm.validate();
					DashboardEventBus.post(new RimborsoKmAction(rimborsoKm, modifica));
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

	public static void open(final RimborsoKm rimborsoKm, boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new RimborsoKmWindow(rimborsoKm, modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
