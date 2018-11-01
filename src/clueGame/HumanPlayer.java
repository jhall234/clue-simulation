package clueGame;

import java.util.*;

public class HumanPlayer extends Player {
	public HumanPlayer() {
		
	}
	
	public HumanPlayer(String playerName, int red, int green, int blue, PlayerType playerType, int row, int column) {
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
