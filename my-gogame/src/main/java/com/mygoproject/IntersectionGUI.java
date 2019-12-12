package com.mygoproject;

import com.mygoproject.BoardGUI.State;

public class IntersectionGUI {

	private State state = null;
	private State shape = null;
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return this.state;
	}
	
	public void setShape(State shape) {
		this.shape = shape;
	}
	
	public State getShape() {
		return this.shape;
	}
	
}