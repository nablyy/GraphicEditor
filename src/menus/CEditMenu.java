package menus;

import java.util.Vector;

import shapes.CGroupManager;
import shapes.CShapeManager;
import constants.CConstants.EMenuItem;


public class CEditMenu extends CMenu {	
	
	private static final long serialVersionUID = 1L;
	
	private Vector<CShapeManager> temps;
	private Vector<CShapeManager> saves;
	
	public CEditMenu(EMenuItem[] eMenuItems) {
		super(eMenuItems);
		temps = new Vector<CShapeManager>();
		saves = new Vector<CShapeManager>();
	}
	
	public void ddo() {
		drawingPanel.redo();
	}
	
	public void undo() {
		drawingPanel.undo();
	}
	
	public void cut() {
		saves.clear();
		for(CShapeManager s : this.drawingPanel.getShapeManagers()){
			temps.add(s);
		}
		for(CShapeManager s : temps) {
			if(s.isSelected()) {
				try {
					saves.add((CShapeManager)s.clone());
					this.drawingPanel.getShapeManagers().remove(s);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	      }
	      drawingPanel.repaint();
	}
	
	public void paste() {
		drawingPanel.resetSelections();
		for(CShapeManager s : saves) {
			try {
				s.setSelected(true);
				this.drawingPanel.getShapeManagers().add((CShapeManager)s.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		drawingPanel.repaint();
	}
	
	public void copy() {
		saves.clear();
		for(CShapeManager s : this.drawingPanel.getShapeManagers()){
			if(s.isSelected()) {
				try {
					saves.add((CShapeManager)s.clone());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void delete() {
		for(CShapeManager s : this.drawingPanel.getShapeManagers()){
			temps.add(s);
		}
		for(CShapeManager s : temps) {
			if(s.isSelected()) {
				this.drawingPanel.getShapeManagers().remove(s);
			}
		}
	      drawingPanel.repaint();
	}
	
	public void group(){this.drawingPanel.group(new CGroupManager());}
	public void ungroup(){this.drawingPanel.ungroup();}
}
