package com.erikpaluka.multileap.leap;

import com.leapmotion.leap.InteractionBox;

public class InteractionBoxObj {
	/**
	 * The center of the InteractionBox in device coordinates (millimeters). This point is equidistant from all sides of the box.
	 */
	public com.leapmotion.leap.Vector center;
	/**
	 * The width, height, and depth of the InteractionBox in millimeters, as an array.
	 */
	public com.leapmotion.leap.Vector size;
	
	public InteractionBox internalBox;
	
	public InteractionBoxObj() {
		
	}
	
	/**
	 * Creates an InteractionBoxObj object from a LeapMotion InteractionBox object
	 * @param ib
	 */
	public InteractionBoxObj(InteractionBox ib) {
		
		this.center = ib.center();
		this.size = new com.leapmotion.leap.Vector(ib.width(), ib.height(), ib.depth());
		internalBox = ib;
	}

	/**
	 * The center of the InteractionBox in device coordinates (millimeters).
     * This point is equidistant from all sides of the box.
	 * @return
	 */
	public com.leapmotion.leap.Vector center() {
		return center;
	}
	
	/**
	 * The depth of the InteractionBox in millimeters, measured along the z-axis.
	 * @return
	 */
	public float depth() {
		return size.getZ();
	}
	
	/**
	 * The height of the InteractionBox in millimeters, measured along the y-axis.
	 * @return
	 */
	public float height() {
		return size.getY();
	}
	
	/**
	 * The width of the InteractionBox in millimeters, measured along the x-axis.
	 * @return
	 */
	public float width() {
		return size.getX();
	}
}
