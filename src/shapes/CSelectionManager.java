package shapes;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

import constants.CConstants;

public class CSelectionManager extends CRectangleManager {
	private static final long serialVersionUID = 1L;
	public Vector<CShapeManager> shapes;

	public void setShapes(Vector<CShapeManager> shapes) {this.shapes = shapes;}
	public void init(Vector<CShapeManager> shapes) {
		this.shapes = shapes;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		float dashes[] = {CConstants.DEFAULT_DASH_OFFSET};
		BasicStroke dashedLineStroke = new BasicStroke(CConstants.DEFAULT_DASHEDLINE_WIDTH,
	            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashes, 0);
		g2D.setStroke(dashedLineStroke);
		g2D.draw(shape);
	}
	
	public void selectShapes() {
		for(CShapeManager shapeManager: shapes)  {
			if(shape.contains(shapeManager.getBounds())) {
				shapeManager.setSelected(true);
			}
		}
	}
	
	public CShapeManager newClone() {
		return new CSelectionManager();
	}
}
