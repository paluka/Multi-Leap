package com.erikpaluka.multileap.input;

import java.util.ArrayList;
import java.util.Iterator;

public class ControllerList implements Iterable<ControllerObj> {

	public ArrayList<ControllerObj> controllers;
	
	public ControllerList() {
		controllers = new ArrayList<ControllerObj>();
	}
	
	
	public ControllerObj getMainController() {
		
		for (ControllerObj c : controllers) {
			
			if (!c.externalLeap) {
				return c;
			}
		}
		
		return null;
	}
	
	public ControllerObj getExtController() {
		
		for (ControllerObj c : controllers) {
			
			if (c.externalLeap) {
				return c;
			}
		}
		
		System.err.println("No external controller.");
		return null;
	}
	
	public void addController(ControllerObj c) {
		controllers.add(c);
	}
	
	public void update() {
		for (ControllerObj c : controllers) {
			c.updateController();
		}
	}
	
	public void stopAll() {
		for (ControllerObj c : controllers) {
			c.stopClient();
		}
	}
	
	@Override
	public Iterator<ControllerObj> iterator() {
		return controllers.iterator();
	}

}
