package com.mygoproject;

import com.mygoproject.BoardGUI.State;

public class IntersectionGUI {

	private State state = null;
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return this.state;
	}
	
}