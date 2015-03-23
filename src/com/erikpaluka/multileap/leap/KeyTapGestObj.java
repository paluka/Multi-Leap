package com.erikpaluka.multileap.leap;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Gesture;

public class KeyTapGestObj extends GestureObj {
	
	public float[] position;
	public float[] direction;
	
	public KeyTapGestObj(ControllerObj controller) {
		super(controller);
	}
	
	public KeyTapGestObj(Gesture g, FrameObj f, ControllerObj controller) {
		super(g, f, controller);
	}
	

}
