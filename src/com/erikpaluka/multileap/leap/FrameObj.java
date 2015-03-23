package com.erikpaluka.multileap.leap;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Frame;

/**
 * The FrameObj class represents a set of hand and finger tracking data detected in a single frame.
 * @author Erik Paluka
 *
 */
public class FrameObj {

	
	public float frameRate;
	//public GestureObj[] gestures;
	//public HandObj[] hands;
	public long id;
	public InteractionBoxObj interactionBox;
	//public PointableObj[] pointables;
	public float[][] r;
	public float s;
	public float[] t;
	public long timeStamp;
	public ToolListObj toolList;
	public HandListObj handList;
	public GestureListObj gestureList;
	public FingerListObj fingerList;
	public PointableListObj pointableList;
	
	public ControllerObj controller;

	public FrameObj(ControllerObj controller) {
		this.controller = controller;
		this.toolList = new ToolListObj(controller);
		this.handList = new HandListObj(controller);
		this.gestureList = new GestureListObj(controller);
		this.fingerList = new FingerListObj(controller);
		this.pointableList = new PointableListObj(controller);
	}
	
	public FrameObj(Frame f, ControllerObj controller) {
		
		this.frameRate = f.currentFramesPerSecond();
		this.id = f.id();
		this.interactionBox = new InteractionBoxObj(f.interactionBox());
		//this.r
		//this.s
		//this.t
		this.timeStamp = f.timestamp();
		this.toolList = new ToolListObj(f.tools(), this, controller);
		this.handList = new HandListObj(f.hands(), this, controller);
		this.gestureList = new GestureListObj(f.gestures(), this, controller);
		this.fingerList = new FingerListObj(f.fingers(), this, controller);
		this.pointableList = new PointableListObj(f.pointables(), this, controller);
		
		this.controller = controller;
	}
	
	/**
	 * The instantaneous framerate.
	 * @return
	 */
	public float currentFramesPerSecond() {
		return frameRate;
	}
	
	/**
	 * The FingerObj object with the specified ID in this frame.
	 * @param id
	 * @return
	 */
	public FingerObj finger(int id) {
		
		for (FingerObj f : fingerList.fingers) {
			
			if (f.id() == id) {
				return f;
			}
		}
		
		return null;
	}
	
	/**
	 * The list of Finger objects detected in this frame, given in arbitrary order.
     * The list can be empty if no fingers are detected.
	 * @return
	 */
	public FingerListObj fingers() {
		return fingerList;
	}
	
	/**
	 * The GestureObj object with the specified ID in this frame.
	 * @param id
	 * @return
	 */
	public GestureObj gesture(int id) {
		
		for (GestureObj g : gestureList.gestures) {
			
			if (g.id() == id) {
				return g;
			}
		}
		
		return null;
	}
	
	/**
	 * The gestures recognized or continuing in this frame.
	 * @return
	 */
	public GestureListObj gestures() {
		return gestureList;
	}
	
	/*
	/**
	 * Returns a GestureList containing all gestures that have occurred since the specified frame.
	 * @param sinceFrame
	 * @return
	 */
	/*
	public GestureListObj gestures(FrameObj sinceFrame) {
		
	}
	*/
	
	/**
	 * The HandObj object with the specified ID in this frame.
	 * @param id
	 * @return
	 */
	public HandObj hand(int id) {
		
		for (HandObj h : handList.hands) {
			
			if (h.id() == id) {
				return h;
			}
		}
		
		return null;
	}
	
	/**
	 * The list of Hand objects detected in this frame, given in arbitrary order.
	 * @return
	 */
	public HandListObj hands() {
		return handList;
	}

	/**
	 * A unique ID for this Frame.
	 * @return
	 */
	public int id() {
		return (int) id;
	}
	
	
	/**
	 * The current InteractionBox for the frame.
	 * @return
	 */
	public InteractionBoxObj interactionBox() {
		return interactionBox;
	}
	
	
	/**
	 * The PointableObj object with the specified ID in this frame.
	 * @param id
	 * @return
	 */
	public PointableObj pointable(int id) {
		
		for (PointableObj p : pointableList.pointables) {
			
			if (p.id() == id) {
				return p;
			}
		}
		
		return null;
	}
	
	/**
	 * The list of PointableObj objects detected in this frame, given in arbitrary order.
	 * @return
	 */
	public PointableListObj pointables() {
		return pointableList;
	}
	
	/**
	 * The frame capture time in microseconds elapsed since the Leap started.
	 * @return
	 */
	public long timestamp() {
		return timeStamp;
	}
	
	/**
	 * The ToolObj object with the specified ID in this frame.
	 * @param id
	 * @return
	 */
	public ToolObj tool(int id) {
		
		for (ToolObj t : toolList.tools) {
			
			if (t.id() == id) {
				return t;
			}
		}
		
		return null;
	}
	
	/**
	 * The list of ToolObj objects detected in this frame, given in arbitrary order.
	 * @return
	 */
	public ToolListObj tools() {
		return toolList;
	}
	
	public HandListObj getHands(long[] handIds) {

		HandListObj list = new HandListObj(controller);
		
		for (long id : handIds) {
			for (HandObj h : handList.hands) {
				if (h.id == id) {
					list.hands.add(h);
					break;
				}
			}
		}
		
		return list;
	}
	
	public PointableListObj getPointables(long[] pointIds) {
		PointableListObj list = new PointableListObj(controller);

		for (long id : pointIds) {
			for (PointableObj p : pointableList.pointables) {
				if (p.id == id) {
					list.pointables.add(p);
					break;
				}
			}
		}
		
		return list;
	}
}
