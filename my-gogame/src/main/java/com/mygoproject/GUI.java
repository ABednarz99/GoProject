package com.mygoproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class GUI extends JFrame {
	
	private ConnectorToGUI parent;
	private JDialog options;
	private JPanel choosingGrid;
	private JPanel choosingPlayer;
	private JPanel container;
	private JPanel buttonPanel;
	private int size;
	private JButton player;
	private JButton bot;
	private JButton surrender;
	private JButton resume;
	private JButton pause;
	private BoardGUI boardGUI;
	
	public GUI(ConnectorToGUI parent) {
		
		this.parent = parent;
		setTitle("Go Game");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			    System.exit(1);
			}
		});
		createPanelContainer();
	    add(container);
	    createDialogOptions();
	    options.setVisible(true);
	}
	
	public void createDialogOptions() {
		
		options = new JDialog(this, "The first step", true);
		JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(new EmptyBorder(20, 20, 20, 20));
	    panel.add(new JLabel("Choose an opponent", JLabel.CENTER), BorderLayout.NORTH);
	    createChoosingGrid();
	    panel.add(choosingGrid, BorderLayout.CENTER);
	    createChoosingPlayer();
	    panel.add(choosingPlayer, BorderLayout.SOUTH);
	    options.add(panel);
	    options.setSize(300, 170);
	    options.setLocationRelativeTo(null);
	    options.setResizable(false);
	}
	
	public void createChoosingGrid() {
		choosingGrid = new JPanel(new FlowLayout());
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
	}
	
	public void createChoosingPlayer() {
		choosingPlayer = new JPanel(new FlowLayout());
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
	}
	
	public void createPanelContainer() {
		container = new JPanel();
	    container.setBackground(Color.GRAY);
	    container.setLayout(new BorderLayout());
	    container.setBorder(new EmptyBorder(0, 20, 20, 20));

	    boardGUI = new BoardGUI(this);
	    container.add(boardGUI, BorderLayout.CENTER);
	    
	    createButtonPanel();
	    container.add(buttonPanel, BorderLayout.NORTH);
	    
	    JTextArea area = new JTextArea(15, 15);
	    area.setEditable(false);
	    area.setFont(new Font("Segoe Script", Font.BOLD, 15));
	    area.setLineWrap(true);
	    area.setText("The course of the game:\n");
	    JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    container.add(scroll, BorderLayout.EAST);
	}
	
	public void createButtonPanel() {
		buttonPanel = new JPanel(new FlowLayout());
	    buttonPanel.setBackground(Color.GRAY);
	    
	    pause = new JButton("resign");
	    resume = new JButton("resume");
	    resume.setEnabled(false);
	    surrender = new JButton("surrender");
	    
	    pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	parent.sendPause();
            	boardGUI.changeCurrentPlayer();
            	pause.setEnabled(false);
            }
        });
	    
	    resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	parent.sendResume();
            	resumedGame(false);
            	resume.setEnabled(false);
            }
        });
	    
	    surrender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	parent.sendSurrender();
            	surrender(true);
            }
        });
	    buttonPanel.add(pause);
	    buttonPanel.add(resume);
	    buttonPanel.add(surrender);
	}
	
	public void action(String name) {
		if(name.equals("bot")) {
			dispose();
			boardGUI.initially(size, "black");
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
	
	public void startGameWithOtherPlayer(String color) {
		dispose();
		boardGUI.initially(size, color);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public boolean sendCoordinates(int x, int y, String color) {
		return parent.sendCoordinates(x, y, color);
	}
	
	public void addStoneToBoard(int x, int y) {
		pause.setEnabled(true);
		boardGUI.addStoneToBoard(x, y);
	}
	
	public void sendRect(int x, int y, String color) {
		parent.sendRect(x, y, color);
	}
	
	public void rect(int x, int y) {
		boardGUI.addEnemyRect(x, y);
	}
	
	public void deleteStones(int x, int y) {
		boardGUI.deleteStones(x, y);
	}
	
	public void pause() {
		boardGUI.changeCurrentPlayer();
	}
	
	public void resumedGame(boolean flag) {
		resume.setEnabled(false);
		boardGUI.resumedGame(flag);
	}
	
	public void pausedGame() {
		resume.setEnabled(true);
		boardGUI.pausedGame();
	}
	
	public void surrender(boolean flag) {
		JDialog dialog = new JDialog(this, "The End", true);
		JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(new EmptyBorder(20, 20, 20, 20));
		if(flag) {
			panel.add(new JLabel("You lost!", JLabel.CENTER), BorderLayout.NORTH);
		} else {
			panel.add(new JLabel("You are winner!", JLabel.CENTER), BorderLayout.NORTH);
		}
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	parent.finish();
            }
        });
		panel.add(ok, BorderLayout.CENTER);
		dialog.add(panel);
		dialog.setSize(300, 170);
	    dialog.setLocationRelativeTo(null);
	    dialog.setResizable(false);
	    dialog.setVisible(true);
	    
	}
}
