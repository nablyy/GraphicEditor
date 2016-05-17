package constants;

import menus.CColorMenu;
import menus.CEditMenu;
import menus.CFileMenu;
import menus.CMenu;
import shapes.CPolygonManager;
import shapes.CRectangleManager;
import shapes.CSelectionManager;
import shapes.CShapeManager;
import transformers.CDrawer;
import transformers.CMover;
import transformers.CResizer;
import transformers.CRotater;
import transformers.CTransformer;

public class CConstants {
	// frame attributes
	public final static int FRAME_X = 100;
	public final static int FRAME_Y = 100;
	public final static int FRAME_W = 400;
	public final static int FRAME_H = 600;
	
	public static final int DEFAULT_DASH_OFFSET = 4;
	public static final int DEFAULT_DASHEDLINE_WIDTH = 1;
	
	public static enum EJFrame {
		ToolBar,
		DrawingPanel;
	}
	
	public interface EMenuItem {
		public String getName();
	}
	// menu attributes
	public static enum EMenus {
		File("File", new CFileMenu(EFileMenuItems.values())),
		Edit("Edit", new CEditMenu(EEditMenuItems.values())),
		Color("Color", new CColorMenu(EColorMenuItems.values()));
		
		private String name;
		private CMenu menu;
		private EMenus(String name, CMenu menu) {
			this.name = name;
			this.menu = menu;
		}
		public String getName() {return name;}
		public CMenu newMenu() {return menu;}
	}
	public static enum EFileMenuItems implements EMenuItem {
		newFile("New"),
		open("Open"),
		save("Save"),
		saveAs("SaveAs"),
		exit("Exit");
		
		private String name;
		private EFileMenuItems(String name) {
			this.name = name;
		}
		public String getName() {return name;}
	}
	
	public static enum EEditMenuItems implements EMenuItem {
		ddo("Do"),
		undo("Undo"),
		cut("Cut"),
		paste("Paste"),
		copy("Copy"),
		delete("Delete"),
		group("Group"),
		ungroup("Ungroup");
		
		private String name;
		private EEditMenuItems(String name) {this.name = name;}
		public String getName() {return name;}
	}
	public static enum EColorMenuItems implements EMenuItem {
		FillColor("FillColor"),
		LineColor("LineColor");
		
		private String name;
		private EColorMenuItems(String name) {this.name = name;}
		public String getName() {return name;}
	}
	
	// toolbar attributes
	public final static int TOOLBAR_W = FRAME_W;
	public final static int TOOLBAR_H = 40;
	
	public static enum EShapeManager { 
		Selection("select.gif", "selectSLT.gif", new CSelectionManager()),
		Rectangle("rectangle.gif", "rectangleSLT.gif", new CRectangleManager()), 
		Ellipse("ellipse.gif", "ellipseSLT.gif", new CRectangleManager()), 
		Line("line.gif", "lineSLT.gif", new CRectangleManager()), 
		Polygon("polygon.gif", "polygonSLT.gif", new CPolygonManager());
		
		private String iconName;
		private String iconSLTName;
		private CShapeManager tool;
		
		private EShapeManager(String iconName, String iconSLTName, CShapeManager tool) {
			this.iconName = iconName;
			this.iconSLTName = iconSLTName;
			this.tool = tool;
		}
		public String getIconName() {return iconName;}
		public String getIconSLTName() {return iconSLTName;}
		public CShapeManager newTool() {return tool;}
	}
	
	public static enum EDrawingType {TwoPoint, NPoint;}
	public static enum EDrawingState {
		idle,
		TPDrawing,
		NPDrawing,
		transforming;
	}
	public static enum ETransformationState {
		draw(new CDrawer()),
		resize(new CResizer()),
		move(new CMover()),
		rotate(new CRotater());
		
		private CTransformer trnasformer;
		private ETransformationState(CTransformer trnasformer) {
			this.trnasformer = trnasformer;			
		}
		public CTransformer newTransformer() {
			return this.trnasformer;
		}
	}	
	public static enum EAnchorType {
		NN, NE, EE, SE, SS, SW, WW, NW, RR;
	}
	public static final int ANCHOR_W = 6;
	public static final int ANCHOR_H = 6;
	
	// drawingPanel attributes
	public static final String DEFAULTFILENAME = "test";
}








