package com.erikpaluka.multileap.leap;

import com.leapmotion.leap.Arm;
import com.leapmotion.leap.Matrix;

/**
 * The ArmObj class represents the forearm.
 * @author Erik Paluka
 *
 */
public class ArmObj {

	public Matrix basis;
	public com.leapmotion.leap.Vector center;
	public com.leapmotion.leap.Vector direction;
	public com.leapmotion.leap.Vector elbowPosition;
	public float width;
	public com.leapmotion.leap.Vector wristPosition;
	
	public ArmObj() {
		
	}
	
	public ArmObj(Arm arm) {
		basis = arm.basis();
		center = arm.center();
		direction = arm.direction();
		elbowPosition = arm.elbowPosition();
		width = arm.width();
		wristPosition = arm.wristPosition();
	}
	
	/**
	 * The orthonormal basis vectors for the Arm bone as a Matrix.
     * Basis vectors specify the orientation of a bone.
	 * @return
	 */
	public Matrix basis() {
		return basis;
	}
	
	/**
	 * The center of the forearm.
     * This location represents the midpoint of the arm between the wrist position and the elbow position.
	 * @return
	 */
	public com.leapmotion.leap.Vector center() {
		return center;
	}
	
	/**
	 * The normalized direction in which the arm is pointing (from elbow to wrist).
	 * @return
	 */
	public com.leapmotion.leap.Vector direction() {
		return direction;
	}
	
	/**
	 * The position of the elbow.
	 * @return
	 */
	public com.leapmotion.leap.Vector elbowPosition() {
		return elbowPosition;
	}
	
	/**
	 * The average width of the arm.
	 * @return
	 */
	public float width() {
		return width;
	}
	
	/**
	 * The position of the wrist.
	 * @return
	 */
	public com.leapmotion.leap.Vector wristPosition() {
		return wristPosition;
	}
}
