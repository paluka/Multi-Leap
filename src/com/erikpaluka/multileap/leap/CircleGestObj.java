package com.erikpaluka.multileap.leap;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Gesture;

public class CircleGestObj extends GestureObj {
	
	public float[] center;
	public double radius;
	public float[] normal;
	public double progress;
	
	public CircleGestObj(ControllerObj controller) {
		super(controller);
	}
	
	public CircleGestObj(Gesture g, FrameObj f, ControllerObj controller) {
		super(g, f, controller);
	}
	
	/**
	 * The number of times the finger tip has traversed the circle.
	 * @return
	 */
	public float progress() {
		return (float) progress;
	}

}
