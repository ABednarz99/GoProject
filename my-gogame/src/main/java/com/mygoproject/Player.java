
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
    private String color;
    private Socket socket;
    private int boardSize;
    private int wantBot;
    private int hasOpponent;
    
    Manager gameManager;

    PrintWriter output;
    
    public Player(Socket socket, String color) {
        this.socket = socket;
        this.color = color;
    }
    
    public void run() {
        try {
            //input = new Scanner(socket.getInputStream());
            //output = new PrintWriter(socket.getOutputStream(), true);
        } catch () {
            
        }
    }
    
    public int getBoardSize() {
        return boardSize;
    }
    
    public int getWantBot() {
        return wantBot;
    }
    
    public int getHasOpponent() {
        return hasOpponent;
    }
    public void setBoardSize(int size) {
        this.boardSize = size;
    }
    
    public void setBotSetting(int wantBot) {
        this.wantBot = wantBot;
    }
    
    public void setGameManager(Manager gameManager) {
        this.gameManager = gameManager;
    }
}
