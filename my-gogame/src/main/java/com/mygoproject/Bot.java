package com.mygoproject;

import java.util.ArrayList;
import java.util.Random;

public class Bot {
	
	private Manager manager;
	private ArrayList<Chain> chains;
	private String color = "white";
	
	public Bot(Manager manager) {
		this.manager = manager;
		this.chains = manager.getChains();
	}
	
	public void action() {
		while(true) {
			//sprawdza łancuch z najmniejsza iloscia oddechow
			int a = 0; //liczba oddechow
			int p = -1; //indeks lancucha
			for(int i = 0; i < chains.size(); i++) {
				if(!(chains.get(i).getColor().equals(color))) {
					if(p == -1) {
						a = chains.get(i).countLiberties();
						p = i;
					}
					if(chains.get(i).countLiberties() < a) {
						a = chains.get(i).countLiberties();
						p = i;
					}
				}
			}
		
			//sprawdza swoj wlasny lancuch z najmniejsza iloscia oddechow
			int b = 0;
			int q = -1;
			for(int i = 0; i < chains.size(); i++) {
				if(chains.get(i).getColor().equals(color)) {
					if(q == -1) {
						b = chains.get(i).countLiberties();
						q = i;
					}
					if(chains.get(i).countLiberties() < b) {
						b = chains.get(i).countLiberties();
						q = i;
					}
				}
			}
		
			if(p == -1 && q == -1) { //bot rozpoczyna gre, sytuacja gdy gracz spasowal na poczatku gry
				if(randomChoice()) {
					continue;
				}
			}
			
			if(a == 1) { //zabija lancuch wroga
				if(searchFreeSpace(p)) {
					continue;
				}
			}
			
			if(b == 1) { //probuje ratowac swoj lancuch
				if(searchFreeSpace(q)) {
					continue;
				}
			}
			
			Random r = new Random(); //gdy zadne z powyzszych to losuje czy powiekszac swoj lancuch, czy gdziekolwiek, czy kolo wroga
			int x = r.nextInt(4);
			if(x == 1) {
				if(searchFreeSpace(q)) {
					continue;
				}
			} else if(x == 2) {
				if(randomChoice()) {
					continue;
				}
			} else {
				if(searchFreeSpace(p)) {
					continue;
				}
			}
		}
	}
	
	public boolean randomChoice() {
		int size = manager.getSizeOfBoard();
		Random r = new Random();
		int x = r.nextInt(size);
		int y = r.nextInt(size);
		return manager.addStoneToChain(x, y, "white");
	}
	
	public int countLiberties(int x, int y) {
		int l = 0;
		if(manager.getIntersectionState(x + 1, y).equals("free")) {
			l++;
		}
		if(manager.getIntersectionState(x - 1, y).equals("free")) {
			l++;
		}
		if(manager.getIntersectionState(x, y + 1).equals("free")) {
			l++;
		}
		if(manager.getIntersectionState(x, y - 1).equals("free")) {
			l++;
		}
		return l;
	}
	
	public boolean searchFreeSpace(int a) {
		for(int i = 0; i < chains.get(a).numberOfElements(); i++) {
			int flag = 0;
			int x = chains.get(a).getX(i);
			int y = chains.get(a).getY(i);
			if(chains.get(a).getColor().equals("black")) {
				flag = 1; //gdy chcemy postawic kamien obok wroga, poniższe warunki z oddechami nie obowiazuja
			}
			if(manager.getIntersectionState(x + 1, y).equals("free")) {
				if(countLiberties(x + 1, y) > 2 || flag == 1) { // dodajac kamien ginie jeden oddech, wiec lancuch nadal bedzie mial tylko jeden oddech
					return manager.addStoneToChain(x + 1, y, "white");
				}
			}
			if(manager.getIntersectionState(x - 1, y).equals("free")) {
				if(countLiberties(x - 1, y) > 2 || flag == 1) {
					return manager.addStoneToChain(x - 1, y, "white");
				}
			}
			if(manager.getIntersectionState(x, y + 1).equals("free")) {
				if(countLiberties(x, y + 1) > 2 || flag == 1) {
					return manager.addStoneToChain(x, y + 1, "white");
				}
			}
			if(manager.getIntersectionState(x, y - 1).equals("free")) {
				if(countLiberties(x, y - 1) > 2 || flag == 1) {
					return manager.addStoneToChain(x, y - 1, "white");
				}
			}
			
		}
		return false;
	}
}
