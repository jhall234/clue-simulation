package clueGame;

import java.util.*;

public class ComputerPlayer extends Player {
	private BoardCell justVisitedRoom;
	
	public ComputerPlayer() {
		
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell board_cell = new BoardCell();
		return board_cell;
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() {
		
	}
	
	public void setJustVisitedRoom(BoardCell justVisitedRoom) {
		this.justVisitedRoom = justVisitedRoom;
	}
	
	public BoardCell getJustVisitedRoom() {
		return this.justVisitedRoom;
	}
}
