package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import constants.CConstants;
import constants.CConstants.EShapeManager;
import shapes.CShapeManager;

public class CToolbar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	// components
	private ActionHandler actionHandler;
	private ButtonGroup buttonGroup;
	
	// associations
	private CDrawingPanel drawingPanel;
	
	public CToolbar() {
		this.setSize(CConstants.TOOLBAR_W, CConstants.TOOLBAR_H);
		actionHandler = new ActionHandler();
		buttonGroup = new ButtonGroup();
		
		for (EShapeManager eButton: EShapeManager.values()) {
			JRadioButton button = new JRadioButton();
			button.setIcon(new ImageIcon(eButton.getIconName()));
			button.setSelectedIcon(new ImageIcon(eButton.getIconSLTName()));
			this.add(button);
			buttonGroup.add(button);
			button.addActionListener(actionHandler);
			button.setActionCommand(eButton.name());
		}
	}
	
	public void init(CDrawingPanel drawingPanel) {
		// set associations
		this.drawingPanel = drawingPanel;
		// set association attributes
		JRadioButton button = (JRadioButton) this.getComponentAtIndex(EShapeManager.Rectangle.ordinal());
		button.doClick();
	}
	
	public class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			CShapeManager tool = EShapeManager.valueOf(e.getActionCommand()).newTool();
			drawingPanel.setCurrentTool(tool);
		}
	}
}