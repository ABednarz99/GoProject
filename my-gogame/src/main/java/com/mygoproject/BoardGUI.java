package com.mygoproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class BoardGUI extends JPanel {
	
	private GUI parent;
	private State currentPlayer;
	private State player;
	private int tileSize = 40;
	private int size;
	private int numberOfTiles;
	private IntersectionGUI[][] intersections;
	private Point lastMove;
	private boolean flag = true;
	
	public enum State {
		BLACK, WHITE, RECTANGLE
	}
	
	public BoardGUI(GUI parent) {
		this.parent = parent;
		setLayout(new BorderLayout());
	}
	
	private Cursor createCircleCursor(Color name) {
		BufferedImage bi = new BufferedImage(30,30,BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.setColor(name);
        g.fillOval(0,0,30,30);
        return Toolkit.getDefaultToolkit().createCustomCursor(bi, new Point(5,5), name + " Circle");
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
		
		//rysuje charakterystyczne punkty
		if(size == 9) {
			for(int i = 2; i < size; i += 4) {
				for(int k = 2; k < size; k += 4) {
					g2.fillOval(i * tileSize + tileSize - 7 / 2,
							k * tileSize + tileSize - 7 / 2,
							7, 7);
				}
			}
			g2.fillOval(4 * tileSize + tileSize - 7 / 2,
					4 * tileSize + tileSize - 7 / 2,
					7, 7);
		} else {
			for(int i = 3; i < size; i += 6) {
				for(int k = 3; k < size; k += 6) {
					g2.fillOval(i * tileSize + tileSize - 7 / 2,
							k * tileSize + tileSize - 7 / 2,
							7, 7);
				}
			}
			if(size == 13) {	
				g2.fillOval(6 * tileSize + tileSize - 7 / 2,
						6 * tileSize + tileSize - 7 / 2,
						7, 7);
			}
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
		        	if(intersections[row][col].getShape() != State.RECTANGLE) {
		        		g2.fillOval(col * tileSize + tileSize - tileSize / 2,
		            		row * tileSize + tileSize - tileSize / 2,
		            		tileSize, tileSize);
		        	} else {
		        		g2.fillRect(col * tileSize + tileSize - tileSize / 2,
			            		row * tileSize + tileSize - tileSize / 2,
			            		tileSize, tileSize);
		        	}
		        }
		    }
		}
		if(lastMove != null) {
			if(intersections[lastMove.y][lastMove.x].getState() == State.BLACK) {
				g2.setColor(Color.WHITE);
			} else {
				g2.setColor(Color.BLACK);
			}
			g2.drawOval(lastMove.x * tileSize + tileSize - tileSize / 2,
					lastMove.y * tileSize + tileSize - tileSize / 2,
	                tileSize, tileSize);
		}
	}
	
	public void pausedGame() {
		flag = false;
	}
	
	public void resumedGame(boolean flag) {
		flag = true;
		for(int i = 0; i < size; i++) {
			for(int k = 0; k < size; k++)	{
				if(intersections[i][k].getShape() != null) {
					intersections[i][k].setShape(null);
					intersections[i][k].setState(null);
				}
			}
		}
		repaint();
		if(flag) {
			currentPlayer = player;
		}
	}
	
	public void changeCurrentPlayer() {
		 if (currentPlayer == State.BLACK) {
	            currentPlayer = State.WHITE;
		 } else {
	            currentPlayer = State.BLACK;
	     }
	}
	
	public void addStoneToBoard(int col, int row) {
		intersections[row][col].setState(currentPlayer);
        lastMove = new Point(col, row);
        if (currentPlayer == State.BLACK) {
            currentPlayer = State.WHITE;
        } else {
            currentPlayer = State.BLACK;
        }
        repaint();
	}
	
	public void addRectToBoard(int col, int row, String color) {
		if(intersections[row][col].getState() == null) {
			intersections[row][col].setState(player);
			intersections[row][col].setShape(State.RECTANGLE);
			parent.sendRect(col, row, color);
			repaint();
		}
			
	}
	
	public void addEnemyRect(int x, int y) { 
		if(player == State.WHITE) {
			intersections[y][x].setState(State.BLACK);
		} else {
			intersections[y][x].setState(State.WHITE);
		}
		intersections[y][x].setShape(State.RECTANGLE);
		repaint();
	}
	
	public void deleteStone(int x, int y) {
		intersections[y][x].setState(null);
		repaint();
	}

	public void initially(int size, String color) {
		
		if(color.contentEquals("white")) {
			this.player = State.WHITE;
			this.setCursor(createCircleCursor(Color.WHITE));
		} else {
			this.player = State.BLACK;
			this.setCursor(createCircleCursor(Color.BLACK));
		}
		
		JPanel xCoordinates = new JPanel(new GridLayout(1, 0));
	    xCoordinates.setBackground(Color.ORANGE);
	    xCoordinates.add(new JLabel(""));
	    for (int i = 0; i < size; i++) {
	         char fileChar = (char) ('A' + i);
	         xCoordinates.add(new JLabel(String.valueOf(fileChar)));
	    }
	    JPanel yCoordinates = new JPanel(new GridLayout(0, 1));
	    yCoordinates.setBackground(Color.ORANGE);
	    for (int i = size; i > 0; i--) {
		    yCoordinates.add(new JLabel(String.valueOf(i)));
		}
	    
	    JPanel yyCoordinates = new JPanel(new GridLayout(0, 1));
	    yyCoordinates.setBackground(Color.ORANGE);
	    for (int i = size; i > 0; i--) {
		    yyCoordinates.add(new JLabel(String.valueOf(i) + " "));
		}
	    JPanel xxCoordinates = new JPanel(new GridLayout(1, 0));
	    xxCoordinates.setBackground(Color.ORANGE);
	    xxCoordinates.add(new JLabel(""));
	    for (int i = 0; i < size; i++) {
	         char fileChar = (char) ('A' + i);
	         xxCoordinates.add(new JLabel(String.valueOf(fileChar)));
	    }
	    
	    add(xCoordinates, BorderLayout.NORTH);
	    add(yCoordinates, BorderLayout.WEST);
	    add(yyCoordinates, BorderLayout.EAST);
	    add(xxCoordinates, BorderLayout.SOUTH);
		
		this.setBackground(Color.ORANGE);
		this.currentPlayer = State.BLACK; //czarny zaczyna
		
		this.size = size;
		this.numberOfTiles = size - 1;
		this.setPreferredSize(new Dimension(numberOfTiles * tileSize + tileSize + 41,
	    		numberOfTiles * tileSize + tileSize * 2));
		
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
	            if(flag) {
	            	if(currentPlayer == player) {
	            		if(parent.sendCoordinates(col, row, color)) {
	            			addStoneToBoard(col, row);
	            		}
	            	}
	            } else {
	            	addRectToBoard(col, row, color);
	            }
			}
		});
	}
}