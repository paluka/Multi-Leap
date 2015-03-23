package com.erikpaluka.multileap.leap;

import java.util.ArrayList;
import java.util.Iterator;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;

/**
 * The HandListObj class represents a list of HandObj objects.
 * @author Erik Paluka
 *
 */
public class HandListObj implements Iterable<HandObj> {

	public ArrayList<HandObj> hands;
	public ControllerObj controller;
	
	/**
	 * Constructs an empty list of hands.
	 */
	public HandListObj(ControllerObj controller) {
		hands = new ArrayList<HandObj>();
		this.controller = controller;
	}
	
	/**
	 * Constructs an list of hands from the provided list.
	 */
	public HandListObj(HandList list, FrameObj frame, ControllerObj controller) {
		hands = new ArrayList<HandObj>();
		this.controller = controller;
		
		for (Hand h : list) {
			hands.add(new HandObj(h, frame, controller));
		}
		
		
	}
	
	public void add(HandObj h) {
		hands.add(h);
	}
	
	/*
	/**
	 * Appends the members of the specified HandListObj to this HandListObj.
	 * @param list
	 * @return
	 */
	/*
	public HandListObj append(HandListObj list) {
		
		for (HandObj h : list.hands) {
			hands.add(h);
		}
		
	}
	*/
	
	
	/**
	 * Returns the number of hands in this list.
	 * @return
	 */
	public int count() {
		return hands.size();
	}
	
	/**
	 * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate).
	 * @return
	 */
	public HandObj frontmost() {
		HandObj fMost = null;
		
		for (HandObj h : hands) {
			
			if (fMost == null || h.palmPosition().getZ() < fMost.palmPosition().getZ()) {
				fMost = h;
			}
		}
		
		return fMost;
	}
	
	/**
	 * Access a list member by its position in the list.
	 * @param index
	 * @return
	 */
	public HandObj get(int index) {
		return hands.get(index);
	}
	
	/**
	 * Reports whether the list is empty.
	 * @return
	 */
	public boolean isEmpty() {
		return hands.isEmpty();
	}
	
	/**
	 * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate).
	 * @return
	 */
	public HandObj leftmost() {
		HandObj lMost = null;
		
		for (HandObj h : hands) {
			
			if (lMost == null || h.palmPosition().getX() < lMost.palmPosition().getX()) {
				lMost = h;
			}
		}
		
		return lMost;
	}
	
	/**
	 * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate).
	 * @return
	 */
	public HandObj rightmost() {
		HandObj rMost = null;
		
		for (HandObj h : hands) {
			
			if (rMost == null || h.palmPosition().getX() > rMost.palmPosition().getX()) {
				rMost = h;
			}
		}
		
		return rMost;
	}

	@Override
	public Iterator<HandObj> iterator() {
		return hands.iterator();
	}
}
