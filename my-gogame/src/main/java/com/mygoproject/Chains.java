package com.mygoproject;

import java.util.ArrayList;

public class Chains {

	private ArrayList<Chain> chains;
	private Manager manager;
	
	public Chains(Manager manager) {
		chains = new ArrayList<Chain>();
		this.manager = manager;
	}
	
	public String getStateOfIntersection(int x, int y) {
		return manager.getStateOfIntersection(x, y);
	}
	
	public void addStoneToChain(int x, int y, String color) {
		
		int[] indexes = new int[4];
		
		for(int i = 0; i < chains.size(); i++) {
			if(chains.get(i).getColor().equals(color)) {
				if(chains.get(i).isHere(x + 1, y)) {
					indexes[indexes.length] = i;
					continue;
				}

				if(chains.get(i).isHere(x - 1, y)) {
					indexes[indexes.length] = i;
					continue;
				}

				if(chains.get(i).isHere(x, y + 1)) {
					indexes[indexes.length] = i;
					continue;
				}

				if(chains.get(i).isHere(x, y - 1)) {
					indexes[indexes.length] = i;
					continue;
				}
			}
		}
		
		if(indexes.length == 0) {
			chains.add(new Chain(this, color, x, y));
		} else {
			chains.add(new Chain(this, color, x, y));
			int theLastElement = chains.size() - 1;
			for(int i = 0; i < indexes.length; i++) {
				for(int k = 0; k < chains.get(indexes[i]).numberOfElements(); k++) {
					int a = chains.get(indexes[i]).getX(k);
					int b = chains.get(indexes[i]).getX(k);
					chains.get(theLastElement).addStone(a, b);
				}
				chains.remove(indexes[i]);
			}
		}
	}
}
