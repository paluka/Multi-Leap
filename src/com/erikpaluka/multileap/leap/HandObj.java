package com.erikpaluka.multileap.leap;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Hand;

/**
 * The HandObj class reports the physical characteristics of a detected hand.
 * @author Erik Paluka
 *
 */
public class HandObj {

	public float confidence;
	public com.leapmotion.leap.Vector direction;
	public float[] elbow;
	public FrameObj frame;
	public float grabStrength;
	public long id;
	public com.leapmotion.leap.Vector palmNormal;
	public com.leapmotion.leap.Vector palmPosition;
	public com.leapmotion.leap.Vector palmVelocity;
	public float palmWidth;
	public float pinchStrength;
	public float[][] r;
	public float s;
	public com.leapmotion.leap.Vector sphereCenter;
	public float sphereRadius;
	public com.leapmotion.leap.Vector stabilizedPalmPosition;
	public float[] t;
	public double timeVisible;
	public String type;
	public com.leapmotion.leap.Vector wrist;
	
	public ControllerObj controller;
	
	//public FingerListObj fingerList;
	//public PointableListObj pointableList;
//	public long[] pointableIds;
	public ArmObj arm;
	
	
	public HandObj(ControllerObj controller) {
		//fingerList = new FingerListObj();
	//	pointableList = new PointableListObj();
		
		this.controller = controller;
		arm = new ArmObj();
	}
	
	public HandObj(Hand h, FrameObj f, ControllerObj controller) {
		this.controller = controller;
		//fingerList = new FingerListObj(h.fingers());
		//pointableList = new PointableListObj(h.pointables());
		
		arm = new ArmObj(h.arm());
		
		confidence = h.confidence();
		direction = h.direction();
		//elbow
		frame = f;
		grabStrength = h.grabStrength();
		id = h.id();
		palmNormal = h.palmNormal();
		palmPosition = h.palmPosition();
		palmVelocity = h.palmVelocity();
		palmWidth = h.palmWidth();
		pinchStrength = h.pinchStrength();
		//r
		//s
		sphereCenter = h.sphereCenter();
		sphereRadius = h.sphereRadius();
		stabilizedPalmPosition = h.stabilizedPalmPosition();
		//t
		timeVisible = h.timeVisible();
		
		if (h.isLeft()) {
			type = "left";
		} else {
			type = "right";
		}
		
		wrist = h.wristPosition();
		
		
	}
	
	/**
	 * The arm to which this hand is attached.
     * If the arm is not completely in view, Arm attributes are estimated based on 
     * the attributes of entities that are in view combined with typical human anatomy.
	 * @return
	 */
	public ArmObj arm() {
		return arm;
	}
	
	/*
	/**
	 * The orientation of the hand as a basis matrix.
     * The basis is defined as follows:
     * xAxis Positive in the direction of the pinky
     * yAxis Positive above the hand
     * zAxis Positive in the direction of the wrist
	 * @return
	 */
	/*
	public Matrix basis() {
		
		return
	}
	*/
	
	
	/**
	 * How confident we are with a given hand pose.
     * The confidence level ranges between 0.0 and 1.0 inclusive.
	 * @return
	 */
	public float confidence() {
		return confidence;
	}
	
	/**
	 * The direction from the palm position toward the fingers.
	 * @return
	 */
	public com.leapmotion.leap.Vector direction() {
		return direction;
	}
	
	/**
	 * The Finger object with the specified ID attached to this hand.
	 * @param id
	 * @return
	 */
	public FingerObj finger(int fID) {
		
		for (FingerObj f : frame.fingerList) {
			if (f.id() == fID && f.hand().id() == id) {
				return f;
			}
		}
		
		return null;
	}
	
	/**
	 * The list of Finger objects detected in this frame that are 
	 * attached to this hand, **NOT** given in order from thumb to pinky.
     * The list cannot be empty.
	 * @return
	 */
	public FingerListObj fingers() {
		FingerListObj fingerList = new FingerListObj(controller);
		
		for (FingerObj f : frame.fingerList) {
			if (f.handId == id) {
				fingerList.add(f);
			}
		}
		
		
		return fingerList;
	}
	
	/**
	 * The Frame associated with this Hand.
	 * @return
	 */
	public FrameObj frame() {
		return frame;
	}
	
	/**
	 * The strength of a grab hand pose.
     * The strength is zero for an open hand, and blends 
     * to 1.0 when a grabbing hand pose is recognized.
	 * @return
	 */
	public float grabStrength() {
		return grabStrength;
	}
	
