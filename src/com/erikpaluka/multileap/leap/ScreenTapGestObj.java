package com.erikpaluka.multileap.leap;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Gesture;

public class ScreenTapGestObj extends GestureObj {

	public float[] position;
	public float[] direction;
	
	public ScreenTapGestObj(ControllerObj controller) {
		super(controller);
	}
	
	public ScreenTapGestObj(Gesture g, FrameObj f, ControllerObj controller) {
		super(g, f, controller);
	}
}
