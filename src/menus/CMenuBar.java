package menus;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import constants.CConstants.EMenus;
import frames.CDrawingPanel;

public class CMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	// associations
	private CDrawingPanel drawingPanel;
	
	public CMenuBar() {
		// add menus
		for (EMenus eMenu: EMenus.values()) {
			JMenu menu = eMenu.newMenu();
			menu.setText(eMenu.getName());
			this.add(menu);
		}
	}
	
	public void init(CDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		for (EMenus eMenu: EMenus.values()) {
			((CMenu)this.getComponents()[eMenu.ordinal()]).init(this.drawingPanel);
		}
	}
}
