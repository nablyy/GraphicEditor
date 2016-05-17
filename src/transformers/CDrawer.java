package transformers;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.CShapeManager;

public class CDrawer extends CTransformer {

	@SuppressWarnings("unused")
	private Vector<CShapeManager> shapes;
	
	public CDrawer() {
		super();
	}
	
	@Override
	//gets the Shape Vector list and shave in transformer class list
	public void getAllShapes(Vector<CShapeManager> shapes) {this.shapes = shapes;}
	
	@Override
	public void initTransforming(int x, int y) {
		this.setOldP(x, y);
		shapeManager.setOrgin(x, y);
	}
	@Override
	public void keepTransforming(Graphics2D g2D, int x, int y) {
		AffineTransform saveAT = g2D.getTransform();
		g2D.translate(this.getAnchorP().getX(), this.getAnchorP().getY());
		this.getShapeManager().draw(g2D);
		shapeManager.movePoint(x, y);
		this.getShapeManager().draw(g2D);
		g2D.setTransform(saveAT);
	}
	@Override
	public void finishTransforming(int x, int y) {
	}
	@Override
	public void addPoint(int x, int y) {
		this.shapeManager.addPoint(x, y);
	}
}
