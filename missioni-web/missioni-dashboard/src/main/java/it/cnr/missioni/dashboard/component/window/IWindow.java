package it.cnr.missioni.dashboard.component.window;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import it.cnr.missioni.dashboard.event.DashboardEvent.CloseOpenWindowsEvent;
import it.cnr.missioni.dashboard.event.DashboardEventBus;

/**
 * @author Salvia Vito
 */
public interface IWindow<B, T> {

    T build();

    T withModifica(boolean modifica);

    T withEnabled(boolean enabled);

    T withIsAdmin(boolean isAdmin);

    T withBean(B bean);


    public abstract class AbstractWindow<B, T> extends Window implements IWindow<B, T> {

        /**
         *
         */
        private static final long serialVersionUID = -8803859104808302749L;
        protected VerticalLayout content;
        protected TabSheet detailsWrapper;
        protected HorizontalLayout footer;
        protected Button ok;
        protected boolean modifica;
        protected boolean isAdmin;
        protected boolean enabled;
        protected B bean;
        private String ID;

        protected AbstractWindow() {
        }

        public AbstractWindow(boolean isAdmin, boolean enabled, boolean modifica) {
            this.modifica = modifica;
            this.enabled = enabled;
            this.isAdmin = isAdmin;
        }

        public T withModifica(boolean modifica) {
            this.modifica = modifica;
            return self();
        }

        public T withEnabled(boolean enabled) {
            this.enabled = enabled;
            return self();
        }

        public T withIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return self();
        }

        public T withBean(B bean) {
            this.bean = bean;
            return self();
        }

        protected void buildWindow() {
            DashboardEventBus.post(new CloseOpenWindowsEvent());

            addStyleName("profile-window");
            setId(getID());
            Responsive.makeResponsive(this);
            setModal(true);
            setCloseShortcut(KeyCode.ESCAPE, null);
            setResizable(false);
            setClosable(true);
            setHeight(90.0f, Unit.PERCENTAGE);
            content = new VerticalLayout();
            content.setSizeFull();
            content.setMargin(new MarginInfo(true, false, false, false));
            setContent(content);
            detailsWrapper = new TabSheet();
            detailsWrapper.setSizeFull();
            detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
            detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
            detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
            content.addComponent(detailsWrapper);
            content.setExpandRatio(detailsWrapper, 1f);
            this.footer = new HorizontalLayout();
            this.footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
            this.footer.setWidth(100.0f, Unit.PERCENTAGE);
            ok = new Button("OK");
            ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        }

        protected HorizontalLayout buildTab(String caption, FontAwesome icon, Layout content) {
            HorizontalLayout tab = new HorizontalLayout();
            tab.setCaption(caption);
            tab.setIcon(icon);
            tab.setWidth(100.0f, Unit.PERCENTAGE);
            tab.setSpacing(true);
            tab.setMargin(true);
            tab.addComponent(content);
            return tab;
        }

        protected abstract Component buildFooter();

        /**
         * @return the iD
         */
        public String getID() {
            return ID;
        }

        /**
         * @param iD
         */
        public void setID(String iD) {
            ID = iD;
        }

        public abstract T self();

    }

}
