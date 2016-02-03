package it.cnr.missioni.dashboard.component.window;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
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
		}

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
