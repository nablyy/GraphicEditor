package frames;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import shapes.CGroupManager;
import shapes.CSelectionManager;
import shapes.CShapeManager;
import transformers.CTransformer;
import constants.CConstants;
import constants.CConstants.EDrawingState;
import constants.CConstants.EDrawingType;
import constants.CConstants.ETransformationState;

public class CDrawingPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	// association variable
	private CShapeManager currentTool;	
	// components
	private Stack<Vector<CShapeManager>> redo;
	private Stack<Vector<CShapeManager>> undo;
	private Vector<CShapeManager> shapeManagers;
	private Vector<CShapeManager> groupList;
	private MouseHandler mouseHandler;
	private CTransformer transformer;

	private CCursorManager cursorManager;
	private BasicStroke dashedLineStroke;
	// working variable
	private boolean bDirty;
	private Color lineColor;
	private Color fillColor;
	
	// setters & getters
	public void setCurrentTool(CShapeManager tool) {this.currentTool = tool;}
	public Vector<CShapeManager> getShapeManagers() {return shapeManagers;}
	public void setShapeManagers(Vector<CShapeManager> shapeManagers) {
		this.shapeManagers = shapeManagers;
		repaint();
	}
	public boolean isDirty() {return bDirty;}
	public void setDirty(boolean bDirty) {this.bDirty = bDirty;}
	
	@SuppressWarnings("unchecked")
	public CDrawingPanel() {
		currentTool = null;
		
		redo = new Stack<Vector<CShapeManager>>();
		undo = new Stack<Vector<CShapeManager>>();
		groupList = new Vector<CShapeManager>();
		shapeManagers = new Vector<CShapeManager>();
		undo.add((Vector<CShapeManager>) shapeManagers.clone());
		mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		transformer = null;
		
		cursorManager = new CCursorManager();
		float dashes[] = {CConstants.DEFAULT_DASH_OFFSET};
		dashedLineStroke = new BasicStroke(
				CConstants.DEFAULT_DASHEDLINE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashes, 0);
		this.bDirty = false;
		initColor();
	}
	
	public void init() {		
	}
	
	@Override
	public void paint(Graphics g) {
		// extends JPanel's paint
		super.paint(g);		
		for (CShapeManager shapeManager: shapeManagers) {
			shapeManager.draw(g);
		}
	}
	public void initPanel() {
		shapeManagers.clear();
		repaint();
		this.bDirty = false;
	}
	public void initColor(){
		lineColor = Color.black;
		fillColor = getBackground();
	}
	
	public CShapeManager onShape(int x, int y) {
		for (CShapeManager shapeManager: shapeManagers) {
			if (shapeManager.contains(x, y)) {
				return shapeManager;
			}
		}
		return null;
	}
	
	public void group(CGroupManager group) {
		boolean check = false;
    	for(int i = shapeManagers.size(); i > 0; i--) {
    		CShapeManager cShapeManager = shapeManagers.get(i - 1);
    		if(cShapeManager.isSelected()) {
    			cShapeManager.setSelected(false);
    			group.add(cShapeManager);
    			shapeManagers.remove(cShapeManager);	
    			check = true;
    		}
    	}
    	if(check) {
    		group.setSelected(true);
    		shapeManagers.add(group);
    	}
    	groupList = group.getGroupList();
    	repaint();
    	
	}

	public void ungroup() {
		Vector<CShapeManager>ungroupList = new Vector<>();
    	for(int i = shapeManagers.size(); i > 0; i--) {
    		CShapeManager shape = shapeManagers.get(i - 1); 
    		if(shape instanceof CGroupManager && shape.isSelected()) {
    			for(CShapeManager childShape : this.groupList) {
    				childShape.setSelected(true);
    				ungroupList.add(childShape);
    			}
    			shapeManagers.remove(shape);
    		}
    	}
    	shapeManagers.addAll(ungroupList);
		repaint();
	}
	
	public void selectTransfomer(int x, int y) {
		CShapeManager currentShape = onShape(x, y);
		this.setSelected(currentShape);
		if (currentShape==null) {
			currentShape = currentTool.newClone();
			currentShape.setLineColor(lineColor);
			currentShape.setFillColor(fillColor);
		}
		transformer = ETransformationState.values()[currentShape.getETransformationState().ordinal()].newTransformer();
		transformer.getAllShapes(shapeManagers);
		transformer.setGroupList(groupList);
		transformer.setShapeManager(currentShape);
		transformer.initTransforming(x, y);
	}
	
	public void keepTransforming(int x, int y) {
		Graphics2D g2D = (Graphics2D) getGraphics();
		g2D.setXORMode(getBackground());
		g2D.setStroke(dashedLineStroke);
		transformer.keepTransforming(g2D, x, y);
	}
	
	@SuppressWarnings("unchecked")
	public void finishTransforming(int x, int y) {
		if( ! ( transformer.getShapeManager() instanceof CSelectionManager)) {
			transformer.finishTransforming(x, y);
			if (transformer.getShapeManager().getETransformationState()==ETransformationState.draw) {
				undo.add((Vector<CShapeManager>) shapeManagers.clone());
				redo.clear();
				shapeManagers.add(transformer.getShapeManager());
				this.setSelected(transformer.getShapeManager());
			} else {
				undo.add((Vector<CShapeManager>) shapeManagers.clone());
				redo.clear();
				repaint();
			}
		} else {
			((CSelectionManager) transformer.getShapeManager()).init(shapeManagers);
			((CSelectionManager) transformer.getShapeManager()).selectShapes();
		}
		
		repaint();
		
		this.bDirty = true;
	}
	private void continueTransforming(int x, int y) {
		transformer.addPoint(x, y);
	}
	
	private void setSelected(CShapeManager selectedShape) {
		if (selectedShape==null) {
			for(CShapeManager shapeManager: shapeManagers) {
				shapeManager.setSelected(false);
			}
			repaint();
		} else {
			if (!selectedShape.isSelected()) {
				for(CShapeManager shapeManager: shapeManagers) {
					shapeManager.setSelected(false);
				}
				selectedShape.setSelected(true);
				repaint();
			}
		}
	}
	public void resetSelections() {
		for(CShapeManager shape : shapeManagers) {
			shape.setSelected(false);
		}
		repaint();
	}
	@SuppressWarnings("unchecked")
	public void redo() {
		if(redo.size() != 0) {
			undo.add((Vector<CShapeManager>)shapeManagers.clone());
			shapeManagers = redo.pop();
			repaint();
		}
	}
	@SuppressWarnings("unchecked")
	public void undo() {
		if(undo.size() != 0) {
			redo.add((Vector<CShapeManager>)shapeManagers.clone());
			shapeManagers = undo.pop();
			repaint();		
		}
	}

	public void setFillColor(Color fillColor) {
		if (colorSelection(false, fillColor)) {
			return;
		}
		this.fillColor = fillColor;
		System.out.println(this.fillColor);
	}

	public void setLineColor(Color lineColor) {
		if (colorSelection(true, lineColor)) {
			return;
		}
		this.lineColor = lineColor;
		System.out.println(this.lineColor);
	}
	
	public boolean colorSelection(boolean flag, Color color) {
		boolean returnValue = false;
		for (CShapeManager colorShape : shapeManagers) {
			if (colorShape.isSelected()) {
				if (flag) {
					colorShape.setLineColor(color);
				} else {
					colorShape.setFillColor(color);
				}
				returnValue = true;
			}
		}
		repaint();
		return returnValue;
	}
	
	// mousehadler
	public class MouseHandler implements MouseInputListener {
		private EDrawingState eDrawingState = EDrawingState.idle;
		private boolean bClicked = false;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
		}
		public void mouse1Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.idle) {
				selectTransfomer(e.getX(), e.getY());
				if (transformer.getShapeManager().getETransformationState()==ETransformationState.draw) {
					if (currentTool.getEDrawingType() == EDrawingType.NPoint) {
						eDrawingState = EDrawingState.NPDrawing;
					}
				}
			} else if (eDrawingState == EDrawingState.NPDrawing) {
				continueTransforming(e.getX(), e.getY());
			}
		}
		public void mouse2Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.NPDrawing) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.idle;
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.idle) {
				setCursor(cursorManager.getCursor(onShape(e.getX(), e.getY())));
			} else if (eDrawingState == EDrawingState.NPDrawing) {
				keepTransforming(e.getX(), e.getY());
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			bClicked = true;
			if (eDrawingState == EDrawingState.idle) {
				selectTransfomer(e.getX(), e.getY());
				if (transformer.getShapeManager().getETransformationState()==ETransformationState.draw) {
					if (currentTool.getEDrawingType() == EDrawingType.TwoPoint) {
						eDrawingState = EDrawingState.TPDrawing;
					}
				} else {
					eDrawingState = EDrawingState.transforming;
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			bClicked = false;
			if (eDrawingState == EDrawingState.TPDrawing || eDrawingState == EDrawingState.transforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.TPDrawing) {
				if (!bClicked) {
					finishTransforming(e.getX(), e.getY());
				}
				eDrawingState = EDrawingState.idle;
			} else if (eDrawingState == EDrawingState.transforming) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.idle;
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
