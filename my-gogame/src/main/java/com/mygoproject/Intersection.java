package com.mygoproject;

public class Intersection {
	
	enum States { FREE, BLACK, WHITE; }
	States s = States.FREE;
	
	public void changeState(String state) {
		if(state.equals("free")) {
			s = States.FREE;
		} else if(state.equals("black")) {
			s = States.BLACK;
		} else if(state.equals("white")) {
			s = States.WHITE;
		}
	}
}
