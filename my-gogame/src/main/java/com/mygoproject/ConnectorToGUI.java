package com.mygoproject;

public class ConnectorToGUI {

	//private Client parent;
	private GUI gui;
	
	public ConnectorToGUI(/*Client Parent*/) {
		//this.parent = parent;
		this.gui = new GUI(this);
	}
	
	//serer przypisuje kolor gracza
	public void startGameWithOtherPlayer(String color) {
		gui.startGameWithOtherPlayer(color);
	}
	
	//dodaje kamien, ktory dodal przeciwnik do gui
	public void addStoneToBoard(int x, int y) {
		gui.addStoneToBoard(x, y);
	}
	
	// wysyla do klienta wspolrzedne do dodania, klient wysyla do serwera, a serwer daje odpowiedz czy tam mozna wstawic kamien i sam wstawia ten kamien i powiadmia przeciwnika
	public boolean sendCoordinates(int x, int y, String color) {
		/*
		if(parent.getCoordinates(x, y, color){
			return true;
		} else {
			return false;
		}
		*/
		return false;
	}
	
	public void deleteStone(int x, int y) { //informuje o usunietych kamieniach
		gui.deleteStone(x, y);
	}
	
	public void sendRect(int x, int y, String color) { //infomuje ze zaznaczyles punkt terytorium 
		//parent.rect(x, y, color);
	}
	
	public void rect(int x, int y) { //informuje ze przeciwnik dodal punkt terytorium
		gui.rect(x, y);
	}
	
	public void sendPause() { //informuje, ze pauzujesz
		//parent.pause();
	}
	
	public void pause() { //informuje, ze przeciwnik pausowal
		gui.pause();
	}
	
	public void pausedGame() { //informuje ze gra zostala wstrzymana
		gui.pausedGame();
	}
	
	public void sendResume() { //informuje ze chcesz wznowic gre
		//parent.resume();
	}
	
	public void resume() { //informuje ze przeciwnik chce wznowic gre
		gui.resumedGame(true);
	}
	
	public void sendSurrender() { //informuje ze sie poddajesz
		//parent.surrender();
	}
	
	public void surrender() { //informuje ze przeciwnik sie poddal
		gui.surrender(false);
	}
	
	public void finish() { //zamyka program klienta
		//parent.finish();
	}
}
