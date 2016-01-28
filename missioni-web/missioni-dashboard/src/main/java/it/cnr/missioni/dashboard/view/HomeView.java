package it.cnr.missioni.dashboard.view;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;

import com.google.common.eventbus.Subscribe;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.addon.calendar.ui.Calendar.TimeFormat;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.DashboardUI;
import it.cnr.missioni.dashboard.client.ClientConnector;
import it.cnr.missioni.dashboard.component.calendar.CalendarPrenotazioni;
import it.cnr.missioni.dashboard.component.table.ElencoMissioniTable;
import it.cnr.missioni.dashboard.component.table.ElencoRimborsiTable;
import it.cnr.missioni.dashboard.component.window.WizardSetupWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.NotificationsCountUpdatedEvent;
import it.cnr.missioni.dashboard.event.DashboardEvent.TableRimborsiUpdatedEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.notification.DashboardNotification;
import it.cnr.missioni.dashboard.utility.Utility;
import it.cnr.missioni.el.model.search.builder.MissioneSearchBuilder;
import it.cnr.missioni.el.model.search.builder.SearchConstants;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.rest.api.response.missione.MissioniStore;

public final class HomeView extends Panel implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5797632444234046456L;
	/**
	 * 
	 */
	public static final String EDIT_ID = "dashboard-edit";
	public static final String TITLE_ID = "dashboard-title";

	private CssLayout dashboardPanels;
	private final VerticalLayout root;
	private ElencoMissioniTable elencoMissioniTable;
	private Window notificationsWindow;
	private NotificationsButton notificationsButton;
	private CalendarPrenotazioni calendar;

	private User user = DashboardUI.getCurrentUser();

	public HomeView() {

		addStyleName(ValoTheme.PANEL_BORDERLESS);
		setSizeFull();
		DashboardEventBus.register(this);

		root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);
		root.addStyleName("dashboard-view");
		setContent(root);
		Responsive.makeResponsive(root);
		root.addComponent(buildHeader());
		Component content = buildContent();
		root.addComponent(content);
		root.setExpandRatio(content, 1);

		DashboardEventBus.post(new DashboardEvent.NotificationsCountUpdatedEvent());
		
		

	}

	private Component buildHeader() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName("viewheader");
		header.setSpacing(true);

		notificationsButton = buildNotificationsButton();
		HorizontalLayout tools = new HorizontalLayout(notificationsButton);
		tools.setSpacing(true);
		tools.addStyleName("toolbar");
		header.addComponent(tools);

		return header;
	}

	/**
	 * 
	 * Costruisce la table per l'elenco dei veicoli
	 * 
	 * @return Component
	 */
	private Component buildTableMissioni() {

		CssLayout l = new CssLayout();
		l.setCaption("Ultime Missioni");
		l.setSizeFull();
		elencoMissioniTable = new ElencoMissioniTable();

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withIdUser(user.getId());

		try {
			MissioniStore missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
			elencoMissioniTable.aggiornaTable(missioniStore);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		l.addComponent(elencoMissioniTable);

		Component p = createContentWrapper(l);
		p.setId("tableMissioni");
		return p;

	}

	/**
	 * 
	 * Costruisce la table per l'elenco dei veicoli
	 * 
	 * @return Component
	 */
	private Component buildTableRimborsi() {

		CssLayout l = new CssLayout();
		l.setCaption("Ultimi Rimborsi");
		l.setSizeFull();
		ElencoRimborsiTable elencoRimborsiTable = new ElencoRimborsiTable();

		MissioneSearchBuilder missioneSearchBuilder = MissioneSearchBuilder.getMissioneSearchBuilder()
				.withFieldExist(SearchConstants.MISSIONE_FIELD_RIMBORSO).withIdUser(user.getId());

		try {
			MissioniStore missioniStore = ClientConnector.getMissione(missioneSearchBuilder);
			elencoRimborsiTable.aggiornaTable(missioniStore);
		} catch (Exception e) {
			Utility.getNotification(Utility.getMessage("error_message"), Utility.getMessage("request_error"),
					Type.ERROR_MESSAGE);
		}

		l.addComponent(elencoRimborsiTable);
		Component p = createContentWrapper(l);
		p.setId("tableRimborsi");
		return p;

	}

	private Component buildCalendar() {

		calendar = new CalendarPrenotazioni("100%", "200px");
		calendar.initContent();
		VerticalLayout l = calendar.getLayoutCalendar();

		Component panel = createContentWrapper(l);
		panel.setId("calendar");
		return panel;
	}

	private Component buildContent() {
		dashboardPanels = new CssLayout();
		dashboardPanels.addStyleName("dashboard-panels");
		Responsive.makeResponsive(dashboardPanels);
		dashboardPanels.addComponent(buildTableMissioni());
		dashboardPanels.addComponent(buildTableRimborsi());
		dashboardPanels.addComponent(buildCalendar());
		return dashboardPanels;
	}

	//
	// private Component createContentWrapper2(final Component content) {
	// final CssLayout slot = new CssLayout();
	// // slot.setWidth("100%");
	// // slot.setHeight("50%");
	// // slot.addStyleName("dashboard-panel-slot");
	// slot.setWidth("100%");
	// CssLayout card = new CssLayout();
	// card.setWidth("100%");
	// card.addStyleName(ValoTheme.LAYOUT_CARD);
	//
	// HorizontalLayout toolbar = new HorizontalLayout();
	// toolbar.addStyleName("dashboard-panel-toolbar");
	// toolbar.setWidth("100%");
	//
	// Label caption = new Label(content.getCaption());
	// caption.addStyleName(ValoTheme.LABEL_H4);
	// caption.addStyleName(ValoTheme.LABEL_COLORED);
	// caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	// content.setCaption(null);
	//
	// MenuBar tools = new MenuBar();
	// tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
	// MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {
	//
	// /**
	// *
	// */
	// private static final long serialVersionUID = -7645614062259408973L;
	//
	// @Override
	// public void menuSelected(final MenuItem selectedItem) {
	// if (!slot.getStyleName().contains("max")) {
	// selectedItem.setIcon(FontAwesome.COMPRESS);
	// toggleMaximized(slot, true);
	// } else {
	// slot.removeStyleName("max");
	// selectedItem.setIcon(FontAwesome.EXPAND);
	// toggleMaximized(slot, false);
	// }
	// }
	// });
	// max.setStyleName("icon-only");
	// MenuItem root = tools.addItem("", FontAwesome.COG, null);
	// root.addItem("Configure", new Command() {
	// /**
	// *
	// */
	// private static final long serialVersionUID = 5014516404913881968L;
	//
	// @Override
	// public void menuSelected(final MenuItem selectedItem) {
	// Notification.show("Not implemented in this demo");
	// }
	// });
	// root.addSeparator();
	// root.addItem("Close", new Command() {
	// /**
	// *
	// */
	// private static final long serialVersionUID = -4608004443713485478L;
	//
	// @Override
	// public void menuSelected(final MenuItem selectedItem) {
	// Notification.show("Not implemented in this demo");
	// }
	// });
	//
	// toolbar.addComponents(caption, tools);
	// toolbar.setExpandRatio(caption, 1);
	// toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);
	//
	// card.addComponents(toolbar, content);
	// slot.addComponent(card);
	// return slot;
	// }

	private Component createContentWrapper(final Component content) {
		final CssLayout slot = new CssLayout();
		slot.setWidth("100%");
		slot.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");

		Label caption = new Label(content.getCaption());
		caption.addStyleName(ValoTheme.LABEL_H4);
		caption.addStyleName(ValoTheme.LABEL_COLORED);
		caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		content.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 847091348534740483L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!slot.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(slot, true);
				} else {
					slot.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(slot, false);
				}
			}
		});
		max.setStyleName("icon-only");
		MenuItem root = tools.addItem("", FontAwesome.COG, null);
		root.addItem("Configure", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7967080645845509438L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				Notification.show("Not implemented in this demo");
			}
		});
		root.addSeparator();
		root.addItem("Close", new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7758314449866999973L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				Notification.show("Not implemented in this demo");
			}
		});

		toolbar.addComponents(caption, tools);
		toolbar.setExpandRatio(caption, 1);
		toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, content);
		slot.addComponent(card);
		return slot;
	}

	@Override
	public void enter(final ViewChangeEvent event) {



	}

	private void toggleMaximized(final Component panel, final boolean maximized) {
		for (Iterator<Component> it = root.iterator(); it.hasNext();) {
			it.next().setVisible(!maximized);
		}
		dashboardPanels.setVisible(true);

		for (Iterator<Component> it = dashboardPanels.iterator(); it.hasNext();) {
			Component c = it.next();
			c.setVisible(!maximized);
		}

		if (maximized) {
			if (panel.getId().equals("calendar")) {
				calendar.getCalendarComponent().setHeightUndefined();
				calendar.getCalendarComponent().setWidth("100%");
			}
			panel.setVisible(true);
			panel.addStyleName("max");

		} else {
			if (panel.getId().equals("calendar")) {
				calendar.getCalendarComponent().setHeight("200px");
				calendar.getCalendarComponent().setWidth("100%");
			}
			panel.removeStyleName("max");
		}
	}

	private NotificationsButton buildNotificationsButton() {
		NotificationsButton result = new NotificationsButton();
		result.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6830083227084878734L;

			@Override
			public void buttonClick(final ClickEvent event) {
				openNotificationsPopup(event);
			}
		});
		return result;
	}

	private void openNotificationsPopup(final ClickEvent event) {
		VerticalLayout notificationsLayout = new VerticalLayout();
		notificationsLayout.setMargin(true);
		notificationsLayout.setSpacing(true);

		Label title = new Label("Notifiche");
		title.addStyleName(ValoTheme.LABEL_H3);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		notificationsLayout.addComponent(title);

		Collection<DashboardNotification> notifications = DashboardUI.getDataProvider().getNotifications();
		DashboardEventBus.post(new NotificationsCountUpdatedEvent());

		for (DashboardNotification notification : notifications) {
			VerticalLayout notificationLayout = new VerticalLayout();
			notificationLayout.addStyleName("notification-item");

			Label titleLabel = new Label(notification.getTitle());
			titleLabel.addStyleName("notification-title");

			Label contentLabel = new Label(notification.getContent());
			contentLabel.addStyleName("notification-content");

			notificationLayout.addComponents(titleLabel, contentLabel);
			notificationsLayout.addComponent(notificationLayout);
		}

		if (notificationsWindow == null) {
			notificationsWindow = new Window();
			notificationsWindow.setWidth(300.0f, Unit.PIXELS);
			notificationsWindow.addStyleName("notifications");
			notificationsWindow.setClosable(false);
			notificationsWindow.setResizable(false);
			notificationsWindow.setDraggable(false);
			notificationsWindow.setCloseShortcut(KeyCode.ESCAPE, null);
			notificationsWindow.setContent(notificationsLayout);
		}

		if (!notificationsWindow.isAttached()) {
			notificationsWindow.setPositionY(event.getClientY() - event.getRelativeY() + 40);
			getUI().addWindow(notificationsWindow);
			notificationsWindow.focus();
		} else {
			notificationsWindow.close();
		}
	}

	public static final class NotificationsButton extends Button {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7788438669790156398L;
		private static final String STYLE_UNREAD = "unread";
		public static final String ID = "dashboard-notifications";

		public NotificationsButton() {
			setIcon(FontAwesome.BELL);
			setId(ID);
			addStyleName("notifications");
			addStyleName(ValoTheme.BUTTON_ICON_ONLY);
			DashboardEventBus.register(this);
		}

		@Subscribe
		public void updateNotificationsCount(final NotificationsCountUpdatedEvent event) {
			setUnreadCount(DashboardUI.getDataProvider().getUnreadNotificationsCount());
		}

		public void setUnreadCount(final int count) {
			setCaption(String.valueOf(count));

			String description = "Notifications";
			if (count > 0) {
				addStyleName(STYLE_UNREAD);
				description += " (" + count + " unread)";
			} else {
				removeStyleName(STYLE_UNREAD);
			}
			setDescription(description);
		}
	}

}
