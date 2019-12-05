package com.mygoproject;

public class Manager {

	private Board board;
	private Chains chains;
	
	public Manager(int size) {
		board = new Board(size);
		chains = new Chains(this);
	}
	
	public String getStateOfIntersection(int x, int y) {
		return board.getStateOfIntersection(x, y);
	}
	
	public void changeStateOfIntersection(int x, int y, String state) {
		board.changeStateOfIntersection(x, y, state);
	}
	
	public void addStoneToChain(int x, int y, String color) {
		chains.addStoneToChain(x, y, color);
	}
	
}
