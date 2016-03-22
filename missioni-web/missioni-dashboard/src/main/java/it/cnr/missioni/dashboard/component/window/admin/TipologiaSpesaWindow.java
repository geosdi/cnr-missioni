package it.cnr.missioni.dashboard.component.window.admin;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.action.admin.TipologiaSpesaAction;
import it.cnr.missioni.dashboard.component.form.tipologiaspesa.TipologiaSpesaForm;
import it.cnr.missioni.dashboard.component.window.IWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.model.configuration.TipologiaSpesa;

public class TipologiaSpesaWindow extends IWindow.AbstractWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6599225757081293003L;

	public static final String ID = "tipologiaspesawindow";


	private final TipologiaSpesa tipologiaSpesa;
	private TipologiaSpesaForm tipologiaSpesaForm;

	private TipologiaSpesaWindow(final TipologiaSpesa tipologiaSpesa,final boolean isAdmin,final boolean enabled,final boolean modifica) {

		super(isAdmin,enabled,modifica);
		this.tipologiaSpesa = tipologiaSpesa;
		setId(ID);
		build();
		detailsWrapper.addComponent(buildTab());

		content.addComponent(buildFooter());

	}

	protected HorizontalLayout buildTab(){
        this.tipologiaSpesaForm = new TipologiaSpesaForm(tipologiaSpesa,true,true,modifica);
		HorizontalLayout tab = super.buildTab("RimbTipologia Spesorso",FontAwesome.EURO,this.tipologiaSpesaForm);
		return tab;
	}

	protected Component buildFooter() {

		ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
		ok.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4000358346700874351L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {

					TipologiaSpesa tipologiaSpesa = tipologiaSpesaForm.validate();

					DashboardEventBus.post(new TipologiaSpesaAction(tipologiaSpesa, modifica));
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

	public static void open(final TipologiaSpesa tipologiaSpesa,final boolean isAdmin,final boolean enabled,final boolean modifica) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new TipologiaSpesaWindow(tipologiaSpesa,isAdmin,enabled, modifica);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

}
