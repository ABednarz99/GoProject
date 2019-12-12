package com.mygoproject;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    ServerSocket serverSocket = null;
    private final int serverPort = 7777;
    
    private ArrayList<Player> players = new ArrayList<Player>();
    private ExecutorService pool = Executors.newFixedThreadPool(512);
    
	public Server() {
		//Manager manager = new Manager(size);
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
	            //Socket socket = serverSocket.accept();
	            pool.execute(new PlayerCreator(serverSocket.accept()));
	            
	            //findOpponent();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	//// KLASA WEWNETRZNA PLAYERCREATOR
	
	private static class PlayerCreator implements Runnable {
	    private Socket socket;
	    
	    PlayerCreator(Socket socket) {
	        this.socket = socket;
	    }
	    
	    @Override
	    public void run() {
	        System.out.println("Creating new player in server");
	        try {
	            setup(socket);
	            Server.this.findOpponent();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private void setup(Socket socket) {
	        Player newPlayer = new Player(socket);
	        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        String clientSettings = in.readLine();
	        StringTokenizer tmp = new StringTokenizer(clientSettings);
	    
	        int boardSize = Integer.parseInt(tmp.nextToken());
	        int vsBot = Integer.parseInt(tmp.nextToken());
	        
	        newPlayer.setBoardSize(boardSize);
	        newPlayer.setBotSetting(vsBot);
	        Server.this.players.add(newPlayer);
	    }
	}
	
	
	
	
	private void findOpponent() {
	    int playerId9 = -1;
	    int playerId13 = -1;
	    int playerId19 = -1;
	    
	    synchronized(this.players) {
	        for(int i = 0; i < players.size(); i++) {
	            Player player = players.get(i);
	            if(!player.getHasOpponent()) {
	                /*
	                 *  If player wants to play with bot
	                 */
	                if(player.getWantBot()) {
	                    Manager gameManager = new Manager(player.getBoardSize());
	                    player.setGameManager(gameManager);
	                }
	                /*
	                 *  If player want to play against another player
	                 */
	                else {
	                    int BoardSizeChoice = player.getBoardSize();
	                    if(BoardSizeChoice == 9) {
	                        if(playerId9 > 0) {
	                            //TODO: create new game with player "playerId9" and "players.get(i)"
	                            
	                            // reset
	                            playerId9 = -1;
	                        }
	                        else {
	                            playerId9 = i;
	                        }
	                    }
	                    else if(BoardSizeChoice == 13) {
	                        if(playerId13 > 0) {
                                //TODO: create new game with player "playerId13" and "players.get(i)"
	                            
	                            // reset
	                            playerId13 = -1;
                            }
                            else {
                                playerId13 = i;
                            }
	                    }
	                    else {
                            if(playerId19 > 0) {
                                //TODO: create new game with player "playerId19" and "players.get(i)"
                                
                                // reset
                                playerId19 = -1;
                            }
                            else {
                                playerId19 = i;
                            }
                        }
	                    
	                }
	            }
	        }
	    }
	}
	
    public static void main(String[] args)
    {
    	Server server = new Server();
    }
}
