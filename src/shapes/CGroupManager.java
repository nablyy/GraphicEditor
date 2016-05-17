package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import constants.CConstants.EDrawingType;

public class CGroupManager extends CShapeManager {

	private static final long serialVersionUID = 1L;
	
	private Vector<CShapeManager> groupList;
	
	public CGroupManager() {
		super(EDrawingType.TwoPoint);
		this.shape = new Rectangle();
		groupList = new Vector<CShapeManager>();
		// TODO Auto-generated constructor stub
	}

	public Vector<CShapeManager> getGroupList() {
		return groupList;
	}

	@Override
	public void setOrgin(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setLocation(x, y);
		rectangle.setSize(0, 0);
	}

	@Override
	public void movePoint(int x, int y) {
		// TODO Auto-generated method stub
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x - rectangle.x, y - rectangle.y);
	}

	@Override
	public void addPoint(int x, int y) {}
	
	public void add(CShapeManager newShape) {
		groupList.add(0, newShape);
		if (groupList.size() == 1) {
			this.shape = newShape.getBounds();
		} else {
			this.shape = this.shape.getBounds().createUnion(
					newShape.getBounds());
		}
	}
	
	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		for (CShapeManager shape : groupList) {
			shape.draw(g2D);
		}
		if (this.isSelected()) {
			g2D.draw(this.shape);
			this.setSelected(true);
			super.draw(g2D);
		}
	}
	
	@Override
	public CShapeManager newClone() {
		// TODO Auto-generated method stub
		return new CGroupManager();
	}

}
