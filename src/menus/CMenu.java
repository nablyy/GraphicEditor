package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.CConstants.EMenuItem;
import frames.CDrawingPanel;

public abstract class CMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	// associations
	protected CDrawingPanel drawingPanel;
	protected MenuHandler menuHandler;
	
	public CMenu(EMenuItem[] eMenuItems) {
       menuHandler = new MenuHandler();	       
		for(EMenuItem eMenuItem: eMenuItems) {
			JMenuItem menuItem = new JMenuItem();
			menuItem.setText(eMenuItem.getName());
			menuItem.addActionListener(menuHandler);
			menuItem.setActionCommand(eMenuItem.toString());
			this.add(menuItem);
		}
	}
	
	public void init(CDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public void invokeMethod(String name) {
		try {
			this.getClass().getMethod(name).invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected class MenuHandler implements ActionListener {		
	    public void actionPerformed(ActionEvent e) {
	    	invokeMethod(e.getActionCommand());
	    }
	}

}
