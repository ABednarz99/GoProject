package com.mygoproject;

public class Intersection {
	
	/*
	enum States { FREE, BLACK, WHITE; }
	private States s = States.FREE;
	
	public void changeState(String state) {
		if(state.equals("free")) {
			s = States.FREE;
		} else if(state.equals("black")) {
			s = States.BLACK;
		} else if(state.equals("white")) {
			s = States.WHITE;
		}
	}
	
	public States getState() {
		return s;
	}
	*/
	
	private String state = "free";
	private String territory = null;
	
	public void changeState(String state) {
		if(state.equals("free")) {
			this.state = state;
		} else if(state.equals("black")) {
			this.state = state;
		} else if(state.equals("white")) {
			this.state = state;
		}
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setTerritory(String territory) {
		this.territory = territory;
	}
	
	public String getTerritory() {
		return this.territory;
	}
	
}
