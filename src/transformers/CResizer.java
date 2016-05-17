package transformers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

import constants.CConstants.EAnchorType;
import shapes.CAnchors;
import shapes.CGroupManager;
import shapes.CShapeManager;

public class CResizer extends CTransformer {

	private Vector<CShapeManager> groupChildList;
	
	public CResizer() {
		super();
	}
	private Point getResizeAnchor() {
		CAnchors anchors = shapeManager.getAnchors();
		Point resizeAnchor = new Point();
		switch (shapeManager.getEAnchorType()) { 
		case EE: resizeAnchor.setLocation(anchors.getBounds(EAnchorType.WW).getX(), 	0); 	 break;
		case WW: resizeAnchor.setLocation(anchors.getBounds(EAnchorType.EE).getX(), 	0); 	 break;
		case SS: resizeAnchor.setLocation(0, 			  								anchors.getBounds(EAnchorType.NN).getY()); break;
		case NN: resizeAnchor.setLocation(0, 			  								anchors.getBounds(EAnchorType.SS).getY()); break;
		case SE: resizeAnchor.setLocation(anchors.getBounds(EAnchorType.NW).getX(), 	anchors.getBounds(EAnchorType.NW).getY()); break;
		case NE: resizeAnchor.setLocation(anchors.getBounds(EAnchorType.SW).getX(), 	anchors.getBounds(EAnchorType.SW).getY()); break;
		case SW: resizeAnchor.setLocation(anchors.getBounds(EAnchorType.NE).getX(), 	anchors.getBounds(EAnchorType.NE).getY()); break;
		case NW: resizeAnchor.setLocation(anchors.getBounds(EAnchorType.SE).getX(), 	anchors.getBounds(EAnchorType.SE).getY()); break;
		default: break;
		}
		return resizeAnchor;
	}	
	@Override
	public void initTransforming(int x, int y) {
		oldP = new Point(x, y);
		// compute anchor point for resizing
		anchorP = getResizeAnchor();
		// normalize anchor point for scale
		affineTransform.setToTranslation(-anchorP.getX(), -anchorP.getY());
		getShapeManager().setShape(affineTransform.createTransformedShape(getShapeManager().getShape()));
		if (getShapeManager().isSelected()) {
			getShapeManager().getAnchors().setTransformedShape(affineTransform);
		}
	}
	private Point2D computeResizeFactor(Point previousP, Point currentP) {
		int deltaW = 0;
		int deltaH = 0;
		switch (shapeManager.getEAnchorType()) {
		case EE: deltaW=  currentP.x-previousP.x; 	deltaH=  0; 						break;
		case WW: deltaW=-(currentP.x-previousP.x);	deltaH=  0; 						break;
		case SS: deltaW=  0;						deltaH=  currentP.y-previousP.y; 	break;
		case NN: deltaW=  0;						deltaH=-(currentP.y-previousP.y); 	break;
		case SE: deltaW=  currentP.x-previousP.x; 	deltaH=  currentP.y-previousP.y;	break;
		case NE: deltaW=  currentP.x-previousP.x; 	deltaH=-(currentP.y-previousP.y);	break;
		case SW: deltaW=-(currentP.x-previousP.x);	deltaH=  currentP.y-previousP.y;	break;			
		case NW: deltaW=-(currentP.x-previousP.x);	deltaH=-(currentP.y-previousP.y);	break;
		default: break;
		}
		// compute resize 
		double currentW = shapeManager.getBounds().getWidth();
		double currentH = shapeManager.getBounds().getHeight();
		double xFactor = 1.0;
		double yFactor = 1.0;
		if (currentW > 0.0)
			xFactor = deltaW / currentW + xFactor;
		if (currentH > 0.0)			
			yFactor = deltaH / currentH + yFactor;
		
		return new Point2D.Double(xFactor, yFactor);
	}
	private Point2D computeCResizeFactor(Point previousP, Point currentP) {
		int deltaW = 0;
		int deltaH = 0;
		switch (shapeManager.getEAnchorType()) {
		case EE: deltaW=  currentP.x-previousP.x; 	deltaH=  0; 						break;
		case WW: deltaW=-(currentP.x-previousP.x);	deltaH=  0; 						break;
		case SS: deltaW=  0;						deltaH=  currentP.y-previousP.y; 	break;
		case NN: deltaW=  0;						deltaH=-(currentP.y-previousP.y); 	break;
		case SE: deltaW=  currentP.x-previousP.x; 	deltaH=  currentP.y-previousP.y;	break;
		case NE: deltaW=  currentP.x-previousP.x; 	deltaH=-(currentP.y-previousP.y);	break;
		case SW: deltaW=-(currentP.x-previousP.x);	deltaH=  currentP.y-previousP.y;	break;			
		case NW: deltaW=-(currentP.x-previousP.x);	deltaH=-(currentP.y-previousP.y);	break;
		default: break;
		}
		// compute resize 
		double currentW = shapeManager.getBounds().getWidth();
		double currentH = shapeManager.getBounds().getHeight();
		double xFactor = 1.0;
		double yFactor = 1.0;
		if (currentW > 0.0)
			xFactor = deltaW / currentW + xFactor;
		if (currentH > 0.0)			
			yFactor = deltaH / currentH + yFactor;
		
		return new Point2D.Double(xFactor, yFactor);
	}
	@Override
	public void keepTransforming(Graphics2D g2D, int x, int y) {
		AffineTransform saveAT = g2D.getTransform();
		g2D.translate(this.getAnchorP().getX(), this.getAnchorP().getY());
		this.getShapeManager().draw(g2D);
		Point2D resizeFactor = computeResizeFactor(oldP, new Point(x, y));		
		affineTransform.setToScale(resizeFactor.getX(), resizeFactor.getY());
		getShapeManager().setShape(affineTransform.createTransformedShape(getShapeManager().getShape()));
		if (getShapeManager().isSelected()) {
			getShapeManager().getAnchors().setTransformedShape(affineTransform);
		}
		if(getShapeManager() instanceof CGroupManager){
			CGroupManager groupChild = (CGroupManager)getShapeManager();
			groupChildList = groupChild.getGroupList();
			for(CShapeManager childShape : groupChildList){
				Point2D CresizeFactor = computeCResizeFactor(oldP, new Point(x, y));
				affineTransform.setToScale(CresizeFactor.getX(), CresizeFactor.getY());
				childShape.setShape(affineTransform.createTransformedShape(childShape.getShape()));
			}
		}
		oldP.setLocation(x, y);
		this.getShapeManager().draw(g2D);
		g2D.setTransform(saveAT);
	}
	@Override
	public void finishTransforming(int x, int y) {
		affineTransform.setToTranslation(anchorP.getX(), anchorP.getY());
		getShapeManager().setShape(affineTransform.createTransformedShape(getShapeManager().getShape()));
		if (getShapeManager().isSelected()) {
			getShapeManager().getAnchors().setTransformedShape(affineTransform);
		}
	}
	@Override
	public void addPoint(int x, int y) {
	}
	@Override
	public void getAllShapes(Vector<CShapeManager> shapes) {}
}
