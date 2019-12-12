package com.mygoproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class GUI extends JFrame {
	private int size;
	private JButton player;
	private JButton bot;
	private BoardGUI boardGUI;
	
	public GUI() {
		JDialog d = new JDialog(this, "The first step", true);
		
		JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(new EmptyBorder(20, 20, 20, 20));
	    
	    panel.add(new JLabel("Choose an opponent", JLabel.CENTER), BorderLayout.NORTH);
	    
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
	    
	    JButton surrender = new JButton("surrender");
	    surrender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            }
        });
	    
	    boardGUI = new BoardGUI();
	    container.add(boardGUI, BorderLayout.CENTER);
	    
	    buttonPanel.add(pause);
	    buttonPanel.add(resume);
	    buttonPanel.add(surrender);
	    container.add(buttonPanel, BorderLayout.NORTH);
	    
	    JTextArea area = new JTextArea(15, 15);
	    area.setEditable(false);
	    area.setFont(new Font("Segoe Script", Font.BOLD, 15));
	    area.setLineWrap(true);
	    area.setText("The course of the game:\n");
	    JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    container.add(scroll, BorderLayout.EAST);
	    
	    add(container);
	    setResizable(false);
		
	    d.add(panel);
	    d.setSize(300, 170);
	    d.setLocationRelativeTo(null);
	    d.setVisible(true);
	}
	
	public void action(String name) {
		if(name.equals("bot")) {
			dispose();
			boardGUI.initially(size);
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
		} else if(name.equals("player")) {
			dispose();
			JDialog dialog = new JDialog(this, "Information", true);
			dialog.setContentPane(new JLabel("Waiting for the other player...", JLabel.CENTER));
			dialog.pack();
		    dialog.setSize(300, 200);
		    dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
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