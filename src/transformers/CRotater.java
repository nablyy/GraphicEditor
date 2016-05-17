package transformers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.CGroupManager;
import shapes.CShapeManager;

public class CRotater extends CTransformer {

	private Point centerP;
	private Vector<CShapeManager> groupChildList;
	
	public CRotater() {
		super();
	}	
	@Override
	public void initTransforming(int x, int y) {
		this.setOldP(x, y);
		centerP = new Point(
				(int)shapeManager.getBounds().getCenterX(), 
				(int)shapeManager.getBounds().getCenterY());
	}	
	private double computeRotationAngle(Point startP, Point previousP, Point currentP) {
		double startAngle = Math.toDegrees(
				Math.atan2(startP.getX()-previousP.getX(), startP.getY()-previousP.getY()));
		double endAngle = Math.toDegrees(
				Math.atan2(startP.getX()-currentP.getX(), startP.getY()-currentP.getY()));
		double angle = startAngle-endAngle;
		if (angle<0) angle += 360;
		return angle;
	}
	@Override
	public void keepTransforming(Graphics2D g2D, int x, int y) {
		AffineTransform saveAT = g2D.getTransform();
		g2D.translate(this.getAnchorP().getX(), this.getAnchorP().getY());
		this.getShapeManager().draw(g2D);
		double rotationAngle = computeRotationAngle(centerP, oldP, new Point(x, y));
		affineTransform.setToRotation(Math.toRadians(rotationAngle), centerP.getX(), centerP.getY());
		getShapeManager().setShape(affineTransform.createTransformedShape(getShapeManager().getShape()));
		if (getShapeManager().isSelected()) {
			getShapeManager().getAnchors().setTransformedShape(affineTransform);
		}
		if(getShapeManager() instanceof CGroupManager){
			CGroupManager groupChild = (CGroupManager)getShapeManager();
			groupChildList = groupChild.getGroupList();
			for(CShapeManager childShape : groupChildList){
				double rotationGAngle = computeRotationAngle(centerP, oldP, new Point(x, y));
				affineTransform.setToRotation(Math.toRadians(rotationGAngle), centerP.getX(), centerP.getY());
				childShape.setShape(affineTransform.createTransformedShape(childShape.getShape()));
			}
		}
		this.setOldP(x, y);
		this.getShapeManager().draw(g2D);
		g2D.setTransform(saveAT);
	}

	@Override
	public void finishTransforming(int x, int y) {}
	@Override
	public void addPoint(int x, int y) {}
	@Override
	public void getAllShapes(Vector<CShapeManager> shapes) {}
}
