package com.erikpaluka.multileap.leap;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Pointable;

/**
 * The FingerObj class represents a tracked finger.
 * @author Erik Paluka
 *
 */
public class FingerObj extends PointableObj {
	
	public float[][][] bases;
	public float[] btipPosition;
	public float[] carpPosition;
	public float[] dipPosition;
	public float[] mcpPosition;
	public float[] pipPosition;
	public Finger.Type type;
	
	public FingerObj(ControllerObj controller) {
		super(controller);
	}
	
	public FingerObj(PointableObj p, ControllerObj controller) {
		super(controller);
		
		if (p.isFinger()) {
			copy(p);
		}
	}
	
	public FingerObj(Pointable p, FrameObj frame, ControllerObj controller) {
		super(controller);
		
		if (p.isFinger()) {
			copy(p, frame);
		}
	}
	
	public FingerObj(FingerObj f, ControllerObj controller) {
		super(controller);
		
		copy(f);
		bases = f.bases;
		btipPosition = f.btipPosition;
		carpPosition = f.carpPosition;
		dipPosition = f.dipPosition;
		mcpPosition = f.mcpPosition;
		pipPosition = f.pipPosition;
		type = f.type;
	}
	
	public FingerObj(Finger f, FrameObj frame, ControllerObj controller) {
		super(controller);
		
		copy(f, frame);
		//bases = f.bases;
		//btipPosition = f.btipPosition;
		//carpPosition = f.carpPosition;
		//dipPosition = f.dipPosition;
		//mcpPosition = f.mcpPosition;
		//pipPosition = f.pipPosition;
		type = f.type();
	}
	
	/*
	/**
	 * The bone at a given bone index on this finger.
	 * @param boneIx
	 * @return
	 */
	/*
	public Bone bone(Bone.Type boneIx) {
		
	}
	*/
	
	/**
	 * The name of this finger.
	 * @return
	 */
	public Finger.Type type() {
		return type;
	}
}
