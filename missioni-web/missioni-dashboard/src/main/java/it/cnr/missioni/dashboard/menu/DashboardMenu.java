package it.cnr.missioni.dashboard.menu;

import com.google.common.eventbus.Subscribe;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.window.CredenzialiWindow;
import it.cnr.missioni.dashboard.component.window.UserCompletedRegistrationWindow;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.MenuUpdateEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.PostViewChangeEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.ProfileUpdatedEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.UserLoggedOutEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.user.RuoloUtenteEnum;
import it.cnr.missioni.model.user.User;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */
public final class DashboardMenu extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 25971648266194928L;
	/**
	 * 
	 */
	public static final String ID = "dashboard-menu";
	public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
	public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
	private static final String STYLE_VISIBLE = "valo-menu-visible";
	private Label reportsBadge;
	private MenuItem settingsItem;
	private User user = getCurrentUser();
	private CssLayout menuItemsLayout;
	private MenuBar settings = new MenuBar();

	public DashboardMenu() {
		setPrimaryStyleName("valo-menu");
		setId(ID);
		setSizeUndefined();

		// There's only one DashboardMenu per UI so this doesn't need to be
		// unregistered from the UI-scoped DashboardEventBus.
		DashboardEventBus.register(this);

		setCompositionRoot(buildContent());
	}

	private Component buildContent() {
		final CssLayout menuContent = new CssLayout();
		menuContent.addStyleName("sidebar");
		menuContent.addStyleName(ValoTheme.MENU_PART);
		menuContent.addStyleName("no-vertical-drag-hints");
		menuContent.addStyleName("no-horizontal-drag-hints");
		menuContent.setWidth(null);
		menuContent.setHeight("100%");
		menuItemsLayout = new CssLayout();
		menuItemsLayout.addStyleName("valo-menuitems");

		menuContent.addComponent(buildTitle());
		menuContent.addComponent(buildUserMenu());
		// menuContent.addComponent(buildMenuAdminItems());
		menuContent.addComponent(buildMenuItems());
		// if(getCurrentUser().getCredenziali().getRuoloUtente() ==
		// RuoloUtenteEnum.UTENTE_ADMIN)
		// menuContent.addComponent(buildMenuAdminItems());

		return menuContent;
	}

	private Component buildTitle() {
		Label logo = new Label("CNR-Missioni <strong>Dashboard</strong>", ContentMode.HTML);
		logo.setSizeUndefined();
		HorizontalLayout logoWrapper = new HorizontalLayout(logo);
		logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
		logoWrapper.addStyleName("valo-menu-title");
		return logoWrapper;
	}

	private User getCurrentUser() {
		return (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
	}

	private MenuBar buildUserMenu() {
		// settings = new MenuBar();
		settings.addStyleName("user-menu");

		settingsItem = settings.addItem("", new ThemeResource("img/profile-pic-300px.jpg"), null);
		updateUserName(null);

		// se la registrazione è completata
		if (user.isRegistrazioneCompletata()) {
			settingsItem.addItem("Edit Profile", new Command() {
				@Override
				public void menuSelected(final MenuItem selectedItem) {
					UserCompletedRegistrationWindow.open(user, false);
				}
			});

			settingsItem.addSeparator();
		}
		settingsItem.addItem("Cambia Password", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				CredenzialiWindow.open((User) (VaadinSession.getCurrent().getAttribute(User.class.getName())), false);
			}
		});

		settingsItem.addSeparator();
		settingsItem.addItem("Logout", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				DashboardEventBus.post(new UserLoggedOutEvent());
			}
		});

		return settings;
	}

	private Component buildMenuItems() {

		menuItemsLayout.addComponent(new ValoMenuItemButton(DashboardViewType.HOME));
		for (final DashboardViewType view : DashboardViewType.values()) {

			// se l'user ha completato la registrazione
			if (user.isRegistrazioneCompletata() && (view == DashboardViewType.GESTIONE_VEICOLO_PROPRIO
					|| view == DashboardViewType.GESTIONE_RIMBORSO 
							|| view == DashboardViewType.CALENDARIO 
					|| view == DashboardViewType.GESTIONE_MISSIONE)) {

				// if (user.isRegistrazioneCompletata()) {
				Component menuItemComponent = new ValoMenuItemButton(view);
				menuItemsLayout.addComponent(menuItemComponent);
				// }

			}
			// se l'user non ha completato la registrazione
			if (!user.isRegistrazioneCompletata() && view == DashboardViewType.COMPLETA_REGISTRAZIONE) {
				Component menuItemComponent = new ValoMenuItemButton(view);
				menuItemsLayout.addComponent(menuItemComponent);
			}

		}

		// Creazione menù admin
		if (user.isRegistrazioneCompletata()
				&& user.getCredenziali().getRuoloUtente() == RuoloUtenteEnum.UTENTE_SEMPLICE) {
			menuItemsLayout.addComponent(new Label("<hr />", ContentMode.HTML));
			menuItemsLayout.addComponent(new Label("Menù Admin"));
			Component menuItemComponent = new ValoMenuItemButton(DashboardViewType.GESTIONE_USER_ADMIN);
			menuItemsLayout.addComponent(menuItemComponent);

			menuItemComponent = new ValoMenuItemButton(DashboardViewType.GESTIONE_VEICOLO_CNR_ADMIN);
			menuItemsLayout.addComponent(menuItemComponent);
		}

		return menuItemsLayout;

	}

	@Override
	public void attach() {
		super.attach();
	}

	// aggiorna il menù a seguito della registrazione completata da parte
	// dell'user
	@Subscribe
	public void updateMenu(MenuUpdateEvent menuUpdateEvent) {
		user = getCurrentUser();
		menuItemsLayout.removeAllComponents();
		buildMenuItems();
		settingsItem = null;
		settings.removeItems();
		settings = buildUserMenu();
	}

	@Subscribe
	public void postViewChange(final PostViewChangeEvent event) {
		// After a successful view change the menu can be hidden in mobile view.
		getCompositionRoot().removeStyleName(STYLE_VISIBLE);
	}

