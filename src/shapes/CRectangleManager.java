package shapes;

import java.awt.Rectangle;

import constants.CConstants.EDrawingType;

public class CRectangleManager extends CShapeManager {

	private static final long serialVersionUID = 1L;
	
	public CRectangleManager() {
		super(EDrawingType.TwoPoint);
		this.shape = new Rectangle();
	}

	@Override
	public void setOrgin(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setLocation(x, y);
		rectangle.setSize(0, 0);
	}
	@Override
	public void movePoint(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x-rectangle.x, y-rectangle.y);
	}
	@Override
	public void addPoint(int x, int y) {		
	}

	@Override
	public CShapeManager newClone() {
		// TODO Auto-generated method stub
		return new CRectangleManager();
	}
}
