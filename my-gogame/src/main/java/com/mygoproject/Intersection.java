package com.mygoproject;

public class Intersection {
	
    private String state = "FREE";
    
    public void changeState(String state) {
       this.state = state;
    }
    
    public String getState() {
        return this.state;
    }
}
