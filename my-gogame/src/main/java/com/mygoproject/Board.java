package com.mygoproject;

public class Board {
	
	Intersection[][] intersections;
	
	public Board(int size) {
		intersections = new Intersection[size][size];
		for(int i = 0; i < size; i++) {
			for(int k = 0; k < size; k++)	{
				intersections[i][k] = new Intersection();
			}
		}
	}
}
