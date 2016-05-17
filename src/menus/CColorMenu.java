package menus;

import java.awt.Color;

import javax.swing.JColorChooser;

import constants.CConstants.EMenuItem;

public class CColorMenu extends CMenu {
	private static final long serialVersionUID = 1L;
	
	public CColorMenu(EMenuItem[] eMenuItems) {
		super(eMenuItems);
	}
	
	public void FillColor(){
		Color fillColor = JColorChooser.showDialog(null, "Fill Color Selection", null);
		if(fillColor != null){
			this.drawingPanel.setFillColor(fillColor);
		}
	}
	
	public void LineColor(){
		Color lineColor = JColorChooser.showDialog(null, "Line Color Selection", null);
		if(lineColor != null){
			this.drawingPanel.setLineColor(lineColor);
		}
	}
}
