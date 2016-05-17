package frames;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import constants.CConstants;
import menus.CMenuBar;

public class CFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private CMenuBar menuBar;
	private CToolbar toolbar;
	private CDrawingPanel drawingPanel;
	
	// 1st phase initialization
	public CFrame() {
		// attributes initialization
		this.setLocation(CConstants.FRAME_X, CConstants.FRAME_Y);
		this.setSize(CConstants.FRAME_W, CConstants.FRAME_H);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// components initialization
		this.menuBar = new CMenuBar();
		this.setJMenuBar(this.menuBar);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.toolbar = new CToolbar();
		this.add(this.toolbar, BorderLayout.NORTH);
		this.drawingPanel = new CDrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);
	}
	
	// 2nd phase initialization
	public void init() {
		// components association initialization
		this.menuBar.init(this.drawingPanel);
		this.toolbar.init(this.drawingPanel);
		this.drawingPanel.init();
		
		// associated attributes initialization
		this.setVisible(true);
	}
}
