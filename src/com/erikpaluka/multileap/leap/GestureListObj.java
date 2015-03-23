package com.erikpaluka.multileap.leap;

import java.util.ArrayList;
import java.util.Iterator;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;

/**
 * The GestureListObj class represents a list of GestureObj objects.
 * @author Erik Paluka
 *
 */
public class GestureListObj implements Iterable<GestureObj> {

	public ArrayList<GestureObj> gestures;
	public ControllerObj controller;
	
	/**
	 * Constructs an empty list of gestures.
	 */
	public GestureListObj(ControllerObj controller) {
		this.controller = controller;
		gestures = new ArrayList<GestureObj>();
	}
	
	public GestureListObj(GestureList list, FrameObj frame, ControllerObj controller) {
		this.controller = controller;
		gestures = new ArrayList<GestureObj>();
		
		for (Gesture g : list) {
			gestures.add(new GestureObj(g, frame, controller));
		}
	}
	
	/*
	/**
	 * Appends the members of the specified GestureListObj to this GestureListObj.
	 * @param list
	 * @return
	 */
	/*
	public GestureListObj append(GestureListObj list) {
		
		for (GestureObj g : list.gestures) {
			gestures.add(g);
		}
		
	}
	*/
	
	
	/**
	 * Returns the number of gestures in this list.
	 * @return
	 */
	public int count() {
		return gestures.size();
	}
	
	/**
	 * Access a list member by its position in the list.
	 * @param index
	 * @return
	 */
	public GestureObj get(int index) {
		return gestures.get(index);
	}
	
	/**
	 * Reports whether the list is empty.
	 * @return
	 */
	public boolean isEmpty() {
		return gestures.isEmpty();
	}

	@Override
	public Iterator<GestureObj> iterator() {
		return gestures.iterator();
	}
}
