package com.mygoproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class GUI extends JFrame {
	private int size;
	private JButton player;
	private JButton bot;
	
	public GUI() {
		JDialog d = new JDialog(this, "The first step", true);
		
		JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(new EmptyBorder(20, 20, 20, 20));
	    
	    panel.add(new JLabel("Wybierz godnego Tobie przeciwnika"), BorderLayout.NORTH);
	    
	    JPanel choosingGrid = new JPanel(new FlowLayout());
	    panel.add(choosingGrid, BorderLayout.CENTER);
	    
	    JPanel choosingPlayer = new JPanel(new FlowLayout());
	    panel.add(choosingPlayer, BorderLayout.SOUTH);
	    
	    player = new JButton("Player");
	    player.setEnabled(false);
	    player.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	action("player");
            }
        });
	    bot = new JButton("Bot");
	    bot.setEnabled(false);
	    bot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	action("bot");
            }
        });
	    
	    choosingPlayer.add(player);
	    choosingPlayer.add(bot);
	    
	    JButton grid9 = new JButton("9x9");
	    grid9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	action("9x9");
            }
        });
	    
	    JButton grid13 = new JButton("13x13");
	    grid13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	action("13x13");
            }
        });
	    
	    JButton grid19 = new JButton("19x19");
	    grid19.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	action("19x19");
            }
        });
	    
	    choosingGrid.add(grid9);
	    choosingGrid.add(grid13);
	    choosingGrid.add(grid19);
	    
		setTitle("Go Game");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			    System.exit(1);
			}
		});
		JPanel container = new JPanel();
	    container.setBackground(Color.GRAY);
	    container.setLayout(new BorderLayout());
	    add(container);
	    container.setBorder(new EmptyBorder(0, 20, 20, 20));

	    JPanel buttonPanel = new JPanel(new FlowLayout());
	    buttonPanel.setBackground(Color.GRAY);
	    
	    JButton pause = new JButton("resign");
	    pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            }
        });
	    JButton resume = new JButton("resume");
	    resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            }
        });
	    
	    BoardGUI boardGUI = new BoardGUI();
	    container.add(boardGUI, BorderLayout.CENTER);
	    
	    buttonPanel.add(pause);
	    buttonPanel.add(resume);
	    container.add(buttonPanel, BorderLayout.NORTH);

	    d.setContentPane(panel);
	    d.pack();
	    d.setSize(300, 200);
	    d.setLocationRelativeTo(null);
	    d.setVisible(true);
	    boardGUI.initially(size);
	    pack();
	    setResizable(false);
	    setLocationRelativeTo(null);
	}
	
	public void action(String name) {
		if(name.equals("player") || name.equals("bot")) {
			dispose();
        	setVisible(true);
		} else {
			player.setEnabled(true);
			bot.setEnabled(true);
			if(name.equals("9x9")) {
				this.size = 9;
			} else if(name.equals("13x13")) {
				this.size = 13;
			} else {
				this.size = 19;
			}
		}
	}
	
	public static void main(String[] args) {
		new GUI();
	}
	
}