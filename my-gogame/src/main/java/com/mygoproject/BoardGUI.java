package com.mygoproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class BoardGUI extends JPanel {
	
	private State currentPlayer;
	private int tileSize = 30;
	private int size;
	private int numberOfTiles;
	private IntersectionGUI[][] intersections;
	private Point lastMove;
	
	public enum State {
		BLACK, WHITE
	}
	
	public BoardGUI() {
		
		this.setCursor(createCircleCursor(Color.BLACK)); // dlugo sie robi...
	}
	
	public void initially(int size) {
		this.setBackground(Color.ORANGE);
		this.currentPlayer = State.BLACK; //czarny zaczyna (rasizm)
		
		this.size = size;
		this.numberOfTiles = size - 1;
		this.intersections = new IntersectionGUI[size][size];
		for(int i = 0; i < size; i++) {
			for(int k = 0; k < size; k++)	{
				intersections[i][k] = new IntersectionGUI();
			}
		}
		this.addMouseListener(new MouseAdapter() {
			
			public void mouseReleased(MouseEvent e) {
				int row = Math.round((float) (e.getY() - tileSize)
	                    / tileSize);
	            int col = Math.round((float) (e.getX() - tileSize)
	                    / tileSize);
	            if (row >= size || col >= size || row < 0 || col < 0) {
	                return;
	            }
	            if(intersections[row][col].getState() != null) {
	            	return;
	            }
	            intersections[row][col].setState(currentPlayer);
	            lastMove = new Point(col, row);
	            if (currentPlayer == State.BLACK) {
	                currentPlayer = State.WHITE;
	            } else {
	                currentPlayer = State.BLACK;
	            }
	            repaint();
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		//poprawia jakosc
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLACK);
		//rysuje wiersze
		for(int i = 0; i < size; i++) {
	        g2.drawLine(tileSize, i * tileSize + tileSize, tileSize
	                * numberOfTiles + tileSize, i * tileSize + tileSize);
	    }
		//rysuje kolumny
		for(int i = 0; i < size; i++) {
	        g2.drawLine(i * tileSize + tileSize, tileSize, i * tileSize
	                + tileSize, tileSize * numberOfTiles + tileSize);
	    }
		//rysuje kropki
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				State state = intersections[row][col].getState();
		        if (state != null) {
		        	if (state == State.BLACK) {
		        		g2.setColor(Color.BLACK);
		            } else {
		            	g2.setColor(Color.WHITE);
		            }
		            g2.fillOval(col * tileSize + tileSize - tileSize / 2,
		            		row * tileSize + tileSize - tileSize / 2,
		            		tileSize, tileSize);
		        }
		    }
		}
		if(lastMove != null) {
			if(intersections[lastMove.y][lastMove.x].getState() == State.BLACK) {
				g2.setColor(Color.WHITE);
				this.setCursor(createCircleCursor(Color.WHITE));
			} else {
				g2.setColor(Color.BLACK);
				this.setCursor(createCircleCursor(Color.BLACK));
			}	
	        g2.drawOval(lastMove.x * tileSize + tileSize - tileSize / 2,
	                lastMove.y * tileSize + tileSize - tileSize / 2,
	                tileSize, tileSize);
		}
	}
	
	public Cursor createCircleCursor(Color name) {
		BufferedImage bi = new BufferedImage(30,30,BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.setColor(name);
        g.fillOval(0,0,30,30);
        return Toolkit.getDefaultToolkit().createCustomCursor(bi, new Point(5,5), name + " Circle");
	}
	
	public Dimension getPreferredSize() {
	    return new Dimension(numberOfTiles * tileSize + tileSize * 2,
	    		numberOfTiles * tileSize + tileSize * 2);
	}
}
