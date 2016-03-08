package it.cnr.missioni.dashboard.component.window;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Salvia Vito
 */
public interface IWindow {

	void build();
	
	
	

	public abstract class AbstractWindow extends Window implements IWindow {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8803859104808302749L;
		private String ID;
		protected VerticalLayout content;
		protected TabSheet detailsWrapper;
		protected HorizontalLayout footer ;
		protected Button ok;
		protected final boolean modifica;
		protected final boolean isAdmin;
		protected final boolean enabled;
		
		public AbstractWindow(boolean isAdmin,boolean enabled,boolean modifica){
			this.modifica = modifica;
			this.enabled = enabled;
			this.isAdmin = isAdmin;
		}

		public void build() {
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
		
		protected HorizontalLayout buildTab(String caption,FontAwesome icon,Layout content){
			HorizontalLayout root = new HorizontalLayout();
			root.setCaption(caption);
			root.setIcon(icon);
			root.setWidth(100.0f, Unit.PERCENTAGE);
			root.setSpacing(true);
			root.setMargin(true);
			root.addComponent(content);
			return root;
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

	}

}
