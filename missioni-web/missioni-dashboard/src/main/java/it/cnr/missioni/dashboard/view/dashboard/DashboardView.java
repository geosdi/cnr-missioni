package it.cnr.missioni.dashboard.view.dashboard;

import java.util.Iterator;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import it.cnr.missioni.dashboard.component.ElencoMissioniTable;
import it.cnr.missioni.dashboard.component.ElencoVeicoliTable;
import it.cnr.missioni.dashboard.component.MissioneWindow;
import it.cnr.missioni.dashboard.component.VeicoloWindow;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;
import it.cnr.missioni.dashboard.view.dashboard.DashboardEdit.DashboardEditListener;
import it.cnr.missioni.model.missione.Missione;
import it.cnr.missioni.model.user.User;
import it.cnr.missioni.model.user.Veicolo;


public final class DashboardView extends Panel implements View,
        DashboardEditListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1115999357356137696L;
	public static final String EDIT_ID = "dashboard-edit";
    public static final String TITLE_ID = "dashboard-title";

    private CssLayout dashboardPanels;
    private final VerticalLayout root;
    private ElencoVeicoliTable elencoVeicoliTable;
    private ElencoMissioniTable elencoMissioniTable;
    
   private User user = (User)VaadinSession.getCurrent().getAttribute(User.class.getName());

    public DashboardView() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        DashboardEventBus.register(this);

        root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        Responsive.makeResponsive(root);


        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);

        root.addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(final LayoutClickEvent event) {
                DashboardEventBus.post(new CloseOpenWindowsEvent());
            }
        });
    }

    
	/**
	 * 
	 * Costruisce la table per l'elenco dei veicoli
	 * 
	 * @return Component
	 */
	private Component buildTableVeicoli() {

		CssLayout l = new CssLayout();
		l.setCaption("Elenco Veicoli");
		l.setSizeFull();
		elencoVeicoliTable = new ElencoVeicoliTable();
//		elencoVeicoliTable.aggiornaTable();
		l.addComponent(elencoVeicoliTable);

		Button b = new Button();

		b.setIcon(FontAwesome.CAR);
		b.setDescription("Nuovo Veicolo");
		b.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				VeicoloWindow.open(new Veicolo(),false,elencoVeicoliTable);
			}
		});

		if(user.isRegistrazioneCompletata())
			b.setEnabled(true);
		else
			b.setEnabled(false);
		l.addComponent(b);
		return createContentWrapper(l);

	}
	
	
	/**
	 * 
	 * Costruisce la table per l'elenco dei veicoli
	 * 
	 * @return Component
	 */
	private Component buildTableMissioni() {

		CssLayout l = new CssLayout();
		l.setCaption("Elenco Missioni");
		l.setSizeFull();
		 elencoMissioniTable = new ElencoMissioniTable();
//		elencoMissioniTable.aggiornaTable();
		l.addComponent(elencoMissioniTable);

		Button b = new Button();

		b.setIcon(FontAwesome.TASKS);
		b.setDescription("Nuova Missione");
		b.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				MissioneWindow.open(new Missione(),false,elencoMissioniTable);
			}
		});

		if(user.isRegistrazioneCompletata())
			b.setEnabled(true);
		else
			b.setEnabled(false);
		l.addComponent(b);
		return createContentWrapper(l);

	}
    
    private Component buildContent() {
        dashboardPanels = new CssLayout();
        dashboardPanels.addStyleName("dashboard-panels");
        Responsive.makeResponsive(dashboardPanels);

        dashboardPanels.addComponent(buildNotes());
        dashboardPanels.addComponent(buildTableVeicoli());
        dashboardPanels.addComponent(buildTableMissioni());
        return dashboardPanels;
    }



    private Component buildNotes() {
        TextArea notes = new TextArea("Notes");
        notes.setValue("Remember to:\n· Zoom in and out in the Sales view\n· Filter the transactions and drag a set of them to the Reports tab\n· Create a new report\n· Change the schedule of the movie theater");
        notes.setSizeFull();
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        Component panel = createContentWrapper(notes);
        panel.addStyleName("notes");
        return panel;
    }




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
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                Notification.show("Not implemented in this demo");
            }
        });
        root.addSeparator();
        root.addItem("Close", new Command() {
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
            panel.setVisible(true);
            panel.addStyleName("max");
        } else {
            panel.removeStyleName("max");
        }
    }

    public static final class NotificationsButton extends Button {
        private static final String STYLE_UNREAD = "unread";
        public static final String ID = "dashboard-notifications";

        public NotificationsButton() {
            setIcon(FontAwesome.BELL);
            setId(ID);
            addStyleName("notifications");
            addStyleName(ValoTheme.BUTTON_ICON_ONLY);
            DashboardEventBus.register(this);
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

	/**
	 * @param name
	 */
	@Override
	public void dashboardNameEdited(String name) {
		
	}

}