	/**
	 * A unique ID assigned to this Hand object, whose value remains the 
	 * same across consecutive frames while the tracked hand remains visible.
	 * @return
	 */
	public int id() {
		return (int) id;
	}
	
	/**
	 * Identifies whether this Hand is a left hand.
	 * @return
	 */
	public boolean isLeft() {
		if (type.equalsIgnoreCase("left")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Identifies whether this Hand is a right hand.
	 * @return
	 */
	public boolean isRight() {
		if (type.equalsIgnoreCase("right")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * The normal vector to the palm.
	 * @return
	 */
	public com.leapmotion.leap.Vector palmNormal() {
		return palmNormal;
	}
	
	/**
	 * The center position of the palm in millimeters from the Leap Motion Controller origin.
	 * @return
	 */
	public com.leapmotion.leap.Vector palmPosition() {
		return controller.toGlobal(palmPosition, "hand");	
	}
	
	/**
	 * The rate of change of the palm position in millimeters/second.
	 * @return
	 */
	public com.leapmotion.leap.Vector palmVelocity() {
		return palmVelocity;
	}
	
	/**
	 * The estimated width of the palm when the hand is in a flat position.
	 * @return
	 */
	public float palmWidth() {
		return palmWidth;
	}
	
	/**
	 * The holding strength of a pinch hand pose.
	 * @return
	 */
	public float pinchStrength() {
		return pinchStrength;
	}
	
	/**
	 * The PointableObj object with the specified ID associated with this hand.
	 * @param id
	 * @return
	 */
	public PointableObj pointable(int pID) {
				
		for (PointableObj p : frame.pointables()) {
			
			if (p.id() == pID && p.hand().id() == id) {
				return p;
			}
		}
	
		return null;
	}
	
	/**
	 * The list of PointableObj objects detected in this frame that are 
	 * associated with this hand, given in arbitrary order.
     * The list will always contain 5 fingers.
	 * @return
	 */
	public PointableListObj pointables() {
		PointableListObj list = new PointableListObj(controller);
	
		for (PointableObj p : frame.pointables()) {
			
			if (p.hand().id() == id) {
				list.add(p);
			}
		}
		
		return list;
	}
	
	/*
	public float rotationAngle(FrameObj sinceFrame) {
		
	}
	public float rotationAngle(FrameObj sinceFrame, com.leapmotion.leap.Vector axis) {
		
	}
	public com.leapmotion.leap.Vector rotationAxis(FrameObj sinceFrame) {
		
	}
	public Matrix rotationMatrix(FrameObj sinceFrame) {
		
	}
	public float rotationProbability(FrameObj sinceFrame) {
		
	}
	public float scaleFactor(FrameObj sinceFrame) {
		
	}
	public float scaleProbability(FrameObj sinceFrame) {
		
	}
	*/
	
	/**
	 * The center of a sphere fit to the curvature of this hand.
	 * @return
	 */
	public com.leapmotion.leap.Vector sphereCenter() {
		return sphereCenter;
	}
	
	/**
	 * The radius of a sphere fit to the curvature of this hand.
     * This sphere is placed roughly as if the hand were holding a ball. 
     * Thus the size of the sphere decreases as the fingers are curled into a fist.
	 * @return
	 */
	public float sphereRadius() {
		return sphereRadius;
	}
	
	/**
	 * The stabilized palm position of this Hand.
	 * @return
	 */
	public com.leapmotion.leap.Vector stabilizedPalmPosition() {
		//System.out.println(controller.displayWidthmm + " " + controller.getClosestXBottom().getX() + " " + (controller.displayWidthmm + Math.abs(controller.getClosestXBottom().getX()) + stabilizedPalmPosition.getX()) + " " + 
		//		stabilizedPalmPosition.getX());
		
		return controller.toGlobal(stabilizedPalmPosition, "hand");
	}
	
	/**
	 * The duration of time this Hand has been visible to the Leap Motion Controller.
	 * @return
	 */
	public float timeVisible() {
		return (float) timeVisible;
	}
	
	/*
	public com.leapmotion.leap.Vector translation(FrameObj sinceFrame) {
		return;
	}
	
	public float translationProbability(FrameObj sinceFrame) {
		return;
	}
	*/
	
	/**
	 * The position of the wrist of this hand.
	 * @return
	 */
	public com.leapmotion.leap.Vector wristPosition() {
		return wrist;
	}
}
