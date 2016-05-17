package shapes;

import java.awt.Polygon;

import constants.CConstants.EDrawingType;

public class CPolygonManager extends CShapeManager {
	private static final long serialVersionUID = 1L;
	
	public CPolygonManager() {
		super(EDrawingType.NPoint);
		this.shape = new Polygon();
	}
	@Override
	public void setOrgin(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.addPoint(x, y);
		polygon.addPoint(x, y);
	}
	@Override
	public void addPoint(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.addPoint(x, y);
	}
	@Override
	public void movePoint(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
	}
	@Override
	public CShapeManager newClone() {
		// TODO Auto-generated method stub
		return new CPolygonManager();
	}
}
