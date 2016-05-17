package shapes;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Vector;

import constants.CConstants;
import constants.CConstants.EAnchorType;

public class CAnchors implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Vector<Shape> anchors;
	
	public CAnchors(Rectangle bounds) {
		anchors = new Vector<Shape>();		
		for (EAnchorType eanchorType: EAnchorType.values()) {
			Ellipse2D.Double shape = new Ellipse2D.Double();
			shape.setFrame(getLocation(bounds, eanchorType), new Dimension(CConstants.ANCHOR_W, CConstants.ANCHOR_H));
			anchors.add(shape);
		}
	}
	
	private Point getLocation(Rectangle bounds, EAnchorType eanchorType) {
		int x = bounds.x;
		int y = bounds.y;
		int w = bounds.width;
		int h = bounds.height;
		int dw = CConstants.ANCHOR_W/2;
		int dh = CConstants.ANCHOR_H/2;
	
		switch(eanchorType) {
		case NN: return new Point(x+w/2-dw, y-dh); 
		case NE: return new Point(x+w-dw, 	y-dh);
		case EE: return new Point(x+w-dw, 	y+h/2-dh);
		case SE: return new Point(x+w-dw, 	y+h-dh);
		case SS: return new Point(x+w/2-dw,	y+h-dh);
		case SW: return new Point(x-dw, 	y+h-dh);
		case WW: return new Point(x-dw, 	y+h/2-dh);
		case NW: return new Point(x-dw, 	y-dh);
		case RR: return new Point(x+w/2-dw, y-CConstants.ANCHOR_H*5-dh);
		default: return null;
		}
	}

	public int size() { return anchors.size(); }
	public Shape get(int i) { return anchors.get(i); }
	public Rectangle getBounds(EAnchorType eAnchorType) { return anchors.get(eAnchorType.ordinal()).getBounds();	}
	public void set(int i, Rectangle shape) { this.set(i, shape); }
	
	public void draw(Graphics2D g2D) {
		for (int i=0; i<anchors.size(); i++) {
			g2D.draw(anchors.get(i));
		}
	}	
	public EAnchorType contanins(int x, int y) {
		for (int i=0; i<anchors.size(); i++) {
			if (anchors.get(i).contains(x, y)) {
				return EAnchorType.values()[i];
			}
		}
		return null;
	}
	public void setTransformedShape(AffineTransform affineTrnasform) {
		for (int i=0; i<anchors.size(); i++) {
			Shape transformedShape = affineTrnasform.createTransformedShape(anchors.get(i));			
			double x = transformedShape.getBounds().getCenterX();
			double y = transformedShape.getBounds().getCenterY();
			Ellipse2D.Double anchor = new Ellipse2D.Double();
			anchor.setFrameFromCenter(x, y, x+CConstants.ANCHOR_W/2, y+CConstants.ANCHOR_H/2);
			anchors.set(i, anchor);
		}
	}
}

