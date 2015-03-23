package com.erikpaluka.multileap.leap;

import java.util.ArrayList;
import java.util.Iterator;

import com.erikpaluka.multileap.input.ControllerObj;
import com.leapmotion.leap.Tool;
import com.leapmotion.leap.ToolList;

/**
 * The ToolListObj class represents a list of ToolObj objects.
 * @author Erik Paluka
 *
 */
public class ToolListObj implements Iterable<ToolObj> {

	public ArrayList<ToolObj> tools;
	ControllerObj controller;
	
	/**
	 * Constructs an empty list of tools.
	 */
	public ToolListObj(ControllerObj controller) {
		this.controller = controller;
		tools = new ArrayList<ToolObj>();
	}
	
	/**
	 * Constructs an list of tools from the provided list.
	 */
	public ToolListObj(ToolList list, FrameObj frame, ControllerObj controller) {
		this.controller = controller;
		tools = new ArrayList<ToolObj>();
		
		for (Tool t : list) {
			tools.add(new ToolObj(t, frame, controller));
		}
	}
	
	/*
	/**
	 * Appends the members of the specified ToolList to this ToolList.
	 * @param list
	 */
	/*
	public ToolListObj append(ToolListObj list) {
		for (ToolObj t : list.tools) {
			tools.add(t);
		}
	}
	*/
	
	/**
	 * Returns the number of tools in this list.
	 * @return
	 */
	public int count() {
		return tools.size();
	}
	
	/**
	 * The member of the list that is farthest to the front within the standard Leap Motion frame of reference (i.e has the smallest Z coordinate).
	 * @return
	 */
	public ToolObj frontmost() {
		ToolObj fMost = null;
		
		for (ToolObj t : tools) {
			
			if (fMost == null || t.tipPosition.getZ() < fMost.tipPosition.getZ()) {
				fMost = t;
			}
		}
		
		return fMost;
	}
	
	/**
	 * Access a list member by its position in the list.
	 * @param index
	 * @return
	 */
	public ToolObj get(int index) {
		return tools.get(index);
	}
	
	/**
	 * Reports whether the list is empty.
	 * @return
	 */
	public boolean isEmpty() {
		return tools.isEmpty();
	}
	
	/**
	 * The member of the list that is farthest to the left within the standard Leap Motion frame of reference (i.e has the smallest X coordinate).
	 * @return
	 */
	public ToolObj leftmost() {
		ToolObj lMost = null;
		
		for (ToolObj t : tools) {
			
			if (lMost == null || t.tipPosition.getX() < lMost.tipPosition.getX()) {
				lMost = t;
			}
		}
		
		return lMost;
	}
	
	/**
	 * The member of the list that is farthest to the right within the standard Leap Motion frame of reference (i.e has the largest X coordinate).
	 * @return
	 */
	public ToolObj rightmost() {
		ToolObj rMost = null;
		
		for (ToolObj t : tools) {
			
			if (rMost == null || t.tipPosition.getX() > rMost.tipPosition.getX()) {
				rMost = t;
			}
		}
		
		return rMost;
	}

	@Override
	public Iterator<ToolObj> iterator() {
		return tools.iterator();
	}
}
