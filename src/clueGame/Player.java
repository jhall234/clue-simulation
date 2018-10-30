package clueGame;
import java.awt.Color;
import java.util.*;
public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	public Player() {
		
	}
	
	public Player(String playerName) {
		//this.playerName = playerName; 
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		Card card = new Card();
		return card;
	}
}
