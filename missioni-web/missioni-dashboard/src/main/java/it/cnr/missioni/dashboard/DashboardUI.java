package it.cnr.missioni.dashboard;

import java.util.Locale;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.action.AddUpdatePrenotazioneAction;
import it.cnr.missioni.dashboard.action.DeletePrenotazioneAction;
import it.cnr.missioni.dashboard.action.LoginAction;
import it.cnr.missioni.dashboard.action.MissioneAction;
import it.cnr.missioni.dashboard.action.RegistrationUserAction;
import it.cnr.missioni.dashboard.action.RimborsoAction;
import it.cnr.missioni.dashboard.action.UpdateUserAction;
import it.cnr.missioni.dashboard.action.UpdateUserByAdminAction;
import it.cnr.missioni.dashboard.action.VeicoloAction;
import it.cnr.missioni.dashboard.action.admin.MassimaleAction;
import it.cnr.missioni.dashboard.action.admin.NazioneAction;
import it.cnr.missioni.dashboard.action.admin.QualificaUserAction;
import it.cnr.missioni.dashboard.action.admin.RimborsoKmAction;
import it.cnr.missioni.dashboard.action.admin.TipologiaSpesaAction;
import it.cnr.missioni.dashboard.action.admin.UpdateRimborsoAction;
import it.cnr.missioni.dashboard.action.admin.UpdateUserResponsabileGruppoAction;
import it.cnr.missioni.dashboard.action.admin.VeicoloCNRAction;
import it.cnr.missioni.dashboard.event.DashboardEvent.BrowserResizeEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.UserLoggedOutEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.notification.INotificationProvider;
import it.cnr.missioni.dashboard.notification.NotificationProvider;
import it.cnr.missioni.dashboard.utility.BeanFieldGrouFactory;
import it.cnr.missioni.dashboard.view.LoginView;
import it.cnr.missioni.dashboard.view.MainView;
import it.cnr.missioni.model.user.User;

@Theme("dashboard")
@Widgetset("it.cnr.missioni.dashboard.DashboardWidgetSet")
@Title("Missioni Dashboard")
public final class DashboardUI extends UI  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3787052440150933035L;
	/*
	 * This field stores an access to the dummy backend layer. In real
	 * applications you most likely gain access to your beans trough lookup or
	 * injection; and not in the UI but somewhere closer to where they're
	 * actually accessed.
	 */
	private final DashboardEventBus dashboardEventbus = new DashboardEventBus();
	private  INotificationProvider notificationProvider = new NotificationProvider();

	@Override
	protected void init(final VaadinRequest request) {
		setLocale(Locale.ITALY);
		VaadinSession.getCurrent().setConverterFactory(new BeanFieldGrouFactory.ConverterFactory());
		DashboardEventBus.register(this);
		Responsive.makeResponsive(this);
		addStyleName(ValoTheme.UI_WITH_MENU);

		updateContent();

		// Some views need to be aware of browser resize events so a
		// BrowserResizeEvent gets fired to the event bus on every occasion.
		Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
			@Override
			public void browserWindowResized(final BrowserWindowResizeEvent event) {
				DashboardEventBus.post(new BrowserResizeEvent());
			}
		});
	}

	/**
	 * Updates the correct content for this UI based on the current user status.
	 * If the user is logged in with appropriate privileges, main view is shown.
	 * Otherwise login view is shown.
	 */
	private void updateContent() {

		// if (user != null && "admin".equals(user.getRole())) {
		if (getCurrentUser() != null) {
		
			// Authenticated user
			setContent(new MainView());
			removeStyleName("loginview");
			getNavigator().navigateTo(getNavigator().getState());
			notificationProvider.check();

			

		} else {
			setContent(new LoginView());
			addStyleName("loginview");
		}
	}

	@Subscribe
	public void userLoginRequested(final LoginAction loginAction) throws InterruptedException {

		if (loginAction.doAction()) {			
			updateContent();
		}

	}
	
	

	@Subscribe
	public void userRegistrationRequested(final RegistrationUserAction registrationUserAction) {
		registrationUserAction.doAction();
	}
	
	@Subscribe
	public void userUpdateUserByAdminRequested(final UpdateUserByAdminAction updateUserByAdminAction) {
		updateUserByAdminAction.doAction();
	}
	
	@Subscribe
	public void userUpdateUserRequested(final UpdateUserAction updateUserAction) {
		updateUserAction.doAction();
	}
	
	@Subscribe
	public void userUpdateUserRequested(final UpdateUserResponsabileGruppoAction updateUserResponsabileGruppoAction) {
		updateUserResponsabileGruppoAction.doAction();
	}
	
	@Subscribe
	public void veicoloRequested(final VeicoloAction veicoloAction) {
		veicoloAction.doAction();
	}
	
	@Subscribe
	public void missioneRequested(final MissioneAction missioneAction) {
		missioneAction.doAction();
	}
	
	@Subscribe
	public void rimborsoRequested(final RimborsoAction rimborsoAction) {
		rimborsoAction.doAction();
	}
	
	@Subscribe
	public void updateRimborsoRequested(final UpdateRimborsoAction updateRimborsoAction) {
		updateRimborsoAction.doAction();
	}
	
	@Subscribe
	public void prenotazioneRequested(final AddUpdatePrenotazioneAction prenotazioneAction) {
		prenotazioneAction.doAction();
	}
	
	@Subscribe
	public void prenotazioneRequested(final DeletePrenotazioneAction deleteAction) {
		deleteAction.doAction();
	}
	
	@Subscribe
	public void veicoloCNRRequested(final VeicoloCNRAction veicoloCNRAction) {
		veicoloCNRAction.doAction();
	}
	
	@Subscribe
	public void qualificaUserRequested(final QualificaUserAction qualificaUserAction) {
		qualificaUserAction.doAction();
	}
	
	@Subscribe
	public void nazioneRequested(final NazioneAction nazioneAction) {
		nazioneAction.doAction();
	}
	
	@Subscribe
	public void tipologiaSpesaRequested(final TipologiaSpesaAction tipologiaSpesaAction) {
		tipologiaSpesaAction.doAction();
	}
	
	@Subscribe
	public void rimborsoKmRequested(final RimborsoKmAction rimborsoAction) {
		rimborsoAction.doAction();
	}
	
	@Subscribe
	public void massimaleRequested(final MassimaleAction massimaleAction) {
		massimaleAction.doAction();
	}
	
	@Subscribe
	public void userLoggedOut(final UserLoggedOutEvent event) {
		// When the user logs out, current VaadinSession gets closed and the
		// page gets reloaded on the login screen. Do notice the this doesn't
		// invalidate the current HttpSession.
		VaadinSession.getCurrent().close();
		Page.getCurrent().reload();
	}

	@Subscribe
	public void closeOpenWindows(final CloseOpenWindowsEvent event) {
		for (Window window : getWindows()) {
			window.close();
		}
	}

	public static DashboardEventBus getDashboardEventbus() {
		return ((DashboardUI) getCurrent()).dashboardEventbus;
	}
	
    /**
     * @return An instance for accessing the  services layer.
     */
    public static INotificationProvider getDataProvider() { 	
        return ((DashboardUI) getCurrent()).notificationProvider;
    }
    
    

	//Ritorna l'utente che si è loggato al sistema
	public static User getCurrentUser(){
		return (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
	}
	
}
