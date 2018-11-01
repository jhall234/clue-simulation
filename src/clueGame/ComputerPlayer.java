package clueGame;

import java.util.*;

public class ComputerPlayer extends Player {
	public ComputerPlayer() {
		super();
	}
	
	public ComputerPlayer(String playerName, int red, int green, int blue, PlayerType playerType, int row, int column) {
		super(playerName, red, green, blue, playerType, row, column);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell board_cell = new BoardCell();
		return board_cell;
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() {
		
	}
}
