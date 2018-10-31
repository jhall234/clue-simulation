package clueGame;

import java.awt.Color;
import java.util.*;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private PlayerType playerType;
	private ArrayList<Card> myCards;
	private ArrayList<Card> seenCards;
	
	/**
	 * Default constructor for Player
	 */
	public Player() {
		this.myCards = new ArrayList<>();
		this.seenCards = new ArrayList<>();
	}
	
	/**
	 * Constructor will initialize all variables with the given paramaters 
	 * @param playerName name of player
	 * @param red Red color value 
	 * @param green Green color value
	 * @param blue Blue color value
	 * @param is_human either "human" or "computer"
	 * @param row starting row of player
	 * @param column starting column of player 
	 */
	public Player(String playerName, int red, int green, int blue, PlayerType playerType, int row, int column) {
		this();
		this.playerName = playerName;
		this.color = new Color(red, green, blue);
		this.playerType = playerType;
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Will try to disprove another players suggestion by returning a Card which matches part of the suggestion
	 * @param suggestion
	 * @return Card 
	 */
	public Card disproveSuggestion(Solution suggestion) {
		Card card = new Card();
		return card;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getPlayerName() {
		return playerName;
	}

	public Color getColor() {
		return color;
	}

	/**
	 * Getter for the list of the players own cards
	 * @return ArrayList
	 */
	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	/**
	 * Will set the myCards variable to the ArrayList that is passed
	 * @param myCards
	 */
	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}
	
	/**
	 * Getter for the list of cards seen by the player
	 * @return
	 */
	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}
	
	/**
	 * Setter for the seenCards variable 
	 * @param seenCards
	 */
	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}


}
