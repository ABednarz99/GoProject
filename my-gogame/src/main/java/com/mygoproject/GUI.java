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
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.io.PrintWriter;

public class GUI extends JFrame {
    private int size;
    private JButton player;
    private JButton bot;
    private BoardGUI boardGUI;
    private JButton resume;
    private JButton pass;
    private JButton countThePoints;
    private JButton surrender;
    JTextArea area;
    PrintWriter out;

    public GUI(final PrintWriter out) {
        this.out = out;
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
                out.println("QUIT");
                System.exit(1);
            }
        });
        JPanel container = new JPanel();
        container.setBackground(Color.GRAY);
        container.setLayout(new BorderLayout());

        container.setBorder(new EmptyBorder(0, 20, 20, 20));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.GRAY);

        pass = new JButton("pass");
        pass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("PASS");
            }
        });
        resume = new JButton("resume");
        resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	out.println("RESUME");
            }
        });

        surrender = new JButton("surrender");
        surrender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("SURRENDER");
            }
        });
        countThePoints = new JButton("count the points");
        countThePoints.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	out.println("COUNT");
            }
        });
        
        pass.setEnabled(false);
        resume.setEnabled(false);
        surrender.setEnabled(false);
        countThePoints.setEnabled(false);
        boardGUI = new BoardGUI(out);
        container.add(boardGUI, BorderLayout.CENTER);

        buttonPanel.add(pass);
        buttonPanel.add(resume);
        buttonPanel.add(surrender);
        buttonPanel.add(countThePoints);
        container.add(buttonPanel, BorderLayout.NORTH);

        area = new JTextArea(15, 15);
        area.setEditable(false);
        area.setFont(new Font("Segoe Script", Font.BOLD, 15));
        area.setLineWrap(true);
        area.setText("The course of the game:\nWaiting for an opponent...\n");
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
            out.println("PREFERENCES" + size + " " + 1);

            boardGUI.initially(size);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        } else if(name.equals("player")) {
            dispose();
            out.println("PREFERENCES" + size + " " + 0);

            boardGUI.initially(size);
            pack();
            setLocationRelativeTo(null);
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

    public void setEnabledPass(boolean flag) {
    	pass.setEnabled(flag);
    }
    
    public void setEnabledResume(boolean flag) {
    	resume.setEnabled(flag);
    }
    
    public void setEnabledSurrender(boolean flag) {
    	surrender.setEnabled(flag);
    }
    
    public void setEnabledCountThePoints(boolean flag) {
    	countThePoints.setEnabled(flag);
    }
    
    public void move(int x, int y, char mark) {
        boardGUI.move(x, y, mark);
    }
    
    public void setPausedMode(int i) {
    	boardGUI.setPausedMode(i);
    }
    
    public void setOpponentPausedMode(int i) {
    	boardGUI.setOpponentPausedMode(i);
    }

    public void remove(int x, int y) {
        boardGUI.remove(x, y);
    }
    
    public void removeRectangles() {
    	boardGUI.removeRectangles();
    }
    public void addMessage(String message) {
        this.area.append(message + "\n");
    }
    
    public void setEnabledBoard(boolean flag) {
    	boardGUI.setEnabledBoard(flag);
    }
    
    public void changeColorOfCursor(char mark) {
    	boardGUI.changeColorOfCursor(mark);
    }
    
    public void builtFinishingDialog(int opponentCapturedStones, int myCapturedStones, char mark, char opponentMark) {
    	int opponentRectangles = boardGUI.countRectangles(opponentMark);
    	if(opponentRectangles == -1) {
    		addMessage("There must be no red rectangle to finish game!");
    		out.println("ERROR");
    		return;
    	}
    	out.println("WITHOUT_ERROR");
    	int myRectangles = boardGUI.countRectangles(mark);
    	int opponentScores = opponentCapturedStones + opponentRectangles;
    	int myScores = myCapturedStones + myRectangles;
    	String name;
    	if(mark == 'B') {
    		name = "Black ";
    	} else {
    		name = "White ";
    	}
    	if(myScores > opponentScores) {
    		name += "is the winner!";
    	} else if (myScores < opponentScores) {
    		name += "is the loser!";
    	} else {
    		name = "The game ended in a draw";
    	}
    	JDialog dialog = new JDialog(this, name, true);
    	JPanel panel = new JPanel(new GridLayout(6, 1, 20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("My score: " + myScores, JLabel.CENTER));
        panel.add(new JLabel("Opponent's score: " + opponentScores, JLabel.CENTER));
        panel.add(new JLabel("My captured stones: " + myCapturedStones, JLabel.CENTER));
        panel.add(new JLabel("Opponent's captured stones: " + opponentCapturedStones, JLabel.CENTER));
        panel.add(new JLabel("My rectangles: " + myRectangles, JLabel.CENTER));
        panel.add(new JLabel("Opponent's rectangles: " + opponentRectangles, JLabel.CENTER));
        dialog.add(panel);
        dialog.pack();
        dialog.setSize(300, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}