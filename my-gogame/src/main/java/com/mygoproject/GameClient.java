package com.mygoproject;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GameClient {
    
    private GUI gui = null;
    private final int serverPort = 7777;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    
    public GameClient() throws Exception {
        socket = new Socket("localhost", serverPort);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        
        gui = new GUI(out);
        play();
    }
    
    public void play() throws Exception {
        try {
            // Waiting for response from server
            String response = in.nextLine();
            char mark = response.charAt(8);
            char opponentMark = mark == 'B' ? 'W' : 'B';
            
            while(!(in.nextLine().equals("CREATING"))) {}
            out.println("CREATED");
            
            // waiting for further messages
            while (in.hasNextLine()) {
                response = in.nextLine();
                if (response.startsWith("VALID_MOVE")) {
                    gui.addMessage(response);
                    response = response.substring(11);
                    
                    StringTokenizer tmp = new StringTokenizer(response);
                    
                    int x = Integer.parseInt(tmp.nextToken());
                    int y = Integer.parseInt(tmp.nextToken());
                    
                    gui.move(x, y, mark);
                    
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    gui.addMessage(response);
                    response = response.substring(15);
                    
                    StringTokenizer tmp = new StringTokenizer(response);
                    
                    int x = Integer.parseInt(tmp.nextToken());
                    int y = Integer.parseInt(tmp.nextToken());
                    
                    gui.move(x, y, opponentMark);
                    
                } else if (response.startsWith("MESSAGE")) {
                    
                    response = response.substring(8);
                    gui.addMessage(response);
                    
                } else if (response.startsWith("REMOVE")) {
                    response = response.substring(6);
                    
                    StringTokenizer tmp = new StringTokenizer(response);
                    
                    int x = Integer.parseInt(tmp.nextToken());
                    int y = Integer.parseInt(tmp.nextToken());
                    
                    gui.remove(x, y);
                
                } else if (response.startsWith("VICTORY")) {
                    gui.addMessage("You won!");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    gui.addMessage("You lost!");
                    break;
                } else if (response.startsWith("OTHER_PLAYER_LEFT")) {
                    gui.addMessage(response);
                    break;
                } else if (response.startsWith("OPPONENT PASSED") || response.startsWith("YOU PASSED")) {
                    gui.addMessage(response);
                } else if (response.startsWith("OPPONENT SURRENDERED")) {
                    gui.addMessage(response);
                    gui.addMessage("You won!");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
            gui.dispose();
        }
    }
    
    public static void main(String[] args) throws Exception {
        GameClient client = new GameClient();
    }
}