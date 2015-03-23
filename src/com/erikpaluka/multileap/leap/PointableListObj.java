package com.erikpaluka.multileap.leap;

import java.util.ArrayList;
import java.util.Iterator;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Pointable;
import com.leapmotion.leap.PointableList;

/**
 * The PointableListObj class represents a list of PointableObj objects.
 * PointableObj objects include entities that can be pointed, such as fingers and tools.
 * @author Erik Paluka
 *
 */
public class PointableListObj implements Iterable<PointableObj> {

	public ArrayList<PointableObj> pointables;
	public ControllerObj controller;
	
	/**
	 * Constructs an empty list of pointables.
	 */
	public PointableListObj(ControllerObj controller) {
		pointables = new ArrayList<PointableObj>();
		this.controller = controller;
	}
	
	public PointableListObj(PointableList list, FrameObj frame, ControllerObj controller) {
		pointables = new ArrayList<PointableObj>();
		
		for (Pointable p : list) {
			pointables.add(new PointableObj(p, frame, controller));
		}
		
		this.controller = controller;
	}
	
	public void add(PointableObj p) {
		pointables.add(p);
	}
	
	/*
	/**
	 * Appends the members of the specified PointableList to this PointableList.
	 * @param list
	 * @return
	 */
	/*
	public PointableListObj append(PointableListObj list) {
		
		for (PointableObj p : list.pointables) {
			pointables.add(p);
		}
		
	}
	*/
	
	/*
	/**
	 * Appends the members of the specified FingerList to this PointableList.
	 * @param list
	 * @return
	 */
	/*
	public PointableListObj append(FingerListObj list) {
		
		for (FingerObj f : list.fingers) {
			pointables.add(f);
		}
	}
	*/

	/*
	/**
	 * Appends the members of the specified ToolList to this PointableList.
	 * @param list
	 * @return
	 */
	/*
	public PointableListObj append(ToolListObj list) {
		
		for (ToolObj t : list.tools) {
			pointables.add(t);
		}
	}
	*/
	
	/**
	 * Returns the number of pointables in this list.
	 * @return
	 */
	public int count() {
		return pointables.size();
	}
	
	/**
	 * Returns a new list containing those pointables in the current list that are extended.
	 * @return
	 */
	public PointableListObj extended() {
		PointableListObj list = new PointableListObj(controller);
		
		for (PointableObj p : pointables) {
			
			if (p.isExtended()) {
				list.pointables.add(p);
			}
		}
		return list;
	}
	
	/**
	 * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate).
	 * @return
	 */
	public PointableObj frontmost() {
		PointableObj fMost = null;
		
		for (PointableObj p : pointables) {
			
			if (fMost == null || p.tipPosition.getZ() < fMost.tipPosition.getZ()) {
				fMost = p;
			}
		}
		
		return fMost;
	}
	
	/**
	 * Access a list member by its position in the list.
	 * @param index
	 * @return
	 */
	public PointableObj get(int index) {
		return pointables.get(index);
	}
	
	/**
	 * Reports whether the list is empty.
	 * @return
	 */
	public boolean isEmpty() {
		return pointables.isEmpty();
	}
	
	/**
	 * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate).
	 * @return
	 */
	public PointableObj leftmost() {
		PointableObj lMost = null;
		
		for (PointableObj p : pointables) {
			
			if (lMost == null || p.tipPosition.getX() < lMost.tipPosition.getX()) {
				lMost = p;
			}
		}
		
		return lMost;
	}
	
	/**
	 * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate).
	 * @return
	 */
	public PointableObj rightmost() {
		PointableObj rMost = null;
		
		for (PointableObj p : pointables) {
			
			if (rMost == null || p.tipPosition.getX() > rMost.tipPosition.getX()) {
				rMost = p;
			}
		}
		
		return rMost;
	}

	@Override
	public Iterator<PointableObj> iterator() {
		return pointables.iterator();
	}
}
