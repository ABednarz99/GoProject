package com.mygoproject;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;


public class Server {
    ServerSocket serverSocket = null;
    private final int serverPort = 7777;
    
    static int tempint = 0;
    
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static ExecutorService pool = Executors.newFixedThreadPool(512);
    
	public Server() {
		createSocket();
		run();
	}
	
	public void createSocket() {
	    try {
	        serverSocket = new ServerSocket(serverPort);
	    } catch (IOException e) {
	        System.out.println(e.getMessage());
	        System.exit(1);
	    }
	}
	
	public void run() {
	    while(true) {
	        try {
	            pool.execute(new PlayerCreator(serverSocket.accept()));

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	//// INNER CLASS PLAYERCREATOR
	
	private static class PlayerCreator implements Runnable {
	    private Socket socket;
	    
	    PlayerCreator(Socket socket) {
	        this.socket = socket;
	    }
	    
	    public void run() {
	        System.out.println("Creating new player in server");
	        try {
	            setup(socket);
	            Server.findOpponent();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private void setup(Socket socket) throws IOException {
	        
	        //int randomNum = ThreadLocalRandom.current().nextInt(0, 10 + 1);
	        Player newPlayer = null;
	        
	        if(tempint == 0) {
	            newPlayer = new Player(socket, "BLACK");
	            tempint = 1;
	        }
	        else if(tempint == 1) {
	            newPlayer = new Player(socket, "WHITE");
	            tempint = 0;
	        }
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        String clientSettings = in.readLine();
	        clientSettings = clientSettings.substring(11);
	        StringTokenizer tmp = new StringTokenizer(clientSettings);
	    
	        int boardSize = Integer.parseInt(tmp.nextToken());
	        int vsBot = Integer.parseInt(tmp.nextToken());
	        
	        newPlayer.setBoardSize(boardSize);
	        newPlayer.setBotSetting(vsBot);
	        Server.players.add(newPlayer);
	        
	        System.out.println("Player choice: " + boardSize + " " + vsBot);
	        pool.execute(newPlayer);
	    }
	}
	
	
	
	
	private static void findOpponent() throws IOException, InterruptedException {
	    int playerId9 = -1;
	    int playerId13 = -1;
	    int playerId19 = -1;
	    	    
	    synchronized(players) {
            for(int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                
                if(!player.getHasOpponent()) {
                    /*
                     *  Determine whether player wants to play with bot or against another player
                     */
                    if(player.getWantBot()) {
                        Manager gameManager = new Manager(player.getBoardSize());
                        player.setGameManager(gameManager);
                    } else {
                        int BoardSizeChoice = player.getBoardSize();
                        
                        if(BoardSizeChoice == 9) {
                            if(playerId9 >= 0 && !(players.get(playerId9).getColor().equals(player.getColor()))) {
                                createNewGame(player, BoardSizeChoice, playerId9);
                                playerId9 = -1;
                            } else {
                                playerId9 = i;
                            }
                        } else if(BoardSizeChoice == 13) {
                            if(playerId13 >= 0 && !(players.get(playerId13).getColor().equals(player.getColor()))) {
                                createNewGame(player, BoardSizeChoice, playerId13);
                                playerId13 = -1;
                            } else {
                                playerId13 = i;
                            }
                        } else {
                            if(playerId19 >= 0 && !(players.get(playerId19).getColor().equals(player.getColor()))) {
                                createNewGame(player, BoardSizeChoice, playerId19);
                                playerId19 = -1;
                            } else {
                                playerId19 = i;
                            }
                        }
                    }
                }
            }
        }
	}
	
	private static void createNewGame(Player player, int BoardSize, int opponent) throws IOException, InterruptedException {
	    Thread.sleep(1000);
        Manager gameManager = new Manager(BoardSize);
        Player playerOpponent = players.get(opponent);
        player.setGameManager(gameManager);
        playerOpponent.setGameManager(gameManager);
        
        player.output.println("CREATING");
        playerOpponent.output.println("CREATING");
        
        player.setHasOpponent(true);
        playerOpponent.setHasOpponent(true);

        PrintWriter output = new PrintWriter(player.socket.getOutputStream(), true);
        PrintWriter output2 = new PrintWriter(playerOpponent.socket.getOutputStream(), true);
    
        player.opponent = playerOpponent;
        playerOpponent.opponent = player;

        if (player.getColor().equals("BLACK")) {
            gameManager.setCurrentPlayer(player);
            output.println("MESSAGE connected with WHITE opponent");
            output2.println("MESSAGE connected with BLACK opponent");
            output.println("MESSAGE Your move");
        } else if (playerOpponent.getColor().equals("BLACK")) {
            gameManager.setCurrentPlayer(playerOpponent);
            output2.println("MESSAGE connected with WHITE opponent");
            output.println("MESSAGE connected with BLACK opponent");
            output2.println("MESSAGE Your move");
        }
    }
	
    public static void main(String[] args)
    {
    	Server server = new Server();
    }
}