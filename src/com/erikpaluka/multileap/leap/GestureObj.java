package com.erikpaluka.multileap.leap;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Gesture;

/**
 * The GestureObj class represents a recognized movement by the user.
 * @author Erik Paluka
 *
 */
public class GestureObj {

	private final float MICRO_IN_SEC = 1000000;
	
	public long duration;
	public long id;
	public long[] handIds;
	public long[] pointableIds;
	//public HandListObj handList;
	//public PointableListObj pointableList;
	public Gesture.Type type;
	public Gesture.State state;
	public FrameObj frame;
	
	public ControllerObj controller;

	public GestureObj(ControllerObj controller) {
		this.controller = controller;
	}
	
	public GestureObj(Gesture g, FrameObj f, ControllerObj controller) {
		this.controller = controller;
		duration = g.duration();
		id = g.id();
		//handList = new HandListObj(g.hands());
		//pointableList = new PointableListObj(g.pointables());
		type = g.type();
		state = g.state();
		frame = f;
	}
	/**
	 * The elapsed duration of the recognized movement up to the frame containing this Gesture object, in microseconds.
	 * @return
	 */
	public long duration() {
		return duration;
	}
	
	/**
	 * The elapsed duration in seconds.
	 * @return
	 */
	public float durationSeconds() {
		return duration / MICRO_IN_SEC;
	}
	
	/**
	 * The Frame containing this Gesture instance.
	 * @return
	 */
	public FrameObj frame() {
		return frame;
	}
	
	/**
	 * The list of hands associated with this Gesture, if any.
	 * @return
	 */
	public HandListObj hands() {
		HandListObj list = new HandListObj(controller);
		
		for (Long id : handIds) {
			for (HandObj h : frame.hands()) {
				
				if (h.id() == id) {
					list.add(h);
				}
			}
		}
		return list;
	}
	
	/**
	 * The gesture ID.
	 * @return
	 */
	public int id() {
		return (int) id;
	}
	
	/**
	 * The finger performing the gesture.
	 * @return
	 */
	public PointableObj pointable() {
		
		for (Long id : pointableIds) {
			for (PointableObj p : frame.pointables()) {
				
				if (p.id() == id) {
					return p;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * The list of fingers and tools associated with this Gesture, if any.
     * If no Pointable objects are related to this gesture, the list is empty.
	 * @return
	 */
	public PointableListObj pointables() {
		PointableListObj list = new PointableListObj(controller);
		
		for (Long id : pointableIds) {
			for (PointableObj p : frame.pointables()) {
				
				if (p.id() == id) {
					list.add(p);
				}
			}
		}
		return list;
	}
	
	
	/**
	 * The gesture state.
	 * @return
	 */
	public Gesture.State state() {
		return state;
	}
	
	
	/**
	 * The gesture type.
	 * @return
	 */
	public Gesture.Type type() {
		return type;
	}
}
