package clueGame;

import java.util.*;

public class ComputerPlayer extends Player {
	private BoardCell justVisitedRoom;
	private Solution suggestion;
	
	public ComputerPlayer() {
		super();
		this.suggestion = new Solution();
	}
	
	public ComputerPlayer(String playerName, int red, int green, int blue, PlayerType playerType, int row, int column) {
		super(playerName, red, green, blue, playerType, row, column);
		this.suggestion = new Solution();
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell board_cell = new BoardCell();
		return board_cell;
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() {		

	}
	
	public Solution getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(Solution suggestion) {
		this.suggestion = suggestion;
	}

	public void setJustVisitedRoom(BoardCell justVisitedRoom) {
		this.justVisitedRoom = justVisitedRoom;
	}
	
	public BoardCell getJustVisitedRoom() {
		return this.justVisitedRoom;
	}
}
