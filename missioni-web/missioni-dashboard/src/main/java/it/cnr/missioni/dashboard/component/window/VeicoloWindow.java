package it.cnr.missioni.dashboard.component.window;

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

import it.cnr.missioni.dashboard.action.VeicoloAction;
import it.cnr.missioni.dashboard.component.form.veicolo.VeicoloForm;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.user.Veicolo;

public class VeicoloWindow  extends  IWindow.AbstractWindow  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -333945068835078811L;

	public static final String ID = "veicolowindow";


	private boolean modifica;
	private Veicolo veicolo;
	private VeicoloForm veicoloForm;


	private VeicoloWindow(final Veicolo veicolo, boolean modifica) {
		super();
		this.veicolo = veicolo;
		this.modifica = modifica;
		setId(ID);
		build();
		buildTab();
		content.addComponent(buildFooter());

	}
	
	private void buildTab() {
		this.veicoloForm = new VeicoloForm(veicolo, false,true,modifica);
		detailsWrapper.addComponent(buildTab("Veicolo", FontAwesome.CAR, this.veicoloForm ));
	}


	protected Component buildFooter() {
		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -932339804718579357L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {

					Veicolo new_veicolo = veicoloForm.validate();

					DashboardEventBus.post(new VeicoloAction(new_veicolo,modifica));
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

	public static void open(final Veicolo veicolo, boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new VeicoloWindow(veicolo, modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
