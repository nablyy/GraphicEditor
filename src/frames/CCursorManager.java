package frames;

import java.awt.Cursor;

import shapes.CShapeManager;
import constants.CConstants.ETransformationState;

public class CCursorManager {
	
	private static Cursor DEFAULT_CURSOR, DRAW_CURSOR, MOVE_CURSOR, ROTATE_CURSOR,
	EE_RESIZE_CURSOR,WW_RESIZE_CURSOR,SS_RESIZE_CURSOR,NN_RESIZE_CURSOR,
	SE_RESIZE_CURSOR,NE_RESIZE_CURSOR,SW_RESIZE_CURSOR,NW_RESIZE_CURSOR;

	public CCursorManager () {
		DEFAULT_CURSOR 		= new Cursor(Cursor.DEFAULT_CURSOR);
		DRAW_CURSOR 		= new Cursor(Cursor.CROSSHAIR_CURSOR);
		MOVE_CURSOR 		= new Cursor(Cursor.MOVE_CURSOR);
		ROTATE_CURSOR 		= new Cursor(Cursor.HAND_CURSOR);
		EE_RESIZE_CURSOR 	= new Cursor(Cursor.E_RESIZE_CURSOR);
		WW_RESIZE_CURSOR 	= new Cursor(Cursor.W_RESIZE_CURSOR);
		SS_RESIZE_CURSOR 	= new Cursor(Cursor.S_RESIZE_CURSOR);
		NN_RESIZE_CURSOR 	= new Cursor(Cursor.N_RESIZE_CURSOR);
		SE_RESIZE_CURSOR 	= new Cursor(Cursor.SE_RESIZE_CURSOR);
		NE_RESIZE_CURSOR 	= new Cursor(Cursor.NE_RESIZE_CURSOR);
		SW_RESIZE_CURSOR 	= new Cursor(Cursor.SW_RESIZE_CURSOR);
		NW_RESIZE_CURSOR 	= new Cursor(Cursor.NW_RESIZE_CURSOR);		
	}
	public Cursor getCursor(CShapeManager shapeManager) {
		if (shapeManager==null)
			return DEFAULT_CURSOR;
		if (shapeManager.getETransformationState()==ETransformationState.draw)
			return DRAW_CURSOR;
		if (shapeManager.getETransformationState()==ETransformationState.move)
			return MOVE_CURSOR;
		switch (shapeManager.getEAnchorType()) { 
		case EE: return EE_RESIZE_CURSOR;
		case WW: return WW_RESIZE_CURSOR;
		case SS: return SS_RESIZE_CURSOR;
		case NN: return NN_RESIZE_CURSOR;
		case SE: return SE_RESIZE_CURSOR;
		case NE: return NE_RESIZE_CURSOR;
		case SW: return SW_RESIZE_CURSOR;
		case NW: return NW_RESIZE_CURSOR;
		case RR: return ROTATE_CURSOR;
		default: return DEFAULT_CURSOR;
		}
	}
}
