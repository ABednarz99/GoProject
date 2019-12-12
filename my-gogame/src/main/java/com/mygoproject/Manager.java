package com.mygoproject;

import java.util.ArrayList;

public class Manager {

	private int size;
	private Board board;
	private ArrayList<Chain> chains;
	
	int x = -1;
	int y = -1;
	String color = null;
	
	public Manager(int size) {
		this.size = size;
		this.board = new Board(size);
		this.chains = new ArrayList<Chain>();
	}
	public int getSizeOfBoard() {
		return this.size;
	}
	public String getIntersectionState(int x, int y) {
		return board.getIntersectionState(x, y);
	}
	
	public void changeIntersectionState(int x, int y, String state) {
		board.changeIntersectionState(x, y, state);
	}
	
	public ArrayList<Chain> getChains() {
		return this.chains;
	}
	
	public boolean checkConditionToAddStone(int x, int y, String color, int counter) {
		if(counter == 0) { //gdy dookola sa sami wrogowie
			if(board.getIntersectionState(x + 1, y).equals("free") || board.getIntersectionState(x - 1, y).equals("free") || 
					board.getIntersectionState(x, y + 1).equals("free") || board.getIntersectionState(x, y - 1).equals("free")) {
				return true;
			} else {
				int p = -1;			 //
				int q = -1;			//
				String col = null; //parametry przeciecia do zablokowania w przyszlej turze
				int oneLiberty = 0;
				int oneStone = 0;
				for(int i = 0; i < chains.size(); i++) {
					if(!(chains.get(i).getColor().equals(color))) {
						if(chains.get(i).isHere(x + 1, y) || chains.get(i).isHere(x - 1, y) || 
								chains.get(i).isHere(x, y + 1) || chains.get(i).isHere(x, y - 1)) {
							if(chains.get(i).countLiberties() == 1) {
								oneLiberty++;
								if(chains.get(i).numberOfElements() == 1) {
									oneStone++;
									p = chains.get(i).getX(0); //jest tylko jeden element w tym lancuchu,
									q = chains.get(i).getY(0); //gdy bedzie wiecej takich kamieni bedzie sie nadpisywaly parametry,
									col = chains.get(i).getColor(); //ale nas interesuje sytuacja, gdy jest tylko taki jeden kamien
								}
							}
						}
					}
				}
				if(oneLiberty == 1 && oneStone == 1) {
					this.x = p;
					this.y = q;
					this.color = col;
					return true;
				} else if (oneLiberty != 0) {
					return true;
				}
			}
		} else {
			// jezeli dookoła sa kamienie roznych kolorow to mozna wstawic, gdy chociaz jeden z przyjacielskich bedzie mial odechow wiecej niz 1
			for(int i = 0; i < chains.size(); i++) {
				if(chains.get(i).getColor().equals(color)) {
					if(chains.get(i).isHere(x + 1, y) || chains.get(i).isHere(x - 1, y) || 
							chains.get(i).isHere(x, y + 1) || chains.get(i).isHere(x, y - 1)) {
						if(chains.get(i).countLiberties() > 1) {
							return true;
						}
					}
				}
			}
			//tutaj wszystkie przyjacielskie maja po jednym oddeechu
			// jesli jeden z nieprzyjacielskich bedzie mial jeden oddech, to po dodaniu bedzie usuniety
			for(int i = 0; i < chains.size(); i++) {
				if(!(chains.get(i).getColor().equals(color))) {
					if(chains.get(i).isHere(x + 1, y) || chains.get(i).isHere(x - 1, y) || 
							chains.get(i).isHere(x, y + 1) || chains.get(i).isHere(x, y - 1)) {
						if(chains.get(i).countLiberties() == 1) {
							return true;
						}
					}
				}
			}
		}
		// gdy sa 4 przyjacielskie po jednym oddechu zawsze to jest samobojstwo 
		return false;
	}
	
	public void deleteBlockedIntersection() {
		this.x = -1;
		this.y = -1;
		this.color = null;
	}
	
	public boolean checkBlockedIntersection(int x, int y, String color) {
		if(this.x == x && this.y == y && this.color == color) {
				return true;
		}
		return false;
	}
	
	public boolean addStoneToChain(int x, int y, String color) {
		
		if(checkBlockedIntersection(x, y, color)) {
			return false;
		}
		
		int[] indexes = new int[4];
		int counter = 0;
		
		for(int i = 0; i < chains.size(); i++) {
			if(chains.get(i).getColor().equals(color)) {
				if(chains.get(i).isHere(x + 1, y) || chains.get(i).isHere(x - 1, y) || 
						chains.get(i).isHere(x, y + 1) || chains.get(i).isHere(x, y - 1)) {
					indexes[counter] = i;
					counter++;
				}
			}
		}
		
		boolean flag = checkConditionToAddStone(x, y, color, counter);
		if(counter == 0 && flag) {
			board.changeIntersectionState(x, y, color);
			chains.add(new Chain(this, color, x, y));
			return true;
		} else if(flag) {
			for(int i = 1; i < counter - 1; i++) {
				for(int k = 0; k < chains.get(indexes[i]).numberOfElements(); k++) {
					int a = chains.get(indexes[i]).getX(k);
					int b = chains.get(indexes[i]).getX(k);
					chains.get(indexes[0]).addStone(a, b);
				}
				removeChain(indexes[i]);
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void sendRemovedStones(int i) {
		//TODO
	}
	
	public void findZeroLiberties() {
		for(int i = 0; i < chains.size(); i++) {
			if(chains.get(i).countLiberties() == 0) {
				sendRemovedStones(i);
				removeChain(i);
			}
			i++;
		}
	}
	
	public void removeChain(int index) {
		chains.remove(index);
	}
	
	public void createTerritory() {
		
	}
}
