package com.erikpaluka.multileap.leap;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Gesture;

public class SwipeGestObj extends GestureObj {

	public float[] position;
	public float[] direction;
	public double speed;
	public float[] startPosition;
	
	public SwipeGestObj(ControllerObj controller) {
		super(controller);
	}
	
	public SwipeGestObj(Gesture g, FrameObj f, ControllerObj controller) {
		super(g, f, controller);
	}
}
