package com.erikpaluka.multileap.leap;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Pointable;

/**
 * The ToolObj class represents a tracked tool.
 * ToolObjs are PointableObj objects that the Leap Motion software has classified as a tool.
 * @author Erik Paluka
 *
 */
public class ToolObj extends PointableObj{
	
	public ToolObj(ControllerObj controller) {
		super(controller);
	}
	
	public ToolObj(PointableObj p, ControllerObj controller) {
		super(controller);
		
		if (p.isTool()) {
			
			copy(p);
		}
		
	}
	
	public ToolObj(Pointable p, FrameObj frame, ControllerObj controller) {
		super(controller);
		
		if (p.isTool()) {
			copy(p, frame);
		}
		
	}
}