//	@Subscribe
//	public void updateReportsCount(final ReportsCountUpdatedEvent event) {
//		reportsBadge.setValue(String.valueOf(event.getCount()));
//		reportsBadge.setVisible(event.getCount() > 0);
//	}

	@Subscribe
	public void updateUserName(final ProfileUpdatedEvent event) {
		User user = getCurrentUser();

		if (!user.isRegistrazioneCompletata())
			settingsItem.setText(user.getCredenziali().getUsername());

		else
			settingsItem.setText(user.getAnagrafica().getCognome() + " " + user.getAnagrafica().getNome());
	}

	public final class ValoMenuItemButton extends Button {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6092998505814688109L;

		private static final String STYLE_SELECTED = "selected";

		private final DashboardViewType view;

		public ValoMenuItemButton(final DashboardViewType view) {
			this.view = view;
			setPrimaryStyleName("valo-menu-item");
			setIcon(view.getIcon());
			setCaption(view.getViewName().substring(0, 1).toUpperCase() + view.getViewName().substring(1));
			DashboardEventBus.register(this);

			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(final ClickEvent event) {

					if (view == DashboardViewType.COMPLETA_REGISTRAZIONE) {
						WizardSetupWindow.getWizardSetup().withTipo("user").withUser(user).build();

					} else {
						UI.getCurrent().getNavigator().navigateTo(view.getViewName());
					}

				}
			});

		}

		@Subscribe
		public void postViewChange(final PostViewChangeEvent event) {
			removeStyleName(STYLE_SELECTED);
			if (event.getView() == view) {
				addStyleName(STYLE_SELECTED);
			}
		}
	}
}
