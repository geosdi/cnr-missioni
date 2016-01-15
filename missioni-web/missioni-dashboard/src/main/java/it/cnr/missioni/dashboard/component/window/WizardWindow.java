package it.cnr.missioni.dashboard.component.window;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Responsive;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import it.cnr.missioni.dashboard.component.wizard.missione.WizardSetup;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;

public class WizardWindow extends Window{

	/**
	 * 
	 */
	private static final long serialVersionUID = -233114930128321445L;

	/**
	 * 
	 */

	public static final String ID = "wizardWindow";

	/**
	 * 
	 * @param rimborso
	 * @param modifica
	 * @param missione
	 * @param isMissione
	 */
	private WizardWindow(boolean modifica,final Missione missione,String tipo ) {
		init();
		setContent(new WizardSetup(modifica,missione,tipo).getComponent());

	}
	
	/**
	 * 
	 * @param user
	 */
	private WizardWindow(User user) {
//		addStyleName("profile-window");
		init();
		setContent(new WizardSetup(user).getComponent());

	}
	
	private void init(){
		setId(ID);
		Responsive.makeResponsive(this);
		setModal(true);
		setCloseShortcut(KeyCode.ESCAPE, null);
		setResizable(false);
		setClosable(true);
//		setSizeFull();
		setHeight("55%");
		setWidth("40%");
	}

	/**
	 * 
	 * @param rimborso
	 * @param modifica
	 * @param missione
	 * @param isMissione
	 */
	public static void openWizardMissioneRimborso( boolean modifica,Missione missione,String tipo ) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new WizardWindow( modifica, missione,tipo);
		UI.getCurrent().addWindow(w);
		w.focus();
	}

	/**
	 * 
	 * @param user
	 */
	public static void openWizardUser( User user ) {
		DashboardEventBus.post(new CloseOpenWindowsEvent());
		Window w = new WizardWindow( user);
		UI.getCurrent().addWindow(w);
		w.focus();
	}




}
