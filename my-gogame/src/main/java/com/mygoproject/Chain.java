package com.mygoproject;

import java.util.ArrayList;

public class Chain {

	private Chains chains;
	private ArrayList<Stone> listOfStones;
	private String color;

	public Chain(Chains chains, String color, int x, int y) {
		this.chains = chains;
		this.color = color;
		listOfStones = new ArrayList<Stone>();
		addStone(x, y);
	}
	
	public void addStone(int x, int y) {
		listOfStones.add(new Stone(x, y));
	}

	public int getCountBreaths() {
		ArrayList<Breath> breaths = new ArrayList<Breath>();
		int x;
		int y;
		for(int i = 0; i < listOfStones.size(); i++) {
			x = listOfStones.get(i).getX();
			y = listOfStones.get(i).getY();
			if(chains.getStateOfIntersection(x + 1, y).equals("free")) {
				for(int k = 0; k < breaths.size(); k++) {
					if(breaths.get(k).getX() == x + 1 && breaths.get(k).getY() == y)
						break;
					if(k == breaths.size() - 1)
						breaths.add(new Breath(x + 1, y));
					k++;
				}
			}
			if(chains.getStateOfIntersection(x - 1, y).equals("free")) {
				for(int k = 0; k < breaths.size(); k++) {
					if(breaths.get(k).getX() == x - 1 && breaths.get(k).getY() == y)
						break;
					if(k == breaths.size() - 1)
						breaths.add(new Breath(x - 1, y));
					k++;
				}
			}
			if(chains.getStateOfIntersection(x, y + 1).equals("free")) {
				for(int k = 0; k < breaths.size(); k++) {
					if(breaths.get(k).getX() == x && breaths.get(k).getY() == y + 1)
						break;
					if(k == breaths.size() - 1)
						breaths.add(new Breath(x, y + 1));
					k++;
				}
			}
			if(chains.getStateOfIntersection(x, y - 1).equals("free")) {
				for(int k = 0; k < breaths.size(); k++) {
					if(breaths.get(k).getX() == x && breaths.get(k).getY() == y - 1)
						break;
					if(k == breaths.size() - 1)
						breaths.add(new Breath(x, y - 1));
					k++;
				}
			}
		}
		return breaths.size();
	}
	
	public String getColor() {
		return color;
	}
	
	public boolean isHere(int x, int y) {
		for(int i = 0; i < listOfStones.size(); i++) {
			if(listOfStones.get(i).getX() == x && listOfStones.get(i).getY() == y) {
				return true;
			}		
		}
		return false;
	}
	
	public int getX(int counter) {
		return listOfStones.get(counter).getX();
	}
	
	public int getY(int counter) {
		return listOfStones.get(counter).getY();
	}
	
	public int numberOfElements() {
		return listOfStones.size();
	}
}
