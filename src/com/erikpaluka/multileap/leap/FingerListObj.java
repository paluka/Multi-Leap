package com.erikpaluka.multileap.leap;

import java.util.ArrayList;
import java.util.Iterator;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;

/**
 * The FingerListObj class represents a list of FingerObj objects.
 * @author Erik Paluka
 *
 */
public class FingerListObj implements Iterable<FingerObj> {

	public ArrayList<FingerObj> fingers;
	public ControllerObj controller;
	
	/**
	 * Constructs an empty list of fingers.
	 */
	public FingerListObj(ControllerObj controller) {
		this.controller = controller;
		fingers = new ArrayList<FingerObj>();
	}
	
	public FingerListObj(FingerList list, FrameObj frame, ControllerObj controller) {
		this.controller = controller;
		fingers = new ArrayList<FingerObj>();
		
		for (Finger f : list) {
			fingers.add(new FingerObj(f, frame, controller));
		}
	}
	
	public void add(FingerObj f) {
		fingers.add(f);
	}
	
	/*
	/**
	 * Appends the members of the specified FingerList to this FingerList.
	 * @param list
	 */
	/*
	public FingerListObj append(FingerListObj list) {
		for (FingerObj f : list.fingers) {
			fingers.add(f);
		}
	}
	*/
	
	/**
	 * Returns the number of fingers in this list.
	 * @return
	 */
	public int count() {
		return fingers.size();
	}
	
	/**
	 * Returns a new list containing those fingers in the current list that are extended.
	 * @return
	 */
	public FingerListObj extended() {
		FingerListObj list = new FingerListObj(controller);
		
		for (FingerObj f : fingers) {
			
			if (f.isExtended()) {
				list.fingers.add(f);
			}
		}
		return list;
	}
	
	/**
	 * Returns a list containing fingers from the current list of a given finger type by modifying the existing list.
	 * @param type
	 * @return
	 */
	public FingerListObj fingerType(Finger.Type type) {
		
		FingerListObj list = new FingerListObj(controller);
		
		for (FingerObj f : fingers) {
			
			if (f.type == type) {
				list.fingers.add(f);
			}
		}
		return list;
	}
	
	/**
	 * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate).
	 * @return
	 */
	public FingerObj frontmost() {
		FingerObj fMost = null;
		
		for (FingerObj f : fingers) {
			
			if (fMost == null || f.tipPosition.getZ() < fMost.tipPosition.getZ()) {
				fMost = f;
			}
		}
		
		return fMost;
	}
	
	/**
	 * Access a list member by its position in the list.
	 * @param index
	 * @return
	 */
	public FingerObj get(int index) {
		return fingers.get(index);
	}
	
	/**
	 * Reports whether the list is empty.
	 * @return
	 */
	public boolean isEmpty() {
		return fingers.isEmpty();
	}
	
	/**
	 * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate).
	 * @return
	 */
	public FingerObj leftmost() {
		FingerObj lMost = null;
		
		for (FingerObj f : fingers) {
			
			if (lMost == null || f.tipPosition.getX() < lMost.tipPosition.getX()) {
				lMost = f;
			}
		}
		
		return lMost;
	}
	
	/**
	 * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate).
	 * @return
	 */
	public FingerObj rightmost() {
		FingerObj rMost = null;
		
		for (FingerObj f : fingers) {
			
			if (rMost == null || f.tipPosition.getX() > rMost.tipPosition.getX()) {
				rMost = f;
			}
		}
		
		return rMost;
	}

	@Override
	public Iterator<FingerObj> iterator() {
		return fingers.iterator();
	}
}
