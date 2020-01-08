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
import java.io.PrintWriter;

import javax.swing.*;

public class BoardGUI extends JPanel {
    
    private State currentPlayer;
    private int tileSize = 40;
    private int size;
    private int numberOfTiles;
    private IntersectionGUI[][] intersections;
    private Point lastMove;
    private PrintWriter out;
    
    public class IntersectionGUI {
        private State state = State.FREE;
        
        public void setState(State state) {
            this.state = state;
        }
        
        public State getState() {
            return this.state;
        }
    }
    
    public enum State {
        FREE, BLACK, WHITE
    }
    
    public BoardGUI(PrintWriter out) {
        this.out = out;
        setLayout(new BorderLayout());
    }
    
    public Cursor createCircleCursor(Color name) {
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
                if (state != State.FREE) {
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

    public void initially(final int size) {
        
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
        
        this.setCursor(createCircleCursor(Color.BLACK)); 
        
        this.setBackground(Color.ORANGE);
        
        this.size = size;
        this.numberOfTiles = size - 1;
        this.setPreferredSize(new Dimension(numberOfTiles * tileSize + tileSize + 41,
                numberOfTiles * tileSize + tileSize * 2));
        
        this.intersections = new IntersectionGUI[size][size];
        for(int i = 0; i < size; i++) {
            for(int k = 0; k < size; k++)   {
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
                if(intersections[row][col].getState() != State.FREE) {
                    return;
                }
                
                // sending message about move
                System.out.println("MOVE" + row + " " + col);
                out.println("MOVE" + row + " " + col);
            }
        });
    }
    
    
    public void move(int x, int y, char mark) {
        if(mark == 'B') {
            currentPlayer = State.BLACK;
        }
        else {
            currentPlayer = State.WHITE;
        }
        intersections[x][y].setState(currentPlayer);
        lastMove = new Point(y, x);
        repaint();
    }
    
    public void remove(int x, int y) {
        intersections[x][y].setState(State.FREE);
        repaint();
    }
    
}
