package com.erikpaluka.multileap.leap;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Pointable;

/**
 * The PointableObj class reports the physical characteristics of a detected finger or tool.
 * @author Erik Paluka
 *
 */
public class PointableObj {

	public com.leapmotion.leap.Vector direction;
	public boolean extended;
	public long id;
	public float length;
	public com.leapmotion.leap.Vector stabilizedTipPosition;
	public double timeVisible;
	public com.leapmotion.leap.Vector tipPosition;
	public com.leapmotion.leap.Vector tipVelocity;
	public boolean tool;
	public float touchDistance;
	public com.leapmotion.leap.Pointable.Zone touchZone;
	public float width;
	public FrameObj frame;
	
	public long handId;
	//public HandObj hand;
	
	public ControllerObj controller;
	
	public PointableObj(ControllerObj controller) {
		this.controller = controller;
	}
	
	public PointableObj(Pointable p, FrameObj f, ControllerObj controller) {
		
		this.controller = controller;
		copy(p, f);
	}
	/**
	 * The direction in which this finger or tool is pointing.
	 * @return
	 */
	public com.leapmotion.leap.Vector direction() {
		return direction;
	}
	
	/**
	 * The Frame associated with this Pointable object.
	 * @return
	 */
	public FrameObj frame() {
		return frame;
	}
	
	/**
	 * The Hand associated with this pointable.
	 * @return
	 */
	public HandObj hand() {
		return frame.hand((int) handId);
	}
	
	/**
	 * A unique ID assigned to this Pointable object, whose value remains the same across consecutive frames while the tracked finger or tool remains visible.
	 * @return
	 */
	public int id() {
		return (int) id;
	}
	
	/**
	 * Whether or not this Pointable is in an extended posture.
     * A finger is considered extended if it is extended straight 
     * from the hand as if pointing. A finger is not extended when 
     * it is bent down and curled towards the palm. Tools are always extended.
	 * @return
	 */
	public boolean isExtended() {
		return extended;
	}
	
	/**
	 * Whether or not this Pointable is classified as a finger.
	 * @return
	 */
	public boolean isFinger() {
		return !tool;
	}
	
	/**
	 * Whether or not this Pointable is classified as a tool.
	 * @return
	 */
	public boolean isTool() {
		return tool;
	}
	
	/**
	 * The estimated length of the finger or tool in millimeters.
	 * @return
	 */
	public float length() {
		return length;
	}
	
	/**
	 * The stabilized tip position of this Pointable.
     * Smoothing and stabilization is performed in order to make this 
     * value more suitable for interaction with 2D content. The stabilized 
     * position lags behind the tip position by a variable amount, depending 
     * primarily on the speed of movement.
	 * @return
	 */
	public com.leapmotion.leap.Vector stabilizedTipPosition() {
		return controller.toGlobal(stabilizedTipPosition, "pointable");
	}
	
	public com.leapmotion.leap.Vector localStabilizedTipPosition() {
		return stabilizedTipPosition;
	}
	
	/**
	 * The duration of time this Pointable has been visible to the Leap Motion Controller.
	 * @return
	 */
	public double timeVisible() {
		return timeVisible;
	}
	
	/**
	 * The tip position in millimeters from the Leap Motion origin.
	 * @return
	 */
	public com.leapmotion.leap.Vector tipPosition() {
		return controller.toGlobal(tipPosition, "pointable");
	}
	
	public com.leapmotion.leap.Vector localTipPosition() {
		return tipPosition;
	}
	
	/**
	 * The rate of change of the tip position in millimeters/second.
	 * @return
	 */
	public com.leapmotion.leap.Vector tipVelocity() {
		return tipVelocity;
	}
	
	/**
	 * A value proportional to the distance between this Pointable object and the adaptive touch plane.
	 * @return
	 */
	public float touchDistance() {
		return touchDistance;
	}
	
	/**
	 * The current touch zone of this Pointable object.
	 * @return
	 */
	public Pointable.Zone touchZone() {
		
		/*if (touchZone.equalsIgnoreCase("none")) {
			return Zone.ZONE_NONE;
		} else if (touchZone.equalsIgnoreCase("hovering")) {
			return Zone.ZONE_HOVERING;
		} else if (touchZone.equalsIgnoreCase("touching")) {
			return Zone.ZONE_TOUCHING;
		} else {
			return null;
		}*/
		
		return touchZone;
	}
	
	/**
	 * The estimated width of the finger or tool in millimeters.
	 * @return
	 */
	public float width() {
		return width;
	}
	
	public void copy(PointableObj p) {
		this.direction = p.direction;
		this.extended = p.extended;
		this.handId = p.handId;
		this.id = p.id;
		this.length = p.length;
		this.stabilizedTipPosition = p.stabilizedTipPosition;
		this.timeVisible = p.timeVisible;
		this.tipPosition = p.tipPosition;
		this.tipVelocity = p.tipVelocity;
		this.tool = p.tool;
		this.touchDistance = p.touchDistance;
		this.touchZone = p.touchZone;
		this.width = p.width;
		this.frame = p.frame;		
	}
	
	public void copy(Pointable p, FrameObj f) {
	
		this.direction = p.direction();
		this.extended = p.isExtended();
		this.handId = p.hand().id();
		this.id = p.id();
		this.length = p.length();
		this.stabilizedTipPosition = p.stabilizedTipPosition();
		this.timeVisible = p.timeVisible();
		this.tipPosition = p.tipPosition();
		this.tipVelocity = p.tipVelocity();
		this.tool = p.isTool();
		this.touchDistance = p.touchDistance();
		this.touchZone = p.touchZone();
		this.width = p.width();
		this.frame = f;	
	}
}
