package com.mygoproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Player implements Runnable {
    private String color;
    Socket socket;
    private int boardSize;
    private boolean wantBot;
    private boolean hasOpponent;
    Player opponent;
    
    Manager gameManager;

    Scanner input;
    PrintWriter output;
    
    public Player(Socket socket, String color) {
        this.socket = socket;
        this.color = color;
    }
    
    public void run() {
        try {
            setup();
            processCommands();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setup() throws IOException, InterruptedException {
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        
        output.println("WELCOME " + color);        
        while(!(input.nextLine().equals("CREATED"))) {}
    }
    
    
    private void processCommands() {
        while (input.hasNextLine()) {
            String command = input.nextLine();
            
            if (command.startsWith("MOVE")) {
                command = command.substring(4);
                
                StringTokenizer tmp = new StringTokenizer(command);
                int x = Integer.parseInt(tmp.nextToken());
                int y = Integer.parseInt(tmp.nextToken());
                
                processMoveCommand(x, y);
            } else if (command.startsWith("QUIT")) {
                output.println("OTHER_PLAYER_LEFT");
                return;
            } else if (command.startsWith("PASS")) {
                // TODO: create logic connected with passing
                if(gameManager.isIntersectionBlocked(this.color)) {
                    gameManager.deleteIntersectionBlockade();
                }
                output.println("YOU PASSED");
                opponent.output.println("OPPONENT PASSED ");
                gameManager.setCurrentPlayer(opponent);
                
            } else if (command.startsWith("SURRENDER")) {
                output.println("DEFEAT");
                opponent.output.println("OPPONENT SURRENDERED ");
                return;
            }
        }
    }
    
    private void processMoveCommand(int x, int y) {
        try {
            if (this != gameManager.getCurrentPlayer()) {
                throw new IllegalStateException("Not your turn");
            } else if (this.getOpponent() == null) {
                throw new IllegalStateException("You don't have an opponent yet");
            }
            
            if(gameManager.addStoneToChain(x, y, this.color, this)) {
                if(gameManager.isIntersectionBlocked(this.color)) {
                    gameManager.deleteIntersectionBlockade();
                }
                output.println("VALID_MOVE " + x + " " + y);
                opponent.output.println("OPPONENT_MOVED " + x + " " + y);   
            }
        } catch (IllegalStateException e) {
            output.println("MESSAGE " + e.getMessage());
        }
    }
    
    
    public int getBoardSize() {
        return boardSize;
    }
    
    public boolean getWantBot() {
        return wantBot;
    }
    
    public boolean getHasOpponent() {
        return hasOpponent;
    }
    
    public Player getOpponent() {
        return opponent;
    }
    
    public String getColor() {
        return color;
    }
    public void setBoardSize(int size) {
        this.boardSize = size;
    }
    
    public void setBotSetting(int wantBot) {
        if(wantBot > 0) {
            this.wantBot = true;
        } else {
            this.wantBot = false;
        }
    }
    
    public void setGameManager(Manager gameManager) {
        this.gameManager = gameManager;
    }
    public void setHasOpponent(boolean a) {
        this.hasOpponent = a;
    }
    
}