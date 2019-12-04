package com.mygoproject;

import java.util.ArrayList;

public class Chain {

	private ArrayList<Stone> listOfStones;
	private int breaths;
	private String Color;

	public Chain() {
		listOfStones = new ArrayList<Stone>();
	}

	public void countBreaths() {
		int x;
		int y;
		for(int i = 0; i < listOfStones.size(); i++) {
			x = listOfStones.get(i).getX();
			y = listOfStones.get(i).getY();
			//if()
		}
	}
}
