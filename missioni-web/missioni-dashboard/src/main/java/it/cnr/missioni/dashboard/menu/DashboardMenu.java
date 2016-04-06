package it.cnr.missioni.dashboard.menu;

import com.google.common.eventbus.Subscribe;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.component.window.CredenzialiWindow;
import it.cnr.missioni.dashboard.component.window.UserWindow;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.component.wizard.user.WizardUser;
import it.cnr.missioni.dashboard.event.DashboardEvent.*;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.model.user.RuoloUserEnum;
import it.cnr.missioni.model.user.User;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */
@SuppressWarnings({"serial", "unchecked"})
public final class DashboardMenu extends CustomComponent {

    public static final String ID = "dashboard-menu";
    public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
    public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    private Label notificationsBadge;
    private MenuItem settingsItem;
    private User user = DashboardUI.getCurrentUser();
    private MenuBar settings = new MenuBar();
    private CssLayout menuItemsLayout;

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
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());
        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("Missioni <strong>Dashboard</strong>", ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }

    @Subscribe
    public void updateUserName(final ProfileUpdatedEvent event) {
        if (!user.isRegistrazioneCompletata())
            settingsItem.setText(DashboardUI.getCurrentUser().getCredenziali().getUsername());
        else
            settingsItem.setText(
                    DashboardUI.getCurrentUser().getAnagrafica().getCognome() + " " + user.getAnagrafica().getNome());
    }

    private MenuBar buildUserMenu() {
        // settings = new MenuBar();
        settings.addStyleName("user-menu");
        settingsItem = settings.addItem("", new ThemeResource("img/profile-pic-300px.jpg"), null);
        updateUserName(null);
        // se la registrazione è completata
        if (user.isRegistrazioneCompletata()) {
            settingsItem.addItem("Edit Profile", new Command() {
                /**
                 *
                 */
                private static final long serialVersionUID = -866924513489337812L;

                @Override
                public void menuSelected(final MenuItem selectedItem) {
//					UserWindow.open(user, false, true, false);
                    UserWindow.getUserWindow().withBean(user).withIsAdmin(false)
                            .withEnabled(true).withModifica(true).build();
                }
            });
            settingsItem.addSeparator();
        }
        settingsItem.addItem("Cambia Password", new Command() {
            /**
             *
             */
            private static final long serialVersionUID = 5022549575366839928L;

            @Override
            public void menuSelected(final MenuItem selectedItem) {
//				CredenzialiWindow.open(DashboardUI.getCurrentUser(), false, true, true);
                CredenzialiWindow.getCredenzialiWindow().withBean(new User()).withIsAdmin(false).withEnabled(true).withModifica(true).build();

            }
        });

        settingsItem.addSeparator();
        settingsItem.addItem("Logout", new Command() {
            /**
             *
             */
            private static final long serialVersionUID = -2415817666648296659L;

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                DashboardEventBus.post(new UserLoggedOutEvent());
            }
        });

        return settings;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);
                }
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private void buildMenuUserType() {
        DashboardViewType.getMenuUser().stream().forEach(view -> {
            Component menuItemComponent = new ValoMenuItemButton(view);
            if (view == DashboardViewType.HOME) {
                notificationsBadge = new Label();
                notificationsBadge.setId(NOTIFICATIONS_BADGE_ID);
                menuItemComponent = buildBadgeWrapper(menuItemComponent, notificationsBadge);
            }
            menuItemsLayout.addComponent(menuItemComponent);
        });
        if (user.getCredenziali().getRuoloUtente() == RuoloUserEnum.UTENTE_ADMIN) {
            menuItemsLayout.addComponent(new Label("<hr />", ContentMode.HTML));
            menuItemsLayout.addComponent(new Label("Menù Admin"));
            DashboardViewType.getMenuAdmin().stream().forEach(view -> {
                Component menuItemComponent = new ValoMenuItemButton(view);
                menuItemsLayout.addComponent(menuItemComponent);
            });
        }
    }
    
    private void buildMenuUserRegistrationNotCompleteType() {
        DashboardViewType.getMenuUserRegistrationNotComplete().stream().forEach(view -> {
            Component menuItemComponent = new ValoMenuItemButton(view);
            menuItemsLayout.addComponent(menuItemComponent);
        });
    }

    private Component buildMenuItems() {
    	if(user.isRegistrazioneCompletata())
        	buildMenuUserType();
    	else
    		buildMenuUserRegistrationNotCompleteType();
        return menuItemsLayout;
    }

    private Component buildBadgeWrapper(final Component menuItemButton, final Component badgeLabel) {
        CssLayout dashboardWrapper = new CssLayout(menuItemButton);
        dashboardWrapper.addStyleName("badgewrapper");
        dashboardWrapper.addStyleName(ValoTheme.MENU_ITEM);
        badgeLabel.addStyleName(ValoTheme.MENU_BADGE);
        badgeLabel.setWidthUndefined();
        badgeLabel.setVisible(true);
        dashboardWrapper.addComponent(badgeLabel);
        return dashboardWrapper;
    }

    @Override
    public void attach() {
        super.attach();
    }

    @Subscribe
    public void updateNotificationsCount(final NotificationsCountUpdatedEvent event) {
        int unreadNotificationsCount = DashboardUI.getDataProvider().getUnreadNotificationsCount();
        notificationsBadge.setValue(String.valueOf(DashboardUI.getDataProvider().getUnreadNotificationsCount()));
        notificationsBadge.setVisible(unreadNotificationsCount > 0);
    }

    // aggiorna il menù a seguito della registrazione completata da parte
    // dell'user
    @Subscribe
    public void updateMenu(MenuUpdateEvent menuUpdateEvent) {
        user = DashboardUI.getCurrentUser();
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
                /**
                 *
                 */
                private static final long serialVersionUID = -1918881911840117255L;

                @Override
                public void buttonClick(final ClickEvent event) {
                    if (view == DashboardViewType.COMPLETA_REGISTRAZIONE) {
                        WizardSetupWindow.getWizardSetup().withTipo(new WizardUser()).withUser(user).withIsAdmin(false).withEnabled(true).withModifica(false).build();
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